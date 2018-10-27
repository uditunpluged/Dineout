package com.phantasmist.dineout.Base

/**
 * INTERFACE FOR DELEGATING EVENTS TO VIEW(ACTIVITY,FRAGMENT) WHEN USE CLICKS ON SPECIFIC UI COMPONENT
 * INSIDE A RECYCLER/LIST VIEW ITEM FOR EX: VIEW DETAILS BUTTON INSIDE LIST ITEM
 * */
interface ItemClickEventListener<in T> {
    fun onItemInsideRecViewClicked(data: T, position: Int)
}