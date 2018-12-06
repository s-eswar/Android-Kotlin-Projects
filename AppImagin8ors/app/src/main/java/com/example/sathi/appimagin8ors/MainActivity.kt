package com.example.sathi.appimagin8ors

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.vishwa.imaginators.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

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
        val indexlist = ArrayList<String>()
        var ind=0
        if (ela is JSONArray) {

            var x=0
            while (x<ela.length()){
                var y=0
                val subtopics: JSONArray = ((grade.get(ela.get(x).toString()) as JSONObject).get("subconcepts") as JSONArray)
                var hashedMap = LinkedHashMap<String,Int>()
                hashedMap.put(ela[x].toString(),-1)
//                indexlist.add("$x.$y")
                arrItems.add(hashedMap)
                while(y < subtopics.length()) {
                    var ob: JSONObject = subtopics.get(y) as JSONObject
                    var count: JSONObject = ob.get("count") as JSONObject
                    val hashMap = LinkedHashMap<String,Int>()
                    hashMap.put(ob.get("name").toString(),(count.get("5").toString()).toInt())
                    arrItems.add(hashMap)
                    ind=x+1
                    indexlist.add("$ind.$y")
                    y++
                }
                ind=x+1
                indexlist.add("$ind.$y")
                x++
            }
        }

        recyclerview_Main.layoutManager = LinearLayoutManager(this)
        recyclerview_Main.adapter = MainAdapter(this@MainActivity,arrItems,indexlist)
    }
}
