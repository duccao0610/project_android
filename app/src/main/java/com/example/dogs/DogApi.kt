package com.example.dogs

import retrofit2.Call
import retrofit2.http.GET

interface DogApi {

    @GET("breeds/list/all")
    fun getDogType() : Call<String>
}