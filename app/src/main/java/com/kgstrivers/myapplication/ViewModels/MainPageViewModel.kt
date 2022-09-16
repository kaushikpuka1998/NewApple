package com.kgstrivers.myapplication.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kgstrivers.myapplication.Models.AddsData
import com.kgstrivers.myapplication.RetroInstance.RetroFitnstance
import com.kgstrivers.myapplication.NnetworkInterface.RetroInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*


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




        GlobalScope.launch (Dispatchers.IO){

            val response = retrofitinstance.getAllProducts().awaitResponse()

            if(response.isSuccessful)
            {
                listdata.postValue(response.body()!!)
            }



        }
    }
}