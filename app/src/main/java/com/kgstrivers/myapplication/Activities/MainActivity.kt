package com.kgstrivers.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgstrivers.myapplication.Models.AddsData
import com.kgstrivers.myapplication.R
import com.kgstrivers.myapplication.ViewModels.MainPageViewModel

class MainActivity : AppCompatActivity() {

    lateinit var  mainPageViewModel:MainPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateViewmodel()
        calldata()

    }

    private fun calldata()
    {
        mainPageViewModel.getProductList()
    }

    private fun initiateViewmodel()
    {
        mainPageViewModel =  ViewModelProvider(this).get(MainPageViewModel::class.java)
        mainPageViewModel.getAddsLiveDataobserver().observe(this, Observer<AddsData> {
            if(it!=null)
            {
                for(data in it.facilities)
                {
                    Log.d("GGGG", data.name)
                }
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
}