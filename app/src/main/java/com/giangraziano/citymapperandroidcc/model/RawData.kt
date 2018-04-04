package com.giangraziano.citymapperandroidcc.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ggmodica on 04/04/18.
 */

data class Arrival(
        @SerializedName("id")
        @Expose
        val id: String?,

        @SerializedName("stationName")
        @Expose
        val stationName: String?,

        @SerializedName("lineId")
        @Expose
        val lineId: String?,

        @SerializedName("naptanId")
        @Expose
        val naptanId: String?,

        @SerializedName("platformName")
        @Expose
        val platformName: String?,

        @SerializedName("timeToStation")
        @Expose
        val timeToStation: Long?
)


data class StopPoints(
        @SerializedName("stopPoints")
        @Expose
        val stopPoints: MutableList<StopPoint>
)

data class StopPoint(
        @SerializedName("naptanId")
        @Expose
        val naptanId: String? = "No station id",

        @SerializedName("platformName")
        @Expose
        val platformName: String? = "No platform name",

        @SerializedName("stationNaptan")
        @Expose
        val stationNaptan: String? = "no station Naptan"
)

data class Line(
        val lineId: String,
        val lineName: String,
        val stations: MutableList<StopLine>
)

data class StopLine(val stationId: String )