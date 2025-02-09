package com.govahanuser.com.model.passengerlivetrackingmodel

import com.google.gson.annotations.SerializedName


data class PassengerLiveTrackingResponseModel(
    @SerializedName("status"                ) var status              : Int?                 = null,
    @SerializedName("message"               ) var message             : String?              = null,
    @SerializedName("data"                  ) var data                : Data?                = Data(),
    @SerializedName("distance"              ) var distance            : String?              = null,
    @SerializedName("expected_deliver_date" ) var expectedDeliverDate : ExpectedDeliverDate? = ExpectedDeliverDate()
)

data class ExpectedDeliverDate(
    @SerializedName("date" ) var date : String? = null,
    @SerializedName("time" ) var time : String? = null
)

data class Data(
    @SerializedName("booking_status"  ) var bookingStatus  : String? = null,
    @SerializedName("fare"            ) var fare           : Int?    = null,
    @SerializedName("picup_location"  ) var picupLocation  : String? = null,
    @SerializedName("drop_location"   ) var dropLocation   : String? = null,
    @SerializedName("booking_date"    ) var bookingDate    : String? = null,
    @SerializedName("booking_time"    ) var bookingTime    : String? = null,
    @SerializedName("body_type"       ) var bodyType       : String? = null,
    @SerializedName("capacity"        ) var capacity       : String? = null,
    @SerializedName("vehicle_numbers" ) var vehicleNumbers : String? = null,
    @SerializedName("payment_mode"    ) var paymentMode    : String? = null,
    @SerializedName("user_id"         ) var userId         : Int?    = null,
    @SerializedName("driver_id"       ) var driverId       : Int?    = null,
    @SerializedName("vechicle_id"     ) var vechicleId     : String? = null,
    @SerializedName("picup_lat"       ) var picupLat       : String? = null,
    @SerializedName("picup_long"      ) var picupLong      : String? = null,
    @SerializedName("drop_lat"        ) var dropLat        : String? = null,
    @SerializedName("drop_long"       ) var dropLong       : String? = null,
    @SerializedName("vehicle_name"    ) var vehicleName    : String? = null,
    @SerializedName("vehicle_image"   ) var vehicleImage   : String? = null,
    @SerializedName("driver_name"     ) var driverName     : String? = null,
    @SerializedName("mobile"          ) var mobile         : String? = null,
    @SerializedName("driver_image"    ) var driverImage    : String? = null
)
//
//data class PassengerLiveTrackingResponseData(
//    val body_type: String,
//    val booking_date: String,
//    val booking_status: String,
//    val booking_time: Any,
//    val capacity: String,
//    val driver_id: Int,
//    val driver_image: String,
//    val driver_name: String,
//    val drop_lat: String,
//    val drop_location: String,
//    val drop_long: String,
//    val fare: String,
//    val mobile: String,
//    val payment_mode: String,
//    val picup_lat: String,
//    val picup_location: String,
//    val picup_long: String,
//    val user_id: Int,
//    val vechicle_id: String,
//    val vehicle_image: String,
//    val vehicle_name: String,
//    val vehicle_numbers: String
//
//)