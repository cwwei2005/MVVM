package com.example.mvvm.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Top250Dao {
    @Insert
    fun insert(top250: Top250)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceInsert(top250: Top250)

    @Insert
    fun insertAll(theaters: List<Top250>)

    @Update
    fun update(top250: Top250)

    @Update
    fun updateAll(theaters: List<Top250>)

    @Delete
    fun delete(top250: Top250)

    @Delete
    fun deleteAll(theaters: List<Top250>)

    @Query("select * from top250")  //查询所有
    fun getAll2(): List<Top250>

    @Query("select * from top250")  //查询所有
    fun getAll(): LiveData<List<Top250>>

}