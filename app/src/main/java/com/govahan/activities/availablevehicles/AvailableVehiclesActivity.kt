package com.govahan.activities.availablevehicles

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
import com.govahan.R
import com.govahan.activities.ReviewsActivity
import com.govahan.activities.bookingreview.BookingReviewActivity
import com.govahan.adapters.AvailableVehiclesAdapter
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityAvailableVehiclesBinding
import com.govahan.databinding.RecordSavedBinding
import com.govahan.model.searchvehiclemodel.SearchVehicleData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AvailableVehiclesActivity : BaseActivity(), AvailableVehiclesAdapter.OnClick {
    private lateinit var binding : ActivityAvailableVehiclesBinding
    private var availableVehiclesAdapter : AvailableVehiclesAdapter ?= null
    private val viewModel : AvailableVehiclesViewModel by viewModels()
    var g_triptask = ""
    var g_pickupLocation = ""
    var pickupLat = ""
    var pickupLong = ""
    var dropLat = ""
    var dropLong = ""
    var g_dropLocation = ""
    var g_truckType = ""
    var g_capacity = ""
    var g_body_type = ""
    var g_wheel = ""
    var g_price_for = ""
    var g_booking_date = ""
    var g_booking_time = ""
//    var filteredData:ArrayList<SearchVehicleData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_available_vehicles)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.header.tvHeaderText.setText("Available Vehicles")
        pickupLat = intent.getStringExtra("pickupLatitude").toString()
        pickupLong = intent.getStringExtra("pickupLongitude").toString()
        dropLat = intent.getStringExtra("dropLatitude").toString()
        dropLong = intent.getStringExtra("dropLongitude").toString()
        g_triptask = intent.getStringExtra("triptask").toString()
        g_pickupLocation = intent.getStringExtra("pickupLocation").toString()
            g_dropLocation = intent.getStringExtra("dropLocation").toString()
        g_truckType = intent.getStringExtra("truckType").toString()
        g_capacity = intent.getStringExtra("capacity").toString()
        g_body_type = intent.getStringExtra("body_type").toString()
        g_wheel = intent.getStringExtra("wheel").toString()
        g_price_for = intent.getStringExtra("price_for").toString()
        g_booking_date = intent.getStringExtra("booking_date").toString()
        g_booking_time = intent.getStringExtra("booking_time").toString()


        viewModel.searchLoaderVehicleApi("Bearer " + userPref.user.apiToken,
            pickupLat,
            pickupLong,
            dropLat,
            dropLong,
            "1",
            g_truckType,
          /*  g_body_type,
            "",
            g_wheel,*/
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
        viewModel.availableVehicleListResponse.observe(this) {
            if (it.error == false) {
                availableVehiclesAdapter = it.result?.trips?.let { it1 ->
                    AvailableVehiclesAdapter(
                        it1,
                        this,this)
                }
                binding.rvAvailableVehicles.apply {
                    adapter = availableVehiclesAdapter
                    layoutManager = LinearLayoutManager(this@AvailableVehiclesActivity)
                }

            } else {
                InsufficientWallet(it.message.toString())
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

    override fun onProceedClicked(searchListModelData: SearchVehicleData) {
        startActivity(Intent(this, BookingReviewActivity :: class.java).also {
            it.putExtra("ModelData", searchListModelData)


        })
    }

    override fun reviewsclick(id: String) {
        startActivity(Intent(this, ReviewsActivity :: class.java).also {
            it.putExtra("driver_id", id)
    })
    }


}