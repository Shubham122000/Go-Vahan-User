package com.govahan.model.noOfTyrePModel

import com.google.gson.annotations.SerializedName


data class GetNoOfTyrePModel (
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : Result?  = Result()

)
data class Result (
    @SerializedName("data" ) var data : ArrayList<NoOfTyrePData> = arrayListOf()

)
data class NoOfTyrePData (
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("type"       ) var type      : Int?    = null,
    @SerializedName("wheel"      ) var wheel     : String? = null

)
