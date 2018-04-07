package com.giangraziano.citymapperandroidcc

import android.Manifest
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.giangraziano.citymapperandroidcc.adapter.NearbyStationsAdapter
import com.giangraziano.citymapperandroidcc.extensions.isPermissionGranted
import com.giangraziano.citymapperandroidcc.extensions.requestPermission
import com.giangraziano.citymapperandroidcc.extensions.shouldShowPermissionRationale
import com.giangraziano.citymapperandroidcc.model.Arrival
import com.giangraziano.citymapperandroidcc.model.StationInfo
import com.giangraziano.citymapperandroidcc.network.Network
import kotlinx.android.synthetic.main.activity_main.*


const val EXTRA_LINE = "line_data"
const val EXTRA_STATION = "station_data"
const val DEFAULT_LOCATION_LAT = 51.5101369
const val DEFAULT_LOCATION_LON = -0.1344048

data class Position(val lat: Double, val lon: Double)

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MAIN_ACTIVITY"
        const val MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 42
        const val MIN_TIME = 40000L
        const val MIN_DISTANCE = 10f
    }

    private val recyclerView: RecyclerView by lazy {
        stations_list.adapter = NearbyStationsAdapter { station, _ -> this.onClick(station) }
        stations_list
    }

    private val network = Network()
    private lateinit var locationManager: LocationManager
    private var started = false
    private var locatedInLondon = true

    private var currentPosition = Position(lat = DEFAULT_LOCATION_LAT, lon = DEFAULT_LOCATION_LON)
        set(value) {
            if (field == value)
                return

            field = value
            serve(field) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (!started) {
            getPosition()
            started = true
            showProgressBar()
        }
    }

    private fun getPosition() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPositionPermission()
        } else {
            Log.i(TAG, "POSITION permission has already been granted.")
            getLocation()
        }
    }

    private fun requestPositionPermission() {
        Log.i(TAG, "POSITION permission has NOT been granted. Requesting permission.")
        if (shouldShowPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Snackbar.make(main_constraint_layout, "Need your position permissions",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", {
                        requestPermission(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                MY_PERMISSIONS_REQUEST_ACCESS_LOCATION
                        )
                    }).show()
        } else {
            requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION
            )
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        try {
            // Request location updates
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE,
                    locationListener
            )
        } catch (ex: SecurityException) {
            Log.d(TAG, "Security Exception, no location available: ${ex.localizedMessage}")
        }
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "I'm located in ${location.longitude}:${location.latitude}")
            currentPosition = Position(
                    lat = location.latitude,
                    lon = location.longitude
            )
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun onClick(stationData: StationInfo) {
        val intent = Intent(this, LineDetailsActivity::class.java)
        intent.putExtra(EXTRA_LINE, stationData.lineId)
        intent.putExtra(EXTRA_STATION, stationData.naptanId)
        startActivity(intent)
    }

    //first start
    private fun serve(position: Position, messageCallback: (String) -> Unit) =
            network.getFirstData(position.lat, position.lon)
                    .subscribe(
                            {
                                parseReceivedRawData(it)
                                hideProgressBar(true)
                                scheduleData(position)
                            },
                            {
                                this.locatedInLondon = false
                                messageCallback(getString(R.string.not_located_in_london))
                                this.currentPosition = Position(lat = DEFAULT_LOCATION_LAT, lon = DEFAULT_LOCATION_LON)
                            }
                    )

    private fun scheduleData(position: Position) =
            network.scheduleData(position.lat, position.lon)
                    ?.subscribe({
                        parseReceivedRawData(it)
                        Log.d(TAG, it.toString())
                    }, {
                        Log.d(TAG, it.localizedMessage)
                    })


    override fun onPause() {
        network.stopScheduler()
        super.onPause()
    }

    private fun parseReceivedRawData(it: List<Arrival>) {
        val parsedData = it
                .sortedBy { it.timeToStation }
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
        Log.d(TAG, it.toString())
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
}
