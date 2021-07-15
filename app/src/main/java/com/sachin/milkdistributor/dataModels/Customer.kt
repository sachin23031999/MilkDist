package com.sachin.milkdistributor.dataModels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlin.Int
import kotlin.String

@Parcelize
@Entity
data class Customer(
    val address: String,
    @PrimaryKey
    val id: Int,
    val mobile: String,
    val name: String,
    val pincode: String,
    val type_of_customer: String,
    val user_id: String
) : Parcelable