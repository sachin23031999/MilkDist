package com.sachin.milkdistributor.dataModels

import kotlin.Int
import kotlin.String

data class DistReq(
    val customer: Customer,
    val id: Int,
    val price: Double,
    val time_of_delivery: String,
    val type_of_milk: String,
    val unit: String
)