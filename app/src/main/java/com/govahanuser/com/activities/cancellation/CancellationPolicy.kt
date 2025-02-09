package com.govahanuser.com.activities.cancellation

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.activities.privacypolicy.PrivacyPolicyViewModel
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityCancellationPolicyBinding
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancellationPolicy : BaseActivity() {
    private lateinit var binding : ActivityCancellationPolicyBinding
    private val viewModel : PrivacyPolicyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cancellation_policy)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText(R.string.cancellation)

        viewModel.calcelation_refund_policy("Bearer " + userPref.user.apiToken)
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.privacyPolicyResponse.observe(this) {
            if (it.status == 1) {

                binding.text.text = Html.fromHtml(it.data!!.message)

            } else {
                toast(it.message!!)
            }
        }

    }
}