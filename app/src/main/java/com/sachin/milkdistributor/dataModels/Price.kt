package com.sachin.milkdistributor.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.Int
import kotlin.String

@Entity
data class Price(
    @PrimaryKey
    val id: Int,
    val milk_type: String,
    val price: Double
)