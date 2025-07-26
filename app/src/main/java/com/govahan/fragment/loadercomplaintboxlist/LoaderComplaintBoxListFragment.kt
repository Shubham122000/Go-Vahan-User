package com.govahan.fragment.loadercomplaintboxlist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahan.R
import com.govahan.activities.loadercomplaintboxdetail.LoaderComplaintBoxDetailActivity
import com.govahan.adapters.LoaderComplaintBoxAdapter
import com.govahan.baseClasses.BaseFragment
import com.govahan.databinding.ActivityLoaderComplaintBoxListBinding
import com.govahan.model.loaderComplaintlistmodel.LoaderComplaintData
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class LoaderComplaintBoxListFragment : BaseFragment(), LoaderComplaintBoxAdapter.OnClick {

    private lateinit var binding : ActivityLoaderComplaintBoxListBinding
    private val viewModel : LoaderComplaintBoxListViewModel by viewModels()



    private var loaderComplaintBoxAdapter : LoaderComplaintBoxAdapter ?= null
    private var listData: ArrayList<LoaderComplaintData> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
       // binding = DataBindingUtil.setContentView(requireActivity(), R.layout.activity_loader_complaint_box_list)
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_loader_complaint_box_list, container, false)

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.getLoaderComplaintListResponse.observe(requireActivity()) {
            if (it.error == false) {
                listData.clear()
                // listData!!.addAll(it.getFavLocdata)
                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvComplaintBoxList.visibility = View.GONE
                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvComplaintBoxList.visibility = View.VISIBLE
                    it.result?.data?.let { it1 -> listData.addAll(it1) }
                    loaderComplaintBoxAdapter = LoaderComplaintBoxAdapter(listData,this)
                    binding.rvComplaintBoxList.apply {
                        adapter = loaderComplaintBoxAdapter
                        layoutManager = LinearLayoutManager(requireActivity())
                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                }
            } else {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
            }
        }
//        viewModel.raiseComplaintList("Bearer " + userPref.user.apiToken)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        viewModel.raiseComplaintList("Bearer " + userPref.user.apiToken)
    }

    override fun onViewDetail(loaderComplaintData: LoaderComplaintData) {
        startActivity(Intent(requireContext(), LoaderComplaintBoxDetailActivity::class.java).also {
            it.putExtra("vehicleDetails", loaderComplaintData)
        })
    }

}