package com.govahanuser.com.activities.tripdetails

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.Uri
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
import com.govahanuser.com.R
import com.govahanuser.com.activities.ridecompleted.RideCompletedActivity
import com.govahanuser.com.adapters.LoaderCancelTripReasonAdapter
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityTripDetailsBinding
import com.govahanuser.com.model.loadercancelreasonmodel.LoaderCancelReasonData
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import okhttp3.*


import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import com.google.maps.android.PolyUtil
import com.govahanuser.com.activities.tripdetails.TripDetailsViewModel
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementData

@AndroidEntryPoint
class TripDetailsActivity : BaseActivity(),OnMapReadyCallback {
    private lateinit var binding: ActivityTripDetailsBinding
    private lateinit var selectedLoaderTripData : LoaderTripManagementData
    private val viewModel: TripDetailsViewModel by viewModels()
    private var listData: ArrayList<LoaderCancelReasonData> = ArrayList()
    private var loaderCancelReasonAdapter: LoaderCancelTripReasonAdapter? = null
    private var listReasonType_id:ArrayList<String> = ArrayList()
    var reasontypevalue_id: String? = ""
    private var polyline: com.google.android.gms.maps.model.Polyline? = null
     var str: String=""
    var selectedDateFormat2 = ""
    var time = ""
    val mcurrentTime = Calendar.getInstance()
    var hour = mcurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mcurrentTime[Calendar.MINUTE]
    val zone = mcurrentTime[Calendar.AM_PM]

    var crnNumber = ""
    var callDriverNumber = ""

    lateinit var bottomSheetLoaderTripDetails: BottomSheetDialog
    lateinit var bottomSheetCancelReason: BottomSheetDialog
    lateinit var bottomSheetRideCancelled: BottomSheetDialog
    lateinit var timeslots: BottomSheetDialog

    // GeeksforGeeks coordinates
    private var originLatitude: Double = 0.0
    private var originLongitude: Double = 0.0

    /* // Coordinates of a park nearby
     private var destinationLatitude: Double = 0.0
     private var destinationLongitude: Double = 0.0*/

    private lateinit var mMap: GoogleMap

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details)
        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.header.tvHeaderText.text = "Trip Details"
        if (intent != null) {
            val data = intent.extras
            selectedLoaderTripData = data?.getParcelable("loaderTripDetails")!!
        }

        val options = PolylineOptions()
        options.color(Color.RED)
        options.width(5f)

        Log.d("TAG___", "onCreate: " + selectedLoaderTripData)

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.loaderRescheduleTripResponse.observe(this) {
            if (it.status == 1) {
                // toast("booking Successful")
                toast(it.message!!)
                finish()
            } else {
                toast(it.message!!)
            }
        }

//        viewModel.loaderTripManagementDetailApi("Bearer " + userPref.user.apiToken,selectedLoaderTripData)
        viewModel.getLoaderCancelReasonListApi("Bearer " + userPref.user.apiToken)

//        var origin = LatLng(originLatitude,originLongitude)
//        var destination = LatLng(25.4484,78.5685)
//        getRoute(origin,destination)

    }
    private fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun loaderTripDetailsDialog() {
        val dialogBinding = LayoutInflater
            .from(this@TripDetailsActivity).inflate(R.layout.bottom_sheet_loader_trip_details, null)
        bottomSheetLoaderTripDetails = BottomSheetDialog(this@TripDetailsActivity)
        bottomSheetLoaderTripDetails.setContentView(dialogBinding)
        bottomSheetLoaderTripDetails.setCancelable(true)
        bottomSheetLoaderTripDetails.setCanceledOnTouchOutside(false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        bottomSheetLoaderTripDetails.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetLoaderTripDetails.behavior.peekHeight = 340
        bottomSheetLoaderTripDetails.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
                bottomSheetInternal.setBackgroundResource(R.drawable.rounded_top_corners)
        }
        val tvTripCode: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_trip_code)!!
        val tvBookingRDate: TextView= bottomSheetLoaderTripDetails.findViewById(R.id.tv_bookingRDate)!!
        val tvBookingRTime: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_bookingRTime)!!
        val vehicleName: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.vehicle_name)!!
        val tvRating: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_rating)!!
        val tvBodytype: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_bodytype)!!
        val vehicleNumber: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.vehicle_number)!!
        val tvCapacity: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_capacity)!!
        val tvDistance: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_distance)!!
        val tvDriverName: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_driverName)!!
        val tvOwner: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_owner)!!
        val tvFrom: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_from)!!
        val tvTo: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_to)!!
        val tv_ridesNumber: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_ridesNumber)!!
        val tvDriverNamee: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_driverNamee)!!
        val tvDriverRating: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_DriverRating)!!
        val tvPickLocation: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_pickLocation)!!
        val tvDropLocation: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_dropLocation)!!
        val tvAmount: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_amount)!!
        val llBtnCancel: LinearLayout = bottomSheetLoaderTripDetails.findViewById(R.id.ll_btnCancel)!!
        val btnCancel: Button = bottomSheetLoaderTripDetails.findViewById(R.id.btnCancel)!!
        val btnReschedule: Button = bottomSheetLoaderTripDetails.findViewById(R.id.btnReschedule)!!
        val cancel: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.cancel)!!
        val llCallDriver: LinearLayout = bottomSheetLoaderTripDetails.findViewById(R.id.llCallDriver)!!
        val status: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.status)!!

//        viewModel.loaderTripDetailResponse.observe(this) {
//            if (it.status == 1) {
//                // toast("booking Successful")
//                toast(it.message!!)

                userPref.setDriverId(selectedLoaderTripData.tripDetails?.driver?.id.toString())

                callDriverNumber = selectedLoaderTripData.tripDetails?.driver?.mobileNumber.toString()

                if(selectedLoaderTripData.status == 3){
                    cancel.visibility=View.VISIBLE
                } else{
                    cancel.visibility=View.GONE
                }
                val vehicleImage: ImageView = bottomSheetLoaderTripDetails.findViewById(R.id.vehicle_image)!!
                val ivDriverImage: ImageView = bottomSheetLoaderTripDetails.findViewById(R.id.iv_DriverImage)!!

                tvTripCode.text = selectedLoaderTripData.rideCode
                tvBookingRDate.text = selectedLoaderTripData.tripDetails?.bookingDateFrom
                tvBookingRTime.text = selectedLoaderTripData.tripDetails?.time
                vehicleName.text = selectedLoaderTripData.tripDetails?.vehicle?.vehicleName
//                tvRating.text = selectedLoaderTripData.tripDetails?.rating
                tvBodytype.text = selectedLoaderTripData.tripDetails?.vehicle?.bodyType?.name
                vehicleNumber.text = selectedLoaderTripData.tripDetails?.vehicle?.vehicleNumber
                tvCapacity.text = selectedLoaderTripData.tripDetails?.vehicle?.capacity
                tvDistance.text = selectedLoaderTripData.tripDetails?.totalDistance
                tvDriverName.text = selectedLoaderTripData.tripDetails?.driver?.name
                tvOwner.text = selectedLoaderTripData.tripDetails?.user?.name
                tvFrom.text = selectedLoaderTripData.tripDetails?.fromTrip
                tvTo.text = selectedLoaderTripData.tripDetails?.toTrip
                tvDriverNamee.text = selectedLoaderTripData.tripDetails?.driver?.name
//                tvDriverRating.text = it.data[0].rating.toString()
                tvPickLocation.text =selectedLoaderTripData.tripDetails?.fromTrip
                tvDropLocation.text = selectedLoaderTripData.tripDetails?.toTrip
                tv_ridesNumber.text = selectedLoaderTripData.rideCode
                tvAmount.text = "₹${selectedLoaderTripData.tripDetails?.freightAmount}"
                Glide.with(this).load(selectedLoaderTripData.tripDetails?.vehicle?.vehicleImage).into(vehicleImage)
                Glide.with(this).load(selectedLoaderTripData.tripDetails?.driver?.profileImage).into(ivDriverImage)

                if (selectedLoaderTripData.status == 1){
                    status.text  = "Ride Pending"
                    llBtnCancel.visibility = View.VISIBLE
                    btnReschedule.visibility = View.VISIBLE
//                    cancel.visibility=View.GONE
                }else if (selectedLoaderTripData.status == 2){
                    status.text  = "Ride Ongoing"
                    llBtnCancel.visibility = View.GONE
                    btnReschedule.visibility = View.GONE
//                    cancel.visibility=View.VISIBLE
                }else if (selectedLoaderTripData.status == 3){
                    status.text  = "Ride Cancelled"
                    llBtnCancel.visibility = View.GONE
                    btnReschedule.visibility = View.GONE
//                    cancel.visibility=View.VISIBLE
                }else if (selectedLoaderTripData.status == 4){
                    status.text  = "Ride Completed"
                    llBtnCancel.visibility = View.GONE
                    btnReschedule.visibility = View.GONE
//                    cancel.visibility=View.VISIBLE
                }
//                Glide.with(this).load(it.data[0].driv).into(ivDriverImage)
                /*destinationLatitude = it.data[0].dropLat!!.toDouble()
                destinationLongitude = it.data[0].dropLong!!.toDouble()
                Log.d(TAG, "loaderTripDetailsDialog: "+destinationLatitude+destinationLongitude)*/

                val originLocation =
                    selectedLoaderTripData.tripDetails?.pickupLat?.toDouble()
                        ?.let { selectedLoaderTripData.tripDetails?.pickupLong?.toDouble()
                            ?.let { it1 -> LatLng(it, it1) } }
        originLocation?.let { MarkerOptions().position(it) }?.let { mMap.addMarker(it) }
                val destinationLocation =
                    selectedLoaderTripData.tripDetails?.dropupLat?.toDouble()
                        ?.let { selectedLoaderTripData.tripDetails?.dropupLong?.toDouble()
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

        tvTripCode.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, RideCompletedActivity :: class.java).also {
                it.putExtra("loaderTripBookingId", selectedLoaderTripData!!) })
        })

        btnCancel.setOnClickListener(View.OnClickListener {
            cancelReasonDialog()
        })


        btnReschedule.setOnClickListener(View.OnClickListener {
            clickDataPicker()
        })

        llCallDriver.setOnClickListener(View.OnClickListener {
            // callDriverNumber.let { it1 -> listener.onCallNowClicked(it1) }
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$callDriverNumber")
            startActivity(intent)
        })
        bottomSheetLoaderTripDetails.show()
    }

//    private fun getURL(from : LatLng, to : LatLng) : String {
//        val origin = "origin=" + from.latitude + "," + from.longitude
//        val dest = "destination=" + to.latitude + "," + to.longitude
//        val sensor = "sensor=false"
//        val params = "$origin&$dest&$sensor"
//        return "https://maps.googleapis.com/maps/api/directions/json?$params"
//    }

//    interface DirectionsService {
//        @GET("maps/api/directions/json")
//        fun getDirections(
//            @Query("origin") origin: String,
//            @Query("destination") destination: String,
//            @Query("key") apiKey: String
//        ): Call<DirectionsResponse>
//    }
//    private fun getDirections(/*origin: LatLng, destination: LatLng*/) {
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://maps.googleapis.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(DirectionsService::class.java)
//        val call = service.getDirections(
//            "37.7749,-122.4194",  // origin
//            "34.0522,-118.2437",  // destination
//            "AIzaSyCrMNDzzK9j97GHsiWRhcPnqF_aI3JP9zw"
//        )
//
//        call.enqueue(object : Callback<DirectionsResponse> {
//            override fun onResponse(
//                call: Call<DirectionsResponse>,
//                response: Response<DirectionsResponse>
//            ) {
//                val directions = response.body()
//                if (directions != null && directions.routes.isNotEmpty()) {
//                    try {
//                        val points = directions.routes[0].overview_polyline.points
//                        val decodedPath = PolyUtil.decode(points)
//                        runOnUiThread {
//                            mMap.addPolyline(
//                                PolylineOptions().addAll(decodedPath).color(Color.BLUE).width(10f)
//                            )
//                        }
//                    }catch (e:Exception){
//                        e.printStackTrace()
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
//                toast(t.message.toString())
//                // Handle the error
//            }
//        })
//    }


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


    private fun cancelReasonDialog() {
        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.bottom_sheet_cancel_trip, null)
        bottomSheetCancelReason = BottomSheetDialog(this)
        bottomSheetCancelReason.setContentView(dialogBinding)

        bottomSheetCancelReason.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        bottomSheetCancelReason.setCancelable(true)

        val rvReasons: RecyclerView = bottomSheetCancelReason.findViewById(R.id.rv_reasons)!!
        val btnConfirmCancel: Button = bottomSheetCancelReason.findViewById(R.id.btnConfirmCancel)!!
        val etFeedback: TextView = bottomSheetCancelReason.findViewById(R.id.et_feedback)!!
        val ivClose: ImageView = bottomSheetCancelReason.findViewById(R.id.iv_close)!!

        viewModel.loaderCancelReasonResponse.observe(this) {
            if (it.status == 1) {
                toast(it.message!!)
                listData.clear()
                listData.addAll(it.data)
                loaderCancelReasonAdapter =
                    LoaderCancelTripReasonAdapter(this, listData)
                rvReasons.apply {
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
                bottomSheetCancelReason.dismiss()
                rideCancelledDialog()
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

            viewModel.loaderTripCancelApi(
                "Bearer " + userPref.user.apiToken,
                selectedLoaderTripData.bookingId.toString(),
                str,
                etFeedback.text.toString()
            )
//            Log.d("CheckBoxInfo",selectedLoaderTripData!!+str+etFeedback.text.toString())

        }
        ivClose.setOnClickListener {
            bottomSheetCancelReason.dismiss()
        }

        bottomSheetCancelReason.show()

    }

    private fun rideCancelledDialog() {

        val dialogBinding = LayoutInflater
            .from(this).inflate(R.layout.bottom_sheet_ridecancellation, null)
        bottomSheetRideCancelled = BottomSheetDialog(this)
        bottomSheetRideCancelled.setContentView(dialogBinding)

        bottomSheetRideCancelled.setOnShowListener { dia ->
            val bottomSheetDialog = dia as BottomSheetDialog
            val bottomSheetInternal: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            bottomSheetInternal.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        bottomSheetRideCancelled.setCancelable(true)

        val tvCRN: TextView = bottomSheetRideCancelled.findViewById(R.id.tv_CRN)!!
        val ivClose: ImageView = bottomSheetRideCancelled.findViewById(R.id.iv_close)!!
        val btnOk: Button = bottomSheetRideCancelled.findViewById(R.id.btnOk)!!

        tvCRN.setText("Your booking with "+  crnNumber +" has been cancelled successfully.")
        bottomSheetRideCancelled.dismiss()
        this.finish()

        ivClose.setOnClickListener {
            bottomSheetRideCancelled.dismiss()
        }
        btnOk.setOnClickListener {
            bottomSheetRideCancelled.dismiss()
        }

        bottomSheetRideCancelled.show()

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
            viewModel.loaderRescheduleTripApi("Bearer " + userPref.user.apiToken,
                selectedLoaderTripData.bookingId.toString(),
                selectedDateFormat2,
                spinner_timeslots.selectedItem.toString())
            timeslots.dismiss()
        }

        timeslots.show()

    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")
        val tvBookingReDate: TextView= bottomSheetLoaderTripDetails.findViewById(R.id.tv_bookingRDate)!!
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

//    @SuppressLint("SetTextI18n")
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun clickTimePicker() {
//        val mTimePicker = TimePickerDialog(
//            this,R.style.DatePickerTheme, { timePicker, selectedHour, selectedMinute ->
//
//                time = "$selectedHour:$selectedMinute "
//                // bottomSheet.findViewById<Button>(R.id.tvTime).text = time
//                val tvBookingReTime: TextView = bottomSheetLoaderTripDetails.findViewById(R.id.tv_bookingRTime)!!
//                tvBookingReTime.text = time
//
//                viewModel.loaderRescheduleTripApi("Bearer " + userPref.user.apiToken,
//                    selectedLoaderTripData!!,
//                    selectedDateFormat2,
//                    time)
//
//                Log.d(TAG, "clickTimePicker: "+selectedLoaderTripData!!+
//                        selectedDateFormat2+time)
//            },
//            hour,
//            minute + zone,
//            false
//        ) //Yes 24 hour time
//        mTimePicker.setTitle("Select Time")
//        mTimePicker.show()
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        val opera = LatLng(-33.9320447,151.1597271)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.addMarker(MarkerOptions().position(opera).title("Opera House"))
        loaderTripDetailsDialog()
    }
}