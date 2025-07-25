package com.govahan.activities.ridecompleted

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahan.R
import com.govahan.activities.writereview.WriteAreviewActivity
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityRideCompletedBinding
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RideCompletedActivity : BaseActivity() {
    private lateinit var binding : ActivityRideCompletedBinding
    lateinit var saveRating : String
    private val viewModel: RideCompletedViewModel by viewModels()
    lateinit var bookingIdd: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ride_completed)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.tvReview.setOnClickListener(View.OnClickListener {

            saveRating = binding.rating.rating.toString()

            val intent = Intent(this, WriteAreviewActivity::class.java)
            intent.putExtra("putRating", saveRating)
            startActivity(intent)
        })

        bookingIdd = intent.getStringExtra("loaderTripBookingId").toString()

        viewModel.loaderRideCompletedResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
               try{
                   binding.tvDropLocation.text = it.data[0].drop_location
                   binding.tvPickLocation.text = it.data[0].picup_location
                   binding.tvAmount.text = it.data[0].fare.toString()

                   if (it.data[0].payment_mode.equals("1")) {
                       binding.tvPaymentModeType.setText("Cash")
                   } else if (it.data[0].payment_mode.equals("2")) {
                       binding.tvPaymentModeType.setText("Online")
                   } else {
                       binding.tvPaymentModeType.setText("Wallet")
                   }

                   if (it.data[0].booking_status == "1"){
                       binding.status.text  = "Ride Pending"
                   }else if (it.data[0].booking_status == "2"){
                       binding.status.text = "Ride Ongoing"
                   }else if (it.data[0].booking_status == "4"){
                       binding.status.text = "Ride Completed"
                   }else if (it.data[0].booking_status == "3"){
                       binding.status.text = "Ride Cancelled"
                }
//                   binding.tvPaymentModeType.text = it.data.payment_mode
                   Glide.with(this).load(it.data[0].profile_image).placeholder(R.drawable.user_image_place_holder).into(binding.imgUser)

               }
               catch (e:Exception){
                   e.printStackTrace()
               }



               // userPref.setDriverId(it.data[0]!!.driverId.toString())

            } else {
                toast(it.message!!)
            }
        }

        viewModel.loaderRideCompletedApi("Bearer " + userPref.user.apiToken, bookingIdd)

        Log.d(TAG, "onCreate: "+bookingIdd)



    }
}