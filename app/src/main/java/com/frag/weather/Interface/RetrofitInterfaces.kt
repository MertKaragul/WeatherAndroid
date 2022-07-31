package com.frag.weather.Interface

import com.frag.weather.Model.SearchModel
import com.frag.weather.Model.WeatherModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterfaces {
    @GET("data/2.5/weather?")
    fun getOneCall(@Query("lat") lat : String, @Query("lon") lon : String, @Query("appid") appid : String, @Query("units") units : String) : Single<WeatherModel>

    @GET("data/2.5/weather?")
    suspend fun getSearch(@Query("q") search : String , @Query("appid") appid: String , @Query("units") units : String) : SearchModel
}