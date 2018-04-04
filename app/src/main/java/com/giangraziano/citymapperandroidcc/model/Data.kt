package com.giangraziano.citymapperandroidcc.model

/**
 * Created by ggmodica on 04/04/18.
 */
data class StationInfo(
        val naptanId: String?,
        val stationName: String?,
        val lineId: String?,
        val arrival1: Long?,
        val arrival2: Long?,
        val arrival3: Long?
)