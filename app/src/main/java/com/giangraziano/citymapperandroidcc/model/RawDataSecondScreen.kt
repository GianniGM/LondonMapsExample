package com.giangraziano.citymapperandroidcc.model

/**
 * Created by giannig on 04/04/18.
 */

data class LineData(
        val lineId: String?,
        val lineName: String?,
        val stations: MutableList<Stations>?,
        val stopPointSequences: MutableList<StopPointSequence>?
)

data class Stations(
        val stationId: String?,
        val stopType: String?,
        val lines: MutableList<Line>?,
        val name: String?
)

data class Line(
        val id: String?,
        val name: String?,
        val fullName: String?
)

data class StopPointSequence(
        val lineId: String?,
        val lineName: String?,
        val direction: String?
)
