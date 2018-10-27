package com.phantasmist.dineout.AppModules.Home.adapters

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.Base.ItemClickEventListener
import com.phantasmist.dineout.R
import com.phantasmist.dineout.databinding.ItemFoodOutletBinding
import javax.inject.Inject

class FoodOutletsRecAdapter @Inject constructor(val context: Context) : RecyclerView.Adapter<FoodOutletsRecAdapter.FoodOutletHolder>() {
    var foodOutlets: MutableLiveData<ArrayList<FoodOutletItem>> = MutableLiveData()
    lateinit var itemClickListener: ItemClickEventListener<FoodOutletItem>

    init {
        foodOutlets.value = ArrayList()
    }


    fun addFoodOutlets(foodOutlet: ArrayList<FoodOutletItem>) {
        foodOutlets.value = foodOutlet
        notifyDataSetChanged()
    }

    fun refreshFoodOutlets(foodOutlet: ArrayList<FoodOutletItem>) {
        if (foodOutlets.value!!.isNotEmpty()) {
            foodOutlets.value!!.clear()
        }
        foodOutlets.value?.addAll(foodOutlet)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FoodOutletHolder {
        val itemFoodOutletBinding: ItemFoodOutletBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_food_outlet, parent, false)
        return FoodOutletHolder(itemFoodOutletBinding)
    }

    override fun getItemCount(): Int {
        return foodOutlets.value!!.size
    }

    override fun onBindViewHolder(holder: FoodOutletHolder?, position: Int) {
        holder?.bindData(foodOutlets.value!!.get(position), position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class FoodOutletHolder constructor(val view: ItemFoodOutletBinding) : RecyclerView.ViewHolder(view.root) {
        var viewDetailsButton: Button

        init {
            viewDetailsButton = view.root.findViewById(R.id.viewDetailsButton)
        }

        fun bindData(foodOutletItem: FoodOutletItem, position: Int) {
            view.foodOutlet = foodOutletItem
            view.rank = position + 1
            viewDetailsButton.setOnClickListener {
                itemClickListener.onItemInsideRecViewClicked(foodOutletItem, position)
            }
        }

    }


}