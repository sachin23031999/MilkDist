package com.sachin.milkdistributor.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sachin.milkdistributor.dataModels.*
import com.sachin.milkdistributor.retrofit.RetroFitInstance
import com.sachin.milkdistributor.retrofit.RetroService
import com.sachin.milkdistributor.room.RoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val retroFitInstance =
        RetroFitInstance.getRetroInstance().create(RetroService::class.java)

    private val customerDao = RoomDB.getDatabase(application).customerDao()
    private val priceDao = RoomDB.getDatabase(application).priceDao()

    fun getCustomerList(): LiveData<List<Customer>> {

        var customers: MutableLiveData<CustomerList> = MutableLiveData()

        val call = retroFitInstance.getAllCustomers()
        call.enqueue(object : Callback<CustomerList> {
            override fun onResponse(call: Call<CustomerList>, response: Response<CustomerList>) {
                Log.d("customers", "Data Fetched")
                viewModelScope.launch(Dispatchers.IO) {
                    customerDao.insertCustomer(response.body()!!)
                }
               // customers.postValue(response.body())
            }

            override fun onFailure(call: Call<CustomerList>, t: Throwable) {
              //  customers.postValue(null)
                Log.d("customers", "Error getting data")
            }

        })
        //customers = customerDao.getAllCustomers() as MutableLiveData<CustomerList>
     return customerDao.getAllCustomers()
    }

    fun getArchivedCustomerList(): MutableLiveData<CustomerList> {

        var archivedCustomers: MutableLiveData<CustomerList> = MutableLiveData()

        val call = retroFitInstance.getArchivedCustomers()
        call.enqueue(object : Callback<CustomerList> {
            override fun onResponse(call: Call<CustomerList>, response: Response<CustomerList>) {
                Log.d("archived", "Data Fetched")
                archivedCustomers.postValue(response.body())
            }

            override fun onFailure(call: Call<CustomerList>, t: Throwable) {
                archivedCustomers.postValue(null)
                Log.d("archived", "Error getting data")
            }

        })
        return archivedCustomers
    }

    fun archiveCustomer(id: Int) {
        //  viewModelScope.launch(Dispatchers.IO) {
        val call = retroFitInstance.archiveCustomer(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("archiveCustomer", response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("archiveCustomer", "error occurred")
            }

        })
        //}
    }

    fun unArchiveCustomer(id: Int) {
        //  viewModelScope.launch(Dispatchers.IO) {
        val call = retroFitInstance.unArchiveCustomer(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("archiveCustomer", response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("archiveCustomer", "error occurred")
            }

        })
        //}
    }


    fun setDelivered(id: String) {
        //  viewModelScope.launch(Dispatchers.IO) {
        val call = retroFitInstance.setDelivered(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("delivered", response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("delivered", "error occurred")
            }
        })
    }

    fun setUndelivered(id: String) {
        //  viewModelScope.launch(Dispatchers.IO) {
        val call = retroFitInstance.setUndelivered(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("undelivered", response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("undelivered", "error occurred")
            }
        })
    }

    fun getPrices(): LiveData<List<Price>> {

//        var prices: MutableLiveData<List<Price>> = MutableLiveData()

        val call = retroFitInstance.getPrices()
        call.enqueue(object : Callback<List<Price>> {
            override fun onResponse(call: Call<List<Price>>, response: Response<List<Price>>) {
                Log.d("prices", "Data Fetched")
               // prices.postValue(response.body())
                viewModelScope.launch(Dispatchers.IO) {
                    priceDao.insertPrice(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<Price>>, t: Throwable) {
  //              prices.postValue(null)
                Log.d("prices", "Error getting data")
            }


        })
        return priceDao.getAllPrices()
    }

    fun updatePrice(id: Int, price: Price) {

        val call = retroFitInstance.updatePrice(id.toString(), price)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("updatePrice", response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("updatePrices", "Error getting data")
            }


        })
    }

    fun getMilkDetails(): MutableLiveData<List<DistReq>> {

        val distReq = MutableLiveData<List<DistReq>>()

        val call = retroFitInstance.getDistRequired()
        call.enqueue(object : Callback<List<DistReq>> {
            override fun onResponse(
                call: Call<List<DistReq>>,
                response: Response<List<DistReq>>
            ) {
                Log.d("MilkReport", "got the response")
                distReq.postValue(response.body())

            }

            override fun onFailure(call: Call<List<DistReq>>, t: Throwable) {
                distReq.postValue(null)
                Log.d("MilkReport", "error occurred")
            }

        })
        return distReq
    }

    fun getDailyDistribution(): MutableLiveData<List<com.sachin.milkdistributor.dataModels.DailyDist>> {

        val dailyDist = MutableLiveData<List<com.sachin.milkdistributor.dataModels.DailyDist>>()

        val call = retroFitInstance.getDailyDistribution()
        call.enqueue(object : Callback<List<com.sachin.milkdistributor.dataModels.DailyDist>> {
            override fun onResponse(
                call: Call<List<com.sachin.milkdistributor.dataModels.DailyDist>>,
                response: Response<List<com.sachin.milkdistributor.dataModels.DailyDist>>
            ) {
                Log.d("MilkReport", "got the response")
                dailyDist.postValue(response.body())

            }

            override fun onFailure(
                call: Call<List<com.sachin.milkdistributor.dataModels.DailyDist>>,
                t: Throwable
            ) {
                dailyDist.postValue(null)
                Log.d("MilkReport", "error occurred")
            }

        })
        return dailyDist
    }

    fun getDailyDistByCustomerID(customer_id: String): MutableLiveData<DailyDistList> {

        val dailyDist = MutableLiveData<DailyDistList>()

        val call = retroFitInstance.getDailyDistByCustomerID(customer_id)
        call.enqueue(object : Callback<DailyDistList> {
            override fun onResponse(
                call: Call<DailyDistList>,
                response: Response<DailyDistList>
            ) {
                Log.d("DailyDistByCusID", "got the response")
                dailyDist.postValue(response.body())

            }

            override fun onFailure(call: Call<DailyDistList>, t: Throwable) {
                dailyDist.postValue(null)
                Log.d("DailyDIstByCusID", "error occurred")
            }

        })
        return dailyDist
    }


    fun getCowBuffReport(start: String, end: String): MutableLiveData<List<ReportCowBuffalo>> {

        val report = MutableLiveData<List<ReportCowBuffalo>>()

        val call = retroFitInstance.reportCowVsBuffalo(start, end)
        call.enqueue(object : Callback<List<ReportCowBuffalo>> {
            override fun onResponse(
                call: Call<List<ReportCowBuffalo>>,
                response: Response<List<ReportCowBuffalo>>
            ) {
                Log.d("MilkReport", "got the response")
                report.postValue(response.body())

            }

            override fun onFailure(call: Call<List<ReportCowBuffalo>>, t: Throwable) {
                report.postValue(null)
                Log.d("MilkReport", "error occurred")
            }

        })
        return report
    }

    fun getTotalEarning(start: String, end: String): MutableLiveData<List<ReportTotalEarning>> {

        val earningReport = MutableLiveData<List<ReportTotalEarning>>()

        val call = retroFitInstance.reportTotalEarning(start, end)
        call.enqueue(object : Callback<List<ReportTotalEarning>> {
            override fun onResponse(
                call: Call<List<ReportTotalEarning>>,
                response: Response<List<ReportTotalEarning>>
            ) {
                Log.d("EarningReport", "got the response")
                earningReport.postValue(response.body())

            }

            override fun onFailure(call: Call<List<ReportTotalEarning>>, t: Throwable) {
                earningReport.postValue(null)
                Log.d("EarningReport", "error occurred")
            }

        })
        return earningReport
    }


}