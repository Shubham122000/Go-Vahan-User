package com.govahan.com.activities.loaderCancelledbookingdetails

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.com.R
import com.govahan.com.baseClasses.BaseActivity
import com.govahan.com.databinding.ActivityLoaderCancelledBookingDetailsBinding
import com.govahan.com.model.cancelledloadertriphistorymodel.CancelledLoaderTripHistoryData
import com.govahan.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoaderCancelledBookingDetailsActivity : BaseActivity() {
    private lateinit var binding : ActivityLoaderCancelledBookingDetailsBinding
    private var selectedLoaderCancelledHistoryData: CancelledLoaderTripHistoryData? = null
    private val viewModel: LoaderCancelledBookingDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loader_cancelled_booking_details)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Booking Details")


        val data = intent.extras
        selectedLoaderCancelledHistoryData = data?.getParcelable<CancelledLoaderTripHistoryData>("loaderCancelledHistoryDetails")

        Log.d("TAG___", "onCreate: " + selectedLoaderCancelledHistoryData!!.bookingId.toString())

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.loaderCancelledHistoryDetailResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                try {
                    if (it.data == null) {
                        toast("no data")
                    } else {
//                        toast(it.message)
                        binding.tvBookingId.text = it.data?.bookingId
                        binding.tvTripStatus.text = "Cancelled"
                        binding.tvPaid.text = "₹" + it.data?.fare.toString()
                        binding.tvDate.text = it.data?.bookingDate
                        binding.tvPickup.text = it.data?.picupLocation
                        binding.tvDropoff.text = it.data?.dropLocation
                        binding.tvVehicleType.text = it.data?.vehicleName
                        binding.tvBodyType.text = it.data?.bodyType
                        binding.tvVehicleNumber.text = it.data?.vehicleNumbers
                        binding.tvTotalLoads.text = it.data?.capacity

                        if (it.data?.paymentMode.equals("1")) {
                            binding.tvPaymentMethod.setText("Cash")
                        } else if (it.data?.paymentMode.equals("2")) {
                            binding.tvPaymentMethod.setText("Online")
                        } else {
                            binding.tvPaymentMethod.setText("Wallet")
                        }

                        binding.tvDriverName.text = it.ownerDetails?.name
                        binding.tvDriverPhone.text = it.ownerDetails?.mobile
                        binding.tvPartyName.text = it.ownerDetails?.name
                        binding.tvPartyNumber.text = it.ownerDetails?.mobile
                        binding.tvUseremail.text = it.userDetails?.email
                        binding.tvUsername.text = it.userDetails?.name
                        binding.tvUserphone.text = it.userDetails?.mobileNumber
                        binding.tvPartyName.text = it.data?.bookingTime.toString()

                        /*if(it.data[i].rideCancelStatus.toString().equals("0")){
                            binding.llBtnCancel.visibility = View.VISIBLE
                        }
                        else if(it.rideCancelStatus.toString().equals("1")){
                            binding.llBtnCancel.visibility = View.GONE
                        }*/


                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

                /*if(it.data[0].payment_mode.equals("1"))
                {binding.tvPaymentMethod.setText("Cash")}
                else  if(it.data[0].payment_mode.equals("2"))
                {binding.tvPaymentMethod.setText("Online")}*/

            } else {
                toast(it.message!!)
            }
        }

        viewModel.loaderOngoingHistoryDetailApi(
            "Bearer " + userPref.user.apiToken,
            selectedLoaderCancelledHistoryData?.bookingId!!
        )

       /* Log.d(TAG, "onCreate9999: "+userPref.user.apiToken+"9999999999"+
            selectedLoaderCancelledHistoryData?.bookingId!!)
*/

        /*binding.btnRaiseComplaint.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoaderRaiseComplaintActivity::class.java)
            intent.putExtra("BookingId", selectedLoaderCancelledHistoryData?.bookingId!!.toString())
            startActivity(intent)
        })*/


    }
}