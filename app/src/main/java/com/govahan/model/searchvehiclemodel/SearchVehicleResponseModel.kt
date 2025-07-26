package com.govahan.model.searchvehiclemodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchVehicleResponseModel (
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : Result?  = Result()
)

data class Result (
    @SerializedName("trips" ) var trips : ArrayList<SearchVehicleData> = arrayListOf()
)
@Parcelize
data class SearchVehicleData (
    @SerializedName("id"                ) var id              : Int?     = null,
    @SerializedName("loader_type"       ) var loaderType      : Int?     = null,
    @SerializedName("user_id"           ) var userId          : Int?     = null,
    @SerializedName("tip_task"          ) var tipTask         : String?  = null,
    @SerializedName("load_caring"       ) var loadCaring      : String?  = null,
    @SerializedName("from_trip"         ) var fromTrip        : String?  = null,
    @SerializedName("pickup_lat"        ) var pickupLat       : String?  = null,
    @SerializedName("pickup_long"       ) var pickupLong      : String?  = null,
    @SerializedName("dropup_lat"        ) var dropupLat       : String?  = null,
    @SerializedName("dropup_long"       ) var dropupLong      : String?  = null,
    @SerializedName("to_trip"           ) var toTrip          : String?  = null,
    @SerializedName("vehicle_id"        ) var vehicleId       : Int?     = null,
    @SerializedName("assign_driver"     ) var assignDriver    : Int?     = null,
    @SerializedName("total_distance"    ) var totalDistance   : String?  = null,
    @SerializedName("billing_type"      ) var billingType     : String?  = null,
    @SerializedName("freight_amount"    ) var freightAmount   : String?  = null,
    @SerializedName("percent_amount"    ) var percentAmount   : String?  = null,
    @SerializedName("remaining_amount"  ) var remainingAmount   : String?  = null,
    @SerializedName("trip_status"       ) var tripStatus      : Int?     = null,
    @SerializedName("booking_date_from" ) var bookingDateFrom : String?  = null,
    @SerializedName("booking_date_to"   ) var bookingDateTo   : String?  = null,
    @SerializedName("time"              ) var time            : String?  = null,
    @SerializedName("driver_amount"     ) var driverAmount    : String?  = null,
    @SerializedName("fuel_charge"       ) var fuelCharge      : Int?     = null,
    @SerializedName("toll_tax"          ) var tollTax         : Int?     = null,
    @SerializedName("driver_fee"        ) var driverFee       : Int?     = null,
    @SerializedName("created_at"        ) var createdAt       : String?  = null,
    @SerializedName("updated_at"        ) var updatedAt       : String?  = null,
    @SerializedName("user"              ) var user            : User?    = User(),
    @SerializedName("driver"            ) var driver          : Driver?  = Driver(),
    @SerializedName("vehicle"           ) var vehicle         : Vehicle? = Vehicle()

) : Parcelable

@Parcelize
data class User (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("country_code"  ) var countryCode  : String? = null

): Parcelable
@Parcelize
data class Driver (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("country_code"  ) var countryCode  : String? = null

): Parcelable
@Parcelize
data class BodyType (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

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
    @SerializedName("seat"             ) var seat            : String?    = null,
    @SerializedName("capacity"         ) var capacity        : String?    = null,
    @SerializedName("image_url"        ) var imageUrl        : String?    = null,
    @SerializedName("vehicle_image"    ) var vehicleImage    : String?    = null,
    @SerializedName("category"         ) var category        : Category?  = Category(),
    @SerializedName("wheels"           ) var wheels          : Wheels?    = Wheels(),
    @SerializedName("model_year"       ) var modelYear       : ModelYear? = ModelYear(),
    @SerializedName("seats"            ) var seats           : String?    = null

): Parcelable
@Parcelize
data class ModelYear (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("year" ) var year : String? = null

): Parcelable


@Parcelize
data class Wheels (

    @SerializedName("id"    ) var id    : Int? = null,
    @SerializedName("wheel" ) var wheel : Int? = null

): Parcelable

@Parcelize
data class Category (

    @SerializedName("id"     ) var id    : Int?    = null,
    @SerializedName("v_type" ) var vType : String? = null

): Parcelable