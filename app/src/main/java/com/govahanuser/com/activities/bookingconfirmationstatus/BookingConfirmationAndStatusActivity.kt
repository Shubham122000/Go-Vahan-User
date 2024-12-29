package com.govahanuser.com.activities.bookingconfirmationstatus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahan.com.R
import com.govahan.com.activities.DashboardActivity
import com.govahan.com.baseClasses.BaseActivity
import com.govahan.com.databinding.ActivityBookingConfirmationAndStatusBinding
import com.govahan.com.model.RazorpaystatusResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingConfirmationAndStatusActivity : BaseActivity() {
    private lateinit var binding : ActivityBookingConfirmationAndStatusBinding
    var paymentMode:String?=null
    var flag:String?=null
    var modelDataList: RazorpaystatusResponse?=null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_confirmation_and_status)
        binding.header.tvHeaderText.text = "Confirmation"
        if (intent!= null){
        modelDataList = (intent.getParcelableExtra<RazorpaystatusResponse>("modelDataList"))
        }
        if (intent!=null){
            paymentMode=intent.getStringExtra("payment_mode")
            flag=intent.getStringExtra("flag")
//                if (paymentMode.equals("online")){
//                for (i in 0 until  BookingReviewActivity.bookingLoaderOnlineDataList.size){
//                    binding.tvRideCode.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].ride_code
//                    binding.bookingid.text = "Booking Id: #"+ BookingReviewActivity.bookingLoaderOnlineDataList[i].bookingId
//                    binding.tvBookingCreatedate.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].createdAt
//                    binding.tvFrom.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].picupLocation
//                    binding.tvTo.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].dropLocation
//                    binding.tvVehicleNumber.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].vehicleNumbers
//                    binding.tvRating.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].rating
//                    binding.tvType.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].bodyType
//                    binding.tvCapacity.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].capacity
//                    binding.tvDistance.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].distance
//                    binding.tvOwner.text = BookingReviewActivity.owner[i].name
//                    binding.tvBookingdate.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].bookingDate
//                    binding.tvBookingtime.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].bookingTime
//                    binding.tvVehicleName.text = BookingReviewActivity.bookingLoaderOnlineDataList[i].vehicle_name
//                    binding.tvAmount.text = "₹${BookingReviewActivity.bookingLoaderOnlineDataList[i].fare}"
//                    binding.tvPaymentmode.text = "Online"
//                    binding.tvBookingStatus.text = "Partial Payment Completed"

            if (flag == "loader"){
                binding.llBodyType.visibility = View.VISIBLE
            }else{
                binding.llBodyType.visibility = View.GONE
            }
                    binding.tvRideCode.text = modelDataList?.data?.startCode
                    binding.bookingid.text = "Booking Id: #"+ modelDataList?.data?.bookingId
                    binding.tvBookingCreatedate.text = modelDataList?.data?.createdAt
                    binding.tvFrom.text = modelDataList?.data?.picupLocation
                    binding.tvTo.text = modelDataList?.data?.dropLocation
                    binding.tvVehicleNumber.text = modelDataList?.data?.vehicleNumbers
                    binding.tvRating.text = "4.0" /* modelDataList?.data?.rating*/
                    binding.tvType.text = modelDataList?.data?.bodyType
                    binding.tvCapacity.text = modelDataList?.data?.capacity
                    binding.tvDistance.text = modelDataList?.data?.distance
                    binding.tvOwner.text = modelDataList?.data?.owner_name
                    binding.tvBookingdate.text = modelDataList?.data?.bookingDate
                    binding.tvBookingtime.text = modelDataList?.data?.bookingTime
                    binding.tvVehicleName.text = modelDataList?.data?.vehicle_name
                    binding.tvAmount.text = "₹${modelDataList?.data?.fare}"
                    binding.tvPaymentmode.text = "Online"
                    binding.tvBookingStatus.text = "Partial Payment Completed"
                    Glide.with(this).load(modelDataList?.data?.main_image).into(binding.vehicleImage)
//                }
//                for (i in 0 until  BookingReviewActivity.bookingLoaderOnlineUserList.size) {
                    binding.tvUsreName.text = modelDataList?.data?.user_name
                    binding.tvUserPhone.text = modelDataList?.data?.user_mobile_no
                    binding.tvUserEmail.text = modelDataList?.data?.user_email
//                }
//                for (i in 0 until  BookingReviewActivity.bookingLoaderOnlineDriverList.size) {
                    binding.tvDriverNam.text = modelDataList?.data?.driver_name
                    binding.tvDrivername.text = modelDataList?.data?.driver_name
                    binding.tvDriverphone.text = modelDataList?.data?.driver_mobile_no
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