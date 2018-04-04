package com.giangraziano.citymapperandroidcc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.giangraziano.citymapperandroidcc.adapter.LineAdapter
import com.giangraziano.citymapperandroidcc.network.Network
import kotlinx.android.synthetic.main.activity_line_details.*
import kotlinx.android.synthetic.main.activity_main.*

class LineDetailsActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        line_detail_info.adapter = LineAdapter()
        line_detail_info
    }


    private val network = Network()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_details)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val lineId = intent.extras.get(EXTRA_STATION).toString()

        serve(lineId) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun serve(lineId: String, messageCallback: (String) -> Unit) {
        network.getLinesValue(lineId)
                .subscribe(
                        {
                            (recyclerView.adapter as LineAdapter).setData(it.stations)
                            messageCallback("Success :)")
                        },
                        {
                            messageCallback("Error :(")
                        }
                )
    }
}
