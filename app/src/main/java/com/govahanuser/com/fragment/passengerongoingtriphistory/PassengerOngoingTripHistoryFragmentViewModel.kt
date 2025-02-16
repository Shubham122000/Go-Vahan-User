package com.govahanuser.com.fragment.passengerongoingtriphistory


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanuser.com.data.MainRepository
import com.govahanuser.com.model.ongoingPassengerTripHistoryModel.OngoingPassengerTripHistoryResponseModel
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import com.govahanuser.com.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class PassengerOngoingTripHistoryFragmentViewModel @Inject constructor(private val mainRepository: MainRepository, private val utils : Utils, @ApplicationContext val context: Context): ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val getPassengerOngoingHistoryResponse = MutableLiveData<LoaderTripManagementResponseModel>()


//    fun passengerOngoingBookingTripHistoryApi(token: String) {
//        progressBarStatus.value = true
//        try {
//            viewModelScope.launch {
//                val response = mainRepository.passengerOngoingBookingTripHistoryApi(
//                    token
//                )
//                if (response.isSuccessful) {
//                    progressBarStatus.value = false
////                Log.d("TAG", response.body().toString())
//                    getPassengerOngoingHistoryResponse.postValue(response.body())
//                } else {
//                    progressBarStatus.value = false
//                    Log.d("TAG", response.body().toString())
//                }
//
//            }
//
//        }
//
        fun UpComingsTripHistoryApi(
            token: String, forPassenger :String, bookingStatus :String
        ) {
            progressBarStatus.value = true
            viewModelScope.launch {

                val response =
                    mainRepository.UpcomingsTripHistory(token, forPassenger, bookingStatus)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    getPassengerOngoingHistoryResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }
        }
//        catch (e: Exception) {
//            progressBarStatus.value = false
//            e.printStackTrace()
//            if (e is ConnectException) {
//                utils.simpleAlert(
//                    context, "Error", "Please check your Internet connection")
//            } else {
//                utils.simpleAlert(context, "Some Error Occurred", "Please check your Internet connection")
//            }
//
//        }
//    }


//    fun passengerPendingBookingTripHistoryApi(token: String) {
//        progressBarStatus.value = true
//        try {
//            viewModelScope.launch {
//                val response = mainRepository.passengerPendingBookingTripHistoryApi(
//                    token
//                )
//                if (response.isSuccessful) {
//                    progressBarStatus.value = false
////                Log.d("TAG", response.body().toString())
//                    getPassengerOngoingHistoryResponse.postValue(response.body())
//                } else {
//                    progressBarStatus.value = false
//                    Log.d("TAG", response.body().toString())
//                }
//
//            }
//
//        }
//        catch (e: Exception) {
//            progressBarStatus.value = false
//            e.printStackTrace()
//            if (e is ConnectException) {
//                utils.simpleAlert(
//                    context, "Error", "Please check your Internet connection")
//            } else {
//                utils.simpleAlert(context, "Some Error Occurred", "Please check your Internet connection")
//            }
//
//        }
//    }









}