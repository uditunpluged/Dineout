package com.phantasmist.dineout.base

interface ItemClickEventListener<in T> {
    fun onItemInsideRecViewClicked(data: T, position: Int)
}