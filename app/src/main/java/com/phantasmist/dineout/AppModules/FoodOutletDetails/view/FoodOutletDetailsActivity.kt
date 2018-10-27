package com.phantasmist.dineout.AppModules.FoodOutletDetails.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.phantasmist.dineout.AppModules.FoodOutletDetails.datamodel.FoodOutletDetailsDataModel
import com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter.FoodOutletDetailActContract
import com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter.FoodOutletDetailActPresenterImpl
import com.phantasmist.dineout.AppModules.FoodOutletDetails.viewmodel.FoodOutletDetailViewModel
import com.phantasmist.dineout.AppModules.home.datamodel.FoodOutletItem
import com.phantasmist.dineout.R
import com.phantasmist.dineout.base.ViewModelFactory
import com.phantasmist.dineout.cache.FoodOutletCacheImpl
import com.phantasmist.dineout.databinding.ActivityFoodOutletDetailsBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_food_outlet_details.*
import javax.inject.Inject

class FoodOutletDetailsActivity : AppCompatActivity(), FoodOutletDetailActContract.FODAView {

    @Inject
    lateinit var presenter: FoodOutletDetailActPresenterImpl

    @Inject
    lateinit var cacheImpl: FoodOutletCacheImpl

    lateinit var mActivityFoodOutletDetailsBinding: ActivityFoodOutletDetailsBinding

    @Inject
    lateinit var viewModeFactory: ViewModelFactory


    @Inject
    lateinit var fodViewModel: FoodOutletDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityFoodOutletDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_food_outlet_details)
        AndroidInjection.inject(this)
        //SETUP TOOLBAR
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent))
        collapsing_toolbar.setStatusBarScrimColor(ContextCompat.getColor(this, R.color.colorAccent))

        //SETUP PRESENTER
        presenter.attach(this)
        presenter.attachCacheImplObject(cacheImpl)

        //GET DATA FROM INTENT
        var data: FoodOutletItem = intent.extras.get("data") as FoodOutletItem

        //SETUP VMODEL
        fodViewModel = ViewModelProviders.of(this, this.viewModeFactory).get(fodViewModel::class.java)

        //BIND DATA TO VIEW MODEL
        fodViewModel.initFoodOutletBasicDetails(data)

        //BIND DATA TO VIEWS from VIEWMODEL OBJECT
        mActivityFoodOutletDetailsBinding.basicData = fodViewModel.foodOutletBasicDetails.value
        mActivityFoodOutletDetailsBinding.outletData = FoodOutletDetailsDataModel("", "", "", "", "", "")
        collapsing_toolbar.title = fodViewModel.foodOutletBasicDetails.value?.name

        //LOAD REMAINING DAT FROM DETAILS API
        if (fodViewModel.apiCallHasBeenMade.value!!.not()) {
            presenter.loadDataFromVenueDetailsApi(data.outletId)
        } else {
            mActivityFoodOutletDetailsBinding.outletData = fodViewModel.foodOutletExtraDetails.value
        }

        dislikeButton.setOnClickListener {
//            if (fodViewModel.foodOutletBasicDetails.value!!.disliked) {
//                //UN DISLIKE ON CLICK
//                fodViewModel.foodOutletBasicDetails.value!!.disliked = false
//                presenter.dislikeFoodOutlet(data.outletId, false)
//            } else {
//                fodViewModel.foodOutletBasicDetails.value!!.disliked = true
//                presenter.dislikeFoodOutlet(data.outletId, true)
//            }
            fodViewModel.foodOutletBasicDetails.value!!.disliked = fodViewModel.foodOutletBasicDetails.value!!.disliked.not()
            presenter.dislikeFoodOutlet(data.outletId, fodViewModel.foodOutletBasicDetails.value!!.disliked)
            mActivityFoodOutletDetailsBinding.basicData = fodViewModel.foodOutletBasicDetails.value
        }
    }

    override fun onDataLoadedFromService(mapFromModel: FoodOutletDetailsDataModel) {
        fodViewModel.apiCallHasBeenMade.value = true
        fodViewModel.initFoodOutletExtraDetails(mapFromModel)
        mActivityFoodOutletDetailsBinding.outletData = fodViewModel.foodOutletExtraDetails.value
    }

    override fun onErrorFromService(localizedMessage: String) {
        Log.e("ERROR", localizedMessage)
    }

    override fun showProgress() {
        detailsProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        detailsProgressBar.visibility = View.GONE
    }


}