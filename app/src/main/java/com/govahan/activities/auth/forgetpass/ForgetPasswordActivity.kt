package com.govahan.activities.auth.forgetpass

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityForgetPasswordBinding


class ForgetPasswordActivity : BaseActivity() {
    private lateinit var binding : ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}