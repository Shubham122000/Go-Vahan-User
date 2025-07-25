package com.govahan.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.activities.loadercomplaintboxdetail.LoaderComplaintBoxDetailViewModel
import com.govahan.adapters.ReviewsAdapter
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityReviewsBinding
import com.govahan.model.ReviewsData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsActivity : BaseActivity() {
    private lateinit var binding : ActivityReviewsBinding
    private val viewModel : LoaderComplaintBoxDetailViewModel by viewModels()
    var reviewsdata : ArrayList<ReviewsData> = ArrayList()
    var driver_id=""
    private var reviewsAdapter : ReviewsAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews)
       if ( intent!=null){
           driver_id=intent.getStringExtra("driver_id").toString()
       }
        viewModel.progressBarStatus.observe(this){
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        viewModel.search_loader_driver_review("Bearer " + userPref.user.apiToken,
            driver_id
        )
        viewModel.reviewsresonse.observe(this){
            if (it.status == 1) {
            reviewsdata.clear()
            if (it.data!=null&& it.data.isNotEmpty()){
                reviewsdata.addAll(it.data)
            }
//                reviewsdata.addAll(it.data)
//                binding.rvReviews.layoutManager = LinearLayoutManager(this)
                reviewsAdapter = ReviewsAdapter(reviewsdata)
                binding.rvReviews.adapter =reviewsAdapter
                reviewsAdapter!!.notifyDataSetChanged()



        }else{
            toast(this,it.message)
            }

    }
    }
}