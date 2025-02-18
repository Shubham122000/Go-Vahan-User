package com.govahanuser.com.activities.bookingsuccess

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.activities.bookingconfirmationstatus.BookingConfirmationAndStatusActivity
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityBookingSuccessBinding
import com.govahanuser.com.model.RazorpaystatusResponse
import com.govahanuser.com.model.RazorpaystatusResponseData
import com.govahanuser.com.model.loaderpaymentsuccessmodel.LoaderPaymentSuccessData
import com.govahanuser.com.model.loaderpaymentsuccessmodel.LoaderPaymentSuccessDriver
import com.govahanuser.com.model.loaderpaymentsuccessmodel.LoaderPaymentSuccessUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingSuccessActivity : BaseActivity() {
    private lateinit var binding : ActivityBookingSuccessBinding
//    var modelDataList: BookingLoaderData?=null
    var modelDataList: RazorpaystatusResponseData?=null
//    var Owner: BookingLoaderData?=null
//    var modelUserList: BookingLoaderUser?=null
//    var modelDriverList: BookingLoaderDriver?=null
    var modelRideCode = ""
    var modelOnlineDataList: LoaderPaymentSuccessData?=null
    var modelOnlineUserList: LoaderPaymentSuccessUser?=null
    var modelOnlineDriverList: LoaderPaymentSuccessDriver?=null
    var modelOnlineRideCode = ""
    var paymentMode:String?=null
    var flag:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_success)

        if (intent!=null){
//            if(intent.getStringExtra("cash").equals("CASH")) {
//                modelOnlineDataList = (intent.getStringExtra("modelDataList") as LoaderPaymentSuccessData?)
//                modelOnlineUserList = (intent.getStringExtra("modelUserList") as LoaderPaymentSuccessUser?)
//                modelOnlineDriverList =
//                    (intent.getStringExtra("modelDriverList") as LoaderPaymentSuccessDriver?)
//                modelOnlineRideCode=intent.getStringExtra("modelRideCode").toString()
//                Log.d(TAG, "onCreatepay:"+"Cash")
//                paymentMode="cash"
//           }else
            flag = intent.getStringExtra("flag")
               if(intent.getStringExtra("online").equals("ONLINE")) {
//                modelDataList = (intent.getStringExtra("modelDataList") as BookingLoaderData?)
                modelDataList = (intent.getParcelableExtra<RazorpaystatusResponseData>("modelDataList") as RazorpaystatusResponseData?)
//                Owner = (intent.getStringExtra("Owner") as BookingLoaderData?)
//                modelUserList = (intent.getStringExtra("modelUserList") as BookingLoaderUser?)
//                modelDriverList = (intent.getStringExtra("modelDriverList") as BookingLoaderDriver?)
                modelRideCode = (intent.getStringExtra("modelRideCode1") as String)
                Log.d(TAG, "onCreatepay:"+"Online")
                paymentMode="online"
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, BookingConfirmationAndStatusActivity::class.java)
                .putExtra("flag",flag)
                .putExtra("modelDataList", intent.getParcelableExtra<RazorpaystatusResponseData>("modelDataList") as RazorpaystatusResponseData?)
            )
            finish()
        }, 1000) // 2000 milliseconds = 2 seconds


//        binding.btnNext.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this, BookingConfirmationAndStatusActivity :: class.java)
//                .putExtra("modelDataList", intent.getParcelableExtra<RazorpaystatusResponse>("modelDataList") as RazorpaystatusResponse?)
//                )
//            finish()
//        })
    }
}