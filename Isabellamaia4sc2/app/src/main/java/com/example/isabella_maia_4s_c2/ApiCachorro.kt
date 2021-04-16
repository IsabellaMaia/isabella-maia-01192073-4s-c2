package com.example.isabella_maia_4s_c2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCachorro {

    @GET("cachorro")
    fun get(): Call<List<Cachorro>>

    @GET("cachorro/{id}") // "/{id}" -> é um Path Param
    fun get(@Path("id") id:Int): Call<Cachorro>

//    @GET("cachorro/indicadoCrianca")
//    fun get(@Path("indicadoCrianca") id:Int): Call<Cachorro>





}