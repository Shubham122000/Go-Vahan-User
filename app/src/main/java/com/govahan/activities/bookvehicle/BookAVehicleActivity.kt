package com.govahan.activities.bookvehicle

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahan.R
import com.govahan.adapters.ViewPagerAdapter
import com.govahan.baseClasses.BaseActivity
import com.govahan.databinding.ActivityBookAvehicleBinding
import com.govahan.model.getfavouritelocation.GetFavouriteLocationData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookAVehicleActivity : BaseActivity() {
    lateinit var binding : ActivityBookAvehicleBinding
    private var pagerAdapter: ViewPagerAdapter? = null

    companion object{
        var selectedPassengerTripData: GetFavouriteLocationData? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_book_avehicle)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_avehicle)

        binding.header.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })


        binding.header.tvHeaderText.setText("Vehicle Booking")




        if(intent.extras!=null) {
            val data = intent.extras
            selectedPassengerTripData =
                data?.getParcelable<GetFavouriteLocationData>("favouriteLocation")
            Log.d("TAG___", "onCreate: " + selectedPassengerTripData!!.pickAddress.toString())

        }

        setTab()

    }

    private fun setTab() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Loader"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Passenger"))


        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)


        binding.tabLayout.getChildAt(0)

        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }


}