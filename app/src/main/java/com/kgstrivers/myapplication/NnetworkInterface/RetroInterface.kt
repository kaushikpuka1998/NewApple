package com.kgstrivers.myapplication.NnetworkInterface

import com.kgstrivers.myapplication.Models.AddsData
import retrofit2.Call
import retrofit2.http.GET

interface RetroInterface {

    @GET("ad-assignment/db")
    fun getAllProducts() : Call<AddsData>
}