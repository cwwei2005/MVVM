package com.example.mvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Insert
    fun insert(users: List<User>)

    @Update
    fun update(user: User)

    @Update
    fun update(users: List<User>)

    @Delete
    fun delete(user: User)

    @Delete
    fun delete(users: List<User>)

    @Query("select * from user")  //查询所有
    fun getUsers2(): List<User>

    @Query("select * from user")  //查询所有
    fun getUsers(): LiveData<List<User>>

}