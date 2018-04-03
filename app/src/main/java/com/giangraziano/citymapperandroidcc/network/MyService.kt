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

    @GET("v2/beers")
    fun getStations(@Query("key") key: String, @Query("format") format: String, @Query("abv") abv: Int): Observable<Station>

}