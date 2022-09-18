package com.kgstrivers.myapplication.RoomDatabase

import android.content.Context
import androidx.room.*
import com.kgstrivers.myapplication.Models.RoomData

@Database(entities = [RoomData::class],version=2, exportSchema = true)

abstract class ResponsDatabase:RoomDatabase() {

    abstract fun responseDao():RoomDAO
    companion object{
        private var instance : ResponsDatabase? = null

        fun getDataBase(context:Context) :ResponsDatabase{
            if(instance == null)
            {
                instance = Room.databaseBuilder(
                    context,ResponsDatabase::class.java,"res"
                ).build()
            }

            return instance!!
        }
    }
}