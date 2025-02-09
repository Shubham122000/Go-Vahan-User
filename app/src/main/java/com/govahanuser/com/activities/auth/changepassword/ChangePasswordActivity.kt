package com.govahanuser.com.activities.auth.changepassword

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityChangePasswordBinding


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