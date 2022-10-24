package com.h_alqatawi.mansourepoultrycompany.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.h_alqatawi.mansourepoultrycompany.model.Customers
import com.h_alqatawi.mansourepoultrycompany.model.Dates
import com.h_alqatawi.mansourepoultrycompany.model.Event
import com.h_alqatawi.mansourepoultrycompany.repository.RepositoryApp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModelApp( private val repository: RepositoryApp) : ViewModel() {

    val getDates = repository.getDate
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun getCustomer(date:String):LiveData<List<Customers>>{
        return repository.getDataCustomer(date)
    }

    fun getCustomerForDelete(date:String):List<Customers>{
        return repository.getDataCustomerForDelete(date)

    }
    fun insertDate(date:Dates):Job = viewModelScope.launch {
        val newDate = repository.addDate(date)
        if (newDate>-1){
            statusMessage.value = Event("Add Date $newDate")
        }else{
            statusMessage.value = Event("error in add date")
        }
    }
    fun insertNewDate(date:Dates , customer: Customers):Job = viewModelScope.launch {
        val newDate = repository.addDate(date)
        if (newDate>-1){
            statusMessage.value = Event("Add New Date} $newDate")
              insertCustomer(customer)
        }else{
            statusMessage.value = Event("error in add new date")
        }
    }
    fun insertCustomer(customer: Customers):Job = viewModelScope.launch {
        val newCustomer = repository.addCustomer(customer)
        if (newCustomer>-1){
            statusMessage.value = Event("Add New Customer $newCustomer")
        }else{
            statusMessage.value = Event("error in add new customer")
        }
    }
    fun updateCustomer(customer: Customers):Job = viewModelScope.launch {
        val updateCustomer = repository.updateCustomer(customer)
        if (updateCustomer>0){
            statusMessage.value = Event("Update Customer $updateCustomer")
        }else{
            statusMessage.value = Event("error in add update customer")
        }
    }
    fun deleteCustomer(customer: Customers):Job = viewModelScope.launch {
        val deleteCustomer = repository.deleteCustomer(customer)
        if (deleteCustomer>0){
            statusMessage.value = Event("Delete Customer $deleteCustomer")
        }else{
            statusMessage.value = Event("error in delete customer")
        }
    }


    fun deleteDate(date: Dates):Job = viewModelScope.launch {
        val deleteDate = repository.deleteDate(date)
        if (deleteDate>0){
            statusMessage.value = Event("Delete Date$deleteDate")
        }else{
            statusMessage.value = Event("error in delete date")
        }
    }

}