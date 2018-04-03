package com.giangraziano.citymapperandroidcc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import android.widget.TextView
import com.giangraziano.citymapperandroidcc.adapter.NearbyStationsAdapter
import kotlinx.android.synthetic.main.activity_main.*

val hardCodedLatitude = 51.504831314
val hardCodedLongitude = -0.123499506

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        stations_list.adapter = NearbyStationsAdapter()
        stations_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
