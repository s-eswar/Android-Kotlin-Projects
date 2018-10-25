package com.example.vishwa.imaginators.Retrofit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory
//

object RetrofitClient {
    private var OurInstance : Retrofit?=null
//    var client = OkHttpClient().newBuilder().cache(com.squareup.okhttp.Cache)
    val instance:Retrofit
    get() {
        if (OurInstance==null)
        {
            OurInstance =Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return OurInstance!!
    }
}