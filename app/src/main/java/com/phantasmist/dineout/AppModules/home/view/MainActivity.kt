package com.phantasmist.dineout.AppModules.home.view

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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.phantasmist.dineout.AppModules.FoodOutletDetails.view.FoodOutletDetailsActivity
import com.phantasmist.dineout.AppModules.home.adapters.FoodOutletsRecAdapter
import com.phantasmist.dineout.AppModules.home.datamodel.FoodOutletItem
import com.phantasmist.dineout.AppModules.home.presenter.MainActiPresenterImpl
import com.phantasmist.dineout.AppModules.home.presenter.MainActivityContract
import com.phantasmist.dineout.AppModules.home.viewmodel.MainActivityViewModel
import com.phantasmist.dineout.R
import com.phantasmist.dineout.Utils.RecyclerItemClickListener
import com.phantasmist.dineout.base.BaseLifecycleAwareObserver
import com.phantasmist.dineout.base.ItemClickEventListener
import com.phantasmist.dineout.base.ViewModelFactory
import com.phantasmist.dineout.cache.FoodOutletCacheImpl
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainActivityContract.MAView, OnMapReadyCallback, RecyclerItemClickListener.OnItemClickListener, ItemClickEventListener<FoodOutletItem> {


    @Inject
    lateinit var presenter: MainActiPresenterImpl

    @Inject
    lateinit var cacheImpl: FoodOutletCacheImpl

    @Inject
    lateinit var foodOutletsRecAdapter: FoodOutletsRecAdapter

    @Inject
    lateinit var viewModeFactory: ViewModelFactory

    val fueledNewYorkOffice = LatLng(40.719981, -74.0022634)


    @Inject
    lateinit var mainViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        presenter.attach(this)
        presenter.attachCacheImplObject(cacheImpl)

        lifecycle.addObserver(BaseLifecycleAwareObserver(presenter))

        //setup viewmodel
        mainViewModel = ViewModelProviders.of(this, this.viewModeFactory).get(mainViewModel::class.java)

        //Setup Map
        val foodOutletMapFragment = supportFragmentManager.findFragmentById(R.id.foodOutletMap) as SupportMapFragment
        foodOutletMapFragment.getMapAsync(this)
        //Setup Rec View
        nearestFoodOutletRecView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        nearestFoodOutletRecView.adapter = foodOutletsRecAdapter
        nearestFoodOutletRecView.addOnItemTouchListener(RecyclerItemClickListener(this, this))
        foodOutletsRecAdapter.itemClickListener = this


    }


    override fun onDataLoaded(items: List<FoodOutletItem>, isFromCache: Boolean) {
        if (mainViewModel.allItems.value!!.isNotEmpty()) {
            mainViewModel.allItems.value?.clear()
            mainViewModel.allItems.value?.addAll(items)
            foodOutletsRecAdapter.notifyDataSetChanged()
        } else {
            mainViewModel.allItems.value?.addAll(items)
            foodOutletsRecAdapter.addFoodOutlets(mainViewModel.allItems.value!!)
            addMarkersToMap(mainViewModel.allItems.value!!)
        }

    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onItemInsideRecViewClicked(data: FoodOutletItem, position: Int) {
        var intent = Intent(this, FoodOutletDetailsActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }


    override fun onItemClick(rv: RecyclerView, view: View, position: Int) {
        mainViewModel.mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(mainViewModel.allMarkers.get(position).position, 18f, 0f, 0f)))
        mainViewModel.allMarkers.get(position).showInfoWindow()
    }

    private fun addMarkersToMap(allItems: ArrayList<FoodOutletItem>) {
        allItems.forEachIndexed { index, it ->
            //            Log.e("LATITIUDE", it.lat.toString().plus(", ").plus(it.lng.toString()))
            val latLng = LatLng(it.lat!!, it.lng!!)
            var markerOptions = generateMarker(latLng, index + 1, it.name)
            var marker = mainViewModel.mGoogleMap.addMarker(markerOptions)
            marker.tag = it
            mainViewModel.allMarkers.add(marker)
        }


    }

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

    override fun onErrorFromService(localizedMessage: String) {
        Log.e("ERROR", localizedMessage)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mainViewModel.mGoogleMap = p0!!
        mainViewModel.mGoogleMap.addMarker(MarkerOptions().position(fueledNewYorkOffice).title("Fueled New York Office"))
        var position = CameraPosition(fueledNewYorkOffice, 18F, 0F, 0F)
        mainViewModel.mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))
        if (mainViewModel.allItems.value!!.isEmpty()) {
            presenter.loadDataFromApi()
        } else {
            foodOutletsRecAdapter.refreshFoodOutlets(mainViewModel.allItems.value!!)
            addMarkersToMap(mainViewModel.allItems.value!!)
        }

    }


}
