package com.kgstrivers.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kgstrivers.myapplication.Adapters.CustomExpandableListAdapter
import com.kgstrivers.myapplication.Models.AddsData
import com.kgstrivers.myapplication.Models.Facility
import com.kgstrivers.myapplication.Models.Opn
import com.kgstrivers.myapplication.R
import com.kgstrivers.myapplication.RoomDatabase.ResponsDatabase
import com.kgstrivers.myapplication.ViewModels.MainPageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  mainPageViewModel:MainPageViewModel
    lateinit var facilitylist: ArrayList<Facility>
    val TAG = "MainActivity"

    lateinit var responseDatabase : ResponsDatabase


    lateinit var expandableListAdapter: ExpandableListAdapter
    lateinit var  customExpandableListAdapter: CustomExpandableListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        responseDatabase = ResponsDatabase.getDataBase(applicationContext)


        facilitylist = ArrayList<Facility>()
        initiateViewmodel()
        calldata()

        notifybutton.setOnClickListener {
            sendNotification()
        }

        //val p  = printmapValue(map)

        //Log.d(TAG,p.toString())

    }

    private fun calldata()
    {
        mainPageViewModel.getProductList(responseDatabase)
    }

    private fun initiateViewmodel()
    {
        mainPageViewModel =  ViewModelProvider(this).get(MainPageViewModel::class.java)
        mainPageViewModel.getAddsLiveDataobserver().observe(this, Observer<AddsData> {

            var mp = HashMap<String,List<Opn>>()
            var v = ArrayList<String>();
            if(it!=null)
            {
                for(data in it.facilities)
                {
                    facilitylist.add(data)
                    v.add(data.name)
                    var tmp =  ArrayList<Opn>()
                    for( df in data.options)
                    {
                        tmp.add(df)
                    }
                    mp.put(data.name,tmp as List<Opn>);
                    Log.d(TAG, data.name)
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
            }
            else
            {
                Toast.makeText(this,"No Result",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun printmapValue(map:HashMap<String,List<Opn>>)
    {
        System.out.println("HHHHH=>"+map.toString())
    }

    private fun sendNotification()
    {
        val intent = Intent(applicationContext,NotificationActivity::class.java)
        startActivity(intent)
    }
}