package com.giangraziano.citymapperandroidcc.model

import java.io.Serializable

/**
 * Created by giannig on 03/04/18.
 */


data class Station (
        var data: MutableList<Data>
): Serializable

data class Data (
        val stationName: String,
        val arrival1: String,
        val arrival2: String,
        val arrival3: String
):Serializable