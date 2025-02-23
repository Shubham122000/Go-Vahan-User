package com.govahanuser.com.activities.referearn

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.activities.auth.login.LoginViewModel
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityReferEarnBinding
import com.govahanuser.com.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReferEarnActivity : BaseActivity() {
    private lateinit var binding : ActivityReferEarnBinding
    private val viewModel : LoginViewModel by viewModels()
    var referalcode=""
    var link=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_refer_earn)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.header.tvHeaderText.setText("Refer & Earn")

        viewModel.refer_userApi("Bearer "+userPref.user.apiToken)
        viewModel.refernearnResponse.observe(this){
            if (it.error == false){
                referalcode= it.result?.referalCode.toString()
                link= it.result?.referalLink.toString()
                binding.tvRefercode.text= it.result?.referalCode.toString()
            }else{
                toast(it.message.toString())
            }
        }
        binding.btnCopy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(android.R.attr.label.toString(), binding.tvRefercode.text)
            clipboard.setPrimaryClip(clip)
        }
        binding.btnReferNow.setOnClickListener {
            val intent= Intent()
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app: "+link + "\n" + "Referral code:" + referalcode)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))

        }
    }
}