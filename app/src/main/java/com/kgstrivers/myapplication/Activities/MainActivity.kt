package com.kgstrivers.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.HeterogeneousExpandableList
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgstrivers.myapplication.Adapters.CustomExpandableListAdapter
import com.kgstrivers.myapplication.Models.AddsData
import com.kgstrivers.myapplication.Models.Facility
import com.kgstrivers.myapplication.Models.Opn
import com.kgstrivers.myapplication.R
import com.kgstrivers.myapplication.ViewModels.MainPageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  mainPageViewModel:MainPageViewModel
    lateinit var facilitylist: ArrayList<Facility>

    lateinit var  childList: List<Opn>

    //lateinit var map :HashMap<String,ArrayList<String>>

    val TAG = "MainActivity"


    lateinit var expandableListAdapter: ExpandableListAdapter
    lateinit var  customExpandableListAdapter: CustomExpandableListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        facilitylist = ArrayList<Facility>()
        initiateViewmodel()
        calldata()

        //val p  = printmapValue(map)

        //Log.d(TAG,p.toString())

    }

    private fun calldata()
    {
        mainPageViewModel.getProductList()
    }

    private fun initiateViewmodel()
    {


        mainPageViewModel =  ViewModelProvider(this).get(MainPageViewModel::class.java)
        mainPageViewModel.getAddsLiveDataobserver().observe(this, Observer<AddsData> {

            var mp = HashMap<String,List<String>>()
            var v = ArrayList<String>();
            if(it!=null)
            {
                for(data in it.facilities)
                {
                    facilitylist.add(data)
                    v.add(data.name)
                    var tmp =  ArrayList<String>()
                    for( df in data.options)
                    {
                        tmp.add(df.name)
                    }
                    mp.put(data.name,tmp as List<String>);


                    Log.d("GGGG", data.name)
                }

                customExpandableListAdapter = CustomExpandableListAdapter(this,v,mp)

                extendedoption!!.setAdapter(customExpandableListAdapter)

                extendedoption!!.setOnGroupExpandListener { groupPosition ->
                    Toast.makeText(
                        applicationContext,
                        (v as ArrayList<String>)[groupPosition] + " List Expanded.",
                        Toast.LENGTH_SHORT
                    ).show()}
                printmapValue(mp)
//                homeadapter.productslist = it.products.toMutableList()
//                dialog.dismiss()
//                homeadapter.notifyDataSetChanged()
            }
            else
            {
                Toast.makeText(this,"No Result",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun printmapValue(map:HashMap<String,List<String>>)
    {
        System.out.println("HHHHH=>"+map.toString())
    }
}