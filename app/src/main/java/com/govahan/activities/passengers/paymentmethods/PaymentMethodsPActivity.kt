package com.govahan.activities.passengers.paymentmethods

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityPaymentMethodsPactivityBinding

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