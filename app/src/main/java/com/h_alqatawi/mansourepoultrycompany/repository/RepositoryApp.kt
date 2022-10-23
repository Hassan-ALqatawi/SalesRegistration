package com.h_alqatawi.mansourepoultrycompany.repository

import androidx.lifecycle.LiveData
import com.h_alqatawi.mansourepoultrycompany.db.DaoDatabase
import com.h_alqatawi.mansourepoultrycompany.model.Customers
import com.h_alqatawi.mansourepoultrycompany.model.Dates

class RepositoryApp(private val dao:DaoDatabase) {

    val getDate = dao.getDates()

    fun getDataCustomer(date : String):LiveData<List<Customers>>{
       return dao.getCustomers(date)
    }
    fun getDataCustomerForDelete(date: String):List<Customers>{
        return dao.getCustomersForDelete(date)
    }
    suspend fun addDate(date:Dates):Long{
        return dao.insertDate(date)
    }
    suspend fun deleteDate(date:Dates):Int{
        return dao.deleteDate(date)
    }
    suspend fun addCustomer(customer: Customers):Long{
        return dao.insertCustomer(customer)
    }
    suspend fun updateCustomer(customer: Customers):Int{
        return dao.upCustomer(customer)
    }
    suspend fun deleteCustomer(customer: Customers):Int{
        return dao.deleteCustomer(customer)
    }


}