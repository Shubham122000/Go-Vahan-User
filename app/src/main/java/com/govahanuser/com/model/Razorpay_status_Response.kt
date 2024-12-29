package com.govahan.com.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import android.content.Intent
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RazorpaystatusResponse(
    @SerializedName("data"          ) var data         : RazorpaystatusResponseData?    = RazorpaystatusResponseData(),
    @SerializedName("message"       ) var message      : String?  = null,
    @SerializedName("status"        ) var status       : Int? = null,
//    @SerializedName("response_code" ) var responseCode : Int?     = null
) : Parcelable

@Parcelize
data class RazorpaystatusResponseData (

    @SerializedName("id"                  ) var id                : Int?    = null,
    @SerializedName("booking_id"          ) var bookingId         : String? = null,
    @SerializedName("user_id"             ) var userId            : Int?    = null,
    @SerializedName("v_id"                ) var vId               : Int?    = null,
    @SerializedName("vechicle_id"         ) var vechicleId        : String? = null,
    @SerializedName("driver_id"           ) var driverId          : Int?    = null,
    @SerializedName("picup_location"      ) var picupLocation     : String? = null,
    @SerializedName("picup_lat"           ) var picupLat          : String? = null,
    @SerializedName("picup_long"          ) var picupLong         : String? = null,
    @SerializedName("drop_location"       ) var dropLocation      : String? = null,
    @SerializedName("drop_lat"            ) var dropLat           : String? = null,
    @SerializedName("drop_long"           ) var dropLong          : String? = null,
    @SerializedName("fare"                ) var fare              : String?    = null,
    @SerializedName("fare_total"          ) var fareTotal         : String? = null,
    @SerializedName("booking_date"        ) var bookingDate       : String? = null,
    @SerializedName("body_type"           ) var bodyType          : String? = null,
    @SerializedName("capacity"            ) var capacity          : String? = null,
    @SerializedName("distance"            ) var distance          : String? = null,
    @SerializedName("vehicle_numbers"     ) var vehicleNumbers    : String? = null,
    @SerializedName("booking_time"        ) var bookingTime       : String? = null,
    @SerializedName("payment_mode"        ) var paymentMode       : String? = null,
    @SerializedName("booking_status"      ) var bookingStatus     : String? = null,
    @SerializedName("start_code"          ) var startCode         : String? = null,
    @SerializedName("assigned_driver_id"  ) var assignedDriverId  : String? = null,
    @SerializedName("cancelation_reason"  ) var cancelationReason : String? = null,
    @SerializedName("payment_status"      ) var paymentStatus     : String? = null,
    @SerializedName("invoice_number"      ) var invoiceNumber     : String? = null,
    @SerializedName("transaction_id"      ) var transactionId     : String? = null,
    @SerializedName("currency"            ) var currency          : String? = null,
    @SerializedName("signature"           ) var signature         : String? = null,
    @SerializedName("booking_relation_id" ) var bookingRelationId : Int?    = null,
    @SerializedName("type"                ) var type              : Int?    = null,
    @SerializedName("is_verified_status"  ) var isVerifiedStatus  : Int?    = null,
    @SerializedName("pod"                 ) var pod               : String? = null,
    @SerializedName("builty"              ) var builty            : String? = null,
    @SerializedName("cancellation_date"   ) var cancellationDate  : String? = null,
    @SerializedName("created_at"          ) var createdAt         : String? = null,
    @SerializedName("updated_at"          ) var updatedAt         : String? = null,
    @SerializedName("driver_name"          ) var driver_name         : String? = null,
    @SerializedName("user_name"          ) var user_name         : String? = null,
    @SerializedName("user_email"          ) var user_email         : String? = null,
    @SerializedName("user_mobile_no"          ) var user_mobile_no         : String? = null,
    @SerializedName("mobile_number"          ) var mobile_number         : String? = null,
    @SerializedName("driver_email"          ) var driver_email         : String? = null,
    @SerializedName("driver_mobile_no"          ) var driver_mobile_no         : String? = null,
    @SerializedName("owner_name"          ) var owner_name         : String? = null,
    @SerializedName("vehicle_name"          ) var vehicle_name         : String? = null,
    @SerializedName("main_image"          ) var main_image         : String? = null,
    @SerializedName("transaction_type"    ) var transactionType   : Int?    = null
) : Parcelable
