package com.govahan.activities.bookingconfirmationstatus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahan.R
import com.govahan.activities.DashboardActivity
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityBookingConfirmationAndStatusBinding
import com.govahan.model.RazorpaystatusResponse
import com.govahan.model.RazorpaystatusResponseData
import com.govahan.prefs.UserPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingConfirmationAndStatusActivity : BaseActivity() {
    private lateinit var binding : ActivityBookingConfirmationAndStatusBinding
    var paymentMode:String?=null
    var flag:String?=null
    var modelDataList: RazorpaystatusResponseData?=null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_confirmation_and_status)
        binding.header.tvHeaderText.text = "Confirmation"
        if (intent!= null){
        modelDataList = (intent.getParcelableExtra<RazorpaystatusResponseData>("modelDataList"))
        }
        if (intent!=null){
            paymentMode=intent.getStringExtra("payment_mode")
            flag=intent.getStringExtra("flag")
            if (flag == "loader"){
                binding.llBodyType.visibility = View.VISIBLE
            }else{
                binding.llBodyType.visibility = View.GONE
            }
                    binding.tvRideCode.text = modelDataList?.rideCode
                    binding.bookingid.text = "Booking Id: "+ modelDataList?.bookingId
//                    binding.tvBookingCreatedate.text = modelDataList?.result?.data?.tripDetails?.bookingDateFrom
                    binding.tvFrom.text = modelDataList?.tripDetails?.fromTrip
                    binding.tvTo.text = modelDataList?.tripDetails?.toTrip
                    binding.tvVehicleNumber.text = modelDataList?.tripDetails?.vehicle?.vehicleNumber
                    binding.tvRating.text = "4.0" /* modelDataList?.result?.data?.rating*/
                    binding.tvType.text = modelDataList?.tripDetails?.vehicle?.bodyType?.name.toString()
                    binding.tvCapacity.text = modelDataList?.tripDetails?.vehicle?.capacity.toString()
                    binding.tvDistance.text = modelDataList?.tripDetails?.totalDistance
                    binding.tvOwner.text = modelDataList?.tripDetails?.user?.name
                    binding.tvBookingdate.text = modelDataList?.tripDetails?.bookingDateFrom
                    binding.tvBookingtime.text = modelDataList?.tripDetails?.time
                    binding.tvVehicleName.text = modelDataList?.tripDetails?.vehicle?.vehicleName
                    binding.tvAmount.text = "₹${modelDataList?.tripDetails?.percentAmount.toString()}"
            if (modelDataList?.paymentDetails?.paymentMode == "2"){
                binding.tvPaymentmode.text = "Wallet"
            }else{
                binding.tvPaymentmode.text = "Online"
            }

                    binding.tvBookingStatus.text = "Partial Payment Completed"
                    Glide.with(this).load(modelDataList?.tripDetails?.vehicle?.vehicleImage).into(binding.vehicleImage)
//                }
//                for (i in 0 until  BookingReviewActivity.bookingLoaderOnlineUserList.size) {
                    binding.tvUsreName.text = userPref.getName()
                    binding.tvUserPhone.text = userPref.getmobile()
                    binding.tvUserEmail.text = userPref.getEmail()
//                }
//                for (i in 0 until  BookingReviewActivity.bookingLoaderOnlineDriverList.size) {
                    binding.tvDriverNam.text = modelDataList?.tripDetails?.driver?.name
                    binding.tvDrivername.text = modelDataList?.tripDetails?.driver?.name
                    binding.tvDriverphone.text = modelDataList?.tripDetails?.driver?.mobileNumber
//                }
//            }
        }


        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        binding.header.tvHeaderText.setText("Booking Confirmation & Status")


        binding.btnBack.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, DashboardActivity::class.java)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            startActivity(intent)
            onBackPressed()
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, DashboardActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        super.onBackPressed()
    }
}