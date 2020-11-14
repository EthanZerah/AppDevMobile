package com.example.testmenu

import retrofit2.Call
import retrofit2.http.GET

interface CoursesService {
    @GET("courses")
    fun listCourses(): Call<List<Course>>
}