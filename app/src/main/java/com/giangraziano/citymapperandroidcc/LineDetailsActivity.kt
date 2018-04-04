package com.giangraziano.citymapperandroidcc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.giangraziano.citymapperandroidcc.adapter.LineAdapter
import com.giangraziano.citymapperandroidcc.network.Network
import kotlinx.android.synthetic.main.activity_line_details.*

class LineDetailsActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        line_detail_info.adapter = LineAdapter(this)
        line_detail_info
    }

    private val network = Network()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_details)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val lineId = intent.extras.get(EXTRA_LINE).toString()
        val stationId = intent.extras.get(EXTRA_STATION).toString()

        serve(lineId, stationId) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun serve(lineId: String, stationId: String, messageCallback: (String) -> Unit) {
        network.getLinesValue(lineId)
                .subscribe(
                        {
                            val r = recyclerView.adapter as LineAdapter
                            val list = it.stations?.filter {
                                it.lines?.any { it.id == lineId }?: false
                            }
                            r.setData(list?.toMutableList(), stationId)
                            messageCallback("Success :)")
                        },
                        {
                            Log.e("LINE_DETAIL_ACTIVITY", it.localizedMessage.toString())
                            messageCallback("Error : ${it.localizedMessage}")
                        }
                )
    }
}
