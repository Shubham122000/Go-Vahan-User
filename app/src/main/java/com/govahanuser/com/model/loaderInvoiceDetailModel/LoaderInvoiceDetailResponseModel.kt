package com.govahanuser.com.model.loaderInvoiceDetailModel

import com.google.gson.annotations.SerializedName


data class LoaderInvoiceDetailResponseModel (


    @SerializedName("status"       ) var status       : Int?              = null,
    @SerializedName("message"      ) var message      : String?           = null,
    @SerializedName("data"         ) var data         : LoaderInvoiceDetailData?             = LoaderInvoiceDetailData(),
    @SerializedName("ownerDetails" ) var ownerDetails : LoaderInvoiceDetailDriverName? = LoaderInvoiceDetailDriverName(),
    @SerializedName("userDetails"  ) var userDetails  : LoaderInvoiceDetailUserDetails?      = LoaderInvoiceDetailUserDetails()
)

data class LoaderInvoiceDetailData(
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
    @SerializedName("fare"                ) var fare              : String? = null,
    @SerializedName("fare_total"          ) var fareTotal         : Int?    = null,
    @SerializedName("booking_date"        ) var bookingDate       : String? = null,
    @SerializedName("body_type"           ) var bodyType          : String? = null,
    @SerializedName("capacity"            ) var capacity          : String? = null,
    @SerializedName("distance"            ) var distance          : String? = null,
    @SerializedName("vehicle_numbers"     ) var vehicleNumbers    : String? = null,
    @SerializedName("booking_time"        ) var bookingTime       : String? = null,
    @SerializedName("payment_mode"        ) var paymentMode       : String? = null,
    @SerializedName("booking_status"      ) var bookingStatus     : String? = null,
    @SerializedName("start_code"          ) var startCode         : String? = null,
    @SerializedName("created_at_in_ist"          ) var createdAtInIst         : String? = null,
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
    @SerializedName("transaction_type"    ) var transactionType   : Int?    = null,
    @SerializedName("vehicle_name"        ) var vehicleName       : String? = null,
    @SerializedName("vehicle_type"        ) var vehicleType       : Int?    = null,
    @SerializedName("vehicle_id"          ) var vehicleId         : Int?    = null,
    @SerializedName("start_date"          ) var startDate         : String? = null,
    @SerializedName("year_of_model"       ) var yearOfModel       : String? = null,
    @SerializedName("vehicle_number"      ) var vehicleNumber     : String? = null,
    @SerializedName("bodyname"            ) var bodyname          : String? = null,
    @SerializedName("driver_name"         ) var driverName        : String? = null,
    @SerializedName("mobile_number"       ) var mobileNumber      : String? = null,
    @SerializedName("driver_amount"       ) var driverAmount      : String? = null,
    @SerializedName("fuel_charge"         ) var fuelCharge        : Int?    = null,
    @SerializedName("toll_tax"            ) var tollTax           : Int?    = null,
    @SerializedName("driver_fee"          ) var driverFee         : Int?    = null,
    @SerializedName("freight_amount"      ) var freightAmount     : String? = null,
    @SerializedName("tax"                 ) var tax               : String? = null
)

data class LoaderInvoiceDetailUserDetails(
    @SerializedName("v_id"          ) var vId          : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null
)

data class LoaderInvoiceDetailDriverName(
    @SerializedName("driver_name"          ) var driverName         : String? = null,
    @SerializedName("mobile_number"         ) var mobileNumber        : String? = null,
)