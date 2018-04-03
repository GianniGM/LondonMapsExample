package com.giangraziano.citymapperandroidcc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.giangraziano.citymapperandroidcc.model.Data
import com.giangraziano.citymapperandroidcc.model.Station

class LineDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_details)

        val station = intent.extras.get(EXTRA_STATION) as Data
    }
}
