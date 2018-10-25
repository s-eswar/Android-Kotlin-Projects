package com.example.vishwa.parsingjson

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.topic.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonfile: String = applicationContext.assets.open("ela.json").bufferedReader().use {
            it.readText()
        }


        val jsonObject = JSONObject(jsonfile)
        val grade: JSONObject = jsonObject.getJSONObject("5")
        val ela: Any = grade.get("ela")
        val arrItems = ArrayList<Any>()
        val indices=ArrayList<String>()
        var ind=0
//        var allitems = ArrayList<String>()
//        var hashedMap = hashMapOf<String,Int>()
//        var hmap = hashMapOf<String, ArrayList<OneSubtopic>>()
        if (ela is JSONArray) {

            var x=0
            while (x<ela.length()){
                var y=0
                val subtopics: JSONArray = ((grade.get(ela.get(x).toString()) as JSONObject).get("subconcepts") as JSONArray)
                var hashedMap = LinkedTreeMap<String,Int>()
                hashedMap.put(ela[x].toString(),-1)
                arrItems.add(hashedMap)
//                val item = ArrayList<OneSubtopic>()
//                val hashMap = LinkedTreeMap<String,Int>()
//                allitems.add(ela[x].toString())
                while(y < subtopics.length()) {
                    var ob: JSONObject = subtopics.get(y) as JSONObject
                    var count: JSONObject = ob.get("count") as JSONObject
//                    var oneSubtopic = OneSubtopic(ob.get("name").toString(),count.get("5").toString())
//                    item.add(oneSubtopic)
//                    println(count.get("5"))
//                    allitems.add(ob.get("name").toString())
//                    allitems.add(count.get("5").toString())
                    var hashMap = LinkedTreeMap<String,Int>()
                    hashMap.put(ob.get("name").toString(),(count.get("5").toString()).toInt())
                    //                    item.add(dicts.get("name").toString(),count.get("5"))
//                    println("sample" + count.get("5"))
                    arrItems.add(hashMap)
                    ind=x+1
                    indices.add("$ind.$y")
                    y++
                }
                ind=x+1
                indices.add("$ind.$y")
//                arrItems.add(hashMap)
//                hmap.put(ela[x].toString(),item)
                x++
            }
        }
//        for(i in arrItems){
//            println("$i")
//}
        recyclerview_Main.layoutManager = LinearLayoutManager(this)
        recyclerview_Main.adapter = MainAdapter(this@MainActivity,arrItems,indices)
    }
}







