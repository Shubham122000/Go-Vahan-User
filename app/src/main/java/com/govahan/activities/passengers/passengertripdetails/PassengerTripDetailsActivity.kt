package com.govahan.activities.passengers.passengertripdetails

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.android.PolyUtil
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import com.govahan.R
import com.govahan.activities.passengers.passengerRideCompleted.PassengerRideCompletedActivity
import com.govahan.activities.passengers.passengertripdetails.PassengerTripDetailsViewModel
import com.govahan.activities.ridecompleted.RideCompletedActivity
import com.govahan.activities.tripdetails.TripDetailsViewModel
import com.govahan.adapters.PassengerCancelTripReasonAdapter
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityPassengerTripDetailsBinding
import com.govahan.model.passengercancelreasonmodel.PassengerCancelReasonData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahan.model.tripmanagementpassengermodel.PassengerTripManagementData
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class PassengerTripDetailsActivity : BaseActivity(), OnMapReadyCallback {

    private val viewModel1: TripDetailsViewModel by viewModels()
    var selectedpassTripData=""
    private lateinit var binding: ActivityPassengerTripDetailsBinding
    private var selectedPassengerTripData: LoaderTripManagementData? = null
    private val viewModel: PassengerTripDetailsViewModel by viewModels()
    private var listData: ArrayList<PassengerCancelReasonData> = ArrayList()
    private var passengerCancelReasonAdapter: PassengerCancelTripReasonAdapter? = null
    private var listReasonType_id:ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""
    private lateinit var str: String
    var selectedDateFormat2 = ""
    var time = ""
    val mcurrentTime = Calendar.getInstance()
    var hour = mcurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mcurrentTime[Calendar.MINUTE]
    val zone = mcurrentTime[Calendar.AM_PM]
    lateinit var timeslots: BottomSheetDialog

    var crnNumber = ""
    var callDriverNumber = ""

    lateinit var bottomSheetPassTripDetails: BottomSheetDialog
    lateinit var bottomSheetPassCancelReason: BottomSheetDialog
    lateinit var bottomSheetPassRideCancelled: BottomSheetDialog


    // GeeksforGeeks coordinates
    private var originLatitude: Double = 0.0
    private var originLongitude: Double = 0.0
    private lateinit var mMap: GoogleMap
    private var polyline: com.google.android.gms.maps.model.Polyline? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_trip_details)

        val mapFragment = supportFragmentManager.findFragmentById(binding.passMap.id) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Trip Details")
        if (intent != null) {
            val data = intent.extras
            selectedPassengerTripData = data?.getParcelable<LoaderTripManagementData>("passengerTripDetails")
        }
        val options = PolylineOptions()
        options.color(Color.RED)
        options.width(5f)


        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }


//        viewModel.passengerTripManagementDetailApi("Bearer " + userPref.user.apiToken, selectedPassengerTripData?.bookingId!!)

        Log.d(TAG, "onCreate:bookingId "   +selectedPassengerTripData?.bookingId!!)
        viewModel.getPassengerCancelReasonListApi("Bearer " + userPref.user.apiToken)
        viewModel1.loaderRescheduleTripResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast(it.message!!)
                finish()
            } else {
                toast(it.message!!)
            }
        }
//        passengerTripDetailsDialog()

    }


//    private fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String) : String{
//        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
//                "&destination=${dest.latitude},${dest.longitude}" +
//                "&sensor=false" +
//                "&mode=driving" +
//                "&key=$secret"
//    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun passengerTripDetailsDialog() {

        val dialogBinding = LayoutInflater
            .from(this@PassengerTripDetailsActivity).inflate(R.layout.bottom_sheet_passenger_trip_details, null)
        bottomSheetPassTripDetails = BottomSheetDialog(this@PassengerTripDetailsActivity)
        bottomSheetPassTripDetails.setContentView(dialogBinding)
        bottomSheetPassTripDetails.setCancelable(true)
        bottomSheetPassTripDetails.setCanceledOnTouchOutside(false)

        bottomSheetPassTripDetails.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetPassTripDetails.behavior.peekHeight = 340
        bottomSheetPassTripDetails.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.rounded_top_corners)
        }

        val tvTripCode: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_trip_code)!!
        val tvRating: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_rating)!!
        val tvPickLocation: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_pickLocation)!!
        val tvDropLocation: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_dropLocation)!!
        val llBtnCancel: LinearLayout = bottomSheetPassTripDetails.findViewById(R.id.ll_btnCancel)!!

        val llCallDriver: LinearLayout = bottomSheetPassTripDetails.findViewById(R.id.llCallDriver)!!

        val tvBookingRDate: TextView= bottomSheetPassTripDetails.findViewById(R.id.tv_bookingRDate)!!
        val tvBookingRTime: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_bookingRTime)!!
        val vehicleName: TextView = bottomSheetPassTripDetails.findViewById(R.id.vehicle_name)!!
        val vehicleNumber: TextView = bottomSheetPassTripDetails.findViewById(R.id.vehicle_number)!!
        val tvCapacity: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_capacity)!!
//        val tv_ridesNumber: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_ridesNumber)!!
        val bookingStatus: TextView = bottomSheetPassTripDetails.findViewById(R.id.bookingStatus)!!
        val tvDistance: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_distance)!!
        val tvDriverName: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_driverName)!!
        val tvOwner: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_owner)!!
        val tvFrom: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_from)!!
        val tvTo: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_to)!!
        val tvDriverNamee: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_driverNamee)!!
        val tvDriverRating: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_DriverRating)!!
        val tvAmount: TextView = bottomSheetPassTripDetails.findViewById(R.id.tv_amount)!!
//        val btnReschedule: Button = bottomSheetPassTripDetails.findViewById(R.id.btnReschedule)!!
        val cancel: TextView = bottomSheetPassTripDetails.findViewById(R.id.cancel)!!
        val vehicleImage: ImageView = bottomSheetPassTripDetails.findViewById(R.id.vehicle_image)!!
        val ivDriverImage: ImageView = bottomSheetPassTripDetails.findViewById(R.id.iv_DriverImage)!!

        val btnCancel: Button = bottomSheetPassTripDetails.findViewById(R.id.btnCancel)!!

//        viewModel.passengerTripDetailResponse.observe(this) {
//            if (it.status == 1) {
//                toast(it.message!!)
//                userPref.setDriverId(it.data[0]!!.driverId.toString())
//
//                callDriverNumber = it.data[0].driverMobileNumber.toString()
//
//                if(it.rideCancelStatus.toString().equals("0")){
//                    cancel.visibility=View.GONE
//                }
//                else if(it.rideCancelStatus.toString().equals("1")){
//                    cancel.visibility=View.VISIBLE
//                }
//
//                tvTripCode.text = it.data[0].startCode
//                tvBookingRDate.text = it.data[0].bookingDate
//                tvBookingRTime.text = it.data[0].bookingTime
//                vehicleName.text = it.data[0].vehicleName
//                tvRating.text = it.data[0].rating.toString()
////                tvBodytype.text = it.data[0].bodyType
//                vehicleNumber.text = it.data[0].vehicleNumber
//                tvCapacity.text = it.data[0].capacity
//                tvDistance.text = it.data[0].distance.toString()
//                tvDriverName.text = it.data[0].driverName
//                if (it.data[0].ownerName.isNullOrEmpty()){
//                    tvOwner.text = it.data[0].driverName
//                }else{
//                    tvOwner.text = it.data[0].ownerName
//                }
//
//                if (it.data[0].bookingStatus == "1"){
//                    bookingStatus.text  = "Ride Pending"
//                    llBtnCancel.visibility = View.VISIBLE
//                    btnReschedule.visibility = View.VISIBLE
////                    cancel.visibility=View.GONE
//                }else if (it.data[0].bookingStatus == "2"){
//                    bookingStatus.text  = "Ride Ongoing"
//                    llBtnCancel.visibility = View.GONE
//                    btnReschedule.visibility = View.GONE
////                    cancel.visibility=View.VISIBLE
//                }else if (it.data[0].bookingStatus == "3"){
//                    bookingStatus.text  = "Ride Cancelled"
//                    llBtnCancel.visibility = View.GONE
//                    btnReschedule.visibility = View.GONE
////                    cancel.visibility=View.VISIBLE
//                }else if (it.data[0].bookingStatus == "4"){
//                    bookingStatus.text  = "Ride Completed"
//                    llBtnCancel.visibility = View.GONE
//                    btnReschedule.visibility = View.GONE
////                    cancel.visibility=View.VISIBLE
//                }
//
//                tvFrom.text = it.data[0].picupLocation
//                tvTo.text = it.data[0].dropLocation
//                tvDriverNamee.text = it.data[0].driverName
//                tv_ridesNumber.text = it.data[0].completed_rides
//                tvDriverRating.text = it.data[0].rating.toString()
//                //  binding.wheelerType.text = it.data[0]..toString()+" Wheeler"
//                // binding.tvRidesNumber.text = it.data[0].r
//                tvPickLocation.text = it.data[0].picupLocation
//                tvDropLocation.text = it.data[0].dropLocation
//                tvAmount.text = "₹${it.data[0].fare}"
////                Glide.with(this).load(it.data[0].mainImage).into(vehicleImage)
//                Glide.with(this).load(it.data[0].image).into(ivDriverImage)
//                Glide.with(this).load(it.data[0].mainImage).into(vehicleImage)
//                val originLocation = LatLng(it.data[0].picupLat!!.toDouble(), it.data[0].picupLong!!.toDouble())
//                mMap.addMarker(MarkerOptions().position(originLocation))
//                val destinationLocation = LatLng(it.data[0].dropLat!!.toDouble(), it.data[0].dropLong!!.toDouble())
//                mMap.addMarker(MarkerOptions().position(destinationLocation))
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 14F))
//                getDirections(originLocation,destinationLocation)
//            } else {
//                toast(it.message!!)
//            }
//        }
        userPref.setDriverId(selectedPassengerTripData?.tripDetails?.driver?.id.toString())

        callDriverNumber = selectedPassengerTripData?.tripDetails?.driver?.mobileNumber.toString()

        if(selectedPassengerTripData?.status == 3){
            cancel.visibility=View.VISIBLE
        } else{
            cancel.visibility=View.GONE
        }
//        val vehicleImage: ImageView = bottomSheetPassTripDetails.findViewById(R.id.vehicle_image)!!
//        val ivDriverImage: ImageView = bottomSheetPassTripDetails.findViewById(R.id.iv_DriverImage)!!

        tvTripCode.text = selectedPassengerTripData?.rideCode
        tvBookingRDate.text = selectedPassengerTripData?.tripDetails?.bookingDateFrom
        tvBookingRTime.text = selectedPassengerTripData?.tripDetails?.time
        vehicleName.text = selectedPassengerTripData?.tripDetails?.vehicle?.vehicleName
//                tvRating.text = selectedPassengerTripData.tripDetails?.rating
//        tvBodytype.text = selectedPassengerTripData?.tripDetails?.vehicle?.bodyType?.name
        vehicleNumber.text = selectedPassengerTripData?.tripDetails?.vehicle?.vehicleNumber
        tvCapacity.text = selectedPassengerTripData?.tripDetails?.vehicle?.capacity
        tvDistance.text = selectedPassengerTripData?.tripDetails?.totalDistance
        tvDriverName.text = selectedPassengerTripData?.tripDetails?.driver?.name
        tvOwner.text = selectedPassengerTripData?.tripDetails?.user?.name
        tvFrom.text = selectedPassengerTripData?.tripDetails?.fromTrip
        tvTo.text = selectedPassengerTripData?.tripDetails?.toTrip
        tvDriverNamee.text = selectedPassengerTripData?.tripDetails?.driver?.name
//                tvDriverRating.text = it.data[0].rating.toString()
        tvPickLocation.text =selectedPassengerTripData?.tripDetails?.fromTrip
        tvDropLocation.text = selectedPassengerTripData?.tripDetails?.toTrip
//        tv_ridesNumber.text = selectedPassengerTripData?.rideCode
        tvAmount.text = "₹${selectedPassengerTripData?.tripDetails?.freightAmount}"
        Glide.with(this).load(selectedPassengerTripData?.tripDetails?.vehicle?.vehicleImage).into(vehicleImage)
        Glide.with(this).load(selectedPassengerTripData?.tripDetails?.driver?.profileImage).into(ivDriverImage)

        if (selectedPassengerTripData?.status == 1){
            bookingStatus.text  = "Ride Pending"
            llBtnCancel.visibility = View.VISIBLE
//            btnReschedule.visibility = View.VISIBLE
//                    cancel.visibility=View.GONE
        }else if (selectedPassengerTripData?.status == 2){
            bookingStatus.text  = "Ride Ongoing"
            llBtnCancel.visibility = View.GONE
//            btnReschedule.visibility = View.GONE
//                    cancel.visibility=View.VISIBLE
        }else if (selectedPassengerTripData?.status == 3){
            bookingStatus.text  = "Ride Cancelled"
            llBtnCancel.visibility = View.GONE
//            btnReschedule.visibility = View.GONE
//                    cancel.visibility=View.VISIBLE
        }else if (selectedPassengerTripData?.status == 4){
            bookingStatus.text  = "Ride Completed"
            llBtnCancel.visibility = View.GONE
//            btnReschedule.visibility = View.GONE
//                    cancel.visibility=View.VISIBLE
        }
//                Glide.with(this).load(it.data[0].driv).into(ivDriverImage)
        /*destinationLatitude = it.data[0].dropLat!!.toDouble()
        destinationLongitude = it.data[0].dropLong!!.toDouble()
        Log.d(TAG, "loaderTripDetailsDialog: "+destinationLatitude+destinationLongitude)*/

        val originLocation =
            selectedPassengerTripData?.tripDetails?.pickupLat?.toDouble()
                ?.let { selectedPassengerTripData?.tripDetails?.pickupLong?.toDouble()
                    ?.let { it1 -> LatLng(it, it1) } }
        originLocation?.let { MarkerOptions().position(it) }?.let { mMap.addMarker(it) }
        val destinationLocation =
            selectedPassengerTripData?.tripDetails?.dropupLat?.toDouble()
                ?.let { selectedPassengerTripData?.tripDetails?.dropupLong?.toDouble()
                    ?.let { it1 -> LatLng(it, it1) } }

        destinationLocation?.let { MarkerOptions().position(it) }?.let { mMap.addMarker(it) }
//                val urll = getDirectionURL(originLocation, destinationLocation, "AIzaSyCHl8Ff_ghqPjWqlT2BXJH5BOYH1q-sw0E")
//                GetDirection(urll).execute()
        originLocation?.let { CameraUpdateFactory.newLatLngZoom(it, 14F) }
            ?.let { mMap.animateCamera(it) }
        if (originLocation != null) {
            if (destinationLocation != null) {
                getDirections(originLocation,destinationLocation)
            }
        }
//            } else {
//                toast(it.message!!)
//            }
//        }

//        tvTripCode.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this, RideCompletedActivity :: class.java).also {
//                it.putExtra("loaderTripBookingId", selectedPassengerTripData!!) })
//        })

        btnCancel.setOnClickListener(View.OnClickListener {
            cancelReasonDialog()
        })


//        btnReschedule.setOnClickListener(View.OnClickListener {
//            clickDataPicker()
//        })

        llCallDriver.setOnClickListener(View.OnClickListener {
            // callDriverNumber.let { it1 -> listener.onCallNowClicked(it1) }
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$callDriverNumber")
            startActivity(intent)
        })
        tvTripCode.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PassengerRideCompletedActivity :: class.java).also {
                it.putExtra("passengerTripBookingId", selectedPassengerTripData)
//                it.putExtra("passengerTripUserId", selectedPassengerTripData?.userId!!)
            })
        })
//        btnReschedule.setOnClickListener(View.OnClickListener {
//            clickDataPicker()
//        })

        btnCancel.setOnClickListener(View.OnClickListener {
            cancelReasonDialog()
        })

        llCallDriver.setOnClickListener(View.OnClickListener {
            // callDriverNumber.let { it1 -> listener.onCallNowClicked(it1) }
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$callDriverNumber")
            startActivity(intent)
        })








        bottomSheetPassTripDetails.show()

    }

    private fun cancelReasonDialog() {
        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.bottom_sheet_cancel_trip, null)
        bottomSheetPassCancelReason = BottomSheetDialog(this)
        bottomSheetPassCancelReason.setContentView(dialogBinding)

        bottomSheetPassCancelReason.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        bottomSheetPassCancelReason.setCancelable(true)

        val rvReasons: RecyclerView = bottomSheetPassCancelReason.findViewById(R.id.rv_reasons)!!
        val btnConfirmCancel: Button = bottomSheetPassCancelReason.findViewById(R.id.btnConfirmCancel)!!
        val etFeedback: TextView = bottomSheetPassCancelReason.findViewById(R.id.et_feedback)!!
        val ivClose: ImageView = bottomSheetPassCancelReason.findViewById(R.id.iv_close)!!


        viewModel.passengerCancelReasonResponse.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                listData.clear()
                listData.addAll(it.data)
                passengerCancelReasonAdapter =
                    PassengerCancelTripReasonAdapter(this, listData)
                rvReasons.apply {
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
                bottomSheetPassCancelReason.dismiss()
                rideCancelledDialog()

                //   finish()
            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }

        btnConfirmCancel.setOnClickListener {
            listReasonType_id.clear()
            etFeedback.text.toString()
            for (i in 0 until rvReasons.childCount){
                val cbReason =rvReasons.getChildAt(i).findViewById(R.id.cb_reason) as CheckBox
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
                selectedPassengerTripData?.bookingId!!,
                str,
                etFeedback.text.toString()
            )
            Log.d("CheckBoxInfo",selectedPassengerTripData?.bookingId!!+str+etFeedback.text.toString())
        }
        ivClose.setOnClickListener {
            bottomSheetPassCancelReason.dismiss()
        }


        bottomSheetPassCancelReason.show()


    }
    private fun getDirections(origin: LatLng, destination: LatLng) {
//        viewModel.startLoading()
        val geoApiContext = GeoApiContext.Builder()
//            .apiKey("AIzaSyCAXiGrUjuJnBFVmHSEaXAQk4mROltfKbU")
            .apiKey(getString(R.string.api_key))
            .build()

        DirectionsApi.newRequest(geoApiContext)
            .mode(TravelMode.DRIVING)
            .origin(com.google.maps.model.LatLng(origin.latitude, origin.longitude))
            .destination(com.google.maps.model.LatLng(destination.latitude, destination.longitude))
            .setCallback(object : PendingResult.Callback<DirectionsResult> {
                override fun onFailure(e: Throwable?) {
//                    viewModel.stopLoading()
//                    viewModel.stopInPageLoading()
                    // Handle failure
                    runOnUiThread {
                        toast(e?.message.toString())
                    }

                }

                override fun onResult(result: DirectionsResult?) {
                    if (result != null && result.routes.isNotEmpty()) {
                        val route = result.routes[0]
                        // Draw the route on the map
                        try {
                            val polylineOptions = PolylineOptions().apply {
                                width(12f)
//                            color(Color.BLACK)
                                color(Color.parseColor("#0300fd"))//Google maps blue color
                                geodesic(true)
                            }
                            for (step in route.legs[0].steps) {
                                for (point in PolyUtil.decode(step.polyline.encodedPath)) {
                                    polylineOptions.add(point)
//                                    latLngList.add(point)
                                }
                            }

//                        setBounds(latLngList)
                            runOnUiThread {
//                                polylineOptions.addAll(latLngList)
                                polyline = mMap?.addPolyline(polylineOptions)
//                                eta = route.legs[0].duration.humanReadable
//                                distance = (calculateDistanceInKm(
//                                    origin.latitude,
//                                    origin.longitude,
//                                    destination.latitude,
//                                    destination.longitude
//                                )).roundToInt()
//                                if (pref.isKMPEnabled()) {
//                                    addOriginMarker(destination, eta, "$distance km.")
//                                }else{
//                                    var givenDistance = (distance/1.60934).roundToInt().toString()
//                                    addOriginMarker(destination, eta, "$givenDistance mi.")
//                                }


//                                viewModel.stopLoading()
//                                viewModel.stopInPageLoading()
                            }
//                            Log.d("ETA", "ETA :" + eta)
//                            Toast.makeText(requireContext(), eta, Toast.LENGTH_SHORT).show()
//                            mGoogleMap?.addPolyline(polylineOptions)
                        } catch (e: Exception) {
                            Log.d("points", e.toString())
                        }
                    }
                }
            })
    }
    private fun timeslots() {
        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.time_popup, null)
        timeslots = BottomSheetDialog(this)
        timeslots.setContentView(dialogBinding)

        timeslots.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        timeslots.setCancelable(true)
        val iv_close: ImageView = timeslots.findViewById(R.id.iv_close)!!
        val spinner_timeslots: Spinner = timeslots.findViewById(R.id.spinner_timeslots)!!
        val btnOk: Button = timeslots.findViewById(R.id.ok)!!


        iv_close.setOnClickListener {
            timeslots.dismiss()
        }
        btnOk.setOnClickListener {
            viewModel1.reschedule_trip_passenger("Bearer " + userPref.user.apiToken,
                selectedPassengerTripData?.bookingId.toString(),
                selectedDateFormat2,
                spinner_timeslots.selectedItem.toString())
                timeslots.dismiss()
        }

        timeslots.show()

    }
    private fun rideCancelledDialog() {
        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.bottom_sheet_ridecancellation, null)
        bottomSheetPassRideCancelled = BottomSheetDialog(this)
        bottomSheetPassRideCancelled.setContentView(dialogBinding)

        bottomSheetPassRideCancelled.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        bottomSheetPassRideCancelled.setCancelable(true)

        val tvCRN: TextView = bottomSheetPassRideCancelled.findViewById(R.id.tv_CRN)!!
        val ivClose: ImageView = bottomSheetPassRideCancelled.findViewById(R.id.iv_close)!!
        val btnOk: Button = bottomSheetPassRideCancelled.findViewById(R.id.btnOk)!!



        tvCRN.setText("Your booking with "+  crnNumber +" has been cancelled successfully.")
        bottomSheetPassRideCancelled.dismiss()
        this.finish()
        ivClose.setOnClickListener {
            bottomSheetPassRideCancelled.dismiss()
        }
        btnOk.setOnClickListener {
            bottomSheetPassRideCancelled.dismiss()
        }

    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")
        val tvBookingReDate: TextView= bottomSheetPassTripDetails.findViewById(R.id.tv_bookingRDate)!!
        //  val tvBookingReTime: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_bookingRTime)!!
        val datePickerDialog = DatePickerDialog(
            this,R.style.DatePickerTheme, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                tvBookingReDate.text = simpleDateFormat.format(cal.time)
                selectedDateFormat2 = simpleDateFormat2.format(cal.time)
                timeslots()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap.clear()
        val sydney = LatLng(-34.0, 151.0)
        val opera = LatLng(-33.9320447,151.1597271)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.addMarker(MarkerOptions().position(opera).title("Opera House"))
        passengerTripDetailsDialog()
    }


    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = java.util.ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }
    class MapData {
        var routes = java.util.ArrayList<Routes>()
    }

    class Routes {
        var legs = java.util.ArrayList<Legs>()
    }

    class Legs {
        var distance = Distance()
        var duration = Duration()
        var end_address = ""
        var start_address = ""
        var end_location = Location()
        var start_location = Location()
        var steps = java.util.ArrayList<Steps>()
    }

    class Steps {
        var distance = Distance()
        var duration = Duration()
        var end_address = ""
        var start_address = ""
        var end_location = Location()
        var start_location = Location()
        var polyline = PolyLine()
        var travel_mode = ""
        var maneuver = ""
    }

    class Duration {
        var text = ""
        var value = 0
    }

    class Distance {
        var text = ""
        var value = 0
    }

    class PolyLine {
        var points = ""
    }

    class Location{
        var lat =""
        var lng =""
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result = java.util.ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path = java.util.ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }

}