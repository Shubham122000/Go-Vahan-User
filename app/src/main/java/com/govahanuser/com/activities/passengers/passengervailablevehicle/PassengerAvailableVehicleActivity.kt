package com.govahan.com.activities.passengers.passengervailablevehicle

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahan.com.R
import com.govahan.com.activities.ReviewsActivity
import com.govahan.com.activities.passengers.passengerbookingreview.BookingReviewPActivity
import com.govahan.com.adapters.AvailablePVehiclesAdapter
import com.govahan.com.baseClasses.BaseActivity
import com.govahan.com.databinding.ActivityPassengerAvailableVehicleBinding
import com.govahan.com.databinding.RecordSavedBinding
import com.govahan.com.model.searchPassengerVehicle.SearchPassengerData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PassengerAvailableVehicleActivity : BaseActivity(), AvailablePVehiclesAdapter.OnClick {
    private lateinit var binding : ActivityPassengerAvailableVehicleBinding
    private val viewModel : AvailablePassengerVehiclesViewModel by viewModels()
    private var availableVehiclesPassAdapter : AvailablePVehiclesAdapter ?= null

    var pickupLat = ""
    var pickupLong = ""
    var dropLat = ""
    var dropLong = ""

    var g_vehicle_type = ""
    var g_seat = ""
    var g_tyers = ""
    var g_booking_date = ""
    var g_booking_time = ""
    var pickup_location = ""
    var dropup_location = ""
//    var filteredData:ArrayList<SearchPassengerData> = arrayListOf()]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_available_vehicle)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

        binding.header.tvHeaderText.setText("Available Vehicles")


        /*val layoutManagerTopSongs: RecyclerView.LayoutManager = LinearLayoutManager(this,
            GridLayoutManager.VERTICAL, false)
        binding.rvAvailableVehicles.setLayoutManager(layoutManagerTopSongs)
        val itemsTopSongs: List<String> = Arrays.asList("item", "item", "item", "item", "item", "item", "item", "item", "item", "item")
        binding.rvAvailableVehicles.setAdapter(AvailablePVehiclesAdapter(this, itemsTopSongs))
*/

        pickupLat = intent.getStringExtra("pickupLatitude")!!
        pickupLong = intent.getStringExtra("pickupLongitude")!!
        dropLat = intent.getStringExtra("dropLatitude")!!
        dropLong = intent.getStringExtra("dropLongitude")!!
        g_vehicle_type = intent.getStringExtra("vehicle_type")!!
        g_seat = intent.getStringExtra("seat")!!
        g_tyers = intent.getStringExtra("tyers")!!
        g_booking_date = intent.getStringExtra("booking_date")!!
        g_booking_time = intent.getStringExtra("booking_time")!!
        pickup_location = intent.getStringExtra("pickup_location")!!
        dropup_location = intent.getStringExtra("dropup_location")!!

//        viewModel.searchPassengerVehicleApi("Bearer " + userPref.user.apiToken,
//            intent.getStringExtra("pickupLatitude")!!,
//            intent.getStringExtra("pickupLongitude")!!,
//            intent.getStringExtra("dropLatitude")!!,
//            intent.getStringExtra("dropLongitude")!!,
//            intent.getStringExtra("vehicle_type")!!,
//            intent.getStringExtra("tyers")!!,
//            intent.getStringExtra("booking_date")!!,
//            intent.getStringExtra("booking_time")!!,
//            intent.getStringExtra("pickup_location")!!,
//            intent.getStringExtra("dropup_location")!!,)

        viewModel.searchLoaderVehicleApi("Bearer " + userPref.user.apiToken,
            pickupLat,
            pickupLong,
            dropLat,
            dropLong,
            "2",
            g_vehicle_type,
//            g_body_type,
//            "",
//            g_wheel,
            g_booking_date,
            g_booking_time
        )


        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.availablePassengerVehicleListResponse.observe(this) {
            if (it.status == 1) {
//                for (item in 0 until it.data.size) {
//                    if (it.data.get(item).distance_search!! < 0) {
//                        filteredData.clear()
//                        filteredData = it.data
//                    }
//                }
                availableVehiclesPassAdapter =  AvailablePVehiclesAdapter(it.data,
                    this,this)
                binding.rvAvailableVehicles.apply {
                    adapter = availableVehiclesPassAdapter
                    layoutManager = LinearLayoutManager(this@PassengerAvailableVehicleActivity)
                }
            } else {
                InsufficientWallet(it.message.toString())
//                Log.d("Response", it.toString())
//                toast(it.message!!)
            }
        }

    }

    private fun InsufficientWallet(text:String) {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: RecordSavedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.record_saved,
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

        bindingDialog.oks.setOnClickListener {
            cDialog.dismiss()
            finish()
        }
    }

    override fun onCallNowClicked(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    override fun onProceedClicked(searchPassListModelData: SearchPassengerData) {
        startActivity(Intent(this, BookingReviewPActivity :: class.java).also {
            it.putExtra("vehicleDetails", searchPassListModelData)
            it.putExtra("pickupLatitude", pickupLat)
            it.putExtra("pickupLongitude", pickupLong)
            it.putExtra("dropLatitude", dropLat)
            it.putExtra("dropLongitude", dropLong)
            it.putExtra("dropLongitude", dropLong)
            it.putExtra("vehicle_type", g_vehicle_type)
        })
    }

    override fun reviewsclick(id: String) {
        startActivity(Intent(this, ReviewsActivity :: class.java).also {
            it.putExtra("driver_id", id)
        })
    }


}