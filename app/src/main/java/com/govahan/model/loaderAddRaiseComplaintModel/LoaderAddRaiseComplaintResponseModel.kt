package com.govahan.model.loaderAddRaiseComplaintModel

import com.google.gson.annotations.SerializedName


data class LoaderAddRaiseComplaintResponseModel (

    @SerializedName("error"  ) var error  : Boolean?    = null,
    @SerializedName("status_code"  ) var statusCode  : Int?    = null,
    @SerializedName("message" ) var message : String? = null

)