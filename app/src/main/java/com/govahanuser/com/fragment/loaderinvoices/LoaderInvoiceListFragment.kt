package com.govahanuser.com.fragment.loaderinvoices

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
import com.govahanuser.com.R
import com.govahanuser.com.activities.invoicesummaryloader.LoaderInvoiceSummaryActivity
import com.govahanuser.com.adapters.LoaderInvoiceListAdapter
import com.govahanuser.com.baseClasses.BaseFragment
import com.govahanuser.com.databinding.FragmentLoaderInvoiceListBinding
import com.govahanuser.com.model.loaderinvoicelistmodel.LoaderInvoiceData
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class LoaderInvoiceListFragment : BaseFragment(), LoaderInvoiceListAdapter.OnClick  {

    private lateinit var binding : FragmentLoaderInvoiceListBinding
    private val viewModel : LoaderInvoiceListFragmentViewModel by viewModels()
    private var loaderInvoiceListAdapter : LoaderInvoiceListAdapter?= null
    private var listData: ArrayList<LoaderTripManagementData> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loader_invoice_list, container, false)


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
                if (it.result?.data?.isEmpty() == true) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvInvoicelist.visibility = View.GONE
                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvInvoicelist.visibility = View.VISIBLE
                    it.result?.data?.let { it1 -> listData.addAll(it1) }
                    loaderInvoiceListAdapter = LoaderInvoiceListAdapter(listData,this)
                    binding.rvInvoicelist.apply {
                        adapter = loaderInvoiceListAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            } else   {
                Log.d("Response", it.toString())
                toast(requireContext(),it.message!!)
            }
        }
        viewModel.UpComingsTripHistoryApi("Bearer " + userPref.user.apiToken,"1","1")

        return binding.root
    }

    override fun onInvoiceClicked(loaderInvoiceData: LoaderTripManagementData) {
        startActivity(Intent(requireContext(), LoaderInvoiceSummaryActivity :: class.java).also {
            it.putExtra("loaderInvoiceDetails", loaderInvoiceData)
//            it.putExtra("loaderInvoiceBookingId", loaderInvoiceData.bookingId.toString())

        })

//        Log.d("TAG++", "onProceedClicked: "+loaderInvoiceData.invoiceNumber.toString())

    }


}