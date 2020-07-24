package com.example.training.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.training.MainActivity
import com.example.training.R
import com.example.training.models.User
import com.example.training.retrofit.AuthAPIService
import com.example.training.retrofit.RestClient
import com.example.training.sharedpreff.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var mAuthApiService: AuthAPIService
    lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuthApiService = RestClient.client.create(AuthAPIService::class.java)
        prefs = Prefs(this)
        val emailText: EditText = findViewById(R.id.email)
        val passwordText: EditText = findViewById(R.id.password)
        val btnLogin: Button = findViewById(R.id.buttonLogin)

        checkIfLoggedIn(this, prefs.token)
        btnLogin.setOnClickListener { clickToLoginButton(emailText, passwordText) }
    }

    private fun clickToLoginButton(emailText: EditText, passwordText: EditText) {
        if (emailText.text.trim().isNotEmpty() and passwordText.text.isNotEmpty()) {
            login(emailText.text.toString(), passwordText.text.toString())
        } else {
            Toast.makeText(
                applicationContext,
                "Email or password cannot be blank!!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun login(email: String, passwd: String) {
        val login = mAuthApiService.doLogin(email, passwd)
        login.enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                // Write code to perform actions when request succeeds.
                Log.i("login: ", response.toString())
                if (response.code() == 200) {
                    prefs.token = response.body()?.token
                    prefs.userID = response.body()?.id.toString()
                    prefs.userEmail = response.body()?.email
                    Companion.checkIfLoggedIn(this@LoginActivity, response.body()!!.token)
                }
                if (response.code() == 422) {
                    Toast.makeText(
                        applicationContext,
                        "Email or Password incorrect!",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Write code perform actions when request fails...
                Log.i("error", t.toString())
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        fun checkIfLoggedIn(loginActivity: LoginActivity, token: String?) {
            if (token != null) {
                val intent = Intent(loginActivity, MainActivity::class.java)
                loginActivity.startActivity(intent)
            }
        }
    }
}