package com.govahan.model.loaderongoinghistorydetailmodel

import com.google.gson.annotations.SerializedName


data class LoaderOngoingHistoryDetailResponseModel(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: LoaderOngoingHistoryData? = LoaderOngoingHistoryData(),
    @SerializedName("ownerDetails") var ownerDetails: LoaderOngoingHistoryOwnerDetails? = LoaderOngoingHistoryOwnerDetails(),
    @SerializedName("userDetails") var userDetails: LoaderOngoingHistoryUserDetails? = LoaderOngoingHistoryUserDetails()
)

data class LoaderOngoingHistoryData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("booking_id") var bookingId: String? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("v_id") var vId: Int? = null,
    @SerializedName("vechicle_id") var vechicleId: String? = null,
    @SerializedName("driver_id") var driverId: Int? = null,
    @SerializedName("picup_location") var picupLocation: String? = null,
    @SerializedName("picup_lat") var picupLat: String? = null,
    @SerializedName("picup_long") var picupLong: String? = null,
    @SerializedName("drop_location") var dropLocation: String? = null,
    @SerializedName("drop_lat") var dropLat: String? = null,
    @SerializedName("drop_long") var dropLong: String? = null,
    @SerializedName("fare") var fare: Any? = null,
    @SerializedName("fare_total") var fareTotal: Any? = null,
    @SerializedName("booking_date") var bookingDate: String? = null,
    @SerializedName("body_type") var bodyType: String? = null,
    @SerializedName("capacity") var capacity: String? = null,
    @SerializedName("distance") var distance: String? = null,
    @SerializedName("vehicle_numbers") var vehicleNumbers: String? = null,
    @SerializedName("booking_time") var bookingTime: String? = null,
    @SerializedName("payment_mode") var paymentMode: String? = null,
    @SerializedName("booking_status") var bookingStatus: String? = null,
    @SerializedName("start_code") var startCode: String? = null,
    @SerializedName("assigned_driver_id") var assignedDriverId: String? = null,
    @SerializedName("cancelation_reason") var cancelationReason: String? = null,
    @SerializedName("payment_status") var paymentStatus: String? = null,
    @SerializedName("invoice_number") var invoiceNumber: String? = null,
    @SerializedName("transaction_id") var transactionId: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("signature") var signature: String? = null,
    @SerializedName("booking_relation_id") var bookingRelationId: Int? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("is_verified_status") var isVerifiedStatus: Int? = null,
    @SerializedName("pod") var pod: String? = null,
    @SerializedName("builty") var builty: String? = null,
    @SerializedName("cancellation_date") var cancellationDate: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("transaction_type") var transactionType: Int? = null,
    @SerializedName("vehicle_name") var vehicleName: String? = null,
    @SerializedName("year_of_model") var yearOfModel: String? = null,
    @SerializedName("vehicle_number") var vehicleNumber: String? = null,
    @SerializedName("bodyname") var bodyname: String? = null,
    @SerializedName("vehicle_owner_name") var vehicleOwnerName: String? = null,
    @SerializedName("driver_name") var driverName: String? = null
)

data class LoaderOngoingHistoryOwnerDetails(
    @SerializedName("email") var email: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("licenseno") var licenseno: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("profile_image") var profileImage: String? = null,
    @SerializedName("v_id") var vId: Int? = null,
)
data class LoaderOngoingHistoryUserDetails(
    @SerializedName("v_id"          ) var vId          : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null
)
//data class LoaderOngoingHistoryDetailResponseModel(
//    val `data`: LoaderOngoingHistoryData,
//    val message: String,
//    val ownerDetails: LoaderOngoingHistoryOwnerDetails,
//    val ownerName: LoaderOngoingHistoryOwnerName,
//    val status: Int,
//    val userDetails: LoaderOngoingHistoryUserDetails
//)
//
//data class LoaderOngoingHistoryData(
//    val assigned_driver_id: Any,
//    val body_type: String,
//    val bodyname: String,
//    val booking_date: String,
//    val booking_id: String,
//    val booking_relation_id: Int,
//    val booking_status: String,
//    val booking_time: Any,
//    val builty: Any,
//    val cancelation_reason: Any,
//    val cancellation_date: Any,
//    val capacity: String,
//    val created_at: String,
//    val currency: String,
//    val distance: String,
//    val driver_id: Int,
//    val drop_lat: String,
//    val drop_location: String,
//    val drop_long: String,
//    val fare: String,
//    val fare_total: Double,
//    val id: Int,
//    val invoice_number: String,
//    val is_verified_status: Int,
//    val no_of_tyres: Int,
//    val payment_mode: String,
//    val payment_status: String,
//    val picup_lat: String,
//    val picup_location: String,
//    val picup_long: String,
//    val pod: Any,
//    val signature: Any,
//    val start_code: String,
//    val transaction_id: String,
//    val type: Int,
//    val updated_at: String,
//    val user_id: Int,
//    val v_id: Int,
//    val vechicle_id: String,
//    val vehicle_image: String,
//    val vehicle_name: String,
//    val vehicle_number: String,
//    val vehicle_numbers: String,
//    val year_of_model: String
//)
//
//data class LoaderOngoingHistoryOwnerDetails(
//    val email: String,
//    val image: String,
//    val licenseno: String,
//    val mobile: String,
//    val name: String,
//    val profile_image: String,
//    val v_id: Int
//)
//
//data class LoaderOngoingHistoryOwnerName(
//    val email: String,
//    val mobile_number: String,
//    val name: String
//)
//
//data class LoaderOngoingHistoryUserDetails(
//    val email: String,
//    val mobile_number: String,
//    val name: String
//)


/*
data class LoaderOngoingHistoryDetailResponseModel (

    @SerializedName("status"       ) var status       : Int?            = null,
    @SerializedName("message"      ) var message      : String?         = null,
    @SerializedName("data"         ) var data         : ArrayList<OngoingHistoryData> = arrayListOf(),
    @SerializedName("ownerDetails" ) var ownerDetails : OngoingHistoryOwnerDetails?   = OngoingHistoryOwnerDetails(),
    @SerializedName("userDetails"  ) var userDetails  : OngoingHistoryUserDetails?    = OngoingHistoryUserDetails()

)

data class OngoingHistoryData (

    @SerializedName("id"                 ) var id                : Int?    = null,
    @SerializedName("booking_id"         ) var bookingId         : String? = null,
    @SerializedName("user_id"            ) var userId            : String? = null,
    @SerializedName("vechicle_id"        ) var vechicleId        : String? = null,
    @SerializedName("driver_id"          ) var driverId          : Int?    = null,
    @SerializedName("picup_location"     ) var picupLocation     : String? = null,
    @SerializedName("picup_lat"          ) var picupLat          : String? = null,
    @SerializedName("picup_long"         ) var picupLong         : String? = null,
    @SerializedName("drop_location"      ) var dropLocation      : String? = null,
    @SerializedName("drop_lat"           ) var dropLat           : String? = null,
    @SerializedName("drop_long"          ) var dropLong          : String? = null,
    @SerializedName("fare"               ) var fare              : String? = null,
    @SerializedName("booking_date"       ) var bookingDate       : String? = null,
    @SerializedName("body_type"          ) var bodyType          : String? = null,
    @SerializedName("capacity"           ) var capacity          : String? = null,
    @SerializedName("distance"           ) var distance          : String? = null,
    @SerializedName("vehicle_numbers"    ) var vehicleNumbers    : String? = null,
    @SerializedName("booking_time"       ) var bookingTime       : String? = null,
    @SerializedName("payment_mode"       ) var paymentMode       : String? = null,
    @SerializedName("booking_status"     ) var bookingStatus     : String? = null,
    @SerializedName("assigned_driver_id" ) var assignedDriverId  : String? = null,
    @SerializedName("cancelation_reason" ) var cancelationReason : String? = null,
    @SerializedName("payment_status"     ) var paymentStatus     : String? = null,
    @SerializedName("invoice_number"     ) var invoiceNumber     : String? = null,
    @SerializedName("transaction_id"     ) var transactionId     : String? = null,
    @SerializedName("currency"           ) var currency          : String? = null,
    @SerializedName("created_at"         ) var createdAt         : String? = null,
    @SerializedName("updated_at"         ) var updatedAt         : String? = null,
    @SerializedName("vehicle_name"       ) var vehicleName       : String? = null,
    @SerializedName("year_of_model"      ) var yearOfModel       : String? = null,
    @SerializedName("vehicle_number"     ) var vehicleNumber     : String? = null,
    @SerializedName("bodyname"           ) var bodyname          : String? = null

)


data class OngoingHistoryOwnerDetails (

    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("email"  ) var email  : String? = null,
    @SerializedName("mobile" ) var mobile : String? = null

)



data class OngoingHistoryUserDetails (

    @SerializedName("v_id"          ) var vId          : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null

)*/
