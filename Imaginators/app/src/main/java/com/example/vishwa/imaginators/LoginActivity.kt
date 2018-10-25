package com.example.vishwa.imaginators

import android.annotation.SuppressLint
import android.content.Intent
import android.media.tv.TvInputService
import android.os.Build
import android.os.Bundle
import android.se.omapi.Session
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.facebook.*
import kotlinx.android.synthetic.main.login.*
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*


class LoginActivity: AppCompatActivity(){
    private var loggedin = false
    private var callbackManager:CallbackManager?=null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        button_login.setOnClickListener {
            if (textView_username.text.toString().equals("eswar") && textView_password.text.toString().equals("eswar")){
            Toast.makeText(this, "Login Succesful", Toast.LENGTH_SHORT).show()
            val background = object : Thread() {
                override fun run() {
                    try {
//                        Thread.sleep(1000)
                        val intent = Intent(baseContext, MainActivity::class.java)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            background.start()
        } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                 val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)

            }

        }
        val fblogin_button= findViewById<LoginButton>(R.id.fb_login)
        fblogin_button.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("email"))
            if (!loggedin) {
                      loggedin=true
                LoginManager.getInstance().registerCallback(callbackManager,
                        object : FacebookCallback<LoginResult> {
                            override fun onSuccess(loginResult: LoginResult) {
//                    println("facebook login successful")
                                Log.d("LoginActivity", "Facebook token" + loginResult.accessToken.token)
                                startActivity(Intent(baseContext, MainActivity::class.java))
                            }

                            override fun onCancel() {
//                    println("facebook login cancelled")

                                Log.d("LoginActivity", "Facebook oncancel")
                                //  startActivity(Intent(this@LoginActivity,LoginActivity::class.java))
                            }

                            override fun onError(error: FacebookException?) {
//                    println("facebook login error")
                                Log.d("LoginActivity", "Facebook onError")
                            }

                        })
            } else {
                loggedin=false

                LoginManager.getInstance().unregisterCallback(callbackManager, object : FacebookCallback<LoginResult>{
                            override fun onSuccess(result: LoginResult?) {
                                GraphRequest(AccessToken.getCurrentAccessToken(), "/{user-id}/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback() {
                                    AccessToken.setCurrentAccessToken(null)
                                    LoginManager.getInstance().logOut()
                                      finish()
                                }).executeAsync()
                                Log.d("LoginActivity", "Facebook token" + result?.accessToken?.token)
                            }

                            override fun onCancel() {
                                Log.d("LoginActivityunregister", "Facebook oncancel")
                            }

                            override fun onError(error: FacebookException?) {
                                Log.d("LoginActivityunregister", "Facebook onError")
                            }

                        })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

    @SuppressLint("NewApi")
    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }


}

private fun Any.unregisterCallback(callbackManager: CallbackManager?, facebookCallback: FacebookCallback<LoginResult>) {

}

