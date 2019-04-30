package com.example.mvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {
    @Insert
    fun insert(article: Article)

    @Insert
    fun insertAll(articles: List<Article>)

    @Update
    fun update(article: Article)

    @Update
    fun updateAll(articles: List<Article>)

    @Delete
    fun delete(article: Article)

    @Delete
    fun deleteAll(articles: List<Article>)

    @Query("select * from article")  //查询所有
    fun getAll2(): List<Article>

    @Query("select * from article")  //查询所有
    fun getAll(): LiveData<List<Article>>

}