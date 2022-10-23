package com.h_alqatawi.mansourepoultrycompany.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "customer_table")
data class Customers(
    @PrimaryKey(autoGenerate = true)
    var customerId : Int = 0,
    @ColumnInfo(name ="customer_name")
    var customerName : String?= null ,
    @ColumnInfo(name = "date")
    val date : String?= null ,
    @ColumnInfo(name = "cup")
    val cup : String?= null ,
    @ColumnInfo(name = "aryw")
    val aryw : String?= null ,
    @ColumnInfo(name = "afyn")
    val afyn : String?= null ,
    @ColumnInfo(name = "rws")
    val rws : String?= null ,
    @ColumnInfo(name = "hibard")
    val hibard : String?= null ,
    @ColumnInfo(name = "ay_ar")
    val ay_ar : String?= null ,
    @ColumnInfo(name = "note1")
    val noteworthy : String?= null ,
    @ColumnInfo(name = "note2")
    val noteworthy2 : String?= null

):Serializable {

}
