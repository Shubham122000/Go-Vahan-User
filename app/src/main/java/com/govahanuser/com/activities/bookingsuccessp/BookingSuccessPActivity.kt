package com.govahanuser.com.activities.bookingsuccessp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.activities.passengers.bookingconfirmationstatus.BookingConfirmationStatusPActivity
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityBookingSuccessPactivityBinding
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerData
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerDriver
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerUser
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessData
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessDriver
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingSuccessPActivity : BaseActivity() {
    private lateinit var binding : ActivityBookingSuccessPactivityBinding
    var modelDataList:BookingPassengerData?=null
    var modelUserList:BookingPassengerUser?=null
    var modelDriverList:BookingPassengerDriver?=null
    var modelOnlineDataList:PassengerPaymentSuccessData?=null
    var modelOnlineUserList:PassengerPaymentSuccessUser?=null
    var modelOnlineDriverList:PassengerPaymentSuccessDriver?=null
    var ppaymentMode:String?=null
    var flag:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_success_pactivity)

        val extras = intent.extras
        if (extras != null) {
            modelDataList = (extras.getSerializable("modelDataList") as BookingPassengerData?)
            modelUserList = (extras.getSerializable("modelUserList") as BookingPassengerUser?)
            modelDriverList = (extras.getSerializable("modelDriverList") as BookingPassengerDriver?)
        }


        if (intent!=null){
            flag = intent.getStringExtra("flag").toString()
            if(intent.getStringExtra("cash").equals("CASH")) {
                modelOnlineDataList = (intent.getStringExtra("modelDataList") as PassengerPaymentSuccessData?)
                modelOnlineUserList = (intent.getStringExtra("modelUserList") as PassengerPaymentSuccessUser?)
                modelOnlineDriverList =
                 (intent.getStringExtra("modelDriverList") as PassengerPaymentSuccessDriver?)
               // modelOnlineRideCode=intent.getStringExtra("modelRideCode").toString()
                Log.d(ContentValues.TAG, "onCreatepay:"+"Cash")
                ppaymentMode="cash"
            }else if(intent.getStringExtra("online").equals("ONLINE")) {
                modelDataList = (intent.getStringExtra("modelDataList") as BookingPassengerData?)
                modelUserList = (intent.getStringExtra("modelUserList") as BookingPassengerUser?)
                modelDriverList =
                    (intent.getStringExtra("modelDriverList") as BookingPassengerDriver?)
               // modelRideCode = (intent.getStringExtra("modelRideCode1") as String)
                Log.d(ContentValues.TAG, "onCreatepay:"+"Online")
                ppaymentMode="online"
            }
        }

        binding.btnNext.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, BookingConfirmationStatusPActivity :: class.java)
            .putExtra("modelDataList", modelDataList)
            .putExtra("modelUserList", modelUserList)
            .putExtra("modelDriverList", modelDriverList)
            .putExtra("payment_mode",ppaymentMode)
            .putExtra("flag",flag)
            )

            finish()
        })
    }

    override fun onBackPressed() {

    }
}