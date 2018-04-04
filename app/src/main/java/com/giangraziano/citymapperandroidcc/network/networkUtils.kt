package com.giangraziano.citymapperandroidcc.network

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

fun callServiceArrivalsFromNaptan(naptanId: String): Observable<List<Arrival>> {
    val scheduler = Schedulers.from(Executors.newSingleThreadExecutor())


    return Retrofit.Builder()
            .baseUrl(NetworkData.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MyService::class.java)

            .getArrivals(naptanId,NetworkData.apiKey, NetworkData.appId)
//            .getStops(
//                    NetworkData.apiKey,
//                    NetworkData.appId,
//                    "NaptanMetroStation",
//                    lat, lon
//            )
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
}

