package com.govahan.activities.passengertracktruckdriver

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahan.R
import com.govahan.activities.trackmap.TrackMapActivity
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityPassengerTrackTruckDriverBinding
import com.govahan.util.DateFormat
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerTrackTruckDriverActivity : BaseActivity() {

    private lateinit var binding : ActivityPassengerTrackTruckDriverBinding
    private val viewModel : PassengerTrackTruckdriverViewModel by viewModels()
    // lateinit var getBookingId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_track_truck_driver)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Track Truck Driver")

        binding.llLiveTracking.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TrackMapActivity::class.java)
            startActivity(intent)
        })




        val getBookingId = intent.getStringExtra("BookingId")





        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }




        viewModel.passengerLiveTrackingResponseModel.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                // finish()

                binding.tvTruckName.text =  it.data?.vehicleName
                binding.tvWheeler.text = "${it.data?.capacity} Tons"
                binding.tvTruckNumber.text = it.data?.vehicleNumbers
                binding.tvDriverName.text = it.data?.driverName
                binding.tvDriverType.text = it.data?.driverName
                binding.tvDrivePhone.text = it.data?.mobile
                binding.tvFrom.text = it.data?.picupLocation
                binding.tvFromdate.text = DateFormat.TimeFormat(it.data?.bookingDate)
                binding.tvTo.text = it.data?.dropLocation
                binding.tvTodate.text = DateFormat.TimeFormat(it.data?.bookingDate)
                binding.tvTripStartedDate.text = "${it.expectedDeliverDate?.date}(${it.expectedDeliverDate?.time})"
                binding.tvTimeLeft.text = DateFormat.TimeFormat(it.data?.bookingDate) + " left to reach destination"
                Glide.with(this).load(it.data?.vehicleImage).into(binding.ivTruck)
                Glide.with(this).load(it.data?.driverImage).into(binding.ivDriver)
                binding.tvTimeLeft.text = it.expectedDeliverDate?.date + " left to reach destination"

                //     1=>pending, 2=>accepted, 3=>cancel, 4=> Completed

                if(it.data?.bookingStatus.toString().equals("1"))
                {
                    binding.tvStatus1.text = "Trip not started."
                    binding.tvStatus1.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.ivDot1.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.vw1.setBackgroundColor(Color.parseColor("#eb8900"))

                }
                else if(it.data?.bookingStatus.toString().equals("2"))
                {
                    binding.tvStatus1.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.tvStatus2.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.ivDot1.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.ivDot2.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.vw1.setBackgroundColor(Color.parseColor("#eb8900"))
                    binding.vw2.setBackgroundColor(Color.parseColor("#eb8900"))
                }

                else if(it.data?.bookingStatus.toString().equals("4"))
                {
                    binding.tvStatus1.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.tvStatus2.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.tvStatus3.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.ivDot1.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.ivDot2.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.ivDot3.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.vw1.setBackgroundColor(Color.parseColor("#eb8900"))
                    binding.vw2.setBackgroundColor(Color.parseColor("#eb8900"))
                    binding.vw3.setBackgroundColor(Color.parseColor("#eb8900"))
                }







            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }



        viewModel.passengerLiveTrackingApi(
            "Bearer " + userPref.user.apiToken,getBookingId.toString()
        )

        Log.d(ContentValues.TAG, "onCreate: " + getBookingId.toString())

    }
}