package com.govahanuser.com.model.vehicletypemodel

import com.google.gson.annotations.SerializedName


data class GetVehicleTypeModel (
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : GetVehicleTypeModelResult?  = GetVehicleTypeModelResult()
)
data class GetVehicleTypeModelResult (

    @SerializedName("data" ) var data : ArrayList<VehicleTypeData> = arrayListOf()

)
data class VehicleTypeData (
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("v_type"     ) var vType     : String? = null,
)
