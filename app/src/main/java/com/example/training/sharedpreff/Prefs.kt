package com.example.training.sharedpreff

import android.content.Context
import android.content.SharedPreferences
import com.example.training.MainActivity

class Prefs(context: Context) {
    val prefsFileName = "com.example.training.sharedpreff"
    val userEmailKey= "emailKey"
    val userIDKey= "IDKey"
    val userToken = "userToken"
    val prefs: SharedPreferences = context.getSharedPreferences(prefsFileName, 0);

    var token: String?
        get() = prefs.getString(userToken, null)
        set(value) = prefs.edit().putString(userToken, value).apply()
    var userID: String?
        get() = prefs.getString(userIDKey, null)
        set(value) = prefs.edit().putString(userID, value).apply()
    var userEmail: String?
        get() = prefs.getString(userEmailKey, null)
        set(value) = prefs.edit().putString(userEmailKey, value).apply()
}