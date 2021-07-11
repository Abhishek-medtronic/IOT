package com.college.userlatlong.network

import com.college.userlatlong.model.RegistrationEntity
import com.college.userlatlong.model.Upload
import com.college.userlatlong.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("/registration")
    fun registerUser(@Body user: User): Call<RegistrationEntity>

    @POST("/userdetails")
    fun uploadData(@Body user: Upload): Call<User>
}