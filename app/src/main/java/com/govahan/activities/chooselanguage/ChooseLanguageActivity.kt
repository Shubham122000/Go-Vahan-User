package com.govahan.activities.chooselanguage

import android.content.Intent
 import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.activities.DashboardActivity
import com.govahan.activities.auth.login.LoginActivity
import com.govahan.baseClasses.BaseActivity
 import com.govahan.databinding.ActivityChooseLanguageBinding
import com.govahan.prefs.UserPref

class ChooseLanguageActivity : BaseActivity() {
    lateinit var binding: ActivityChooseLanguageBinding
    lateinit var viewModel: ChooseLanguageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_language)
        userPref = UserPref(this)
        viewModel=ChooseLanguageViewModel(userPref)
        binding.viewModel = viewModel
     //   ViewModelProviders.of(this, chooseLanguageViewModelFactory).get(ChooseLanguageViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.progressBarVisibility.observe(this, {
            if (it == 1) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })


        viewModel.error.observe(this, {
            snackbar(getString(it))
        })



        viewModel.errorString.observe(this, {
            snackbar(it)
        })


        binding.btnContinue.setOnClickListener(View.OnClickListener {


            var prefLanguage="en"

            if(viewModel.selected.value=="1")
            {
                prefLanguage="en"
            }
            else if(viewModel.selected.value=="2")
            {
                prefLanguage="hi"
            }



            userPref.setLocale(prefLanguage)


            changeLanguage(userPref.getLocale())


            if(userPref.isLogin) {
                Intent(this, DashboardActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            else
            {
                Intent(this, LoginActivity::class.java).putExtra("language_type",viewModel.selected.value.toString()).also {
                    startActivity(it)
                }
            }

        })

//        binding.tvEnglish.setOnClickListener{
//            binding.tvEnglish.setBackgroundResource(R.drawable.rounded_btn)
//            binding.tvHindi.setBackgroundResource(R.drawable.border_blue)
//            binding.tvOdiya.setBackgroundResource(R.drawable.border_blue)
//            binding.tvEnglish.setTextColor(resources.getColor(R.color.white))
//            binding.tvHindi.setTextColor(resources.getColor(R.color.btn_color))
//            binding.tvOdiya.setTextColor(resources.getColor(R.color.btn_color))
//
//        }
//
//        binding.tvHindi.setOnClickListener{
//            binding.tvHindi.setBackgroundResource(R.drawable.rounded_btn)
//            binding.tvEnglish.setBackgroundResource(R.drawable.border_blue)
//            binding.tvOdiya.setBackgroundResource(R.drawable.border_blue)
//            binding.tvHindi.setTextColor(resources.getColor(R.color.white))
//            binding.tvEnglish.setTextColor(resources.getColor(R.color.btn_color))
//            binding.tvOdiya.setTextColor(resources.getColor(R.color.btn_color))
//
//        }
//
//        binding.tvOdiya.setOnClickListener{
//            binding.tvOdiya.setBackgroundResource(R.drawable.rounded_btn)
//            binding.tvEnglish.setBackgroundResource(R.drawable.border_blue)
//            binding.tvHindi.setBackgroundResource(R.drawable.border_blue)
//            binding.tvOdiya.setTextColor(resources.getColor(R.color.white))
//            binding.tvEnglish.setTextColor(resources.getColor(R.color.btn_color))
//            binding.tvHindi.setTextColor(resources.getColor(R.color.btn_color))
//
//        }


    }


}