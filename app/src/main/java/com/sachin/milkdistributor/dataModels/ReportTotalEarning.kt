package com.sachin.milkdistributor.dataModels

import kotlin.String

data class ReportTotalEarning(
    val customer_id: String,
    val details: List<Detail>,
    val total_earning: String
)