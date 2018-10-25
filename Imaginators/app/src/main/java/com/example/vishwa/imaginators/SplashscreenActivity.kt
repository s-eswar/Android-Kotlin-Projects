package com.example.vishwa.imaginators

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.facebook.BuildConfig
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import kotlin.system.exitProcess

class SplashscreenActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );

            super.onCreate(savedInstanceState)
            setContentView(R.layout.imagin8ors_splash)

//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)
            val background = object : Thread() {
                override fun run() {
                    try {
                        Thread.sleep(1000)
                        val intent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            background.start()

    }

}