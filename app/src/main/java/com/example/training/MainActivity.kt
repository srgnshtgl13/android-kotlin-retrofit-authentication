package com.example.training

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.training.auth.LoginActivity
import com.example.training.models.ResponseModel
import com.example.training.models.User
import com.example.training.retrofit.AuthAPIService
import com.example.training.retrofit.RestClientWithBearerToken
import com.example.training.sharedpreff.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var prefs: Prefs

    lateinit var mAuthApiService: AuthAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = Prefs(this)
        mAuthApiService = RestClientWithBearerToken(this).client()!!.create(AuthAPIService::class.java)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_option -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {

        val logout = mAuthApiService.doLogout(User(prefs.userID, prefs.userEmail, "", ""))
        logout.enqueue(object : Callback<ResponseModel>{
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.i("logout problem", t.toString())
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                Log.i("erer", response.toString())
                if (response.code() == 200){

                    goToLogin()
                }
            }
        })

    }

    fun goToLogin(){
        prefs.token = null
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
