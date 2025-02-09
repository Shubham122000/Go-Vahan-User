package com.govahanuser.com.activities.bookingreview

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.activities.bookingsuccess.BookingSuccessActivity
import com.govahanuser.com.activities.bookingsuccessp.BookingSuccessPActivity
import com.govahanuser.com.activities.passengers.passengerbookingreview.BookingReviewPActivity
import com.govahanuser.com.activities.passengers.passengerbookingreview.BookingReviewPViewModel
import com.govahanuser.com.activities.wallet.LoaderWalletActivity
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityPaymentThroughBinding
import com.govahanuser.com.databinding.InsufficientWalletBinding
import com.govahanuser.com.util.DateFormat
import com.govahanuser.com.util.toast
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class PaymentThroughActivity : BaseActivity(),
   /* PaymentResultListener,*/ PaymentResultWithDataListener {
    lateinit var binding:ActivityPaymentThroughBinding
    private val viewModel: BookingReviewViewModel by viewModels()
    private val viewModel1: BookingReviewPViewModel by viewModels()
    var amount: Int = 0
    var paymentprice=0.0
    var finalPamountInt = 0
    var pickupLocation = ""
    var dropLocation = ""
    var pickupLat = ""
    var pickupLong = ""
    var dropLat = ""
    var dropLong = ""
    var driverId=""
    var bodytype=""
    var capacity=""
    var distance=""
    var vehicleNumber=""
    var selectedDateFormat2=""
    var selectedDate=""
    var flag=""
    var vehicle_id=""
    var total_fare=""
    var id=""
    var phonepayurl = ""
    var transactionId=""
    var flag1=""
    var amount1=""
    var pretime=""
    var bPaymentMode: Boolean? = null
    var bpPaymentMode: Boolean? = null
    private val B2B_PG_REQUEST_CODE = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_through)

        binding.header.tvHeaderText.text="Select Payment Option"

       if (intent!=null){
           flag1= intent.getStringExtra("flag1").toString()

           amount1 = intent.getStringExtra("amount").toString()

           flag=intent.getStringExtra("flag").toString()
           total_fare=intent.getStringExtra("total_fare").toString()
           pickupLocation=intent.getStringExtra("pickupLocation").toString()
           selectedDateFormat2=intent.getStringExtra("selectedDateFormat2").toString()
           pickupLocation=intent.getStringExtra("pickupLocation").toString()
           pretime=intent.getStringExtra("pretime").toString()
           pickupLat=intent.getStringExtra("pickupLat").toString()
           pickupLong=intent.getStringExtra("pickupLong").toString()
           dropLocation = intent.getStringExtra("dropLocation").toString()
           dropLat=intent.getStringExtra("dropLat").toString()
           vehicle_id=intent.getStringExtra("vehicle_id").toString()
           dropLong=intent.getStringExtra("dropLong").toString()
           driverId=intent.getStringExtra("driverId").toString()
           bodytype=intent.getStringExtra("bodytype").toString()
           capacity=intent.getStringExtra("capacity").toString()
           distance=intent.getStringExtra("distance").toString()
           vehicleNumber=intent.getStringExtra("vehicleNumber").toString()
           id=intent.getStringExtra("id").toString()

         }
        selectedDate= DateFormat.dateform2(selectedDateFormat2).toString()
        binding.header.ivBack.setOnClickListener {
            finish()
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        binding.walletButton.setOnClickListener {
           if (flag.equals("loader")){
               viewModel.razorpayPayment(
                   "Bearer " + userPref.user.apiToken,
                   id,
                   "1",
                   "2",
                   "walletTrasactionId",
                   "walletInvoice",
                   "INR",
                   amount1
               )
           }else{
               viewModel.razorpayPayment(
                   "Bearer " + userPref.user.apiToken,
                   id,
                   "2",
                   "2",
                   "walletTrasactionId",
                   "walletInvoice",
                   "INR",
                   amount1
               )
           }
        }
        binding.onlineButton.setOnClickListener {
            startPayment(amount1.toString())
//            paymentprice=amount1.toDouble()
//            var price=paymentprice
//            finalPamountInt=(price*100).toInt()
//            viewModel.checkupApi("Bearer " + userPref.user.apiToken,finalPamountInt.toString(),userPref.getmobile().toString(),userPref.getUserId().toString())
        }
        viewModel.ChecksumResponse.observe(this) {
            if (it.success == true) {
                phonepayurl = it.data.instrumentResponse.redirectInfo.url
                transactionId=it.data.merchantTransactionId
                flag1=""
//                startActivity(
//                    Intent(applicationContext, WebViewActivity::class.java)
//                        .putExtra("phonepay", phonepayurl.toString())
//                )
// To Initiate Payment.
//                startActivityForResult(intent, B2B_PG_REQUEST_CODE);
              } else {
                toast(it.message!!)
              }
              }
        viewModel1.passengerPaymentSuccessResponseModel.observe(this) {
            if (it.status == 1) {
                finish()
                if (it.status == 1) {
                    toast(it.message!!)
//                    time = selectedPassVehicleData?.bookingTime!!
                    BookingReviewPActivity.bookingPassengerOnlineDataList.add(it.data!!)
                    BookingReviewPActivity.bookingPassengerOnlineUserList.add(it.user!!)
                    BookingReviewPActivity.bookingPassengerOnlineDriverList.add(it.driver!!)
//                    BookingReviewPActivity.bookingPassengerOnlineDriverList.add(it.Owner!!)
                    bpPaymentMode = true
                    startActivity(Intent(this, BookingSuccessPActivity :: class.java)
                        .putExtra("modelDataList", BookingReviewPActivity.bookingPassengerDataList)
                        .putExtra("modelUserList", BookingReviewPActivity.bookingPassengerUserList)
                        .putExtra("modelDriverList",
                            BookingReviewPActivity.bookingPassengerDriverList
                        )
                        .putExtra("online","ONLINE")
                        .putExtra("flag",flag)
                    )
                    // toast(it.bookingPassengerData[0].bookingId!!)
                    finish()
                    } else {
                    toast(it.message!!)
                }

            } else {
                toast(it.message!!)
            }
        }
        viewModel.bookingLoaderResponseModel.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                BookingReviewActivity.bookingLoaderDataList.add(it.bookingLoaderData!!)
                BookingReviewActivity.bookingLoaderUserList.add(it.bookingLoaderUser!!)
                BookingReviewActivity.bookingLoaderDriverList.add(it.bookingLoaderDriver!!)
                BookingReviewActivity.bookingLoaderRideCode = it.bookingLoaderrideCode!!
                bPaymentMode = false
                startActivity(
                    Intent(this, BookingSuccessActivity::class.java)
                        .putExtra("modelDataList", BookingReviewActivity.bookingLoaderDataList)
                        .putExtra("modelUserList", BookingReviewActivity.bookingLoaderUserList)
                        .putExtra("modelDriverList", BookingReviewActivity.bookingLoaderDriverList)
                        .putExtra("modelRideCode", BookingReviewActivity.bookingLoaderRideCode)
                        .putExtra("cash", "CASH")
                        .putExtra("flag",flag)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
//                intent.putExtra("modelDataList", bookingLoaderDataList)
//                intent.putExtra("modelUserList", bookingLoaderUserList)
//                intent.putExtra("modelDriverList", bookingLoaderDriverList)
//                intent.putExtra("modelRideCode", bookingLoaderRideCode)
//                intent.putExtra("cash",bPaymentMode)
                // toast(it.bookingPassengerData[0].bookingId!!)
                finish()

            } else {
                toast(it.message!!)
            }
        }
        viewModel1.loaderPaymentSuccessResponseModel.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                finish()
                BookingReviewActivity.bookingLoaderOnlineDataList.add(it.data!!)
                BookingReviewActivity.bookingLoaderOnlineUserList.add(it.user!!)
                BookingReviewActivity.bookingLoaderOnlineDriverList.add(it.driver!!)
                BookingReviewActivity.owner.add(it.Owner!!)
                BookingReviewActivity.bookingLoaderOnlineRideCode = "1234"
                bPaymentMode = true
                startActivity(
                    Intent(this, BookingSuccessActivity::class.java)
                        .putExtra("modelDataList",
                            BookingReviewActivity.bookingLoaderOnlineDataList
                        )
                        .putExtra("modelUserList",
                            BookingReviewActivity.bookingLoaderOnlineUserList
                        )
                        .putExtra("modelDriverList",
                            BookingReviewActivity.bookingLoaderOnlineDriverList
                        )
                        .putExtra("modelRideCode1",
                            BookingReviewActivity.bookingLoaderOnlineRideCode
                        )
                        .putExtra("online", "ONLINE")
                        .putExtra("flag",flag)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                     )
                finish()
            } else {
                toast(it.message!!)
            }
        }
      viewModel.loaderPaymentSuccessResponseModel.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                finish()
                BookingReviewActivity.bookingLoaderOnlineDataList.add(it.data!!)
                BookingReviewActivity.bookingLoaderOnlineUserList.add(it.user!!)
                BookingReviewActivity.owner.add(it.Owner!!)
                BookingReviewActivity.bookingLoaderOnlineDriverList.add(it.driver!!)
                BookingReviewActivity.bookingLoaderOnlineRideCode = "1234"
                bPaymentMode = true
                startActivity(
                    Intent(this, BookingSuccessActivity::class.java)
                        .putExtra("modelDataList",
                            BookingReviewActivity.bookingLoaderOnlineDataList
                        )
                        .putExtra("modelUserList",
                            BookingReviewActivity.bookingLoaderOnlineUserList
                        )
                        .putExtra("modelDriverList",
                            BookingReviewActivity.bookingLoaderOnlineDriverList
                        )
                        .putExtra("modelRideCode1",
                            BookingReviewActivity.bookingLoaderOnlineRideCode
                        )
                        .putExtra("online", "ONLINE")
                        .putExtra("flag",flag)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
            } else {
                toast(it.message!!)
            }
        }
        viewModel.razorpayStatusResponse.observe(this){
            if (it.error == false){
                toast(it.message.toString())
                finish()
//                BookingReviewActivity.bookingLoaderOnlineDataList.add(it.data!!)
//                BookingReviewActivity.bookingLoaderOnlineUserList.add(it.user!!)
//                BookingReviewActivity.owner.add(it.Owner!!)
//                BookingReviewActivity.bookingLoaderOnlineDriverList.add(it.driver!!)
//                BookingReviewActivity.bookingLoaderOnlineRideCode = "1234"
//                bPaymentMode = true

                startActivity(
                    Intent(this, BookingSuccessActivity::class.java).apply {
                        putExtra("modelDataList", viewModel.razorpayStatusResponse.value)
                            .putExtra("flag",flag)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                )

            }else if (it.error == true && it.statusCode == 109){
                InsufficientWallet(it.message.toString())
            }else{
                toast(this,it.message.toString())
            }
        }

    }
    private fun InsufficientWallet(text:String) {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: InsufficientWalletBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.insufficient_wallet,
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

        bindingDialog.insufficientText.text = text
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }
        bindingDialog.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoaderWalletActivity::class.java).putExtra("flag1","1"))
        }
    }



    private fun startPayment(amountofuser:String) {
        /*
      *  You need to pass current activity in order to let Razorpay create CheckoutActivity
      * */
//        amount = (binding.payableAmount.text.toString().replace("₹", "").toFloat() * 100).toInt()
        amount = (amountofuser.toFloat()  * 100).toInt()
        finalPamountInt = amount
        val co = Checkout()
        co.setKeyID("rzp_test_fWTippdyDGtkYn")
        try {
            var options = JSONObject()
            options.put("name", userPref.user.name)
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "")
            options.put("currency", "INR")
            options.put("amount", finalPamountInt)
            options.put("send_sms_hash", true)

            val prefill = JSONObject()
            prefill.put("email", userPref.user.email)
            prefill.put("contact", userPref.user.mobileNumber)

            options.put("prefill", prefill)

            co.open(this@PaymentThroughActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@PaymentThroughActivity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        viewModel.payment_status_check("Bearer " + userPref.getToken().toString(),p1!!.paymentId)
        if (flag.equals("loader")) {
            viewModel.razorpayPayment(
                "Bearer " + userPref.user.apiToken,
                id,
                "1",
                "1",
                p1?.paymentId.toString(),
                "",
                "INR",
                amount1
            )
        }else{
            viewModel.razorpayPayment(
                "Bearer " + userPref.user.apiToken,
                id,
                "2",
                "1",
                p1?.paymentId.toString(),
                "",
                "INR",
                amount1
            )
        }

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this@PaymentThroughActivity , "Error in payment: ", Toast.LENGTH_LONG).show()

    }

//    override fun onStart() {
//        super.onStart()
//        if(flag1.equals("1")){
//        }
//        else{
//        viewModel.paymentcheckApi("Bearer " + userPref.user.apiToken,transactionId)
//        viewModel.Paymentsuccessmsgresponse.observe(this) {
//            if (it.code == "PAYMENT_SUCCESS") {
//                toast(it.message)
//                 if (flag.equals("loader")) {
//
//                } else {
//                    viewModel1.passengerPaymentSuccessApi(
//                        "Bearer " + userPref.user.apiToken,
//                        pickupLocation,
//                        pickupLat, pickupLong,
//                        dropLocation,
//                        dropLat,
//                        dropLong,
//                        vehicle_id,
//                        paymentprice.toString(),total_fare,
//                        "2",
//                        selectedDate,pretime,
//                        driverId,
//                        "dis",
//                        bodytype,
//                        capacity,
//                        distance,
//                        vehicleNumber,
//                        transactionId,
//                        "1",
//                        "INR", id
//                    )
//                }
//
//            } else {
//                toast("Payment Failed")
//
//            }
//        }
//        }
//    }
//    https://zaakpay.com/

    fun handleUPIIntent(intentUri: String?) {
        val uri = Uri.parse(intentUri)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        //                startActivityForResult(intent, B2B_PG_REQUEST_CODE);

        startActivityForResult(intent,B2B_PG_REQUEST_CODE)
    }

//    override fun onPaymentSuccess(p0: TransactionResponse?) {
//        if (flag.equals("loader")){
//            viewModel.loaderPaymentSuccessApi(
//                "Bearer " + userPref.user.apiToken,
//                pickupLocation!!,
//                pickupLat!!, pickupLong!!,
//                dropLocation!!,
//                dropLat!!,
//                dropLong!!,
//                vehicle_id ,
//                payableamount,
//                "2",
//                selectedDateFormat2,
//                driverId,
//                "dis",
//                bodytype,
//                capacity,
//                distance,
//                vehicleNumber,
//                p0?.pgTransId.toString(),
//                "1",
//                "INR",id
//            )
//        }
//        else{
//            viewModel1.passengerPaymentSuccessApi(
//                "Bearer " + userPref.user.apiToken,
//                pickupLocation!!,
//                pickupLat!!, pickupLong!!,
//                dropLocation!!,
//                dropLat!!,
//                dropLong!!,
//                vehicle_id,
//                payableamount,
//                "2",
//                selectedDateFormat2,
//                driverId,
//                "dis",
//                bodytype,
//                capacity,
//                distance,
//                vehicleNumber,
//                p0?.pgTransId.toString(),
//                "1",
//                "INR",id
//            )
//        }
//    }

//    override fun onPaymentFailure(p0: String?, p1: String?) {
//        try {
//            Toast.makeText(
//                this@PaymentThroughActivity,
//                "Payment Unsuccessful", Toast.LENGTH_LONG
//            ).show()
//            Log.e("@@Fail",p0.toString())
//            Log.e("@@@Fail",p1.toString())
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }


}