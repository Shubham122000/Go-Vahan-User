package com.govahan.activities.loadertracktruckdriver

import android.content.ContentValues.TAG
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
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityLoaderTrackTruckDriverBinding
import com.govahan.util.DateFormat
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoaderTrackTruckDriverActivity : BaseActivity() {
    private lateinit var binding : ActivityLoaderTrackTruckDriverBinding
    private val viewModel : LoaderTrackTruckdriverViewModel by viewModels()
   // lateinit var getBookingId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loader_track_truck_driver)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Track Truck Driver")

        val getBookingId = intent.getStringExtra("BookingId")

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.loaderLiveTrackingResponseModel.observe(this) {
            if (it.error == false) {
                toast(it.message!!)
               // finish()
                binding.tvTruckName.text = it.result?.data?.tripDetails?.vehicle?.vehicleName
                binding.tvWheeler.text = "${it.result?.data?.tripDetails?.vehicle?.capacity} Tons"
                binding.tvTruckNumber.text = it.result?.data?.tripDetails?.vehicle?.vehicleNumber
                binding.tvDriverName.text = it.result?.data?.tripDetails?.driver?.name
//                binding.tvDriverType.text = it.data.booking_details[0].driver_name
                binding.tvDrivePhone.text = it.result?.data?.tripDetails?.driver?.mobileNumber
                binding.tvFrom.text = it.result?.data?.tripDetails?.fromTrip
//                binding.tvFromdate.text = DateFormat.TimeFormat(it.data?.from)
                binding.tvTo.text = it.result?.data?.tripDetails?.toTrip
                binding.tvTodate.text = DateFormat.TimeFormat(it.result?.data?.tripDetails?.bookingDateFrom)
                binding.tvTripStartedDate.text = "${it.result?.data?.tripDetails?.bookingDateFrom}(${it.result?.estimatedTime})"
                binding.tvTimeLeft.text = DateFormat.TimeFormat(it.result?.data?.tripDetails?.bookingDateFrom) + " left to reach destination"
                Glide.with(this).load(it.result?.data?.tripDetails?.vehicle?.vehicleImage).into(binding.ivTruck)
                Glide.with(this).load(it.result?.data?.tripDetails?.driver?.profileImage).into(binding.ivDriver)

                //     1=>pending, 2=>accepted, 3=>cancel, 4=> Completed

                if(it.result?.data?.status.toString().equals("1"))
                {
                    binding.tvStatus1.text = "Trip not started."
                    binding.tvStatus1.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.ivDot1.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.vw1.setBackgroundColor(Color.parseColor("#eb8900"))

                }
                else if(it.result?.data?.status.toString().equals("2"))
                {
                    binding.tvStatus1.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.tvStatus2.setTextColor(resources.getColor(R.color.theme_yellow))
                    binding.ivDot1.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.ivDot2.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_tracking_dot1))
                    binding.vw1.setBackgroundColor(Color.parseColor("#eb8900"))
                    binding.vw2.setBackgroundColor(Color.parseColor("#eb8900"))
                }
                else if(it.result?.data?.status.toString().equals("4"))
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



        viewModel.bookingTracking(
            "Bearer " + userPref.user.apiToken,getBookingId.toString()
        )

        Log.d(TAG, "onCreate: " + getBookingId.toString())

    }
}