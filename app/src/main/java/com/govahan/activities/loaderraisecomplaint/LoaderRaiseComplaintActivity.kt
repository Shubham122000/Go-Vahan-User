package com.govahan.activities.loaderraisecomplaint

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityRaiseComplaintBinding
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoaderRaiseComplaintActivity : BaseActivity() {
    private lateinit var binding: ActivityRaiseComplaintBinding
    private val viewModel: LoaderRaiseComplaitViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_raise_complaint)
        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Raise Complaint")
        val getBookingId = intent.getStringExtra("BookingId")
        binding.bookingid.setText(getBookingId)
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.addLoaderComplaintResponse.observe(this) {
            if (it.error == false) {
                toast(it.message!!)
                finish()

            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }
        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            if (binding.etSubject.text.toString().isBlank()) {
                toast(this, "Please enter complaint subject.")
            } else if (binding.etComplaint.text.toString().isBlank()) {
                toast(this, "Please enter your complaint.")
            } else {
                viewModel.raiseComplaintApi(
                    "Bearer " + userPref.user.apiToken, getBookingId.toString(),
                    binding.etSubject.text.toString(),
                    binding.etComplaint.text.toString()
                )
            }
        })
    }
}