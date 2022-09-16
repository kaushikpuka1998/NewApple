package com.kgstrivers.myapplication.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kgstrivers.myapplication.Models.AddsData
import com.kgstrivers.myapplication.RetroInstance.RetroFitnstance
import com.kgstrivers.myapplication.NnetworkInterface.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainPageViewModel:ViewModel(){

    lateinit var listdata : MutableLiveData<AddsData>

    init{
        listdata = MutableLiveData()
    }


    fun getAddsLiveDataobserver():MutableLiveData<AddsData>{
        return listdata
    }

    fun getProductList()
    {
        makeretrofit()
    }

    private fun makeretrofit() {
        val retrofitinstance = RetroFitnstance.getretroInstance().create(RetroInterface::class.java)

        val allproduct = retrofitinstance.getAllProducts()

        allproduct.enqueue( object: Callback<AddsData> {
            override fun onResponse(call: Call<AddsData>, response: Response<AddsData>) {
                val resbody = response.body()!!


                listdata.postValue(resbody)
            }

            override fun onFailure(call: Call<AddsData>, t: Throwable) {

                System.out.println("Failue");
            }
        })
    }
}