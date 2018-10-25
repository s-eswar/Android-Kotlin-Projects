package com.example.vishwa.imaginators

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ImageView
import com.example.vishwa.imaginators.Retrofit.IMyAPI
import com.example.vishwa.imaginators.Retrofit.RetrofitClient
import com.example.vishwa.myapplication2.Model.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    internal lateinit var jsonAPI: IMyAPI
    internal lateinit var compositeDisposable: CompositeDisposable
    private val CAMERA_REQUEST_CODE=0
    private val request=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progress_btn =findViewById<ImageView>(R.id.progress_image)
        val discover_btn = findViewById<ImageView>(R.id.discover_image)
        val concept_btn = findViewById<ImageView>(R.id.concept_image)

        val retrofit = RetrofitClient.instance
        jsonAPI = retrofit.create(IMyAPI::class.java) //transformation of the data from http to the java interface
        compositeDisposable =CompositeDisposable()
        val jsonfile: String = applicationContext.assets.open("ela.json").bufferedReader().use {
            it.readText()
        }


        val jsonObject = JSONObject(jsonfile)
//        val Grade = intent.getStringExtra(GradeAdapter.KEY_NAME)
        val grade: JSONObject = jsonObject.getJSONObject("5")
        val ela: Any = grade.get("ela")
        val arrItems = ArrayList<Any>()
        val indices = ArrayList<String>()
//        val topic_indices = ArrayList<Int>()
        var ind:Int

        if (ela is JSONArray) {

            var x = 0
            while (x < ela.length()) {
                var y = 0
                val subtopics: JSONArray = ((grade.get(ela.get(x).toString()) as JSONObject).get("subconcepts") as JSONArray)
                var hashedMap = LinkedHashMap<String, Int>()
                hashedMap.put(ela[x].toString(), -1)
                arrItems.add(hashedMap)
//                val index = x + 1
//                topic_indices.add(index)
                while (y < subtopics.length()) {
                    var ob: JSONObject = subtopics.get(y) as JSONObject
                    var count: JSONObject = ob.get("count") as JSONObject
                    var hashMap = LinkedHashMap<String, Int>()
                    hashMap.put(ob.get("name").toString(), (count.get("5").toString()).toInt())
                    arrItems.add(hashMap)
                    ind = x + 1
                    indices.add("$ind.$y")
                    y++
                }
                ind = x + 1
                indices.add("$ind.$y")
                x++
            }
        }

        concept_btn.setOnClickListener {

            fetchData(arrItems, indices)
        }

        discover_btn.setOnClickListener {
            DiscoverData(arrItems,indices)
            Log.d("discover","do nothing")
        }
        progress_btn.setOnClickListener {
            Log.d("progress","do nothing")
        }

//        imageview_photo.setOnClickListener {
//            val callcamIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (callcamIntent.resolveActivity(packageManager)!=null){
//                startActivityForResult(callcamIntent,CAMERA_REQUEST_CODE)
//            }
//
//        }
//
//        imageview_audiorec.setOnClickListener {
//            val mediaRecorder = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
//            if (mediaRecorder.resolveActivity(packageManager)!=null){
//                startActivityForResult(mediaRecorder,request)
//            }
////            val mediaPlayer = MediaPlayer.create()
//        }



    }

    private fun DiscoverData(arrItems: ArrayList<Any>, indices: ArrayList<String>) {
        compositeDisposable.add(jsonAPI.posts.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ posts ->
                    displayDiscoverData(arrItems, indices, posts)
                }, {

                    it.printStackTrace()
                })
        )
    }

    private fun displayDiscoverData(items: ArrayList<Any>, indices: ArrayList<String>, posts: List<Post>?) {
        recyclerview_Main.setHasFixedSize(true)
        recyclerview_Main.layoutManager = LinearLayoutManager(this)
        Log.i("size",items.size.toString())
//        recyclerview_Main.adapter =
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            CAMERA_REQUEST_CODE->{
//                if (resultCode == Activity.RESULT_OK && data!=null){
//                    imageview_photo.setImageBitmap(data.extras.get("data") as Bitmap)
//                }
//            }
//            else ->
//            {
//                Toast.makeText(this,"unrecognised request code",Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun fetchData(arr: ArrayList<Any>, indices: ArrayList<String>) {
        compositeDisposable.add(jsonAPI.posts.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ posts ->
                    displayData(arr, indices, posts)
                }, {

                    it.printStackTrace()
                })
        )
    }

    private fun displayData(arrItems: ArrayList<Any>, indices: ArrayList<String>, posts: List<Post>) {
        recyclerview_Main.setHasFixedSize(true)
        recyclerview_Main.layoutManager = LinearLayoutManager(this)
        Log.i("size",arrItems.size.toString())
        recyclerview_Main.adapter = MainAdapter(this@MainActivity, arrItems, indices, posts)
        recyclerview_Main.adapter.notifyDataSetChanged()
    }
}
