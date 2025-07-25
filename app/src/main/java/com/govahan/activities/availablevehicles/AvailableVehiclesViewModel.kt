package com.govahan.activities.availablevehicles

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahan.data.MainRepository
import com.govahan.model.searchvehiclemodel.SearchVehicleResponseModel
import com.govahan.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AvailableVehiclesViewModel  @Inject constructor(private val mainRepository: MainRepository, private val utils : Utils, @ApplicationContext val context: Context) : ViewModel() {

    var availableVehicleListResponse = MutableLiveData<SearchVehicleResponseModel>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun searchLoaderVehicleApi(
        token : String,
        pickup_lat: String,
        pickup_long: String,
        dropup_lat: String,
        dropup_long: String,
        loader_type: String,
        vehicle_category: String,
//        body_type: String,
//        seat: String,
//        wheels: String,
        booking_date: String,
        booking_time: String,
                            ) {
        progressBarStatus.value = true
        try {
        viewModelScope.launch {
            val response = mainRepository.searchLoaderVehicleApi(
                token,
                pickup_lat, pickup_long, dropup_lat, dropup_long, loader_type, vehicle_category,/* body_type, seat, wheels,*/ booking_date, booking_time



            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                availableVehicleListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }

        }

        }
        catch (e: Exception) {
            progressBarStatus.value = false
            e.printStackTrace()
            if (e is ConnectException) {
                utils.simpleAlert(
                    context, "Error", "Please check your Internet connection")
            } else {
                utils.simpleAlert(context, "Some Error Occurred", "Please check your Internet connection")
            }

        }
    }
}