package com.govahan.com.activities.editprofile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahan.com.data.MainRepository
import com.govahan.com.model.EditProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel   @Inject constructor(private val mainRepository: MainRepository): ViewModel(){

    val updateUserProfileResponse = MutableLiveData<EditProfileResponse>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun updateUserProfile(token : String, name : String ,email : String, mobile : String,
                             address : String, device_token : String, device_type : String, device_id : String, profile_image : MultipartBody.Part?) {

        val name1: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val email1: RequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobile1: RequestBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
        val address1: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val device_token1: RequestBody = device_token.toRequestBody("text/plain".toMediaTypeOrNull())
        val device_type1: RequestBody = device_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val device_id1: RequestBody = device_id.toRequestBody("text/plain".toMediaTypeOrNull())
//
//        val userName: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
//        val emailN : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
//        val mobileN : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), mobile)
//        val addressN : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), address)
//        val device_tokenN : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), device_token)
//        val device_typeN : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), device_type)
//        val device_idN : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), device_id)

//        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.updateUserProfile(
                    token,
                    name1, email1, mobile1, address1, device_token1,
                    device_type1, device_id1, profile_image!!
                )
                if (response.isSuccessful) {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                    updateUserProfileResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

}