package com.govahanuser.com.fragment.loaderongoingtriphistory

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanuser.com.R
import com.govahanuser.com.activities.loaderongoingbookingdetails.LoaderOngoingBookingDetailsActivity
import com.govahanuser.com.adapters.OngoingLoaderTripHistoryAdapter
import com.govahanuser.com.baseClasses.BaseFragment
import com.govahanuser.com.databinding.FragmentOngoingTripHistoryBinding
import com.govahanuser.com.model.ongoingloadertriphistorymodel.OngoingLoaderHistoryData
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OngoingLoaderTripHistoryFragment : BaseFragment() , OngoingLoaderTripHistoryAdapter.OnClick{

    private lateinit var binding : FragmentOngoingTripHistoryBinding
    val progressBarStatus = MutableLiveData<Boolean>()
   private val viewModel : LoaderOngoingTripHistoryFragmentViewModel by viewModels()
    private var ongoingTripHistoryAdapter : OngoingLoaderTripHistoryAdapter?= null
    private var listData: ArrayList<LoaderTripManagementData> = ArrayList()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ongoing_trip_history, container, false)

        // Inflate the layout for this fragment

       /* val layoutManagerTopSongs: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),
            GridLayoutManager.VERTICAL, false)
        binding.rvOngoing.setLayoutManager(layoutManagerTopSongs)
        val itemsTopSongs: List<String> = Arrays.asList("item", "item", "item", "item", "item", "item", "item", "item", "item", "item")
        binding.rvOngoing.setAdapter(OngoingTripAdapter(requireContext(), itemsTopSongs))
*/






        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.getLoaderOngoingHistoryResponse.observe(requireActivity()) {
            if (it.error == false) {
                listData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvOngoing.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvOngoing.visibility = View.VISIBLE
                    it.result?.let { it1 -> it1?.data?.let { it2 -> listData.addAll(it2) } }
                    ongoingTripHistoryAdapter = OngoingLoaderTripHistoryAdapter(listData,this@OngoingLoaderTripHistoryFragment)
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
            }
        }





        viewModel.loaderOngoingBookingTripHistoryApi("Bearer " + userPref.user.apiToken)
        Log.d(TAG, "onCreateView: OngoingLoader"+userPref.user.apiToken)









        return binding.root    }






    override fun onDetailClicked(ongoingLoaderHistoryData: LoaderTripManagementData) {
        startActivity(Intent(requireContext(), LoaderOngoingBookingDetailsActivity :: class.java).also {
            it.putExtra("loaderOngoingHistoryDetails", ongoingLoaderHistoryData)

        })

       Log.d("TAG++", "onProceedClicked: "+ongoingLoaderHistoryData.bookingId.toString())
    }


}