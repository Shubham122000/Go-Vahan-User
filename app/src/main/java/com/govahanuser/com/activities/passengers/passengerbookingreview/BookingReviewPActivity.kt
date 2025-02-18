package com.govahanuser.com.activities.passengers.passengerbookingreview

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahanuser.com.R
import com.govahanuser.com.activities.bookingreview.PaymentThroughActivity
import com.govahanuser.com.activities.bookingsuccessp.BookingSuccessPActivity
import com.govahanuser.com.activities.passengers.paymentmethods.PaymentMethodsPActivity
import com.govahanuser.com.activities.transportowner.TransportOwnerActivity
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityBookingReviewPactivityBinding
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerData
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerDriver
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerUser
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessData
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessDriver
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessOwner
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessUser
import com.govahanuser.com.model.searchPassengerVehicle.SearchPassengerData
import com.govahanuser.com.model.searchvehiclemodel.SearchVehicleData
import com.govahanuser.com.util.toast
import com.razorpay.PaymentData
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class BookingReviewPActivity : BaseActivity() {
    private lateinit var binding : ActivityBookingReviewPactivityBinding
    private var selectedPassVehicleData : SearchVehicleData?= null
    private val viewModel : BookingReviewPViewModel by viewModels()
    var total_fare=""
    private var searchPassengerData : SearchPassengerData ?= null
    lateinit var getSelectedPaymentMode : String
    var selectedDateFormat2 = ""
    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mcurrentTime[Calendar.MINUTE]
    val zone = mcurrentTime[Calendar.AM_PM]
    var pickupLocation = ""
    var dropLocation = ""
    var pickupLat = ""
    var pickupLong = ""
    var dropLat = ""
    var id = ""
    var dropLong = ""
    var date = ""
    var time = ""
    var pretime = ""
//    var h_vehicle_type = ""
    var amount=""
    var driverId=""
    var bodytype=""
    var capacity=""
    var distance=""
    var vehicleNumber=""
    var finalamountInt=0
    var vehiclenumber=""
    var amountPayable = ""
    var selectedItem = ""

    companion object{
        /*Todo:- Cash*/
        //var passengerData: BookingPassengerData? = null
        var bookingPassengerDataList:ArrayList<BookingPassengerData> = ArrayList()
        var bookingPassengerUserList:ArrayList<BookingPassengerUser> = ArrayList()
        var bookingPassengerDriverList:ArrayList<BookingPassengerDriver> = ArrayList()
        /*Todo:- Online*/
        var bookingPassengerOnlineDataList:ArrayList<PassengerPaymentSuccessData> = ArrayList()
        var bookingPassengerOnlineUserList:ArrayList<PassengerPaymentSuccessUser> = ArrayList()
        var bookingPassengerOnlineDriverList:ArrayList<PassengerPaymentSuccessDriver> = ArrayList()
        var bookingPassengerOnlineOwnerList:ArrayList<PassengerPaymentSuccessOwner> = ArrayList()
//        var bookingReviewData:ArrayList<BookingReviewPassengerData> = ArrayList()
    }

    var bpPaymentMode:Boolean?=null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_review_pactivity)
        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.header.tvHeaderText.setText("Booking Review")

        binding.llNext.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PaymentMethodsPActivity::class.java)
            startActivity(intent)
        })

        //  getSelectedPaymentMode = intent.getStringExtra("choosenPaymentMode").toString()

        val data = intent.extras
        selectedPassVehicleData = data?.getParcelable<SearchVehicleData>("ModelData")

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

//        viewModel.bookingReviewPassengerResponse.observe(this) {
//            if (it.status == 1) {
//                 toast(it.message!!)
//                 binding.tvVehicleName.text = it.data[0].vehicleName
//                 binding.wheelerType.text =  it.data[0].no_tyres.toString()
//                 binding.vehicleNumber.text = it.data[0].vehicle_no
//                 binding.tvCapacity.text = it.data[0].seat.toString()
//                 binding.tvDriverName.text = it.data[0].driverName
//                 binding.tvFrom.text = it.data[0].picupLocation
//                 binding.tvTo.text = it.data[0].dropupLocation
//                 binding.tvDistance.text = it.data[0].distance
//                 binding.tvBookingdate.text = it.data[0].bookingDate
//                 binding.tvOwner.text = it.data[0].ownerName
//                 vehiclenumber = it.data[0].vehicle_no.toString()
//                 binding.tvAmount.text = "₹" + it.data[0].totalFare.toString()
//                 binding.payableAmount.text = "₹" + it.data[0]?.amount_pay.toString()
//                 Glide.with(this).load(selectedPassVehicleData?.mainImage).into(binding.ivVehicleImage)
//                 amount=it.data[0]?.amount_pay.toString()
//                 total_fare=it.data[0]?.totalFare.toString()
//                 id=it.data[0]?.id.toString()
//                 userPref.setDriverId(it.data[0].driverId.toString())
//                 pretime = it.data[0].bookingTime.toString()
//                try {
////                    val sdf = SimpleDateFormat("H:mm")
//////                    val dateObj = sdf.parse(it.data[0].bookingTime)
////                    System.out.println(dateObj)
////                    println(SimpleDateFormat("K:mm a").format(dateObj))
////                    binding.tvBookingtime.text = SimpleDateFormat("K:mm a").format(dateObj)
//                } catch (e: ParseException) {
//                    e.printStackTrace()
//                }
//                if(it.data[0].available.toString().equals("0")){
//                    binding.tvAvailable.text = "Not Available"
//                    binding.tvAvailable.visibility = View.VISIBLE
//                    binding.ivCheck.visibility = View.GONE
//                }
//                else if(it.data[0].available.toString().equals("1")){
//                    binding.tvAvailable.text = "Available"
//                    binding.tvAvailable.visibility = View.VISIBLE
//                    binding.ivCheck.visibility = View.VISIBLE
//                }
//            } else {
//                toast(it.message!!)
//            }
//        }
        binding.tvVehicleName.text = selectedPassVehicleData?.vehicle?.vehicleName
        binding.vehicleNumber.text = selectedPassVehicleData?.vehicle?.vehicleNumber.toString()
        binding.tvCapacity.text = selectedPassVehicleData?.vehicle?.capacity.toString()
        binding.tvDistance.text = selectedPassVehicleData?.totalDistance.toString()
        binding.tvFrom.text = selectedPassVehicleData?.fromTrip.toString()
        binding.tvTo.text = selectedPassVehicleData?.toTrip.toString()
        binding.tvBookingdate.text = selectedPassVehicleData?.bookingDateFrom
//                    binding.tvBookingtime.text = selectedPassVehicleData?.bookingTime.toString()
        binding.tvCapacity.text = selectedPassVehicleData?.vehicle?.seats?.toString()
        binding.tvDriverName.text = selectedPassVehicleData?.driver?.name
        binding.tvAmount.text = "₹${selectedPassVehicleData?.freightAmount.toString()}"
//        if (selectedPassVehicleData?.user?.name.isNullOrEmpty()){
//            binding.tvOwnername.text =selectedPassVehicleData?.driver?.name
//        }else{
//            binding.tvOwnername.text =selectedPassVehicleData?.user?.name
//        }

        amountPayable = (selectedPassVehicleData?.freightAmount?.toFloat()?.times(0.15)).toString()

        binding.payableAmount.text = "₹" + amountPayable.toString()
//                    payAmount = selectedPassVehicleData.amount_pay.toString()
        Glide.with(this).load(selectedPassVehicleData?.vehicle?.imageUrl).into(binding.ivVehicleImage)
        userPref.setDriverId(selectedPassVehicleData?.driver?.id.toString())
        pretime = selectedPassVehicleData?.time.toString()
        pickupLat = selectedPassVehicleData?.pickupLat.toString()
        pickupLong = selectedPassVehicleData?.pickupLong.toString()
        dropLat = selectedPassVehicleData?.dropupLat.toString()
        dropLong = selectedPassVehicleData?.dropupLong.toString()
//                    payableAmount=selectedPassVehicleData.amount_pay.toString()
        total_fare=selectedPassVehicleData?.freightAmount.toString()
        id=selectedPassVehicleData?.id.toString()
        selectedDateFormat2= selectedPassVehicleData?.bookingDateFrom.toString()
        viewModel.bookingPassengerResponseModel.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                bookingPassengerDataList.add(it.bookingPassengerData!!)
                bookingPassengerUserList.add(it.bookingPassengerUser!!)
                bookingPassengerDriverList.add(it.bookingPassengerdriver!!)
                bpPaymentMode = false
                startActivity(Intent(this, BookingSuccessPActivity :: class.java)
                .putExtra("modelDataList", bookingPassengerDataList)
                .putExtra("modelUserList", bookingPassengerUserList)
                .putExtra("modelDriverList", bookingPassengerDriverList)
                    .putExtra("cash","CASH"))
             // toast(it.bookingPassengerData[0].bookingId!!)
              finish()
            } else {
                toast(it.message!!)
            }
        }
//        viewModel.passengerPaymentSuccessResponseModel.observe(this) {
//            if (it.status == 1) {
//
//                finish()
//                if (it.status == 1) {
//                    toast(it.message!!)
//                    selectedDateFormat2 = selectedPassVehicleData?.bookingDate!!
////                    time = selectedPassVehicleData?.bookingTime!!
//                    bookingPassengerOnlineDataList.add(it.data!!)
//                    bookingPassengerOnlineUserList.add(it.user!!)
//                    bookingPassengerOnlineDriverList.add(it.driver!!)
//                    bookingPassengerOnlineOwnerList.add(it.Owner!!)
//                    bpPaymentMode = true
//                    startActivity(Intent(this, BookingSuccessPActivity :: class.java)
//                        .putExtra("modelDataList", bookingPassengerDataList)
//                        .putExtra("modelUserList", bookingPassengerUserList)
//                        .putExtra("modelDriverList", bookingPassengerDriverList)
//                        .putExtra("online","ONLINE"))
//                    // toast(it.bookingPassengerData[0].bookingId!!)
//                    finish()
//                } else {
//                    toast(it.message!!)
//                }
//            } else {
//                toast(it.message!!)
//            }
//        }
//        viewModel.searchPassengerDetailApi(
//            "Bearer " + userPref.user.apiToken,
//            selectedPassVehicleData?.pickupLat!!,
//            selectedPassVehicleData?.pickupLong!!,
//            selectedPassVehicleData?.dropupLat!!,
//            selectedPassVehicleData?.dropupLong!!,
//            h_vehicle_type,
//            selectedPassVehicleData?.seat!!.toString(),
//            selectedPassVehicleData?.noTyres!!.toString(),
//            selectedPassVehicleData?.bookingDate!!,
//            pretime,
//            selectedPassVehicleData?.vehicleId.toString(),
//            selectedPassVehicleData?.id.toString()
//
//
//        )

//        Log.d("TAG__",h_vehicle_type+"__vehicleId__"+ selectedPassVehicleData?.vehicleId.toString())

//        binding.tvOwner.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, TransportOwnerActivity::class.java)
//            intent.putExtra("putdriveridd", selectedPassVehicleData?.driverId.toString())
//            startActivity(intent)
//        })
        binding.llChangeDateTime.setOnClickListener(View.OnClickListener {
            clickDataPicker()
        })

        pickupLocation=selectedPassVehicleData?.fromTrip!!
        pickupLat=selectedPassVehicleData?.pickupLat!!
        pickupLong=  selectedPassVehicleData?.pickupLong!!
        dropLocation= selectedPassVehicleData?.toTrip!!
        dropLat= selectedPassVehicleData?.dropupLat!!
        dropLong= selectedPassVehicleData?.dropupLong!!
        driverId= selectedPassVehicleData?.driver?.id.toString()
//        bodytype= selectedPassVehicleData?.vehicle?.seats.toString()
        distance=selectedPassVehicleData?.totalDistance.toString()
        vehicleNumber= selectedPassVehicleData?.vehicle?.vehicleNumber.toString()
//        h_vehicle_type = intent.getStringExtra("vehicle_type")!!
        capacity=selectedPassVehicleData?.vehicle?.seats.toString()

        selectedDateFormat2 = selectedPassVehicleData?.bookingDateFrom!!
        binding.btnConfirmbook.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PaymentThroughActivity :: class.java).also {
                it.putExtra("amount", selectedPassVehicleData)
                it.putExtra("flag1","1")
                it.putExtra("flag", "passenger")
                it.putExtra("total_fare", total_fare)
                it.putExtra("vehicle_id",  selectedPassVehicleData?.vehicleId.toString())
                it.putExtra("pickupLocation", pickupLocation)
                it.putExtra("selectedDateFormat2", selectedDateFormat2)
                it.putExtra("pretime", pretime)
                it.putExtra("pickupLocation", pickupLocation)
                it.putExtra("pickupLat", pickupLat)
                it.putExtra("pickupLong", pickupLong)
                it.putExtra("dropLocation", dropLocation)
                it.putExtra("dropLat", dropLat)
                it.putExtra("dropLong", dropLong)
                it.putExtra("driverId", driverId)
                it.putExtra("bodytype", bodytype)
                it.putExtra("capacity", capacity)
                it.putExtra("distance", distance)
                it.putExtra("vehicleNumber", vehicleNumber)
                it.putExtra("id", id)
            })

                /*Log.d(TAG, "onPaymentSuccessP: "+userPref.user.apiToken+searchPassengerData?.picupLocation!!+
                        searchPassengerData?.pickupLat!!+searchPassengerData?.pickupLong!!+searchPassengerData?.dropupLocation!!+
                        searchPassengerData?.dropupLat!!+searchPassengerData?.dropupLong!!+1+searchPassengerData?.totalFare.toString()+
                        binding.spinnerPaymentMode.selectedItem.toString()+selectedDateFormat2+time+
                        searchPassengerData?.driverId.toString()+"dis"+searchPassengerData?.bodytype.toString()+
                        searchPassengerData?.seat.toString()+searchPassengerData?.distance.toString()+
                        searchPassengerData?.vehicleNumber.toString())*/

//            }
//            else if (selectedItem=="Cash") {
//                viewModel.bookingPassengerApi(
//                    "Bearer " + userPref.user.apiToken,
//                    selectedPassVehicleData?.picupLocation!!,
//                    selectedPassVehicleData?.pickupLat!!,
//                    selectedPassVehicleData?.pickupLong!!,
//                    selectedPassVehicleData?.dropupLocation!!,
//                    selectedPassVehicleData?.dropupLat!!,
//                    selectedPassVehicleData?.dropupLong!!,
//                    "1",
//                    selectedPassVehicleData?.totalFare.toString(),
//                    binding.spinnerPaymentMode.selectedItem.toString(),
//                    selectedDateFormat2,
//                    time,
//                    selectedPassVehicleData?.driverId.toString()
//                )
//
//            }
        })

        /*Log.d(
            TAG, "onPassPaymentSuccess: "+ "signature"+searchPassengerData?.picupLocation!!+
                    "picupLocation"+ searchPassengerData?.picupLocation!!+
                    "pickupLat"+ searchPassengerData?.pickupLat!!+
                    "dropupLocation"+ searchPassengerData?.dropupLocation!!+
                    "dropupLat"+ searchPassengerData?.dropupLat!!+
                    "dropupLong"+searchPassengerData?.dropupLong!!+
                    "totalFare"+ searchPassengerData?.totalFare.toString()+
                    "selectedItem"+ binding.spinnerPaymentMode.selectedItem.toString()+
                    "selectedDateFormat2"+ selectedDateFormat2+
                    "time"+ time +
                    "driverId"+searchPassengerData?.driverId.toString()+
                    "bodytype"+ searchPassengerData?.bodytype.toString()+
                    "distance"+ searchPassengerData?.distance.toString()+
                    "vehicleNumber"+ searchPassengerData?.vehicleNo.toString()
        )*/

    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            this, R.style.DatePickerTheme,{ view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                binding.tvBookingdate.text = simpleDateFormat.format(cal.time)
                selectedDateFormat2 = simpleDateFormat2.format(cal.time)
//                clickTimePicker()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

//    @SuppressLint("SetTextI18n")
//    @RequiresApi(Build.VERSION_CODES.N)
//    fun clickTimePicker() {
//        val mTimePicker = TimePickerDialog(
//            this, { timePicker, selectedHour, selectedMinute ->
//                /*val AM_PM: String = if (selectedHour < 12) {
//                    "AM"
//                } else {
//                    "PM"
//                }*/
//                time = "$selectedHour:$selectedMinute "
//                // bottomSheet.findViewById<Button>(R.id.tvTime).text = time
//            },
//            hour,
//            minute + zone,
//            false
//        ) //Yes 24 hour time
//        mTimePicker.setTitle("Select Time")
//        mTimePicker.show()
//    }

//    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        try {
//            viewModel.passengerPaymentSuccessApi("Bearer " + userPref.user.apiToken,
//                selectedPassVehicleData?.picupLocation!!,
//                selectedPassVehicleData?.pickupLat!!,
//                selectedPassVehicleData?.pickupLong!!,
//                selectedPassVehicleData?.dropupLocation!!,
//                selectedPassVehicleData?.dropupLat!!,
//                selectedPassVehicleData?.dropupLong!!,
//                selectedPassVehicleData?.vehicleId!!.toString(),
//                selectedPassVehicleData?.totalFare.toString(),
//            "2","",
//                binding.tvBookingdate.text.toString(),"",
//                selectedPassVehicleData?.driverId.toString(),
//            "dis",
//                selectedPassVehicleData?.bodytype.toString() ,
//                selectedPassVehicleData?.seat.toString() ,
//                selectedPassVehicleData?.distance.toString() ,
//                selectedPassVehicleData?.vehicleNumber.toString() ,
//            p1?.paymentId.toString(),
//            "1",
//            "INR",""
//        )
//
//            toast(p1?.paymentId.toString())
//
//
//
//        }catch (e: Exception){
//            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
//            e.printStackTrace()
//        }
//    }


//    override fun onPaymentSuccess(p1: String?) {
//        viewModel.passengerPaymentSuccessApi("Bearer " + userPref.user.apiToken,
//            searchPassengerData?.picupLocation!!,
//            searchPassengerData?.pickupLat!!,
//            searchPassengerData?.pickupLong!!,
//            searchPassengerData?.dropupLocation!!,
//            searchPassengerData?.dropupLat!!,
//            searchPassengerData?.dropupLong!!,
//            "1",
//            searchPassengerData?.totalFare.toString(),
//            binding.spinnerPaymentMode.selectedItem.toString(),
//            selectedDateFormat2,
//            time,
//            searchPassengerData?.driverId.toString(),
//            "dis",
//            searchPassengerData?.bodytype.toString() ,
//            searchPassengerData?.seat.toString() ,
//            searchPassengerData?.distance.toString() ,
//            searchPassengerData?.vehicleNumber.toString() ,
//            "transaction id",
//            "1" ,
//            "INR"
//        )
//    }




}