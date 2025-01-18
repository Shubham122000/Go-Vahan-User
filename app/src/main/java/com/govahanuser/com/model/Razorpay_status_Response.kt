package com.govahan.com.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import android.content.Intent
import com.govahanuser.com.model.searchvehiclemodel.BodyType
import com.govahanuser.com.model.searchvehiclemodel.Category
import com.govahanuser.com.model.searchvehiclemodel.ModelYear
import com.govahanuser.com.model.searchvehiclemodel.Wheels
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RazorpaystatusResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : RazorpaystatusResult?  = RazorpaystatusResult()
) : Parcelable
@Parcelize
data class RazorpaystatusResult(
    @SerializedName("booking_details" ) var data : RazorpaystatusResponseData? = RazorpaystatusResponseData()
): Parcelable
@Parcelize
data class RazorpaystatusResponseData (
    @SerializedName("id"              ) var id             : Int?            = null,
    @SerializedName("booking_id"      ) var bookingId      : String?         = null,
    @SerializedName("user_id"         ) var userId         : Int?            = null,
    @SerializedName("booking_for"     ) var bookingFor     : Int?            = null,
    @SerializedName("trip_id"         ) var tripId         : Int?            = null,
    @SerializedName("status"          ) var status         : Int?            = null,
    @SerializedName("booking_time"    ) var bookingTime    : String?         = null,
    @SerializedName("ride_code"       ) var rideCode       : String?         = null,
    @SerializedName("created_at"      ) var createdAt      : String?         = null,
    @SerializedName("updated_at"      ) var updatedAt      : String?         = null,
    @SerializedName("payment_details" ) var paymentDetails : PaymentDetails? = PaymentDetails(),
    @SerializedName("trip_details"    ) var tripDetails    : TripDetails?    = TripDetails()

) : Parcelable
@Parcelize
data class TripDetails (

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("loader_type"       ) var loaderType      : Int?    = null,
    @SerializedName("user_id"           ) var userId          : Int?    = null,
    @SerializedName("tip_task"          ) var tipTask         : String? = null,
    @SerializedName("load_caring"       ) var loadCaring      : String? = null,
    @SerializedName("from_trip"         ) var fromTrip        : String? = null,
    @SerializedName("pickup_lat"        ) var pickupLat       : String? = null,
    @SerializedName("pickup_long"       ) var pickupLong      : String? = null,
    @SerializedName("dropup_lat"        ) var dropupLat       : String? = null,
    @SerializedName("dropup_long"       ) var dropupLong      : String? = null,
    @SerializedName("to_trip"           ) var toTrip          : String? = null,
    @SerializedName("vehicle_id"        ) var vehicleId       : Int?    = null,
    @SerializedName("assign_driver"     ) var assignDriver    : Int?    = null,
    @SerializedName("total_distance"    ) var totalDistance   : String? = null,
    @SerializedName("billing_type"      ) var billingType     : String? = null,
    @SerializedName("freight_amount"    ) var freightAmount   : String? = null,
    @SerializedName("percent_amount"    ) var percentAmount   : String? = null,
    @SerializedName("trip_status"       ) var tripStatus      : Int?    = null,
    @SerializedName("booking_date_from" ) var bookingDateFrom : String? = null,
    @SerializedName("booking_date_to"   ) var bookingDateTo   : String? = null,
    @SerializedName("time"              ) var time            : String? = null,
    @SerializedName("fuel_charge"       ) var fuelCharge      : Int?    = null,
    @SerializedName("toll_tax"          ) var tollTax         : Int?    = null,
    @SerializedName("driver_fee"        ) var driverFee       : Int?    = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null,
    @SerializedName("vehicle"           ) var vehicle         : Vehicle? = Vehicle(),
    @SerializedName("user"              ) var user            : User?    = User(),
    @SerializedName("driver"            ) var driver          : RazorpayDriver? = RazorpayDriver()

) : Parcelable
@Parcelize
data class RazorpayDriver (

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("role"              ) var role            : Int?    = null,
    @SerializedName("user_type"         ) var userType        : Int?    = null,
    @SerializedName("v_id"              ) var vId             : Int?    = null,
    @SerializedName("service_id"        ) var serviceId       : String? = null,
    @SerializedName("vendor_driver"     ) var vendorDriver    : String? = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("franchise_name"    ) var franchiseName   : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("mobile_number"     ) var mobileNumber    : String? = null,
    @SerializedName("country_code"      ) var countryCode     : String? = null,
    @SerializedName("address"           ) var address         : String? = null,
    @SerializedName("profile_image"     ) var profileImage    : String? = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String? = null,
    @SerializedName("device_token"      ) var deviceToken     : String? = null,
    @SerializedName("device_name"       ) var deviceName      : String? = null,
    @SerializedName("device_type"       ) var deviceType      : String? = null,
    @SerializedName("device_id"         ) var deviceId        : String? = null,
    @SerializedName("otp"               ) var otp             : String? = null,
    @SerializedName("status"            ) var status          : Int?    = null,
    @SerializedName("complete_profile"  ) var completeProfile : Int?    = null,
    @SerializedName("api_token"         ) var apiToken        : String? = null,
    @SerializedName("amount"            ) var amount          : String? = null,
    @SerializedName("is_approved"       ) var isApproved      : Int?    = null,
    @SerializedName("last_login"        ) var lastLogin       : String? = null,
    @SerializedName("login_status"      ) var loginStatus     : Int?    = null,
    @SerializedName("latitude"          ) var latitude        : String? = null,
    @SerializedName("longtitude"        ) var longtitude      : String? = null,
    @SerializedName("gst_number"        ) var gstNumber       : String? = null,
    @SerializedName("licence_number"    ) var licenceNumber   : String? = null,
    @SerializedName("experience"        ) var experience      : String? = null,
    @SerializedName("referal_code"      ) var referalCode     : String? = null,
    @SerializedName("refered_by"        ) var referedBy       : String? = null,
    @SerializedName("id_proof_doc"      ) var idProofDoc      : String? = null,
    @SerializedName("driving_licence"   ) var drivingLicence  : String? = null,
    @SerializedName("whatsapp_status"   ) var whatsappStatus  : Int?    = null,
    @SerializedName("sms_email"         ) var smsEmail        : Int?    = null,
    @SerializedName("payment_status"    ) var paymentStatus   : Int?    = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null

) : Parcelable

@Parcelize
data class PaymentDetails (

    @SerializedName("booking_id"     ) var bookingId     : Int?    = null,
    @SerializedName("payment_mode"   ) var paymentMode   : String? = null,
    @SerializedName("invoice"        ) var invoice       : String? = null,
    @SerializedName("transaction_id" ) var transactionId : String? = null,
    @SerializedName("status"         ) var status        : Int?    = null,
    @SerializedName("currency"       ) var currency      : String? = null,
    @SerializedName("amount"         ) var amount        : String? = null,
    @SerializedName("updated_at"     ) var updatedAt     : String? = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("id"             ) var id            : Int?    = null

): Parcelable
@Parcelize
data class Vehicle (

    @SerializedName("id"               ) var id              : Int?       = null,
    @SerializedName("vehicle_name"     ) var vehicleName     : String?    = null,
    @SerializedName("vehicle_number"   ) var vehicleNumber   : String?    = null,
    @SerializedName("vehicle_category" ) var vehicleCategory : Int?       = null,
    @SerializedName("year_of_model"    ) var yearOfModel     : Int?       = null,
    @SerializedName("height"           ) var height          : Int?       = null,
    @SerializedName("no_of_tyres"      ) var noOfTyres       : Int?       = null,
    @SerializedName("body_type"        ) var bodyType        : BodyType?  = BodyType(),
    @SerializedName("vehicle_image"    ) var vehicleImage    : String?    = null,
    @SerializedName("seat"             ) var seat            : String?    = null,
    @SerializedName("capacity"         ) var capacity        : String?    = null,
    @SerializedName("category"         ) var category        : Category?  = Category(),
    @SerializedName("wheels"           ) var wheels          : Wheels?    = Wheels(),
    @SerializedName("model_year"       ) var modelYear       : ModelYear? = ModelYear(),
    @SerializedName("seats"            ) var seats           : String?    = null

) : Parcelable
@Parcelize
data class User (
    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("role"              ) var role            : Int?    = null,
    @SerializedName("user_type"         ) var userType        : Int?    = null,
    @SerializedName("v_id"              ) var vId             : String? = null,
    @SerializedName("service_id"        ) var serviceId       : String? = null,
    @SerializedName("vendor_driver"     ) var vendorDriver    : String? = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("franchise_name"    ) var franchiseName   : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("mobile_number"     ) var mobileNumber    : String? = null,
    @SerializedName("country_code"      ) var countryCode     : String? = null,
    @SerializedName("address"           ) var address         : String? = null,
    @SerializedName("profile_image"     ) var profileImage    : String? = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String? = null,
    @SerializedName("device_token"      ) var deviceToken     : String? = null,
    @SerializedName("device_name"       ) var deviceName      : String? = null,
    @SerializedName("device_type"       ) var deviceType      : String? = null,
    @SerializedName("device_id"         ) var deviceId        : String? = null,
    @SerializedName("otp"               ) var otp             : String? = null,
    @SerializedName("status"            ) var status          : Int?    = null,
    @SerializedName("complete_profile"  ) var completeProfile : Int?    = null,
    @SerializedName("api_token"         ) var apiToken        : String? = null,
    @SerializedName("amount"            ) var amount          : String? = null,
    @SerializedName("is_approved"       ) var isApproved      : Int?    = null,
    @SerializedName("last_login"        ) var lastLogin       : String? = null,
    @SerializedName("login_status"      ) var loginStatus     : Int?    = null,
    @SerializedName("latitude"          ) var latitude        : String? = null,
    @SerializedName("longtitude"        ) var longtitude      : String? = null,
    @SerializedName("gst_number"        ) var gstNumber       : String? = null,
    @SerializedName("licence_number"    ) var licenceNumber   : String? = null,
    @SerializedName("experience"        ) var experience      : String? = null,
    @SerializedName("referal_code"      ) var referalCode     : String? = null,
    @SerializedName("refered_by"        ) var referedBy       : String? = null,
    @SerializedName("id_proof_doc"      ) var idProofDoc      : String? = null,
    @SerializedName("driving_licence"   ) var drivingLicence  : String? = null,
    @SerializedName("whatsapp_status"   ) var whatsappStatus  : Int?    = null,
    @SerializedName("sms_email"         ) var smsEmail        : Int?    = null,
    @SerializedName("payment_status"    ) var paymentStatus   : Int?    = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null

) : Parcelable