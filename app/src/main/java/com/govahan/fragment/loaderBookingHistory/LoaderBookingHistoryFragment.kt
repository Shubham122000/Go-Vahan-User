package com.govahan.fragment.loaderBookingHistory

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahan.R
import com.govahan.activities.loaderCancelledbookingdetails.LoaderCancelledBookingDetailsActivity
import com.govahan.activities.loadercompletedbookingdetails.LoaderCompletedBookingDetailsActivity
import com.govahan.activities.loaderongoingbookingdetails.LoaderOngoingBookingDetailsActivity
import com.govahan.adapters.CancelledLoaderTripHistoryAdapter
import com.govahan.adapters.CompletedLoaderTripHistoryAdapter
import com.govahan.adapters.LoaderBookingHistoryViewPagerAdapter
import com.govahan.adapters.OngoingLoaderTripHistoryAdapter
import com.govahan.baseClasses.BaseFragment
import com.govahan.databinding.FragmentLoaderBookingHistoryBinding
import com.govahan.fragment.loadercompletedtriphistory.LoaderCancelledTripHistoryFragmentViewModel
import com.govahan.fragment.loadercompletedtriphistory.LoaderCompletedTripHistoryFragmentViewModel
import com.govahan.fragment.loaderongoingtriphistory.LoaderOngoingTripHistoryFragmentViewModel
import com.govahan.model.cancelledloadertriphistorymodel.CancelledLoaderTripHistoryData
import com.govahan.model.completedloadertriphistorymodel.CompletedLoaderHistoryData
import com.govahan.model.ongoingloadertriphistorymodel.OngoingLoaderHistoryData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class LoaderBookingHistoryFragment : BaseFragment(), OngoingLoaderTripHistoryAdapter.OnClick, CompletedLoaderTripHistoryAdapter.OnClick, CancelledLoaderTripHistoryAdapter.OnClick {
    lateinit var binding : FragmentLoaderBookingHistoryBinding

    private var loaderpagerAdapter: LoaderBookingHistoryViewPagerAdapter? = null
    var selectedItem = ""
    private val viewModelOngoingLoader : LoaderOngoingTripHistoryFragmentViewModel by viewModels()
    private var ongoingTripHistoryAdapter : OngoingLoaderTripHistoryAdapter ?= null
    private var listDataOngoingLoader: ArrayList<LoaderTripManagementData> = ArrayList()

    private val viewModelCompletedLoader : LoaderCompletedTripHistoryFragmentViewModel by viewModels()
    private var completedTripHistoryAdapter : CompletedLoaderTripHistoryAdapter?= null
    private var listDataCompletedLoader: ArrayList<CompletedLoaderHistoryData> = ArrayList()
    private val viewModelCancelledLoader : LoaderCancelledTripHistoryFragmentViewModel by viewModels()
    private var cancelledTripHistoryAdapter : CancelledLoaderTripHistoryAdapter?= null
    private var listDataCancelledLoader: ArrayList<CancelledLoaderTripHistoryData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
      ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loader_booking_history, container, false)
           // setTab()

        viewModelOngoingLoader.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }



        binding.spinnerLoaderHistory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedItem = parent.getItemAtPosition(position).toString()
                Log.d("PositionSelected",selectedItem)

                if (selectedItem == "Pending") {
                    toast(requireContext(),"Pending")
                    viewModelOngoingLoader.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "1"
                    )


                }else if (selectedItem == "Ongoing") {
                    toast(requireContext(),"Ongoing")
                    viewModelOngoingLoader.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "2"
                    )

                }
                else if (selectedItem == "Completed") {
                    viewModelOngoingLoader.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "4"
                    )
                }
                else if (selectedItem == "Cancelled") {

                    viewModelOngoingLoader.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "3"
                    )
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModelOngoingLoader.getLoaderOngoingHistoryResponse.observe(requireActivity()) {
            if (it.error == false) {
                listDataOngoingLoader.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvOngoing.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvOngoing.visibility = View.VISIBLE
                    it.result?.data?.let { it1 -> listDataOngoingLoader.addAll(it1) }
                    ongoingTripHistoryAdapter = OngoingLoaderTripHistoryAdapter(listDataOngoingLoader,this@LoaderBookingHistoryFragment)
                    binding.rvOngoing.apply {
                        adapter = ongoingTripHistoryAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }

            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvOngoing.visibility = View.GONE
            }
        }

        viewModelOngoingLoader.UpComingsTripHistoryApi(
            "Bearer " + userPref.getToken().toString(), "1", "1"
        )
//        viewModelOngoingLoader.loaderPendingBookingTripHistoryApi("Bearer " + userPref.user.apiToken)
//        viewModelOngoingLoader.loaderOngoingBookingTripHistoryApi("Bearer " + userPref.user.apiToken)
        Log.d(ContentValues.TAG, "onCreateView: OngoingLoader"+userPref.user.apiToken)





        viewModelCompletedLoader.getLoaderCompletedHistoryResponse.observe(requireActivity()) {
            if (it.status == 1) {
                listDataCompletedLoader.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvOngoing.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvOngoing.visibility = View.VISIBLE
                    listDataCompletedLoader.addAll(it.data)
                    completedTripHistoryAdapter = CompletedLoaderTripHistoryAdapter(listDataCompletedLoader,this@LoaderBookingHistoryFragment)
                    binding.rvOngoing.apply {
                        adapter = completedTripHistoryAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvOngoing.visibility = View.GONE
            }
        }
        /*viewModelCompletedLoader.loaderCompletedBookingTripHistoryApi("Bearer " + userPref.user.apiToken)
        Log.d(ContentValues.TAG, "onCreateView: "+userPref.user.apiToken)*/

        viewModelCancelledLoader.getLoaderCancelledHistoryResponse.observe(requireActivity()) {
            if (it.status == 1) {
                listDataCancelledLoader.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvOngoing.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvOngoing.visibility = View.VISIBLE
                    listDataCancelledLoader.addAll(it.data)
                    cancelledTripHistoryAdapter = CancelledLoaderTripHistoryAdapter(listDataCancelledLoader,this@LoaderBookingHistoryFragment)
                    binding.rvOngoing.apply {
                        adapter = cancelledTripHistoryAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
                binding.idNouser.visibility = View.VISIBLE
                binding.rvOngoing.visibility = View.GONE
            }
        }
        return binding.root
        }

    /*private fun setTab() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Ongoing"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completed"))
        loaderpagerAdapter = LoaderBookingHistoryViewPagerAdapter(getParentFragmentManager())
        binding.viewPager.adapter = loaderpagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getChildAt(0)
        binding.viewPager.adapter = loaderpagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }*/

    override fun onDetailClicked(ongoingLoaderHistoryData: LoaderTripManagementData) {
        startActivity(Intent(requireContext(), LoaderOngoingBookingDetailsActivity :: class.java).also {
            it.putExtra("loaderOngoingHistoryDetails", ongoingLoaderHistoryData)

        })

        Log.d("TAG++", "onProceedClicked: "+ongoingLoaderHistoryData.bookingId.toString())
    }

 
    override fun onCompletedDetailClicked(completedLoaderHistoryData: CompletedLoaderHistoryData) {
        startActivity(Intent(requireContext(), LoaderCompletedBookingDetailsActivity :: class.java).also {
            it.putExtra("loaderCompletedHistoryDetails", completedLoaderHistoryData)

        })

        Log.d("TAG++", "onProceedClicked: "+completedLoaderHistoryData.bookingId.toString())
    }

    override fun onCancelledDetailClicked(cancelledLoaderHistoryData: CancelledLoaderTripHistoryData) {
        startActivity(Intent(requireContext(), LoaderCancelledBookingDetailsActivity :: class.java).also {
            it.putExtra("loaderCancelledHistoryDetails", cancelledLoaderHistoryData)

        })

        Log.d("TAG++", "onProceedClicked: "+cancelledLoaderHistoryData.bookingId.toString())
    }


}





