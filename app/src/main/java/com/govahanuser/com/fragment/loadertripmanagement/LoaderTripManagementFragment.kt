package com.govahanuser.com.fragment.loadertripmanagement

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanuser.com.R
import com.govahanuser.com.activities.tripdetails.TripDetailsActivity
import com.govahanuser.com.adapters.LoaderTripManagementAdapter
import com.govahanuser.com.baseClasses.BaseFragment
import com.govahanuser.com.databinding.FragmentLoaderTripManagementBinding
import com.govahanuser.com.fragment.loadertripmanagement.LoaderTripManagementFragmentViewModel
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementData
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class LoaderTripManagementFragment : BaseFragment(), LoaderTripManagementAdapter.OnClick  {

    private lateinit var binding : FragmentLoaderTripManagementBinding
    private val viewModel : LoaderTripManagementFragmentViewModel by viewModels()
    private var tripManagementAdapter : LoaderTripManagementAdapter?= null
    private var listData: ArrayList<LoaderTripManagementData> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loader_trip_management, container, false)

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
                // listData!!.addAll(it.getFavLocdata)

                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvTrip.visibility = View.GONE

                } else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvTrip.visibility = View.VISIBLE
                    it.result?.data?.let { it1 -> listData.addAll(it1) }
                    tripManagementAdapter = LoaderTripManagementAdapter(listData,this@LoaderTripManagementFragment)
                    binding.rvTrip.apply {
                        adapter = tripManagementAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }

            } else   {
                toast(requireContext(),it.message!!)
            }
        }
        return binding.root
    }




    override fun onProceedClicked(booking: LoaderTripManagementData?) {
        startActivity(Intent(requireContext(), TripDetailsActivity :: class.java).also {
            it.putExtra("loaderTripDetails", booking)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLoaderTripManagementApi("Bearer " + userPref.user.apiToken,"1","1")

    }




}