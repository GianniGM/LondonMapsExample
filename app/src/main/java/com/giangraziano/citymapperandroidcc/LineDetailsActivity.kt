package com.giangraziano.citymapperandroidcc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.giangraziano.citymapperandroidcc.adapter.LineAdapter
import com.giangraziano.citymapperandroidcc.network.NetworkUtils
import kotlinx.android.synthetic.main.activity_line_details.*

class LineDetailsActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        line_detail_info.adapter = LineAdapter(this)
        line_detail_info
    }

    private val network = NetworkUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_details)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val lineId = intent.extras.get(EXTRA_LINE).toString()
        val stationId = intent.extras.get(EXTRA_STATION).toString()

        showProgressBar()
        serve(lineId, stationId) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun serve(lineId: String, stationId: String, messageCallback: (String) -> Unit) {
        showProgressBar()
        network.getLinesValue(lineId)
                .subscribe(
                        {
                            val r = recyclerView.adapter as LineAdapter
                            val list = it.stations?.filter {
                                it.lines?.any { it.id == lineId } ?: false
                            }
                            r.setData(list?.toMutableList(), stationId)
                            messageCallback("Success :)")
                            hideProgressBar(true)
                        },
                        {
                            Log.e("LINE_DETAIL_ACTIVITY", it.localizedMessage.toString())
                            messageCallback("Error : ${it.localizedMessage}")
                            hideProgressBar(false)
                        }
                )
    }

    private fun showProgressBar() {
        progress_bar_details.visibility = ProgressBar.VISIBLE
        recyclerView.visibility = RecyclerView.GONE
    }

    private fun hideProgressBar(loadingSuccess: Boolean) {
        progress_bar_details.visibility = ProgressBar.GONE
        recyclerView.visibility = if (loadingSuccess) RecyclerView.VISIBLE else RecyclerView.GONE
    }
}
