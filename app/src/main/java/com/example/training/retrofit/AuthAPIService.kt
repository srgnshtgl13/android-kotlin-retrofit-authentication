package com.example.training.retrofit

import com.example.training.models.ResponseModel
import com.example.training.models.User
import retrofit2.Call
import retrofit2.http.*


interface AuthAPIService {

    @FormUrlEncoded
    @POST("auth/signin")    //End Url
    fun doLogin(@Field("email") email: String, @Field("password") password: String): Call<User>

    @POST("auth/signout")
    fun doLogout(@Body user: User): Call<ResponseModel>
}