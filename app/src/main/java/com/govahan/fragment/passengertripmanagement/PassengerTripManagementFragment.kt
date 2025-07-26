package com.govahan.fragment.passengertripmanagement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahan.R
import com.govahan.activities.passengers.passengertripdetails.PassengerTripDetailsActivity
import com.govahan.adapters.PassengerTripManagementAdapter
import com.govahan.baseClasses.BaseFragment
import com.govahan.databinding.FragmentPassengerTripManagementBinding
import com.govahan.fragment.passengertripmanagement.PassengerTripManagementFragmentViewModel
import com.govahan.model.tripmanagementpassengermodel.PassengerTripManagementData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PassengerTripManagementFragment : BaseFragment(), PassengerTripManagementAdapter.OnClick {

    private lateinit var binding : FragmentPassengerTripManagementBinding
    private val viewModel : PassengerTripManagementFragmentViewModel by viewModels()
    private var tripManagementAdapter : PassengerTripManagementAdapter?= null
    private var listData: ArrayList<LoaderTripManagementData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger_trip_management, container, false)
//        viewModel.getPassengerTripManagementApi("Bearer " + userPref.user.apiToken)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.getLoaderTripManagementResponse.observe(requireActivity()) {
            if (it.error == false) {
                listData.clear()
                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvPassengerTrip.visibility = View.GONE
                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvPassengerTrip.visibility = View.VISIBLE
                    it.result?.data?.let { it1 -> listData.addAll(it1) }
                    tripManagementAdapter = PassengerTripManagementAdapter(listData,this@PassengerTripManagementFragment)
                    binding.rvPassengerTrip.apply {
                        adapter = tripManagementAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLoaderTripManagementApi("Bearer " + userPref.user.apiToken,"2","1")
    }

    override fun onProceedPassengerClicked(passengerTripListModelData: LoaderTripManagementData) {
        startActivity(Intent(requireContext(), PassengerTripDetailsActivity :: class.java).also {
            it.putExtra("passengerTripDetails", passengerTripListModelData)

        })

        Log.d("TAG++", "onProceedClicked: "+passengerTripListModelData.bookingId.toString())
    }


}