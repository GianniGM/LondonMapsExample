package com.giangraziano.citymapperandroidcc.network

import com.giangraziano.citymapperandroidcc.model.Arrival
import com.giangraziano.citymapperandroidcc.model.StopPoints
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by giannig on 03/04/18.
 */
interface MyService {

    //https://api.tfl.gov.uk/StopPoint?stopTypes=NaptanMetroStation&lat=51.5101369&lon=-0.1344048
    @GET("StopPoint")
    fun getStops(
            @Query("app_key") key: String,
            @Query("app_id") id: String,
            @Query("stopTypes") format: String,
            @Query("lat") lat: Double,
            @Query("lon") lon: Double
    ): Observable<StopPoints>

    //https://api.tfl.gov.uk/StopPoint/940GZZLUASL/Arrivals
    @GET("StopPoint/{id}/Arrivals")
    fun getArrivals(
            @Path("id") naptanId: String,
            @Query("app_key") key: String,
            @Query("app_id") id: String
    ): Observable<List<Arrival>>

}