package com.example.mvvm.model.net

import com.example.mvvm.model.db.Theater
import com.example.mvvm.model.db.Top250
import io.reactivex.Flowable
import retrofit2.http.GET

interface HttpApi {
    @GET("v2/movie/in_theaters")
    fun getTheaters(): Flowable<Theater>

    @GET("v2/movie/top250")
    fun getTop250(): Flowable<Top250>
}