package com.giangraziano.citymapperandroidcc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.giangraziano.citymapperandroidcc.R
import com.giangraziano.citymapperandroidcc.common.converToTimeString
import com.giangraziano.citymapperandroidcc.model.StationInfo


/**
 * Created by giannig on 03/04/18.
 */
class NearbyStationsAdapter(private val onElementClick: (StationInfo, Context) -> Unit)
    : RecyclerView.Adapter<NearbyStationsAdapter.StationsViewHolder>() {

    private lateinit var list: MutableList<StationInfo>


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StationsViewHolder?, position: Int) {
        val station = list[position]
        holder?.setText(station)
        holder?.setOnClick { context -> onElementClick(station, context) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StationsViewHolder {
        val ctx = parent?.context
        val inflater = LayoutInflater.from(ctx)
        val v = inflater.inflate(R.layout.main_activity_line_item, parent, false)

        return StationsViewHolder(v)
    }

    fun setData(list: MutableList<StationInfo>) {
        this.list = list
        notifyDataSetChanged()
    }

    class StationsViewHolder(private val view: View?) : RecyclerView.ViewHolder(view) {

        private val stationText = view?.findViewById(R.id.station_name) as TextView
        private val arrival1 = view?.findViewById(R.id.live_arrivals1) as TextView
        private val arrival2 = view?.findViewById(R.id.live_arrivals2) as TextView
        private val arrival3 = view?.findViewById(R.id.live_arrivals3) as TextView

        fun setText(data: StationInfo?) {
            stationText.text = data?.stationName
            arrival1.text = data?.arrival1?.converToTimeString()
            arrival2.text = data?.arrival2?.converToTimeString()
            arrival3.text = data?.arrival3?.converToTimeString()
        }

        fun setOnClick(onClick: (Context) -> Unit) {
            view?.setOnClickListener { v -> onClick(v.context) }
        }
    }
}