package com.giangraziano.citymapperandroidcc.network

import com.giangraziano.citymapperandroidcc.DEFAULT_LOCATION_LAT
import com.giangraziano.citymapperandroidcc.DEFAULT_LOCATION_LONG
import com.giangraziano.citymapperandroidcc.model.Arrival
import com.giangraziano.citymapperandroidcc.model.StopPoints
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

//todo: do a factory here i want instantiate network just only once
class Network {

    private val retrofit = Retrofit.Builder()
            .baseUrl(NetworkData.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MyService::class.java)

    fun callServiceArrivalsFromNaptan(): Observable<List<Arrival>> {
        val scheduler = Schedulers.from(Executors.newSingleThreadExecutor())

        return retrofit
                .getArrivals("940GZZLUASL", NetworkData.apiKey, NetworkData.appId)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDataFromLatLon(lat: Double, lon: Double): Observable<StopPoints> {
        return retrofit
                .getStops(
                        NetworkData.apiKey,
                        NetworkData.appId,
                        "NaptanMetroStation",
                        lat,
                        lon
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}