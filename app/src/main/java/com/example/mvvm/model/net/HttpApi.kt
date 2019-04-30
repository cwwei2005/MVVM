package com.example.mvvm.model.net

import com.example.mvvm.model.db.Theater
import io.reactivex.Flowable
import retrofit2.http.GET

interface HttpApi {
    @GET("v2/movie/in_theaters")
    fun getTheaters(): Flowable<Theater>
}