package com.example.vishwa.myapplication2.Retrofit

import com.example.vishwa.myapplication2.Model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface IMyAPI {
    @get:GET("posts")
    val posts:Observable<List<Post>>
}