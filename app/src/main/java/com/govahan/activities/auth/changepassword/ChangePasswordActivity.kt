package com.govahan.activities.auth.changepassword

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityChangePasswordBinding


class ChangePasswordActivity : BaseActivity() {
    private lateinit var binding : ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}