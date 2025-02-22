package com.govahanuser.com.activities.loaderongoingbookingdetails

import android.app.Dialog
import android.content.ContentValues.TAG
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
import com.govahanuser.com.activities.loaderraisecomplaint.LoaderRaiseComplaintActivity
import com.govahanuser.com.activities.loadertracktruckdriver.LoaderTrackTruckDriverActivity
import com.govahanuser.com.adapters.LoaderCancelTripReasonAdapter
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityBookingDetailsBinding
import com.govahanuser.com.databinding.BottomSheetCancelTripBinding
import com.govahanuser.com.databinding.BottomSheetRidecancellationBinding
import com.govahanuser.com.model.loadercancelreasonmodel.LoaderCancelReasonData
import com.govahanuser.com.model.ongoingloadertriphistorymodel.OngoingLoaderHistoryData
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoaderOngoingBookingDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityBookingDetailsBinding
    private var selectedLoaderOngoingHistoryData: LoaderTripManagementData? = null
    private val viewModel: LoaderOngoingBookingDetailsViewModel by viewModels()
    private var listData: ArrayList<LoaderCancelReasonData> = ArrayList()
    private var loaderCancelReasonAdapter: LoaderCancelTripReasonAdapter? = null
    var crnNumber = ""
    private var listReasonType_id: ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""
    private lateinit var str: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_details)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Booking Details")

        binding.btnTracktruck.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoaderTrackTruckDriverActivity::class.java)
            intent.putExtra("BookingId", selectedLoaderOngoingHistoryData?.bookingId!!.toString())
            startActivity(intent)
        })

        binding.btnRaiseComplaint.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoaderRaiseComplaintActivity::class.java)
            intent.putExtra("BookingId", selectedLoaderOngoingHistoryData?.bookingId!!.toString())
            startActivity(intent)
        })

        val data = intent.extras
        selectedLoaderOngoingHistoryData =
            data?.getParcelable<LoaderTripManagementData>("loaderOngoingHistoryDetails")

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

//        viewModel.loaderOngoingHistoryDetailResponse.observe(this) {
//            if (it.status == 1) {
//                // toast("booking Successful")
//                try {
//                    if (it.data == null) {
//                        toast("no data")
//                    } else {
//                        toast(it.message)
        binding.tvBookingId.text = selectedLoaderOngoingHistoryData?.bookingId
        if (selectedLoaderOngoingHistoryData?.status == 1) {
            binding.tvTripStatus.text = "Pending"
        } else {
            binding.tvTripStatus.text = "Ongoing"
        }

        binding.tvTotalfare.text =
            "₹ ${selectedLoaderOngoingHistoryData?.tripDetails?.freightAmount.toString()}"
        binding.tvDate.text = selectedLoaderOngoingHistoryData?.tripDetails?.bookingDateTo
        binding.tvPickup.text = selectedLoaderOngoingHistoryData?.tripDetails?.fromTrip
        binding.tvDropoff.text = selectedLoaderOngoingHistoryData?.tripDetails?.toTrip
        binding.tvVehicleType.text =
            selectedLoaderOngoingHistoryData?.tripDetails?.vehicle?.vehicleName
        binding.tvBodyType.text =
            selectedLoaderOngoingHistoryData?.tripDetails?.vehicle?.bodyType?.name
        binding.tvVehicleNumber.text =
            selectedLoaderOngoingHistoryData?.tripDetails?.vehicle?.vehicleNumber
        binding.tvTotalLoads.text = selectedLoaderOngoingHistoryData?.tripDetails?.vehicle?.capacity

        if (selectedLoaderOngoingHistoryData?.paymentDetails?.get(0)?.paymentMode.equals("1")) {
            binding.tvPaymentMethod.setText("Cash")
        } else if (selectedLoaderOngoingHistoryData?.paymentDetails?.get(0)?.paymentMode.equals("2")) {
            binding.tvPaymentMethod.setText("Online")
        } else {
            binding.tvPaymentMethod.setText("Wallet")
        }

        binding.tvDriverName.text = selectedLoaderOngoingHistoryData?.tripDetails?.driver?.name
        binding.tvDriverPhone.text =
            selectedLoaderOngoingHistoryData?.tripDetails?.driver?.mobileNumber
        binding.tvPartyName.text = selectedLoaderOngoingHistoryData?.tripDetails?.driver?.name
        binding.tvPartyNumber.text = selectedLoaderOngoingHistoryData?.tripDetails?.driver?.name
        binding.tvUseremail.text = userPref.getEmail()
        binding.tvUsername.text = userPref.getName()
        binding.tvUserphone.text = userPref.getmobile()
//                        binding.tvPartyName.text = it.data?.bookingTime.toString()

        /*if(it.data[i].rideCancelStatus.toString().equals("0")){
            binding.llBtnCancel.visibility = View.VISIBLE
        }
        else if(it.rideCancelStatus.toString().equals("1")){
            binding.llBtnCancel.visibility = View.GONE
        }*/


//                    }


//                    Log.d(
//                        TAG, "onCreatehis: " + "Bearer " + userPref.user.apiToken +
//                                selectedLoaderOngoingHistoryData?.bookingId!!
//                    )
//                }catch (e:Exception){
//                    e.printStackTrace()
//                }
//
//            } else {
//                toast(it.message!!)
//            }
//        }

//        viewModel.loaderOngoingHistoryDetailApi(
//            "Bearer " + userPref.user.apiToken,
//            selectedLoaderOngoingHistoryData?.bookingId!!
//        )

//        viewModel.getLoaderCancelReasonListApi("Bearer " + userPref.user.apiToken)

        binding.btnRaiseComplaint.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, LoaderRaiseComplaintActivity::class.java)
            intent.putExtra("BookingId", selectedLoaderOngoingHistoryData?.bookingId!!.toString())
            startActivity(intent)
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

        viewModel.loaderCancelReasonResponse.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                listData.clear()
                listData.addAll(it.data)
                loaderCancelReasonAdapter =
                    LoaderCancelTripReasonAdapter(this, listData)
                bindingDialog.rvReasons.apply {
                    adapter = loaderCancelReasonAdapter
                    layoutManager = LinearLayoutManager(context)

                }
            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }

        viewModel.loaderTripCancelResponse.observe(this) {
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
            listReasonType_id.clear()
            bindingDialog.etFeedback.text.toString()
            for (i in 0 until bindingDialog.rvReasons.childCount) {
                val cbReason =
                    bindingDialog.rvReasons.getChildAt(i).findViewById(R.id.cb_reason) as CheckBox
                if (cbReason.isChecked) {
                    val id = listData[i].id
                    listReasonType_id.add(id.toString())

                    reasontypevalue_id = listReasonType_id.toString()
                    str = android.text.TextUtils.join(",", listReasonType_id)
                }
            }


            viewModel.loaderTripCancelApi(
                "Bearer " + userPref.user.apiToken,
                selectedLoaderOngoingHistoryData?.bookingId!!,
                str,
                bindingDialog.etFeedback.text.toString()
            )
            Log.d(
                "CheckBoxInfo",
                selectedLoaderOngoingHistoryData?.bookingId!! + str + bindingDialog.etFeedback.text.toString()
            )

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

        bindingDialog.tvCRN.setText("Your booking with " + crnNumber + " has been cancelled successfully.")
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