package com.giangraziano.citymapperandroidcc.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ggmodica on 04/04/18.
 */

data class Arrival(
        val id: String?,
        val stationName: String?,
        val lineId: String?,
        val naptanId: String?,
        val platformName: String?,
        val timeToStation: Long?
)

data class StopPoints(
        val stopPoints: MutableList<StopPoint>
)

data class StopPoint(
        val naptanId: String? = "No station id",
        val platformName: String? = "No platform name",
        val stationNaptan: String? = "no station Naptan"
)

data class Line(
        val lineId: String?,
        val lineName: String?,
        val stations: MutableList<StopLine>?
)

data class StopLine(
        val stationId: String?
)