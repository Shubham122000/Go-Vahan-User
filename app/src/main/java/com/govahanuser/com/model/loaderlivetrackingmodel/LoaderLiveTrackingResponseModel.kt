package com.govahanuser.com.model.loaderlivetrackingmodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.govahanuser.com.model.TripDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoaderLiveTrackingResponseModel(
//    @SerializedName("status"                ) var status              : Int?                 = null,
//    @SerializedName("message"               ) var message             : String?              = null,
//    @SerializedName("data"                  ) var data                : LoaderLiveTrackingResponseData? = LoaderLiveTrackingResponseData(),
//    @SerializedName("distance"              ) var distance            : String?              = null,
//    @SerializedName("expected_deliver_date" ) var expectedDeliverDate : ExpectedDeliverDate? = ExpectedDeliverDate()

    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : Result?  = Result()
) : Parcelable
@Parcelize
data class Result (

    @SerializedName("data"           ) var data          : LoaderLiveTrackingResponseData?   = LoaderLiveTrackingResponseData(),
    @SerializedName("distance"       ) var distance      : String? = null,
    @SerializedName("estimated_time" ) var estimatedTime : String? = null

) : Parcelable
@Parcelize
data class ExpectedDeliverDate(
    @SerializedName("date" ) var date : String? = null,
    @SerializedName("time" ) var time : String? = null
) : Parcelable

//data class Data(
//    val booking_details: LoaderLiveTrackingResponseData = LoaderLiveTrackingResponseData()
//)
@Parcelize
data class LoaderLiveTrackingResponseData(
    @SerializedName("id"           ) var id          : Int?         = null,
    @SerializedName("booking_id"   ) var bookingId   : String?      = null,
    @SerializedName("user_id"      ) var userId      : Int?         = null,
    @SerializedName("booking_for"  ) var bookingFor  : Int?         = null,
    @SerializedName("trip_id"      ) var tripId      : Int?         = null,
    @SerializedName("status"       ) var status      : Int?         = null,
    @SerializedName("booking_time" ) var bookingTime : String?      = null,
    @SerializedName("ride_code"    ) var rideCode    : String?      = null,
    @SerializedName("created_at"   ) var createdAt   : String?      = null,
    @SerializedName("updated_at"   ) var updatedAt   : String?      = null,
    @SerializedName("trip_details" ) var tripDetails : TripDetails? = TripDetails()
) : Parcelable