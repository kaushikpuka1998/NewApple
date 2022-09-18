package com.kgstrivers.myapplication.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import org.json.JSONObject

@Entity(tableName = "resp")
data class RoomData(
    @ColumnInfo(name="facility_id") val facility_id:Int,
    @ColumnInfo(name="name") val name:String,
    @ColumnInfo(name="icon") val icon:String,
    @ColumnInfo(name="facility_name") val facility_name:String,
    @ColumnInfo(name="id") val id:Int





){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}


