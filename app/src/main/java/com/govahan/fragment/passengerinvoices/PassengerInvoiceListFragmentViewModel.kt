package com.govahan.fragment.passengerinvoices


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahan.data.MainRepository
import com.govahan.model.passengerinvoicelistmodel.PassengerInvoiceListResponseModel
import com.govahan.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class PassengerInvoiceListFragmentViewModel @Inject constructor(private val mainRepository: MainRepository, private val utils : Utils, @ApplicationContext val context: Context): ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val getPassengerInvoiceListResponse = MutableLiveData<PassengerInvoiceListResponseModel>()


    fun passengerInvoiceListApi(token: String) {
        progressBarStatus.value = true
        try {
            viewModelScope.launch {
                val response = mainRepository.passengerInvoiceListApi(
                    token
                )
                if (response.isSuccessful) {
                    progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                    getPassengerInvoiceListResponse.postValue(response.body())
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