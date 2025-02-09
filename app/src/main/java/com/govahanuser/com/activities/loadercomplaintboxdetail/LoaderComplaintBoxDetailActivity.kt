package com.govahanuser.com.activities.loadercomplaintboxdetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityLoaderComplaintBoxDetailBinding
import com.govahanuser.com.model.loaderComplaintlistmodel.LoaderComplaintData
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoaderComplaintBoxDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityLoaderComplaintBoxDetailBinding
    private val viewModel : LoaderComplaintBoxDetailViewModel by viewModels()
    private var loaderComplaintData : LoaderComplaintData?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loader_complaint_box_detail)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Complaint Detail")






        val data = intent.extras
        loaderComplaintData = data?.getParcelable<LoaderComplaintData>("vehicleDetails")

//        viewModel.progressBarStatus.observe(this) {
//            if (it) {
//                showProgressDialog()
//            } else {
//                hideProgressDialog()
//            }
//        }

//        viewModel.loaderComplaintListDetailResponse.observe(this) {
//            if (it.status == 1) {
                // toast("booking Successful")
//                toast(it.message!!)
                binding.tvCompalintNumber.text = loaderComplaintData?.message.toString()
//                binding.tvDate.text =  loaderComplaintData?.
                binding.tvBookingid.text =  loaderComplaintData?.bookingId.toString()
                binding.tvFrom.text = loaderComplaintData?.tripDetails?.fromTrip
                binding.tvTo.text = loaderComplaintData?.tripDetails?.toTrip
                binding.tvTotalfare.text ="₹" + loaderComplaintData?.tripDetails?.freightAmount.toString()
                binding.tvBookingDate.text = loaderComplaintData?.tripDetails?.bookingDateFrom
                binding.tvBookingTime.text = loaderComplaintData?.tripDetails?.time
                binding.tvVehicleType.text = loaderComplaintData?.tripDetails?.vehicle?.vehicleName.toString()
                binding.tvBodyType.text = loaderComplaintData?.tripDetails?.vehicle?.bodyType?.name.toString()
                binding.tvVehicleNumber.text = loaderComplaintData?.tripDetails?.vehicle?.vehicleNumber.toString()
//                binding.tvAdminRemarks.text = it.data[0].adminReply
                if (loaderComplaintData?.confirmation==1){
                    binding.tvAdminRemarks.text = "Resolved"
                    binding.resolvedNotresolved.visibility = View.GONE
                }else if (loaderComplaintData?.confirmation==2){
                    binding.tvAdminRemarks.text = "Not Resolved"
                }
                else{
                    binding.tvAdminRemarks.text = "Pending"
                }


                binding.tvComplaintText.text = loaderComplaintData?.message
                binding.tvPaymentMethod.text = loaderComplaintData?.paymentDetails?.get(0)?.paymentMode


//            } else {
//                toast(it.message!!)
//            }
//        }


      viewModel.resolvedResponse.observe(this){
          if (it.status==1){
              toast(it.message!!)
              finish()
          }else{
              toast(it.message!!)
          }
      }

        viewModel.loaderComplaintListDetailApi("Bearer " + userPref.user.apiToken,
            loaderComplaintData?.bookingId.toString()
        )


        binding.btnResolved.setOnClickListener {
            viewModel.complaint_resolved("Bearer " + userPref.user.apiToken,
                loaderComplaintData?.bookingId.toString()  ,"1")
        }
        binding.btnNotResolved.setOnClickListener {
            viewModel.complaint_resolved("Bearer " + userPref.user.apiToken,
                loaderComplaintData?.bookingId.toString()  ,"2")
        }



    }
}