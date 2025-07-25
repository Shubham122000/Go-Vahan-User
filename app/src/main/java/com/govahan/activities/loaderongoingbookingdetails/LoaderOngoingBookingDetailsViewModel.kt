package com.govahan.activities.loaderongoingbookingdetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahan.data.MainRepository
import com.govahan.model.loadercancelreasonmodel.LoaderCancelReasonListResponseModel
import com.govahan.model.loadercanceltripmodel.LoaderTripCancelResponseModel
import com.govahan.model.loaderongoinghistorydetailmodel.LoaderOngoingHistoryDetailResponseModel
import com.govahan.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class LoaderOngoingBookingDetailsViewModel   @Inject constructor(private val mainRepository: MainRepository, private val utils : Utils, @ApplicationContext val context: Context) : ViewModel(){

    val loaderOngoingHistoryDetailResponse = MutableLiveData<LoaderOngoingHistoryDetailResponseModel>()
    val progressBarStatus = MutableLiveData<Boolean>()

    val loaderCancelReasonResponse = MutableLiveData<LoaderCancelReasonListResponseModel>()
    val loaderTripCancelResponse = MutableLiveData<LoaderTripCancelResponseModel>()


    fun loaderOngoingHistoryDetailApi(token: String,
                                      booking_id: String) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.loaderOngoingHistoryDetailApi(
                token, booking_id
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                loaderOngoingHistoryDetailResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }





    fun getLoaderCancelReasonListApi(token: String) {
        progressBarStatus.value = true
        try {
            viewModelScope.launch {
                val response = mainRepository.getLoaderCancelReasonListApi(
                    token
                )
                if (response.isSuccessful) {
                    progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                    loaderCancelReasonResponse.postValue(response.body())
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

    fun loaderTripCancelApi(token: String,
                            booking_id: String,
                            reasons_id: String,
                            message: String) {
        progressBarStatus.value = true
        try {
            viewModelScope.launch {
                val response = mainRepository.loaderTripCancelApi(
                    token,
                    booking_id,
                    reasons_id,
                    message
                )
                if (response.isSuccessful) {
                    progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                    loaderTripCancelResponse.postValue(response.body())
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