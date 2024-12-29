package com.govahan.com.model.loaderRideCompletedModel



 data class LoaderRideCompletedResponseModel(
    val `data`: List<LoaderRideCompletedData>,
    val message: String,
    val status: Int
)

data class LoaderRideCompletedData(
    val drop_location: String,
    val fare: Double,
    val payment_mode: String,
    val picup_location: String,
    val booking_status: String,
    val profile_image: String
)