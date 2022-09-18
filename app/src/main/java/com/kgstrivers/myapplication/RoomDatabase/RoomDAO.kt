package com.kgstrivers.myapplication.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kgstrivers.myapplication.Models.RoomData

@Dao
interface RoomDAO {
    @Insert
    suspend fun insertData(data: RoomData)

    @Query("SELECT * FROM resp")
    suspend fun getdata() :List<RoomData>
}