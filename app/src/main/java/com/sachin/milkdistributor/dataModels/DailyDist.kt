package com.sachin.milkdistributor.dataModels

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlin.Int
import kotlin.String

@Parcelize
data class DailyDist(
    val customer: Customer,
    val delivered_at: String,
    val id: Int,
    val quantity: Double,
    val time_period: String,
    val milk_type: String
) : Parcelable