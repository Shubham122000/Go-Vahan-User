package com.govahanuser.com.model.passengerAddRaiseComplaintModel

import com.google.gson.annotations.SerializedName


data class PassengerAddRaiseComplaintResponseModel (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null

)