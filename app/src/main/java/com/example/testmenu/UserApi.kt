package com.example.testmenu

import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("/?results=3&lego&inc=name")
    fun getUser(): Call<List<User>>

}