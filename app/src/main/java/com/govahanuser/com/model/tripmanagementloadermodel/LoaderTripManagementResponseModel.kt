package com.govahanuser.com.model.tripmanagementloadermodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.govahan.com.model.PaymentDetails
import com.govahan.com.model.TripDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoaderTripManagementResponseModel(

    @SerializedName("error") var error: Boolean? = null,
    @SerializedName("status_code") var statusCode: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: Result? = Result()
) : Parcelable

@Parcelize
data class Result(

    @SerializedName("data") var data: ArrayList<LoaderTripManagementData> = arrayListOf(),
    @SerializedName("page") var page: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("total") var total: Int? = null

) : Parcelable

@Parcelize
data class LoaderTripManagementData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("booking_id") var bookingId: String? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("booking_for") var bookingFor: Int? = null,
    @SerializedName("trip_id") var tripId: Int? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("booking_time") var bookingTime: String? = null,
    @SerializedName("ride_code") var rideCode: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("payment_details") var paymentDetails: ArrayList<PaymentDetails> = arrayListOf(),
    @SerializedName("trip_details") var tripDetails: TripDetails? = TripDetails()

) : Parcelable