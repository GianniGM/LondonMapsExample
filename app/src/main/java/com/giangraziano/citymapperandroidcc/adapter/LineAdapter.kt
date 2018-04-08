package com.giangraziano.citymapperandroidcc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.giangraziano.citymapperandroidcc.R
import com.giangraziano.citymapperandroidcc.extensions.setFromResources
import com.giangraziano.citymapperandroidcc.model.Stations


/**
 * Created by ggmodica on 04/04/18.
 */
class LineAdapter(private val context: Context)
    : RecyclerView.Adapter<LineAdapter.LineViewHolder>() {

    private var list: MutableList<Stations> = arrayListOf()
    private var selectedId: String? = ""
    private lateinit var stationId: String

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LineViewHolder {
        val ctx = parent?.context
        val inflater = LayoutInflater.from(ctx)
        val v = inflater.inflate(R.layout.line_detail_info_item, parent, false)

        return LineViewHolder(v)
    }

    fun setData(stations: MutableList<Stations>?, stationId: String) {
        if (stations != null) {
            this.list = stations
            this.stationId = stationId
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder?, position: Int) {
        val element = list[position]

        holder?.setImage(stationId == element.stationId)
        holder?.setText(
                element.name.toString()
        ) ?: context.getString(R.string.no_line_name)

        holder?.setOnClick { _ ->
            if (element.stationId == selectedId) {
                selectedId = ""
                true
            } else {
                selectedId = element.stationId
                false
            }
        }
    }

    inner class LineViewHolder(private val view: View?) : RecyclerView.ViewHolder(view) {

        private val imageCurrentPosition by lazy {
            val image = view?.findViewById(R.id.stop_image_current) as ImageView
            image.setFromResources("redLinePoint.png")
            image
        }

        private val imageLineBus by lazy {
            val image = view?.findViewById(R.id.stop_image_line) as ImageView
            image.setFromResources("redLine.jpg")
            image
        }

        private val stationName = view?.findViewById(R.id.stop_name_text) as TextView
        private val stationNameSelected = view?.findViewById(R.id.stop_name_text_selected) as TextView

        fun setImage(isCurrent: Boolean) {
            if (isCurrent) {
                imageCurrentPosition.visibility = View.VISIBLE
                imageLineBus.visibility = View.GONE
            } else {
                imageCurrentPosition.visibility = View.GONE
                imageLineBus.visibility = View.VISIBLE
            }
        }

        fun setText(text: String) {
            stationNameSelected.text = text
            stationName.text = text
        }

        //todo this can't works because you are using a RECYCLER VIEW, find another method
        fun setOnClick(onClick: (Context) -> Boolean) {
            view?.setOnClickListener { v ->
                if (onClick(v.context)) {
                    stationNameSelected.visibility = TextView.VISIBLE
                    stationName.visibility = TextView.GONE
                } else {
                    stationName.visibility = TextView.VISIBLE
                    stationNameSelected.visibility = TextView.GONE
                }
            }
        }
    }
}