package com.govahanuser.com.util

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object Constants {
//    const val BASE_URL = "https://sanbhavtechnologies.com/gv/transport/public/api/"
    const val BASE_URL = "https://govahanapp.com/public/api/"

//    val BASE_URL = "http://103.154.2.115/~apitest/transport/public/api/"
//    val BASE_URL = "http://172.16.0.233/~apitest/transport/public/api/"

    const val PERMISSION_REQUEST_CODE = 200
    fun checkPermission(context: Context?): Boolean {
        val location =
            ContextCompat.checkSelfPermission((context as Activity?)!!, ACCESS_FINE_LOCATION)
        return location == PackageManager.PERMISSION_GRANTED
    }

}