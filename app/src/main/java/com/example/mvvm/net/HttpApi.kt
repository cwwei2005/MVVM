package com.example.mvvm.net

import com.example.mvvm.db.Article
import com.example.mvvm.db.Theater
import com.example.mvvm.db.User
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface HttpApi {
    @GET("article_list.php")
    fun getArticles(): Flowable<List<Article>>

    @GET("v2/movie/in_theaters")
    fun getTheaters(): Flowable<Theater>
}