package com.kgstrivers.myapplication.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kgstrivers.myapplication.Models.*
import com.kgstrivers.myapplication.NnetworkInterface.RetroInterface
import com.kgstrivers.myapplication.RetroInstance.RetroFitnstance
import com.kgstrivers.myapplication.RoomDatabase.ResponsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import java.util.*


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
                    val facility_name = Facility.name
                    Facility.options.forEach{
                        Option->
                         Log.d("PPP",h+" "+Option.name+"   "+Option.icon)
                         val k = RoomData(h.toInt(),Option.name,Option.icon, facility_name,Option.id)
                        responseDatabase.responseDao().insertData(k)
                    }
                }


                Log.d(TAG, response.body()!!.toString())
                listdata.postValue(response.body()!!)

                //getDataFromLocal(responseDatabase)
            }
        }
    }

    private fun getDataFromLocal(responseDatabase:  ResponsDatabase)
    {

        GlobalScope.launch(Dispatchers.IO) {
            val getData  =responseDatabase.responseDao().getdata()

            var tmp = 1
            var rty= -99
            var b  = ArrayList<Facility>()
            var options =  ArrayList<Opn>()
            getData.forEach{
                data->

                if(data.facility_id == tmp)
                {
                    val v = Opn(data.name,data.icon,data.id)
                    options.add(v)

                }else{
                    var tb = Facility(data.facility_id.toString(),data.facility_name,options)
                    b.add(tb)
                    rty = tmp
                    val v = Opn(data.name,data.icon,data.id)
                    options.clear()
                    options.add(v)
                    tmp = data.facility_id
                }
            }

            Log.d("UNDER LOCAL",b.toString())
            var myList: ArrayList<List<Exclusion>> = arrayListOf()
            val h = AddsData(b as List<Facility>,myList as List<List<Exclusion>>)
            listdata.postValue(h)
        }

    }
}