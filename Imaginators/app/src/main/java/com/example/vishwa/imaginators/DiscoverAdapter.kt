package com.example.vishwa.imaginators

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vishwa.myapplication2.Model.Post

class DiscoverAdapter(val context: Context, val items: ArrayList<Any>, val indices: ArrayList<String>, var postList: List<Post>): RecyclerView.Adapter<CustomDiscoverAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomDiscoverAdapter {
        val layout = LayoutInflater.from(context).inflate(R.layout.discover_layout,parent,false)
        return CustomDiscoverAdapter(layout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomDiscoverAdapter?, position: Int) {
//        val item = items.get(position) as LinkedHashMap<String,Int>
//        if (item.containsValue(-1)){
//            holder?.view
//        }
    }

}

class CustomDiscoverAdapter(val view: View): RecyclerView.ViewHolder(view){

}