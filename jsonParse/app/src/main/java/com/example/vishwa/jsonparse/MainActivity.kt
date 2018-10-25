package com.nplix.jsontutorialkotlin
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.vishwa.jsonparse.R
import com.google.gson.Gson
import java.io.*
class MainActivity : AppCompatActivity() {
    private var textView: TextView?=null;
    private var stringBuilder:StringBuilder?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
// Get the file Location and name where Json File are get stored
        val fileName = cacheDir.absolutePath+"/PostJson.json"
//call write Json method
        writeJSONtoFile(fileName)
//Read the written Json File
        readJSONfromFile(fileName)
    }
    private fun writeJSONtoFile(s:String) {
//Create list to store the all Tags
        var tags = ArrayList<String>()
// Add the Tag to List
        tags.add("Android")
        tags.add("Angular")
//Create a Object of Post
        var post = Post("Json Tutorial", "www.nplix.com", "Pawan Kumar", tags)
//Create a Object of Gson
        var gson = Gson()
//Convert the Json object to JsonString
        var jsonString:String = gson.toJson(post)
//Initialize the File Writer and write into file
        val file=File(s)
        file.writeText(jsonString)
    }
    private fun readJSONfromFile(f:String) {
//Creating a new Gson object to read data
        var gson = Gson()
//Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
// Read the text from buffferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }
//Convert the Json File to Gson Object
        var post = gson.fromJson(inputString, Post::class.java)
//Initialize the String Builder
        stringBuilder = StringBuilder("Post Details\n---------------------")
        +Log.d("Kotlin",post.postHeading)
        stringBuilder?.append("\nPost Heading: " + post.postHeading)
        stringBuilder?.append("\nPost URL: " + post.postUrl)
        stringBuilder?.append("\nPost Author: " + post.postAuthor)
        stringBuilder?.append("\nTags:")
//get the all Tags
        post.postTag?.forEach { tag -> stringBuilder?.append(tag + ",") }
//Display the all Json object in text View
        textView?.setText(stringBuilder.toString())
    }
}