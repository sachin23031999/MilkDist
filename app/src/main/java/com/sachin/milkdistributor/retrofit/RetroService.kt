package com.sachin.milkdistributor.retrofit

import com.sachin.milkdistributor.dataModels.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetroService {

    //Get all active customers
    @GET("customers")
    fun getAllCustomers(): Call<CustomerList>

    //Get all archived customers
    @GET("customers/archived")
    fun getArchivedCustomers(): Call<CustomerList>

    //Archive customer
    @PATCH("customers/{customer_id}/archive")
    fun archiveCustomer(@Path("customer_id") id: Int): Call<ResponseBody>

    //Unarchive customer
    @PATCH("customers/{customer_id}/active")
    fun unArchiveCustomer(@Path("customer_id") id: Int): Call<ResponseBody>

    //Set Delivered
    @PATCH("daily/{dist_id}/delivered")
    fun setDelivered(@Path("dist_id") dist_id: String): Call<ResponseBody>

    //Set Undelivered
    @PATCH("daily/{dist_id}/undelivered")
    fun setUndelivered(@Path("dist_id") dist_id: String): Call<ResponseBody>

    //Get all Milk Details
    @GET("milk")
    fun getDistRequired(): Call<List<DistReq>>


    //Get all Daily Distribution
    @GET("daily")
    fun getDailyDistribution(): Call<List<com.sachin.milkdistributor.dataModels.DailyDist>>

    //Get Daily Distribution by Customer ID
    @GET("daily/customer/{customer_id}")
    fun getDailyDistByCustomerID(@Path("customer_id") customer_id: String): Call<DailyDistList>

    //Get price details
    @GET("price")
    fun getPrices(): Call<List<Price>>

    //Set price details
    @POST("price")
    fun addPrices(price: Price): Call<ResponseBody>

    @PUT("price/{id}")
    fun updatePrice(@Path("id") id: String, @Body price: Price): Call<ResponseBody>

    //Get cow vs buffalo report
    @GET("reports/cow-vs-buffalo/{start_date}/{end_date}")
    fun reportCowVsBuffalo(
        @Path("start_date") start: String,
        @Path("end_date") end: String
    ): Call<List<ReportCowBuffalo>>

    //Get total earning report
    @GET("reports/total-earning/{start_date}/{end_date}")
    fun reportTotalEarning(
        @Path("start_date") start: String,
        @Path("end_date") end: String
    ): Call<List<ReportTotalEarning>>

//    @PATCH("customers/{customer_id}/daily/{dailyDist_id}")
//    fun patchDeliveryDateAndQuantity(
//        @Path("customer_id") customer_id: Int,
//        @Path("dailyDist_id") dailyDist_id: Int
//    ): Call<ResponseBody>


}