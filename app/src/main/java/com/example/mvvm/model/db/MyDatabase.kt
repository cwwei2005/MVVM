package com.example.mvvm.model.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm.App


@Database(entities = [Theater::class, Top250::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase(){

    companion object {
        @Volatile private var intence: MyDatabase? = null

        fun getInstance(): MyDatabase{
            if (intence == null){
                synchronized(MyDatabase::class.java){
                    if (intence == null){
                        intence = Room.databaseBuilder(App.instance, MyDatabase::class.java, "my.db").build()
                    }
                }
            }
            return intence!!
        }
    }

    abstract fun theaterDao(): TheaterDao
    abstract fun top250Dao(): Top250Dao
}