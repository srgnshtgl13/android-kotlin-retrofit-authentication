package com.example.training.retrofit

import android.content.Context
import com.example.training.sharedpreff.Prefs
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RestClient {

    private const val BASE_URL = "http://10.0.3.2:8000/api/"

    val client: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}

class RestClientWithBearerToken(context: Context) {
    private var prefs: Prefs = Prefs(context)
    private var client = OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor(prefs.token.toString()))
        .build()

    companion object {
        private const val BASE_URL = "http://10.0.3.2:8000/api/"
    }

    fun client(): Retrofit? {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }
}


class OAuthInterceptor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "Bearer $accessToken").build()

        return chain.proceed(request)
    }
}

/**
 * Another way to create retrofit client
 */
/*
class BasicAuthClient<T>(prefs: Prefs) {
    private val client = OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor(prefs.token.toString()))
        .build()

    val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.3.2:8000/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
*/
