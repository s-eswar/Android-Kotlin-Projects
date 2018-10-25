package com.example.vishwa.kotlinimagin8ors

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_view.*
import kotlinx.android.synthetic.main.row_view.view.*
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
        if (ela is JSONArray) {
            var x=0
            while (x<ela.length()){
                var y=0
                val subtopics: JSONArray = ((grade.get(ela.get(x).toString()) as JSONObject).get("subconcepts") as JSONArray)
                var hashedMap = LinkedHashMap<String,Int>()
                hashedMap.put(ela[x].toString(),-1)
                arrItems.add(hashedMap)
                while(y < subtopics.length()) {
                    var ob: JSONObject = subtopics.get(y) as JSONObject
                    var count: JSONObject = ob.get("count") as JSONObject
                    var hashMap = LinkedHashMap<String,Int>()
                    hashMap.put(ob.get("name").toString(),(count.get("5").toString()).toInt())

                    arrItems.add(hashMap)
                    y++
                }
                x++
            }
        }
        val listView = findViewById<ListView>(R.id.main_listview)
        listView.setDivider(null)
        listView.dividerHeight = 0
        listView.adapter = MyCustomAdapter(arrItems,this)

    }

    private class MyCustomAdapter(var items: ArrayList<Any>, val context: Context): BaseAdapter(){
        var subtopic_count=0
        var subtopic_subcount=0
        private val mContext: Context
        init{
            mContext = context
        }
        override fun getCount(): Int {
            return items.count()
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "Test"
        }
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
            val layout = LayoutInflater.from(mContext).inflate(R.layout.row_view, viewGroup,false)
            val number =layout.findViewById<TextView>(R.id.textView_number)
            val progress = layout.findViewById<ProgressBar>(R.id.progressBar2)
            val imageView = layout.findViewById<ImageView>(R.id.imageView)
            val imageView_b = layout.findViewById<ImageView>(R.id.imageView_count)
            val count = layout.findViewById<TextView>(R.id.textView_count)
            val subtopic =layout.findViewById<TextView>(R.id.textView_subtopic_title)
            val topic =layout.findViewById<TextView>(R.id.textView_topic_title)
            val topicTitle = items.get(position) as LinkedHashMap<String,Int>
            if (topicTitle.containsValue(-1)){
                for ((key,value) in topicTitle) {
                    topic.text = key
                    if(subtopic_count%2==0){
                        imageView.setImageResource(R.drawable.blue)
                    }
                    else{
                        imageView.setImageResource(R.drawable.pink)
                    }
                    if (subtopic_count%3==0) {
                        imageView.setImageResource(R.drawable.yellow)
                    }
                }
                subtopic.visibility = View.GONE
                number.visibility = View.GONE
                count.visibility = View.GONE
                imageView_b.visibility =View.GONE
                 progress.visibility = View.GONE
                subtopic_subcount=1
                subtopic_count++

            } else {
                imageView_b.setImageResource(R.drawable.bulb_on)
                for ((key,value) in topicTitle) {
                    subtopic.text = key
                    number.text = "$subtopic_count.$subtopic_subcount"
                    count.text=value.toString()
                }
                subtopic_subcount++
                imageView.visibility = View.GONE
                topic.visibility = View.GONE

            }
            return layout
//            val textView = TextView(mContext)
//            var line = items.get(position)
//            textView.text = line.toString()
//            return textView
        }
    }
}
