package com.project.mytask.Url

import com.project.mytask.Pojo.Data
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    fun getData(): Call<ArrayList<Data>>

 /* *//*  @GET("/v3/projects/{projectId}/categories/")
    fun getProjectCategories(
        @Path("projectId") projectId: String?,
        @Header("Authorization") token: String?
    ): Call<List<Category2?>?>?*//*

    @GET("/posts")
    fun getData2(): Call<List<Data>>

    @GET("/posts")
    fun getAllData(): Call<ArrayList<Data>>*/


    companion object {
        var BASE_URL = "https://jsonplaceholder.typicode.com"
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}