package com.govahan.activities.aboutus

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.activities.privacypolicy.PrivacyPolicyViewModel
import com.govahan.databinding.ActivityAboutUsBinding
import com.govahan.baseClasses.BaseActivity
import com.govahan.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsActivity : BaseActivity() {
    private lateinit var binding : ActivityAboutUsBinding
    private val viewModel : PrivacyPolicyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("About Us")

        viewModel.aboutUs("Bearer " + userPref.user.apiToken)
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.privacyPolicyResponse.observe(this) {
            if (it.status == 1) {

                binding.aboutus.text = it.data!!.description

            } else {
                toast(it.message!!)
            }
        }
    }
}