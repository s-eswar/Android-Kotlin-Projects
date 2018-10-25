package com.example.vishwa.parsingjson

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.topic.*
import kotlinx.android.synthetic.main.topic.view.*

class MainAdapter(var context: Context,var items: ArrayList<Any>,val indices: ArrayList<String>): RecyclerView.Adapter<CustomViewHolder>(){

    val topicTitles = listOf("First","Sec","Third","More")
    // number of items
    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        //how to do we create a view
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.topic,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
//        val topicTitle = topicTitles.get(position)
        UIview(holder,position)
    }

    fun UIview(holder: CustomViewHolder?,position: Int){
        val topicTitle = items.get(position) as LinkedTreeMap<*, *>
        holder?.setIsRecyclable(false)
        if (topicTitle.containsValue(-1)) {
            for ((key,values) in topicTitle) {
                holder?.view?.textView_topic_title?.text = key.toString()
            }
            holder?.view?.imageView?.setImageResource(R.drawable.pink)
            holder?.view?.progressBar?.visibility = View.GONE
            holder?.view?.imageView2_bulb?.visibility = View.GONE
            holder?.view?.textView2_count?.visibility = View.GONE
            holder?.view?.textView2_subtopic_title?.visibility = View.GONE
            holder?.view?.textView_number?.visibility=View.GONE


        }
        else{
            holder?.view?.imageView2_bulb?.setImageResource(R.drawable.bulb_on)

            holder?.view?.textView_number?.text= indices.get(position)
            for ((key,value) in topicTitle) {
                holder?.view?.textView2_subtopic_title?.text = key.toString()
                val mAlertDialogTextView = holder?.view?.textView2_subtopic_title
                mAlertDialogTextView?.setOnClickListener {
                    val mAlertDialog = AlertDialog.Builder(context)
                    mAlertDialog.setMessage(key.toString())
                    mAlertDialog.setNegativeButton("cancel",{ dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss()})
                    mAlertDialog.show()
                }
                holder?.view?.textView2_count?.text = value.toString()
            }
            holder?.view?.textView_topic_title?.visibility= View.GONE
            holder?.view?.imageView?.visibility = View.GONE

        }

    }
    override fun onViewRecycled(holder: CustomViewHolder?) {
        UIview(holder, holder?.adapterPosition!!)
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}