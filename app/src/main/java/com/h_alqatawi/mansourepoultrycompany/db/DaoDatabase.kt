package com.h_alqatawi.mansourepoultrycompany.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.h_alqatawi.mansourepoultrycompany.model.Customers
import com.h_alqatawi.mansourepoultrycompany.model.Dates

@Dao
interface DaoDatabase {
    @Insert
   suspend fun insertDate(date : Dates):Long
   @Delete
   suspend fun deleteDate(date: Dates):Int
    @Query("SELECT * FROM dates_table")
    fun getDates():LiveData<List<Dates>>


    @Insert
   suspend fun insertCustomer(customers: Customers):Long
   @Update
   suspend fun upCustomer(customer: Customers):Int
    @Delete
    suspend fun deleteCustomer(customers: Customers):Int
    @Query("SELECT * FROM customer_table WHERE date=:date")
    fun getCustomers(date : String):LiveData<List<Customers>>

    @Query("SELECT * FROM customer_table WHERE date=:date")
    fun getCustomersForDelete(date : String):List<Customers>
}