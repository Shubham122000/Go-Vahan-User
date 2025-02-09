package com.govahanuser.com.activities.passengers.paymentmethods

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanuser.com.R
import com.govahanuser.com.baseClasses.BaseActivity
import com.govahanuser.com.databinding.ActivityPaymentMethodsPactivityBinding

class PaymentMethodsPActivity : BaseActivity() {
    private lateinit var binding : ActivityPaymentMethodsPactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_methods_pactivity)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}