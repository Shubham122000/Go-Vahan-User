package com.govahanuser.com.activities.authorizedfranchise

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanuser.com.R
import com.govahanuser.com.adapters.AuthorizedFranchiseDetailAdapter
import com.govahanuser.com.adapters.AuthorizedFranchisePassengerAdapter
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityAuthorizedFranchisesDetailBinding
import com.govahanuser.com.model.LoaderName
import com.govahanuser.com.model.PassengerName
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class AuthorizedFranchisesDetail : BaseActivity() {
    lateinit var binding: ActivityAuthorizedFranchisesDetailBinding
    private val viewModel : AuthorizedFranchisesViewModel by viewModels()
    private var authorizedFranchiseAdapter : AuthorizedFranchiseDetailAdapter?= null
    private var authorizedFranchiseAdapter1 : AuthorizedFranchisePassengerAdapter?= null
    private var listData: ArrayList<LoaderName> = ArrayList()
    private var listData1: ArrayList<PassengerName> = ArrayList()
    var id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authorized_franchises_detail)
        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
         if (intent!=null){
             id=intent.getStringExtra("id").toString()
         }
        binding.header.tvHeaderText.setText("Details")

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.AuthorisedFranchisesDetailResponse.observe(this) {
            if (it.status == 1) {
                listData.clear()
                binding.tvOwnerName.text=it.data.name
                binding.tvPhn.text=it.data.mobile_number
                binding.tvEmail.text=it.data.email
                binding.address.text=it.data.address
                binding.count.text=it.data.total_vehicle.toString()
                binding.passengerCount.text="Passeneger Vehicle (${it.data.number_of_passenger_vehicle})"
                binding.loaderCount.text="Loader Vehicle (${it.data.number_of_loader_vehicle})"
                listData.clear()
                listData1.clear()
                    listData.addAll(it.loaderName)
                   listData1.addAll(it.passengerName)
                    binding.rvSearchAuthorizedFranchise.layoutManager = LinearLayoutManager(this)
                    authorizedFranchiseAdapter = AuthorizedFranchiseDetailAdapter(this, listData)
                    binding.rvSearchAuthorizedFranchise.adapter =authorizedFranchiseAdapter

                binding.rvSearchAuthorizedFranchise.layoutManager = LinearLayoutManager(this)
                authorizedFranchiseAdapter1 = AuthorizedFranchisePassengerAdapter(this, listData1)
                    binding.rvPassengerVehicle.adapter =authorizedFranchiseAdapter1
            } else   {
                Log.d("Response", it.toString())
                toast(this,it.message!!)
            }
        }
        viewModel.vendor_number_vehicle_list("Bearer " + userPref.user.apiToken,id)

    }
}