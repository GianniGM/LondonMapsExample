package com.giangraziano.citymapperandroidcc.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by giannig on 03/04/18.
 */


data class Station (
        @SerializedName("stopPoints")
        @Expose
        var data: MutableList<Data>
): Serializable

data class Data (
        @SerializedName("platformName")
        @Expose
        val stationName: String? = "Station name",

        @SerializedName("icsCode")
        @Expose
        val arrival1: String? = "arr 1",

        @SerializedName("smsCode")
        @Expose
        val arrival2: String? = "arr 2",

        @SerializedName("stopType")
        @Expose
        val arrival3: String = "arr 3"
):Serializable