package com.giangraziano.citymapperandroidcc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.giangraziano.citymapperandroidcc.adapter.LineAdapter
import kotlinx.android.synthetic.main.activity_line_details.*
import kotlinx.android.synthetic.main.activity_main.*

class LineDetailsActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy {
        line_detail_info.adapter = LineAdapter(this)
        line_detail_info
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_details)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val station = intent.extras.get(EXTRA_STATION)
    }
}
