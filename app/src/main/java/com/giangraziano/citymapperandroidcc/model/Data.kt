package com.giangraziano.citymapperandroidcc.model

/**
 * Created by ggmodica on 04/04/18.
 */
data class StationInfo(
        val naptanId: String? = "no naptanId",
        val stationName: String? = "no station Name",
        val arrival1: Long?,
        val arrival2: Long?,
        val arrival3: Long?
)