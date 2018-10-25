package com.example.vishwa.myapplication2.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.post_layout.view.*

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val txt_author = itemView.txt_author
    val txt_content = itemView.txt_content
    val txt_title = itemView.txt_title
}