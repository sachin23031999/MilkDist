package com.sachin.milkdistributor.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.sachin.milkdistributor.dataModels.Customer
import com.sachin.milkdistributor.dataModels.CustomerList

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customers: List<Customer>)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("SELECT * FROM customer")
    fun getAllCustomers() : LiveData<List<Customer>>
}