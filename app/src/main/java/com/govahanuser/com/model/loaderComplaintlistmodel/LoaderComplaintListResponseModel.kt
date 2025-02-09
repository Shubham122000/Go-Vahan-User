package com.govahanuser.com.model.loaderComplaintlistmodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.govahanuser.com.model.PaymentDetails
import com.govahanuser.com.model.TripDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoaderComplaintListResponseModel (
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : LoaderComplaintResult?  = LoaderComplaintResult()

): Parcelable
@Parcelize
data class LoaderComplaintResult (
    @SerializedName("data" ) var data : ArrayList<LoaderComplaintData> = arrayListOf()
): Parcelable
@Parcelize
data class LoaderComplaintData (
    @SerializedName("id"              ) var id             : Int?                      = null,
    @SerializedName("user_id"         ) var userId         : Int?                      = null,
    @SerializedName("booking_id"      ) var bookingId      : String?                   = null,
    @SerializedName("message"         ) var message        : String?                   = null,
    @SerializedName("confirmation"    ) var confirmation   : Int?                      = null,
    @SerializedName("admin_reply"     ) var adminReply     : String?                   = null,
    @SerializedName("status"          ) var status         : Int?                      = null,
    @SerializedName("trip_details"    ) var tripDetails    : TripDetails?              = TripDetails(),
    @SerializedName("payment_details" ) var paymentDetails : ArrayList<PaymentDetails> = arrayListOf()

): Parcelable
