package com.kgstrivers.myapplication.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.kgstrivers.myapplication.Models.AddsData
import com.kgstrivers.myapplication.Models.Facility
import com.kgstrivers.myapplication.Models.RoomData
import com.kgstrivers.myapplication.RetroInstance.RetroFitnstance
import com.kgstrivers.myapplication.NnetworkInterface.RetroInterface
import com.kgstrivers.myapplication.RoomDatabase.ResponsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*



class MainPageViewModel:ViewModel(){

    lateinit var listdata : MutableLiveData<AddsData>


    val TAG = "MainPageViewModel"
    init{
        listdata = MutableLiveData()

    }


    fun getAddsLiveDataobserver():MutableLiveData<AddsData>{
        return listdata
    }

    fun getProductList(responseDatabase:  ResponsDatabase)
    {
        makeretrofit(responseDatabase)
    }

    private fun makeretrofit(responseDatabase:  ResponsDatabase) {

        val retrofitinstance = RetroFitnstance.getretroInstance().create(RetroInterface::class.java)
        GlobalScope.launch (Dispatchers.IO){

            val response = retrofitinstance.getAllProducts().awaitResponse()

            if(response.isSuccessful)
            {
                response.body()!!.facilities.forEach { Facility ->

                    val h = Facility.facility_id
                    Facility.options.forEach{
                        Option->
                         Log.d("PPP",h+" "+Option.name+"   "+Option.icon)
                         val k = RoomData(h.toInt(),Option.name,Option.icon)
                        responseDatabase.responseDao().insertData(k)
                    }
                }


                Log.d(TAG, response.body()!!.toString())
                listdata.postValue(response.body()!!)
            }
        }
    }
}