package com.example.vishwa.myapplication2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.vishwa.myapplication2.Adapter.PostAdapter
import com.example.vishwa.myapplication2.Model.Post
import com.example.vishwa.myapplication2.Retrofit.IMyAPI
import com.example.vishwa.myapplication2.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    internal lateinit var jsonApi:IMyAPI
    var compositeDisposable: CompositeDisposable= CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init Api
        val retrofit = RetrofitClient.instance
        jsonApi = retrofit.create(IMyAPI::class.java)

        //view
        recycler_posts.setHasFixedSize(true)
        recycler_posts.layoutManager = LinearLayoutManager(this)
        fetchData()
    }

    private fun fetchData() {
        compositeDisposable.add(jsonApi.posts
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{posts->displayData(posts)}
        )
    }

    private fun displayData(posts: List<Post>?) {
        val adapter = PostAdapter(this,posts!!)
        recycler_posts.adapter = adapter
    }
}