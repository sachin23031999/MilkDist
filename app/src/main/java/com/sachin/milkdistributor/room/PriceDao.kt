package com.sachin.milkdistributor.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sachin.milkdistributor.dataModels.Price

@Dao
interface PriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrice(prices : List<Price>)

    @Query("SELECT * FROM Price")
    fun getAllPrices() : LiveData<List<Price>>
}