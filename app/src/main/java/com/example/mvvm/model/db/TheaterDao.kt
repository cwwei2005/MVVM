package com.example.mvvm.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TheaterDao {
    @Insert
    fun insert(theater: Theater)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceInsert(theater: Theater)

    @Insert
    fun insertAll(theaters: List<Theater>)

    @Update
    fun update(theater: Theater)

    @Update
    fun updateAll(theaters: List<Theater>)

    @Delete
    fun delete(theater: Theater)

    @Delete
    fun deleteAll(theaters: List<Theater>)

    @Query("select * from theater")  //查询所有
    fun getAll2(): List<Theater>

    @Query("select * from theater")  //查询所有
    fun getAll(): LiveData<List<Theater>>

}