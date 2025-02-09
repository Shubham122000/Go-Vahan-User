package com.govahanuser.com.model

data class AuthorizedFranchiseDetailsApi(
    val `data`: AuthorizedFranchiseDetailsData,
    val loaderName: List<LoaderName>,
    val message: String,
    val passengerName: List<PassengerName>,
    val status: Int
)

data class AuthorizedFranchiseDetailsData(
    val address: String,
    val email: String,
    val email_id: String,
    val id: Int,
    val mobile_number: String,
    val name: String,
    val number_of_loader_vehicle: Int,
    val number_of_passenger_vehicle: Int,
    val post_image: List<Any>,
    val profile_image: String,
    val total_vehicle: Int,
    val user_id: Int,
    val v_type: Any,
    val vehicle_count: Int,
    val vehicle_numbers: Int
)

data class LoaderName(
    val id: Int,
    val vehicle_name: String,
    val vehicle_type: Int
)

data class PassengerName(
    val id: Int,
    val vehicle_name: String,
    val vehicle_type: Int
)