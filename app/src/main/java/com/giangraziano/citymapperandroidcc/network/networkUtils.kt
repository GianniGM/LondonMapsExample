package com.giangraziano.citymapperandroidcc.network

import android.util.Log
import com.giangraziano.citymapperandroidcc.DEFAULT_LOCATION_LAT
import com.giangraziano.citymapperandroidcc.DEFAULT_LOCATION_LONG
import com.giangraziano.citymapperandroidcc.model.Arrival
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


/**
 * Created by giannig on 03/04/18.
 *
 * "NaptanMetroAccessArea","NaptanMetroEntrance","NaptanMetroPlatform","NaptanMetroStation",
 */

fun callServiceArrivalsFromNaptan(): Observable<List<Arrival>> {
    val scheduler = Schedulers.from(Executors.newSingleThreadExecutor())

//    val retrofit = Retrofit.Builder()
//            .baseUrl(NetworkData.baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(MyService::class.java)
//
//    val getNearbyStation = retrofit.getStops(
//            NetworkData.apiKey,
//            NetworkData.appId,
//            "NaptanMetroStation",
//            DEFAULT_LOCATION_LAT,
//            DEFAULT_LOCATION_LONG
//    )
//
//    return getNearbyStation.flatMap { response ->
//        return@flatMap retrofit.getArrivals(
//                NetworkData.apiKey,
//                NetworkData.appId,
//                response.stopPoints[0].naptanId ?: "940GZZLUASL"
//        )
//    }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())

    return Retrofit.Builder()
            .baseUrl(NetworkData.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MyService::class.java)
//            .getStops(
//                    NetworkData.apiKey,
//                    NetworkData.appId,
//                    "NaptanMetroStation",
//                    lat, lon
//            )
            .getArrivals("940GZZLUASL",NetworkData.apiKey, NetworkData.appId)
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
}

