package com.sachin.milkdistributor.room

import android.content.Context
import androidx.room.RoomDatabase
import com.sachin.milkdistributor.dataModels.Customer
import androidx.room.Database
import androidx.room.Room
import com.sachin.milkdistributor.dataModels.Price

@Database(entities = [Customer::class, Price::class], version = 2, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun priceDao(): PriceDao

    companion object {

        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "customer_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}