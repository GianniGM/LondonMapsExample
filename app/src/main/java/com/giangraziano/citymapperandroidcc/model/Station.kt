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
        val stationName: String?,

        @SerializedName("icsCode")
        @Expose
        val arrival1: String?,

        @SerializedName("smsCode")
        @Expose
        val arrival2: String?,

        @SerializedName("stopType")
        @Expose
        val arrival3: String?
):Serializable