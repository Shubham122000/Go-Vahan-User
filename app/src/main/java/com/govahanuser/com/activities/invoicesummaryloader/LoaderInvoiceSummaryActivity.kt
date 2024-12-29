package com.govahan.com.activities.invoicesummaryloader

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.com.R
import com.govahan.com.baseClasses.BaseActivity
import com.govahan.com.databinding.ActivityInvoiceSummaryBinding
import com.govahan.com.util.DateFormat
import com.govahan.com.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class LoaderInvoiceSummaryActivity : BaseActivity() {
    private lateinit var binding: ActivityInvoiceSummaryBinding
    private var selectedInvoiceListData: String? = null
    private var selectedInvoiceLoaderBookingId: String? = null
    private val viewModel: InvoiceSummaryDetailsViewModel by viewModels()
    var downlloadpdf= ""
    var downloadbilty= ""
    var downloadsignature= ""
    var freightamount= ""
    var tolltax= ""
    var totalamount=""
    var booking_id=""
    var manager: DownloadManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_summary)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Invoice Summary")
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
        binding.signature.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadsignature))
            startActivity(browserIntent)
            toast("Signature Downloaded successfully!")
        }

        val data = intent.extras

        selectedInvoiceListData = intent.getStringExtra("loaderInvoiceDetails").toString()
        selectedInvoiceLoaderBookingId = intent.getStringExtra("loaderInvoiceBookingId").toString()
          //    selectedInvoiceListData = data?.getParcelable<LoaderInvoiceData>("loaderInvoiceDetails")
         //     selectedPassengerListData = data?.getParcelable<PassengerInvoiceData>("loaderInvoiceDetails")
        //Log.d("TAG___", "onCreate: " + selectedInvoiceListData!!.invoiceNumber.toString())

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.loaderInvoiceSummaryDetailResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast(it.message!!)

                try {
                    binding.tvInvoiceNumber.text = it.data?.invoiceNumber
                    binding.tvInvoiceDate.text = DateFormat.TimeFormat(it.data?.createdAt)

                    booking_id= it.data?.bookingId.toString()
                    if (it.userDetails?.name==null){
                        binding.tvUsername.text = ""
                    }else{
                        binding.tvUsername.text = it.userDetails?.name.toString()
                    }

                    if (it.userDetails?.email==null){
                        binding.tvUseremail.text =""
                    }else{
                        binding.tvUseremail.text = it.userDetails?.email.toString()
                    }
//                    binding.tvUsername.text = it.userDetails?.name
                    binding.tvUserphone.text = it.userDetails?.mobileNumber
//                    binding.tvUseremail.text = it.userDetails?.email

                    binding.tvTotalLoads.text = it.data?.capacity
                    binding.tvDriverName.text = it.data?.driverName
//                    binding.tvDriverNumber.text = it.data?.driverName?.
                    binding.tvDepartureplace.text = it.data?.picupLocation
                    binding.tvArrivalplace.text = it.data?.dropLocation
                    binding.tvBookingDate.text = it.data?.createdAtInIst
                    binding.tvCompleteDate.text = it.data?.bookingDate
                    binding.tvFuelcharges.text="₹${it.data?.fuelCharge}"
                    binding.driverCharges.text="₹${it.data?.driverFee}"
                    binding.fare.text="₹${it.data?.fare}"
                    binding.tax.text="₹${addNumberValueTwoDegit(it.data?.tax.toString())}"
                    binding.tollCharges.text="₹${it.data?.tollTax}"
                    freightamount= it.data?.fare.toString()
//                        tolltax=it.data.tax.toString()
//                    totalamount= (freightamount.toInt() + tolltax.toFloat()).toString()
                    binding.tvTotalamount.text="₹${it.data?.fareTotal}"
                    binding.tvBodyType.text = it.data?.bodyType
                    binding.tvVehicleType.text = it.data?.vehicleName
                    binding.tvVehicleNumber.text = it.data?.vehicleNumbers
                    if (it.data?.paymentMode.equals("1")) {
                        binding.tvPaymentMode.setText("Cash")
                    } else if (it.data?.paymentMode.equals("2")) {
                        binding.tvPaymentMode.setText("Online")
                    }else if (it.data?.paymentMode.equals("3")) {
                        binding.tvPaymentMode.setText("Wallet")
                    }
                    binding.tvCgst.text = it.data?.bookingTime

//                    downlloadpdf = it.data..toString()
//                    if (it.data.builty.toString().isNullOrEmpty()){
//                        downloadbilty=""
//                    }
//                    else{
//                        downloadbilty = it.data.builty.toString()
//
//                    }
//                    downloadsignature = it.data.signature.toString()

                }catch (e:Exception){
                    e.printStackTrace()
                }
            } else {
                toast(it.message!!)
            }
        }

        viewModel.loaderDownloadInvoiceResponseModel.observe(this) {
            if (it.status == 1) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                toast("Invoice Downloaded successfully!")
            } else {
                toast(it.message!!)
            }
        }

        viewModel.loaderInvoiceDetailApi(
            "Bearer " + userPref.user.apiToken,
            selectedInvoiceListData.toString()
        )


        binding.btnDownload.setOnClickListener {
            viewModel.loader_invoice_url_second(
                "Bearer " + userPref.user.apiToken,
                booking_id
            )
        }

//        viewModel.loaderSendMailResponseModel.observe(this) {
//            if (it.status == 1) {
//                // toast("booking Successful")
//                toast("Email Sent L successfully!")
//
//            } else {
//                toast(it.message!!)
//            }
//        }
//        binding.btnEmail.setOnClickListener(View.OnClickListener {
//            viewModel.sendMailLoaderInvoiceApi("Bearer " + userPref.user.apiToken, selectedInvoiceLoaderBookingId.toString())
//        })
//
//
//        viewModel.loaderDownloadInvoiceResponseModel.observe(this) {
//            if (it.status == 1) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
//                toast("Invoice Downloaded successfully!")
//            } else {
//                toast(it.message!!)
//            }
//        }
//        binding.btnDownload.setOnClickListener(View.OnClickListener {
//            viewModel.loader_invoice_url_second("Bearer " + userPref.user.apiToken,
//                selectedInvoiceLoaderBookingId!!
//            )
//        })

    }
    fun addNumberValueTwoDegit(totalAmount: String?): String {
        val amount: Double? = totalAmount?.toDouble()
        val formatter = DecimalFormat("####.00")
        val formatted: String = formatter.format(amount)
        return formatted
    }

}

