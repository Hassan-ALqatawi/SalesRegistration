package com.h_alqatawi.mansourepoultrycompany.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.h_alqatawi.mansourepoultrycompany.model.Customers
import com.h_alqatawi.mansourepoultrycompany.model.Dates

@Database(entities = [Dates::class,Customers::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase(){
    abstract val dao:DaoDatabase

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase?=null

        fun getInstance(context: Context):AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance =Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,"database"
                    ).build()
                }
                return instance
            }

        }
    }

}