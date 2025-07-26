package com.govahan.activities.invoicesummaryloader

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityInvoiceSummaryBinding
import com.govahan.model.loaderinvoicelistmodel.LoaderInvoiceData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahan.util.DateFormat
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class LoaderInvoiceSummaryActivity : BaseActivity() {
    private lateinit var binding: ActivityInvoiceSummaryBinding
    private var selectedInvoiceListData: LoaderTripManagementData? = null
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

//        selectedInvoiceListData = intent.getStringExtra("loaderInvoiceDetails").toString()
        selectedInvoiceListData = data?.getParcelable<LoaderTripManagementData>("loaderInvoiceDetails")
        selectedInvoiceLoaderBookingId = intent.getStringExtra("loaderInvoiceBookingId").toString()
//              selectedInvoiceListData = data?.getParcelable<LoaderInvoiceData>("loaderInvoiceDetails")
         //     selectedPassengerListData = data?.getParcelable<PassengerInvoiceData>("loaderInvoiceDetails")
        //Log.d("TAG___", "onCreate: " + selectedInvoiceListData!!.invoiceNumber.toString())

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

//        viewModel.loaderInvoiceSummaryDetailResponse.observe(this) {
//            if (it.status == 1) {
                // toast("booking Successful")
//                toast(it.message!!)

                try {
                    if (selectedInvoiceListData?.paymentDetails?.isNotEmpty() == true) {
                        binding.tvInvoiceNumber.text =
                            selectedInvoiceListData?.paymentDetails?.get(0)?.invoice
                    }
                    binding.tvInvoiceDate.text = selectedInvoiceListData?.paymentDetails?.get(0)?.createdAt?.let {
                        DateFormat.convertDate(
                            it
                        )
                    }
                    binding.tvCompleteDate.text = selectedInvoiceListData?.paymentDetails?.get(0)?.createdAt?.let {
                        DateFormat.convertDate(
                            it
                        )
                    }

                    booking_id= selectedInvoiceListData?.bookingId.toString()
//                    if (it.userDetails?.name==null){
//                        binding.tvUsername.text = ""
//                    }else{
                        binding.tvUsername.text = userPref.getName()
//                    }

//                    if (it.userDetails?.email==null){
//                        binding.tvUseremail.text =""
//                    }else{
                        binding.tvUseremail.text = userPref.getEmail()
//                    }
//                    binding.tvUsername.text = it.userDetails?.name
                    binding.tvUserphone.text = userPref.getmobile()
//                    binding.tvUseremail.text = it.userDetails?.email

                    binding.tvTotalLoads.text = selectedInvoiceListData?.tripDetails?.vehicle?.capacity
                    binding.tvDriverName.text = selectedInvoiceListData?.tripDetails?.driver?.name
                    binding.tvDriverNumber.text = selectedInvoiceListData?.tripDetails?.driver?.mobileNumber
                    binding.tvDepartureplace.text = selectedInvoiceListData?.tripDetails?.fromTrip
                    binding.tvArrivalplace.text = selectedInvoiceListData?.tripDetails?.toTrip
                    binding.tvBookingDate.text = selectedInvoiceListData?.tripDetails?.bookingDateFrom
                    binding.tvFuelcharges.text="₹${selectedInvoiceListData?.tripDetails?.fuelCharge}"
                    binding.driverCharges.text="₹${selectedInvoiceListData?.tripDetails?.driverFee}"
                    binding.fare.text="₹${selectedInvoiceListData?.tripDetails?.freightAmount}"
                    binding.tax.text="₹${addNumberValueTwoDegit(selectedInvoiceListData?.tripDetails?.tax.toString())}"
                    binding.tollCharges.text="₹${selectedInvoiceListData?.tripDetails?.tollTax.toString()}"
                    freightamount= selectedInvoiceListData?.tripDetails?.freightAmount.toString()
                        tolltax = selectedInvoiceListData?.tripDetails?.tollTax.toString()
//                    totalamount= (freightamount.toInt() + tolltax.toFloat()).toString()
                    binding.tvTotalamount.text="₹${selectedInvoiceListData?.tripDetails?.freightAmount}"
                    binding.tvBodyType.text = selectedInvoiceListData?.tripDetails?.vehicle?.bodyType?.name
                    binding.tvVehicleType.text = selectedInvoiceListData?.tripDetails?.vehicle?.vehicleName
                    binding.tvVehicleNumber.text = selectedInvoiceListData?.tripDetails?.vehicle?.vehicleNumber

                    if (selectedInvoiceListData?.paymentDetails?.isNotEmpty() == true) {
                        if (selectedInvoiceListData?.paymentDetails?.get(0)?.paymentMode.equals("1")) {
                            binding.tvPaymentMode.setText("Online")
                        } else if (selectedInvoiceListData?.paymentDetails?.get(0)?.paymentMode.equals(
                                "2"
                            )
                        ) else if (selectedInvoiceListData?.paymentDetails?.get(0)?.paymentMode.equals(
                                "3"
                            )
                        ) {
                            binding.tvPaymentMode.setText("Cash")
                        }
                    }
                    binding.tvCgst.text = selectedInvoiceListData?.bookingTime

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
//            } else {
//                toast(it.message!!)
//            }
//        }

//        viewModel.loaderDownloadInvoiceResponseModel.observe(this) {
//            if (it.status == 1) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
//                toast("Invoice Downloaded successfully!")
//            } else {
//                toast(it.message!!)
//            }
//        }

//        viewModel.loaderInvoiceDetailApi(
//            "Bearer " + userPref.user.apiToken,
//            selectedInvoiceListData.toString()
//        )


        binding.btnDownload.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(selectedInvoiceListData?.pdfUrl.toString())))
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

