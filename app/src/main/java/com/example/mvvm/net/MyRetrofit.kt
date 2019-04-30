package com.example.mvvm.net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class MyRetrofit {
    companion object {
        @Volatile private var intence: MyRetrofit? = null
        fun getInstance(): MyRetrofit{
            if (intence == null){
                synchronized(MyRetrofit::class.java){
                    if (intence == null){
                        intence = MyRetrofit()
                    }
                }
            }
            return intence!!
        }
    }


    val httpApi: HttpApi
    val cookies = HashSet<String>()
    var baseUrl = "https://api.douban.com/"
        set(url) {
            field = url
            intence = null
        }

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(GetCookiesInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder()
            .client(client)
//            .addConverterFactory(ScalarsConverterFactory.create())////增加返回值为String的支持
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(this.baseUrl)
            .build()
        httpApi = retrofit.create(HttpApi::class.java)
        Log.e("tag", "baseUrl:"+this.baseUrl)
//        System.setProperty("http.proxyHost", "api.douban.com" )
//        System.setProperty("http.proxyPort", "1234")
    }

    inner class GetCookiesInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalResponse = chain.proceed(chain.request())
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                for (header in originalResponse.headers("Set-Cookie")) cookies.add(header)
            }
            return originalResponse
        }
    }

    inner class AddCookiesInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val builder = chain.request().newBuilder()
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
                Log.v("OkHttp", "Adding Header: $cookie") // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
            return chain.proceed(builder.build())
        }
    }
}