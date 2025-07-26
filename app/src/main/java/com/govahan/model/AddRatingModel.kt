package com.govahan.model

import com.google.gson.annotations.SerializedName


data class AddRatingModel (

    @SerializedName("status"  ) var status  : Int?     = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : Boolean? = null

)