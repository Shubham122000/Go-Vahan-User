package com.govahanuser.com.activities.passengerOngoingBookingDetails

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanuser.com.R
import com.govahanuser.com.activities.passengerraisecomplaint.PassengerRaiseComplaintActivity
import com.govahanuser.com.activities.passengertracktruckdriver.PassengerTrackTruckDriverActivity
import com.govahanuser.com.adapters.PassengerCancelTripReasonAdapter
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityPassengerOngoingBookingDetailsBinding
import com.govahanuser.com.databinding.BottomSheetCancelTripBinding
import com.govahanuser.com.databinding.BottomSheetRidecancellationBinding
import com.govahanuser.com.model.ongoingPassengerTripHistoryModel.OngoingPassengerHistoryData
import com.govahanuser.com.model.passengercancelreasonmodel.PassengerCancelReasonData
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerOngoingBookingDetailsActivity : BaseActivity() {


    private lateinit var binding : ActivityPassengerOngoingBookingDetailsBinding
    private var selectedPassengerOngoingHistoryData: OngoingPassengerHistoryData? = null
    private val viewModel: PassengerOngoingBookingDetailsViewModel by viewModels()


    private var listData: ArrayList<PassengerCancelReasonData> = ArrayList()
    private var passengerCancelReasonAdapter: PassengerCancelTripReasonAdapter? = null
    var crnNumber = ""
    private var listReasonType_id:ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""
    private lateinit var str: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_ongoing_booking_details)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.header.tvHeaderText.setText("Booking Details")

        binding.btnTracktruck.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,PassengerTrackTruckDriverActivity::class.java)
            intent.putExtra("BookingId", selectedPassengerOngoingHistoryData?.bookingId!!.toString())
            startActivity(intent)
        })

        binding.btnRaiseComplaint.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PassengerRaiseComplaintActivity::class.java)
            intent.putExtra("BookingId", selectedPassengerOngoingHistoryData?.bookingId!!.toString())
            startActivity(intent)
        })





        val data = intent.extras
        selectedPassengerOngoingHistoryData = data?.getParcelable<OngoingPassengerHistoryData>("passengerOngoingHistoryDetails")

        Log.d("TAG___", "onCreate: " + selectedPassengerOngoingHistoryData!!.bookingId.toString())

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.passengerOngoingHistoryDetailResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast(it.message!!)

                binding.tvBookingId.text = it.data.booking_id
                binding.tvTripStatus.text = it.data.booking_status
                binding.tvTotalfare.text = "₹" +it.data.fare_total.toString()

                if (it.data?.payment_mode.equals("1")) {
                    binding.tvPaymentMethod.setText("Cash")
                } else if (it.data?.payment_mode.equals("2")) {
                    binding.tvPaymentMethod.setText("Online")
                } else {
                    binding.tvPaymentMethod.setText("Wallet")
                }

                binding.tvBookingId.text = it.data.booking_id
                binding.tvTripStatus.text = "Ongoing"
                binding.tvDate.text = it.data.booking_date
                binding.tvTime.text = it.data.booking_time.toString()
                binding.tvPickup.text = it.data.picup_location
                binding.tvDropoff.text = it.data.drop_location
                binding.tvVehicleType.text = it.data.vehicle_name
//                binding.tvBodyType.text = it.data.bodyname
                binding.tvVehicleNumber.text = it.data.vehicle_numbers
                binding.tvTotalLoads.text = it.data.capacity.toString()+" Seats"
                    binding.tvDriverName.text = it.data.driver_name
                    binding.tvPartyName.text = it.ownerDetails?.name


                    binding.tvDriverPhone.text = it.ownerDetails?.mobile

                    binding.tvPartyNumber.text = it.ownerName?.mobile_number


                binding.tvUseremail.text = it.userDetails?.email
                binding.tvUsername.text = it.userDetails?.name
                binding.tvUserphone.text = it.userDetails?.mobile_number

                /*if (it.data[0].paymentMode.equals("1")) {
                    binding.tvPaymentMethod.setText("Cash")
                } else if (it.data[0].paymentMode.equals("2")) {
                    binding.tvPaymentMethod.setText("Online")
                }*/



/*

                binding.tvDate.text = it.data[0].bookingDate
                binding.tvTime.text = it.data[0].bookingTime
                binding.tvPickup.text = it.data[0].picupLocation
                binding.tvDropoff.text = it.data[0].dropLocation
                binding.tvVehicleType.text = it.data[0].vehicleName
                binding.tvBodyType.text = it.data[0].bodyType
                binding.tvVehicleNumber.text = it.data[0].vehicleNumber
                binding.tvTotalLoads.text = it.data[0].vehicleNumber
                binding.tvPartyName.text = it.data[0].vehicleNumber
                binding.tvPartyNumber.text = it.data[0].vehicleNumber


                binding.tvDriverName.text = it.ownerDetails!!.name
                binding.tvDriverPhone.text = it.ownerDetails!!.mobile
                // binding.tvRidesNumber.text = it.data[0].r
                binding.tvUsername.text = it.userDetails!!.name
                binding.tvUserphone.text = it.userDetails!!.mobileNumber
                binding.tvUseremail.text = it.userDetails!!.email
*/

                //  userPref.setDriverId(it.data[0]!!.driverId.toString())

            } else {
                toast(it.message)
            }
        }




        viewModel.passengerOngoingHistoryDetailApi(
            "Bearer " + userPref.user.apiToken,
            selectedPassengerOngoingHistoryData?.bookingId!!
        )



        viewModel.getPassengerCancelReasonListApi("Bearer " + userPref.user.apiToken)









        binding.btnCanceltrip.setOnClickListener(View.OnClickListener {

            cancelReasonDialog()


        })


    }




    private fun cancelReasonDialog() {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: BottomSheetCancelTripBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.bottom_sheet_cancel_trip,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)

        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()

        viewModel.passengerCancelReasonResponse.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                listData.clear()
                listData.addAll(it.data)
                passengerCancelReasonAdapter =
                    PassengerCancelTripReasonAdapter(this, listData)
                bindingDialog.rvReasons.apply {
                    adapter = passengerCancelReasonAdapter
                    layoutManager = LinearLayoutManager(context)

                }
            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }








        viewModel.passengerTripCancelResponse.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                crnNumber = it.CRN.toString()
                cDialog.dismiss()
                rideCancelledDialog()

                //   finish()
            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }


        bindingDialog.btnConfirmCancel.setOnClickListener {
            // cDialog.dismiss()

            listReasonType_id.clear()
            bindingDialog.etFeedback.text.toString()
            for (i in 0 until bindingDialog.rvReasons.childCount){
                val cbReason =bindingDialog.rvReasons.getChildAt(i).findViewById(R.id.cb_reason) as CheckBox
                if (cbReason.isChecked){
                    val id=listData[i].id
                    listReasonType_id.add(id.toString())

                    reasontypevalue_id =  listReasonType_id.toString()
                    str= android.text.TextUtils.join(",", listReasonType_id)
                    //   datetypevalue_id = android.text.TextUtils.join(",", listDateType_id);

                }
            }


            viewModel.passengerTripCancelApi(
                "Bearer " + userPref.user.apiToken,
                selectedPassengerOngoingHistoryData?.bookingId!!,
                str,
                bindingDialog.etFeedback.text.toString()
            )
            Log.d("CheckBoxInfo",   selectedPassengerOngoingHistoryData?.bookingId!!+str+bindingDialog.etFeedback.text.toString())

        }
        bindingDialog.ivClose.setOnClickListener {
            cDialog.dismiss()
        }

    }



    private fun rideCancelledDialog() {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: BottomSheetRidecancellationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.bottom_sheet_ridecancellation,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)

        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()

        bindingDialog.tvCRN.setText("Your booking with "+  crnNumber +" has been cancelled successfully.")
        cDialog.dismiss()
        this.finish()

        bindingDialog.ivClose.setOnClickListener {
            cDialog.dismiss()
        }
        bindingDialog.btnOk.setOnClickListener {
            cDialog.dismiss()
        }

    }

}