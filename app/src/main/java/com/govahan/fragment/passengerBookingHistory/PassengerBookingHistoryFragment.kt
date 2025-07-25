package com.govahan.fragment.passengerBookingHistory

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
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahan.R
import com.govahan.activities.passengerCancelledBookingDetails.PassengerCancelledBookingDetailsActivity
import com.govahan.activities.passengerCompletedBookingDetails.PassengerCompletedBookingDetailsActivity
import com.govahan.activities.passengerOngoingBookingDetails.PassengerOngoingBookingDetailsActivity
import com.govahan.adapters.*
import com.govahan.baseClasses.BaseFragment
import com.govahan.databinding.FragmentPassengerBookingHistoryBinding
import com.govahan.fragment.passengercompletedtriphistory.PassengerCancelledTripHistoryFragmentViewModel
import com.govahan.fragment.passengercompletedtriphistory.PassengerCompletedTripHistoryFragmentViewModel
import com.govahan.fragment.passengerongoingtriphistory.PassengerOngoingTripHistoryFragmentViewModel
import com.govahan.model.cancelledpassengertriphistorymodel.CancelledPassengerTripHistoryData
import com.govahan.model.completedpassengertriphistorymodel.CompletedPassengerHistoryData
import com.govahan.model.ongoingPassengerTripHistoryModel.OngoingPassengerHistoryData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PassengerBookingHistoryFragment : BaseFragment() , OngoingPassengerTripHistoryAdapter.OnClick, CompletedPassengerTripHistoryAdapter.OnClick, CancelledPassengerTripHistoryAdapter.OnClick {

    lateinit var binding : FragmentPassengerBookingHistoryBinding
    private var passengerpagerAdapter: PassengerBookingHistoryViewPagerAdapter? = null
    var selectedItem = ""
    val progressBarStatus = MutableLiveData<Boolean>()
    private val viewModelPassengerOngoing : PassengerOngoingTripHistoryFragmentViewModel by viewModels()
    private var ongoingTripHistoryAdapter : OngoingPassengerTripHistoryAdapter?= null
    private var listDataPassengerOngoing: ArrayList<LoaderTripManagementData> = ArrayList()
    private val viewModelPassengerCompleted : PassengerCompletedTripHistoryFragmentViewModel by viewModels()
    private var completedPassengerTripHistoryAdapter : CompletedPassengerTripHistoryAdapter?= null
    private var listDataPassengerCompleted: ArrayList<CompletedPassengerHistoryData> = ArrayList()
    private val viewModelCancelledPassenger : PassengerCancelledTripHistoryFragmentViewModel by viewModels()
    private var cancelledPassengerTripHistoryAdapter : CancelledPassengerTripHistoryAdapter?= null
    private var listDataCancelledPassenger: ArrayList<CancelledPassengerTripHistoryData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger_booking_history, container, false)


        /*viewModelPassengerOngoing.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }*/

      //  setTab()

        binding.spinnerLoaderHistory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedItem = parent.getItemAtPosition(position).toString()
                Log.d("PositionSelected",selectedItem)

                if (selectedItem == "Pending") {
                    viewModelPassengerOngoing.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "2"
                    )

                }else if (selectedItem == "Ongoing") {
                    viewModelPassengerOngoing.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "2", "2"
                    )
                }
                else if (selectedItem == "Completed") {
                    viewModelPassengerOngoing.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "4", "2"
                    )
                }
                else if (selectedItem == "Cancelled") {
                    viewModelPassengerOngoing.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "3", "2"
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModelPassengerOngoing.getPassengerOngoingHistoryResponse.observe(requireActivity()) {
            if (it.error == false) {
                listDataPassengerOngoing.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvOngoing.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvOngoing.visibility = View.VISIBLE
                    it.result?.data?.let { it1 -> listDataPassengerOngoing.addAll(it1) }
                    ongoingTripHistoryAdapter = OngoingPassengerTripHistoryAdapter(listDataPassengerOngoing,this@PassengerBookingHistoryFragment)
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

//        viewModelPassengerOngoing.passengerPendingBookingTripHistoryApi("Bearer " + userPref.user.apiToken)
//        viewModelPassengerOngoing.passengerOngoingBookingTripHistoryApi("Bearer " + userPref.user.apiToken)

//        viewModelPassengerCompleted.getPassengerCompletedHistoryResponse.observe(requireActivity()) {
//            if (it.status == 1) {
//                listDataPassengerCompleted.clear()
//                // listData!!.addAll(it.getFavLocdata)
//
//                if (it.data.isEmpty() ) {
//                    binding.idNouser.visibility = View.VISIBLE
//                    binding.rvOngoing.visibility = View.GONE
//
//
//                }
//                else {
//                    binding.idNouser.visibility = View.GONE
//                    binding.rvOngoing.visibility = View.VISIBLE
//                    listDataPassengerCompleted.addAll(it.data)
//                    completedPassengerTripHistoryAdapter = CompletedPassengerTripHistoryAdapter(listDataPassengerCompleted,this@PassengerBookingHistoryFragment)
//                    binding.rvOngoing.apply {
//                        adapter = completedPassengerTripHistoryAdapter
//                        layoutManager = LinearLayoutManager(requireActivity())
//                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
//                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
//                    }
//                }
//
//            } else   {
//                Log.d("Response", it.toString())
//                toast(requireContext(),it.message!!)
//                binding.idNouser.visibility = View.VISIBLE
//                binding.rvOngoing.visibility = View.GONE
//            }
//        }





    //    viewModelPassengerCompleted.passengerCompletedBookingTripHistoryApi("Bearer " + userPref.user.apiToken)
        Log.d(ContentValues.TAG, "onCreateView: "+userPref.user.apiToken)








        viewModelCancelledPassenger.getPassengerCancelledHistoryResponse.observe(requireActivity()) {
            if (it.status == 1) {
                listDataCancelledPassenger.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvOngoing.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvOngoing.visibility = View.VISIBLE
                    listDataCancelledPassenger.addAll(it.data)
                    cancelledPassengerTripHistoryAdapter = CancelledPassengerTripHistoryAdapter(listDataCancelledPassenger,this@PassengerBookingHistoryFragment)
                    binding.rvOngoing.apply {
                        adapter = cancelledPassengerTripHistoryAdapter
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

        passengerpagerAdapter = PassengerBookingHistoryViewPagerAdapter(getParentFragmentManager())
        binding.viewPager.adapter = passengerpagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)


        binding.tabLayout.getChildAt(0)

        binding.viewPager.adapter = passengerpagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }*/



    override fun onDetailClicked(ongoingPassengerHistoryData: LoaderTripManagementData) {
          startActivity(Intent(requireContext(), PassengerOngoingBookingDetailsActivity :: class.java).also {
             it.putExtra("passengerOngoingHistoryDetails", ongoingPassengerHistoryData)

         })

        Log.d("TAG++", "onProceedClicked: "+ongoingPassengerHistoryData.bookingId.toString())
    }

    override fun onCompletedDetailClicked(completedPassengerHistoryData: CompletedPassengerHistoryData) {
        startActivity(Intent(requireContext(), PassengerCompletedBookingDetailsActivity :: class.java).also {
             it.putExtra("passengerCompletedHistoryDetails", completedPassengerHistoryData)

         })

        Log.d("TAG++", "onProceedClicked: "+completedPassengerHistoryData.bookingId.toString())
    }

    override fun onCancelledPassDetailClicked(cancelledPassengerTripHistoryData: CancelledPassengerTripHistoryData) {
        startActivity(Intent(requireContext(), PassengerCancelledBookingDetailsActivity :: class.java).also {
            it.putExtra("passengerCancelledHistoryDetails", cancelledPassengerTripHistoryData)

        })
    }


}
