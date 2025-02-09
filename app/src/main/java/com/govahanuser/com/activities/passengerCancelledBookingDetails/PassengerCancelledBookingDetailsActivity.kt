package com.govahanuser.com.activities.passengerCancelledBookingDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityPassengerCancelledBookingDetailsBinding
import com.govahanuser.com.model.cancelledpassengertriphistorymodel.CancelledPassengerTripHistoryData
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerCancelledBookingDetailsActivity : BaseActivity() {
    private lateinit var binding : ActivityPassengerCancelledBookingDetailsBinding
    private var selectedPassengerCancelledHistoryData: CancelledPassengerTripHistoryData? = null
    private val viewModel: PassengerCancelledBookingDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_cancelled_booking_details)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Booking Details")

        val data = intent.extras
        selectedPassengerCancelledHistoryData = data?.getParcelable<CancelledPassengerTripHistoryData>("passengerCancelledHistoryDetails")

        Log.d("TAG___", "onCreate: " + selectedPassengerCancelledHistoryData!!.bookingId.toString())

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.passengerCancelledHistoryDetailResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast(it.message!!)

                try {
                    binding.tvBookingId.text = it.data.booking_id
                    binding.tvTripStatus.text = it.data.booking_status
                    binding.tvPaid.text = "₹${it.data.fare_total}"

                    if (it.data.payment_mode.equals("1")) {
                        binding.tvPaymentMethod.setText("Cash")
                    } else if (it.data.payment_mode.equals("2")) {
                        binding.tvPaymentMethod.setText("Online")
                    }

                    binding.tvBookingId.text = it.data.booking_id
                    binding.tvTripStatus.text = "Cancelled"
                    binding.tvDate.text = it.data.booking_date
                    binding.tvPickup.text = it.data.picup_location
                    binding.tvDropoff.text = it.data.drop_location
                    binding.tvVehicleType.text = it.data.vehicle_name
                    binding.tvBodyType.text = it.data.body_type
                    binding.tvVehicleNumber.text = it.data.vehicle_numbers
                    binding.tvTotalLoads.text = it.data.capacity.toString() + " Seats"
                    binding.tvDriverName.text = it.ownerDetails?.name
                    binding.tvDriverPhone.text = it.ownerDetails?.mobile
                    binding.tvPartyName.text = it.ownerName?.name
                    binding.tvPartyNumber.text = it.ownerName?.mobile_number
                    binding.tvUseremail.text = it.userDetails?.email
                    binding.tvUsername.text = it.userDetails?.name
                    binding.tvUserphone.text = it.userDetails?.mobile_number
                    binding.tvTime.text = it.data.booking_time.toString()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            } else {
                toast(it.message)
            }
        }

        viewModel.passengerOngoingHistoryDetailApi(
            "Bearer " + userPref.user.apiToken,
            selectedPassengerCancelledHistoryData?.bookingId!!
        )

    }

}

