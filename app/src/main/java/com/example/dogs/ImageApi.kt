package com.example.dogs

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {
    @GET("breeds/image/random/5")
    fun getImage() : Call<String>

//    @GET("api/breed/{name}/images/random")
//    fun getImageRandom(@Path("name") name:String): Call<String>
}