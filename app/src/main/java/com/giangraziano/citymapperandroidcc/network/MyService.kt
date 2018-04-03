package com.giangraziano.citymapperandroidcc.network

import com.giangraziano.citymapperandroidcc.model.Station
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by giannig on 03/04/18.
 */
interface MyService {

    //todo add this apis
    @GET("StopPoint")
    fun getStations(
            @Query("app_key") key: String,
            @Query("app_id") id: String,
            @Query("stopTypes") format: String,
            @Query("lat") lat: Double,
            @Query("lon") lon: Double
    ): Observable<Station>

}