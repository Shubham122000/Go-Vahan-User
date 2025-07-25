package com.govahan.activities.loaderraisecomplaint

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahan.data.MainRepository
import com.govahan.model.loaderAddRaiseComplaintModel.LoaderAddRaiseComplaintResponseModel

import com.govahan.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class LoaderRaiseComplaitViewModel  @Inject constructor(private val mainRepository: MainRepository, private val utils : Utils, @ApplicationContext val context: Context) : ViewModel() {

    var addLoaderComplaintResponse = MutableLiveData<LoaderAddRaiseComplaintResponseModel>()
    val progressBarStatus = MutableLiveData<Boolean>()



    fun raiseComplaintApi(token: String,
                          booking_id: String,
                          subject : String,
                          com_message: String
    ) {
        progressBarStatus.value = true
        try {
        viewModelScope.launch {
            val response = mainRepository.raiseComplaintApi(
                token,
                booking_id,subject, com_message
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                addLoaderComplaintResponse.postValue(response.body())
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