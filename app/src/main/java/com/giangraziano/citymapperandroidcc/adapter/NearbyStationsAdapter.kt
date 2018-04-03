package com.giangraziano.citymapperandroidcc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.giangraziano.citymapperandroidcc.R
import com.giangraziano.citymapperandroidcc.model.Station


/**
 * Created by giannig on 03/04/18.
 */
class NearbyStationsAdapter(private val onElementClick: (Station, Context) -> Unit)
    : RecyclerView.Adapter<NearbyStationsAdapter.StationsViewHolder>(){

    private lateinit var list: MutableList<Station>


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
        val v = inflater.inflate(R.layout.activity_line_details, parent, false)

        return StationsViewHolder(v)
    }

    fun setData(list :MutableList<Station>){
        this.list = list
        notifyDataSetChanged()
    }

    class StationsViewHolder(private val view: View?) : RecyclerView.ViewHolder(view) {
        private val stationText by lazy{
            view?.findViewById(R.id.station_name) as TextView
        }
        private val arrival1 by lazy{
            view?.findViewById(R.id.live_arrivals1) as TextView
        }
        private val arrival2 by lazy{
            view?.findViewById(R.id.live_arrivals2) as TextView
        }
        private val arrival3 by lazy{
            view?.findViewById(R.id.live_arrivals3) as TextView
        }

        fun setText(station: Station){
            stationText.text = station.stationName
            arrival1.text = station.arrival1
            arrival2.text = station.arrival2
            arrival3.text = station.arrival3
        }

        fun setOnClick(onClick: (Context) -> Unit) {
            view?.setOnClickListener { v -> onClick(v.context) }
        }
    }
}