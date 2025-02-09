package com.govahanuser.com.activities.passengerCompletedBookingDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.activities.invoicesummaryloader.InvoiceSummaryDetailsViewModel
import com.govahanuser.com.activities.invoicesummarypassenger.PassengerInvoiceSummaryDetailsViewModel
import com.govahanuser.com.activities.loadercompletedbookingdetails.LoaderCompletedBookingDetailsViewModel

import com.govahanuser.com.adapters.PassengerCancelTripReasonAdapter
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityPassengerCompletedBookingDetailsBinding

import com.govahanuser.com.model.completedpassengertriphistorymodel.CompletedPassengerHistoryData
import com.govahanuser.com.model.passengercancelreasonmodel.PassengerCancelReasonData
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerCompletedBookingDetailsActivity : BaseActivity() {
    private val viewModel1: PassengerInvoiceSummaryDetailsViewModel by viewModels()

    var downlloadpdf= ""
    var downloadbilty= ""
    var downloadsignature= ""
    private lateinit var binding : ActivityPassengerCompletedBookingDetailsBinding
    private var selectedPassengerCompletedHistoryData: CompletedPassengerHistoryData? = null
    private val viewModel: PassengerCompletedBookingDetailsViewModel by viewModels()
    private val viewModel3: InvoiceSummaryDetailsViewModel by viewModels()
    private var listData: ArrayList<PassengerCancelReasonData> = ArrayList()
    private var passengerCancelReasonAdapter: PassengerCancelTripReasonAdapter? = null
    var crnNumber = ""
    private var listReasonType_id:ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""
    private lateinit var str: String
    private val viewModel2: LoaderCompletedBookingDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_completed_booking_details)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Booking Details")
        binding.podDownload.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(downlloadpdf))
            startActivity(browserIntent)
            toast("Pod Downloaded successfully!")
        }
        binding.bilty.setOnClickListener {
            if (downloadbilty.isNullOrEmpty()){
                toast("No Road Challan/Fine Found")
            }else{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadbilty))
                startActivity(browserIntent)
                toast("Road Challan Downloaded successfully!")
            }

        }
        viewModel3.loaderSendMailResponseModel.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast("Email Sent L successfully!")
            } else {
                toast(it.message!!)
            }
        }
        binding.btnEmail.setOnClickListener(View.OnClickListener {
            viewModel3.sendMailLoaderInvoiceApi("Bearer " + userPref.user.apiToken, selectedPassengerCompletedHistoryData?.bookingId!!.toString())
        })
        viewModel3.loaderDownloadInvoiceResponseModel.observe(this) {
            if (it.status == 1) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                toast("Invoice Downloaded successfully!")
            } else {
                toast(it.message!!)
            }
        }

        viewModel2.UploadDocuments.observe(this) {
            if (it.status == 1) {

                if (it.data.pod==null){
                    downlloadpdf=""
                }
                else{
                    downlloadpdf = it.data.pod.toString()

                }
                if (it.data.builty==null){
                    downloadbilty=""
                }
                else{
                    downloadbilty = it.data.builty.toString()
                }

                if (it.data.signature==null){
                    downloadsignature=""
                }
                else{
                    downloadsignature = it.data.signature.toString()
                }

            } else {
                toast(it.message!!)
            }
        }

        binding.signature.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadsignature))
            startActivity(browserIntent)
            toast("Signature Downloaded successfully!")
        }

        val data = intent.extras
        selectedPassengerCompletedHistoryData = data?.getParcelable<CompletedPassengerHistoryData>("passengerCompletedHistoryDetails")

        Log.d("TAG___", "onCreate: " + selectedPassengerCompletedHistoryData!!.bookingId.toString())

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.passengerCompletedHistoryDetailResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast(it.message!!)

try {
    binding.tvBookingId.text = it.data.booking_id
    binding.tvTripStatus.text = "Completed"
    binding.tvPaid.text = "₹${it.data.fare_total}"
    if (it.data.payment_mode.equals("1")) {
        binding.tvPaymentMethod.setText("Cash")
    } else if (it.data.payment_mode.equals("2")) {
        binding.tvPaymentMethod.setText("Online")
    }
    binding.tvBookingId.text = it.data.booking_id
    binding.tvTripStatus.text = "Completed"
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

}
catch (e:Exception){
    e.printStackTrace()
}
//                binding.tvPartyName.text = it.data.pa


//                binding.tvDate.text = it.data[0].booking_date
//                binding.tvTime.text = it.data[0].bookingTime
//                binding.tvPickup.text = it.data[0].picupLocation
//                binding.tvDropoff.text = it.data[0].dropLocation
//                binding.tvVehicleType.text = it.data[0].vehicleName
//                binding.tvBodyType.text = it.data[0].bodyType
//                binding.tvVehicleNumber.text = it.data[0].vehicleNumber
//                binding.tvTotalLoads.text = it.data[0].vehicleNumber
//                binding.tvPartyName.text = it.data[0].vehicleNumber
//                binding.tvPartyNumber.text = it.data[0].vehicleNumber
//
//
//
//                binding.tvDriverName.text = it.ownerDetails?.name
//                binding.tvDriverPhone.text = it.ownerDetails?.mobile
//                // binding.tvRidesNumber.text = it.data[0].r
//                binding.tvUsername.text = it.userDetails!!.name
//                binding.tvUserphone.text = it.userDetails!!.mobileNumber
//                binding.tvUseremail.text = it.userDetails!!.email
//
//
//



                //  userPref.setDriverId(it.data[0]!!.driverId.toString())

            } else {
                toast(it.message!!)
            }
        }
        binding.btnDownload.setOnClickListener {
            viewModel1.passengerDownloadInvoiceUrlApi("Bearer " + userPref.user.apiToken,   selectedPassengerCompletedHistoryData?.bookingId!!)

        }


        viewModel.passengerOngoingHistoryDetailApi(
            "Bearer " + userPref.user.apiToken,
            selectedPassengerCompletedHistoryData?.bookingId!!
        )


        viewModel.getPassengerCancelReasonListApi("Bearer " + userPref.user.apiToken)




        binding.btnRaiseComplaint.setOnClickListener(View.OnClickListener {

            /*val intent = Intent(this, PassengerRaiseComplaintActivity::class.java)
            intent.putExtra("BookingId", selectedPassengerCompletedHistoryData?.bookingId!!.toString())
            startActivity(intent)*/


        })



        viewModel1.passengerDownloadInvoiceResponseModel.observe(this) {
            if (it.status == 1) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))


//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                toast("Invoice Downloaded successfully!")
            } else {
                toast(it.message!!)
            }
        }





    }







}

