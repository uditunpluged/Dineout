package com.phantasmist.dineout.AppModules.Home.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.phantasmist.dineout.AppModules.FoodOutletDetails.view.FoodOutletDetailsActivity
import com.phantasmist.dineout.AppModules.Home.adapters.FoodOutletsRecAdapter
import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.AppModules.Home.presenter.HomeActiPresenterImpl
import com.phantasmist.dineout.AppModules.Home.presenter.HomeActivityContract
import com.phantasmist.dineout.AppModules.Home.viewmodel.HomeActivityViewModel
import com.phantasmist.dineout.Base.BaseLifecycleAwareObserver
import com.phantasmist.dineout.Base.ItemClickEventListener
import com.phantasmist.dineout.Base.ViewModelFactory
import com.phantasmist.dineout.Cache.FoodOutletCacheImpl
import com.phantasmist.dineout.R
import com.phantasmist.dineout.Utils.RecyclerItemClickListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * This class is the V from MVP architecture. V can be any activity / fragment
 * */
class HomeActivity : AppCompatActivity(), HomeActivityContract.MAView, OnMapReadyCallback, RecyclerItemClickListener.OnItemClickListener, ItemClickEventListener<FoodOutletItem> {


    @Inject
    lateinit var presenter: HomeActiPresenterImpl

    @Inject
    lateinit var cacheImpl: FoodOutletCacheImpl

    @Inject
    lateinit var foodOutletsRecAdapter: FoodOutletsRecAdapter

    @Inject
    lateinit var viewModeFactory: ViewModelFactory

    val fueledNewYorkOffice = LatLng(40.719981, -74.0022634)


    @Inject
    lateinit var homeViewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        /**
         * SETUP PRESENTER
         * */
        presenter.attach(this)
        presenter.attachCacheImplObject(cacheImpl)
        /**
         * ADD LIFECYCLE OBSERVER
         * */
        lifecycle.addObserver(BaseLifecycleAwareObserver(presenter))
        /**
         * SETUP VIEWMODEL
         * */
        homeViewModel = ViewModelProviders.of(this, this.viewModeFactory).get(homeViewModel::class.java)

        /**
         * SETUP MAP
         * */
        val foodOutletMapFragment = supportFragmentManager.findFragmentById(R.id.foodOutletMap) as SupportMapFragment
        foodOutletMapFragment.getMapAsync(this)
        /**
         * SETUP RECVIEW
         * */
        nearestFoodOutletRecView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        nearestFoodOutletRecView.adapter = foodOutletsRecAdapter
        nearestFoodOutletRecView.addOnItemTouchListener(RecyclerItemClickListener(this, this))
        foodOutletsRecAdapter.itemClickListener = this


    }

    /**
     * ANY CHANGES TO DATA - UPDATE UI ACCORDINGLY
     * */
    override fun onDataLoaded(items: List<FoodOutletItem>, isFromCache: Boolean) {
        Log.e("DATACHANGEd",isFromCache.toString())
        if (isFromCache.not()){
            //load data from database
            presenter.loadDataFromApi()
        }else {
            if (homeViewModel.allItems.value!!.isNotEmpty()) {
                homeViewModel.allItems.value?.clear()
                homeViewModel.allItems.value?.addAll(items)
                foodOutletsRecAdapter.notifyDataSetChanged()
            } else {
                homeViewModel.allItems.value?.addAll(items)
                foodOutletsRecAdapter.addFoodOutlets(homeViewModel.allItems.value!!)
                addMarkersToMap(homeViewModel.allItems.value!!)
            }
        }

    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    /**
     * VIEW DETAILS BUTTON INSIDE RECVIEW ITEM
     * */
    override fun onItemInsideRecViewClicked(data: FoodOutletItem, position: Int) {
        var intent = Intent(this, FoodOutletDetailsActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }


    /**
     * ON REC VIEW ITEM CLICKED
     * */
    override fun onItemClick(rv: RecyclerView, view: View, position: Int) {
        homeViewModel.mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(homeViewModel.allMarkers.get(position).position, 18f, 0f, 0f)))
        homeViewModel.allMarkers.get(position).showInfoWindow()
    }


    /**
     * ADDS FOODOUTLE MARKERS TO MAP
     * */
    private fun addMarkersToMap(allItems: ArrayList<FoodOutletItem>) {
        allItems.forEachIndexed { index, it ->
            //            Log.e("LATITIUDE", it.lat.toString().plus(", ").plus(it.lng.toString()))
            val latLng = LatLng(it.lat!!, it.lng!!)
            var markerOptions = generateMarker(latLng, index + 1, it.name)
            var marker = homeViewModel.mGoogleMap.addMarker(markerOptions)
            marker.tag = it
            homeViewModel.allMarkers.add(marker)
        }
    }

    /**
     * GENERATE A CUSTOM MARKER FOR GOOGLE MAP
     * */
    fun generateMarker(coordinates: LatLng, count: Int, name: String): MarkerOptions {
        var markerOptions = MarkerOptions()
        markerOptions.position(coordinates)
        markerOptions.title(name)
        var px = resources.getDimensionPixelSize(R.dimen._30dp)
        var markerView: View = LayoutInflater.from(this).inflate(R.layout.map_marker_layout, null)
        markerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        markerView.layout(0, 0, px, px)
        markerView.buildDrawingCache()
        var mMarkerBitmap = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(mMarkerBitmap)
        var foodOutletMarkerTV: TextView = markerView.findViewById(R.id.foodOutletMarkerTV)
        foodOutletMarkerTV.text = count.toString()
        markerView.draw(canvas)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mMarkerBitmap))
        return markerOptions

    }

    /**
     * LOG ANY ERRORS
     * */
    override fun onErrorFromService(localizedMessage: String) {
        Log.e("ERROR", localizedMessage)
        Toast.makeText(this,localizedMessage, Toast.LENGTH_LONG).show()
    }

    /**
     * ONMAPREADY FUNCTION FROM ONMAPREADYCALLBACK
     * */
    override fun onMapReady(p0: GoogleMap?) {
        /**
         * ADD FUELED OFFICE MARKER
         * */
        homeViewModel.mGoogleMap = p0!!
        homeViewModel.mGoogleMap.addMarker(MarkerOptions().position(fueledNewYorkOffice).title("Fueled New York Office"))
        var position = CameraPosition(fueledNewYorkOffice, 18F, 0F, 0F)
        homeViewModel.mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        /**
         * CHECK WHETHER TO LOAD DATA FROM CACHE/REMOTE OR LOAD IT FROM VIEWMODEL IF IT ALLREADY EXISTS
         * */
        if (homeViewModel.allItems.value!!.isEmpty()) {
            presenter.loadDataFromApi()
        } else {
            foodOutletsRecAdapter.refreshFoodOutlets(homeViewModel.allItems.value!!)
            addMarkersToMap(homeViewModel.allItems.value!!)
        }

    }


}
