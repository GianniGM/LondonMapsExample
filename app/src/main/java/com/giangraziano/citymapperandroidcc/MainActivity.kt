package com.giangraziano.citymapperandroidcc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.giangraziano.citymapperandroidcc.adapter.NearbyStationsAdapter
import com.giangraziano.citymapperandroidcc.model.Arrival
import com.giangraziano.citymapperandroidcc.model.StationInfo
import com.giangraziano.citymapperandroidcc.network.Network
import kotlinx.android.synthetic.main.activity_main.*


const val EXTRA_STATION = "station_data"
const val DEFAULT_LOCATION_LAT = 51.5101369
const val DEFAULT_LOCATION_LONG = -0.1344048

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        stations_list.adapter = NearbyStationsAdapter { station, _ -> this.onClick(station) }
        stations_list
    }

    private val network = Network()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        serve {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
        progress_bar.visibility = ProgressBar.VISIBLE
        error_text_message.visibility = TextView.GONE
        recyclerView.visibility = RecyclerView.GONE
    }

    private fun hideProgressBar(loadingSuccess: Boolean) {
        progress_bar.visibility = ProgressBar.GONE
        recyclerView.visibility = if (loadingSuccess) RecyclerView.VISIBLE else RecyclerView.GONE
        error_text_message.visibility = if (loadingSuccess) TextView.GONE else TextView.VISIBLE
    }

    private fun onClick(stationData: StationInfo) {
        val intent = Intent(this, LineDetailsActivity::class.java)
        intent.putExtra(EXTRA_STATION, stationData.lineId)
        startActivity(intent)
    }

    private fun serve(messageCallback: (String) -> Unit) {
        showProgressBar()
        network.getData(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LONG)
                .subscribe(
                        {
                            parseReceivedRawData(it)
                            messageCallback("Success :)")

                        },
                        {
                            hideProgressBar(false)
                            messageCallback("Error :(")
                        }
                )
    }

    private fun parseReceivedRawData(it: List<Arrival>) {
        val parsedData = it
                .groupBy { it.naptanId }
                .map {
                    StationInfo(
                            it.key,
                            it.value[0].stationName,
                            it.value[0].lineId,
                            it.value[0].timeToStation,
                            it.value[1].timeToStation,
                            it.value[2].timeToStation
                    )
                }

        (recyclerView.adapter as NearbyStationsAdapter).setData(parsedData as MutableList<StationInfo>)
        hideProgressBar(true)
        Log.d("MAIN_ACTIVITY", it.toString())
    }

//    private fun getFromLatLon(messageCallback: (String) -> Unit) {
//        showProgressBar()
//        network.getDataFromLatLon(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LONG).subscribe(
//                {
//                    getArrivals(it.stopPoints[0].naptanId ?: "940GZZLUASL") {
//                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                    }
//                },
//                {
//                    hideProgressBar(false)
//                    messageCallback("Error :(")
//                }
//        )
//
//    }
//
//    private fun getArrivals(naptanId: String, messageCallback: (String) -> Unit) {
//        showProgressBar()
//        network.callServiceArrivalsFromNaptan(naptanId).subscribe(
//                {
//                    //todo adding right data format
//                    val parsedData = it.groupBy { it.naptanId }
//                            .map {
//                                StationInfo(
//                                        it.key,
//                                        it.value[0].stationName,
//                                        it.value[0].timeToStation,
//                                        it.value[1].timeToStation,
//                                        it.value[2].timeToStation
//                                )
//                            }
//
//                    (recyclerView.adapter as NearbyStationsAdapter).setData(parsedData as MutableList<StationInfo>)
//                    hideProgressBar(true)
//                    Log.d("MAIN_ACTIVITY", it.toString())
//                    messageCallback("Success :)")
//                },
//                {
//                    hideProgressBar(false)
//                    messageCallback("Error :(")
//                }
//        )
//    }

}
