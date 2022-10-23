package com.h_alqatawi.mansourepoultrycompany.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "dates_table")
data class Dates(
    @ColumnInfo(name = "date")
    val date : String
){
    @PrimaryKey(autoGenerate = true)
    var customerId : Int = 0
}
