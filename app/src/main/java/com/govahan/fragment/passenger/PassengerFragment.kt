package com.govahan.fragment.passenger

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.govahan.R
import com.govahan.activities.passengers.passengervailablevehicle.PassengerAvailableVehicleActivity
import com.govahan.baseClasses.BaseFragment
import com.govahan.databinding.FragmentPassengerBinding
import com.govahan.model.noOfTyrePModel.NoOfTyrePData
import com.govahan.model.seatingcapacitymodel.SeatingCapacityData
import com.govahan.model.vehicletypemodel.VehicleTypeData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PassengerFragment : BaseFragment() {

    private lateinit var binding : FragmentPassengerBinding
    private val viewModel : PassengerFragmentViewModel by viewModels()
    private var vehicleTypeListData = ArrayList<VehicleTypeData>()
    var selectedVehicleType = ""
    private var vehicleTypeList = ArrayList<String>()
    var datePicker: DatePickerDialog? = null
    private var seatingCapacityListData = ArrayList<SeatingCapacityData>()
    var selectedseatingCapacityType = ""
    private var seatingCapacityList = ArrayList<String>()
    private var noOfTyresPListData = ArrayList<NoOfTyrePData>()
    var selectednoOfTyresPType = ""
    private var noOfTyresPList = ArrayList<String>()
    var placesClient: PlacesClient? = null
    private val AUTOCOMPLETE_FROM_REQUEST_CODE = 1
    private val AUTOCOMPLETE_TO_REQUEST_CODE = 2
    var latLng: LatLng? = null
    var pickupLongitude = 0.0
    var pickupLatitude = 0.0
    var dropLongitude = 0.0
    var dropLatitude = 0.0
    var selectedDateFormat2 = ""
    val mcurrentTime = Calendar.getInstance()
    var hour = mcurrentTime[Calendar.HOUR_OF_DAY]
    val minute = mcurrentTime[Calendar.MINUTE]
    val zone = mcurrentTime[Calendar.AM_PM]
    var date = ""
    var time = ""
    var minutes = mcurrentTime[Calendar.MINUTE]

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger, container, false)

        //  GetVehicleType
        viewModel.vehicleTypeApi("Bearer " + userPref.user.apiToken,"2")
        binding.spinnerVehicletype.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
            toast(requireContext(), vehicleTypeListData[newIndex].id.toString())
           // selectedVehicleType = vehicleTypeListData[newIndex].vType.toString()

            selectedVehicleType = vehicleTypeListData[newIndex].id.toString()
        // for Passenger & "2" for Loader
        }
        viewModel.vehicleTypeListResponse.observe(requireActivity()) {
            if (it.error == false) {
                vehicleTypeList.clear()
                vehicleTypeListData.clear()
                it.result?.data?.let { it1 -> vehicleTypeListData.addAll(it1) }
                for (i in vehicleTypeListData){
                    i.vType?.let { it1 -> vehicleTypeList.add(it1) }
                }

                binding.spinnerVehicletype.setItems(vehicleTypeList)
            } else {
                it.message?.let { it1 -> toast(requireContext(), it1) }
            }
        }


        //  GetSeatingCapacity
        viewModel.seatingCapacityApi("Bearer " + userPref.user.apiToken)
        //  NoOfTyresPassenger
        viewModel.noOfTyrePApi("Bearer " + userPref.user.apiToken,"2")
//        binding.spinnerNooftyresP.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
////            toast(requireContext(), noOfTyresPListData[newIndex].id.toString())
//            selectednoOfTyresPType = noOfTyresPListData[newIndex].id.toString()
//            //  viewModel.truckFeetAndTonApi("Bearer " + userPref.user.api_token, truckTypeListData[newIndex].id.toString())
//        }
//        viewModel.noOfTyresPListResponse.observe(requireActivity()) {
//            if (it.error == false) {
//                noOfTyresPList.clear()
//                noOfTyresPListData.clear()
//                noOfTyresPListData.add(NoOfTyrePData(id = -1, wheel = "SELECT"))
//                it.result?.data?.let { it1 -> noOfTyresPListData.addAll(it1) }
//
//                for (i in noOfTyresPListData){
//                    i.wheel?.let { it1 -> noOfTyresPList.add(it1) }
//                }
//                binding.spinnerNooftyresP.setItems(noOfTyresPList)
//            } else {
//                it.message?.let { it1 -> toast(requireContext(), it1) }
//            }
//        }

        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }
        // Create a new Places client instance.
        placesClient = Places.createClient(requireContext())

        binding.rlFromLocation.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_FROM_REQUEST_CODE)
        }
        binding.rlToLocation.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_TO_REQUEST_CODE)
        }

        viewModel.progressBarStatus.observe(requireActivity()) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }



        binding.btnSearch.setOnClickListener {

            if (binding.fromLocation.text == ""){
                toast(requireContext() , "Please enter your pickup location.")
            } else if (binding.dropLocation.text == ""){
                toast(requireContext() , "Please enter your drop location.")
            } else if (selectedVehicleType == ""){
                toast(requireContext() , "Please Select Vehicle Type.")
            }
            else if (binding.tvSelectedDate.text == ""){
                toast(requireContext() , "Please select date.")
            }
            else {
                startActivity(Intent(requireContext(), PassengerAvailableVehicleActivity :: class.java).also {
                    it.putExtra("pickupLatitude", pickupLatitude.toString())
                    it.putExtra("pickupLongitude", pickupLongitude.toString())
                    it.putExtra("dropLatitude", dropLatitude.toString())
                    it.putExtra("dropLongitude", dropLongitude.toString())
                    it.putExtra("vehicle_type", selectedVehicleType)
                    it.putExtra("booking_date", binding.tvSelectedDate.text.toString())
                    it.putExtra("booking_time", binding.spinnerTimeslots.selectedItem.toString())
                    it.putExtra("pickup_location", binding.fromLocation.text.toString())
                    it.putExtra("dropup_location", binding.dropLocation.text.toString())

                })
            }

            /*System.out.print("pickupLocation"+binding.fromLocation.text.toString()+
                    "pickupLatitude"+ pickupLatitude.toString()+
                    "pickupLatitude"+ pickupLatitude.toString()+
                    "dropLatitude"+ dropLatitude.toString()+
                    "dropLongitude"+ dropLongitude.toString()+
                    "dropLocation"+ binding.dropLocation.text.toString()+
                    "truckType"+ selectedTruckType+
                    "ton"+ selectedTon+
                    "feet"+ selectedFeet)*/

            //   Toast.makeText(requireContext(), "selectedDateFormat2"+selectedDateFormat2, Toast.LENGTH_SHORT).show()

        }

        binding.llDate.setOnClickListener {
            clickDataPicker()
        }




        return binding.root
    }


    private fun placesAPiCall(requestCode : Int) {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireActivity())
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_FROM_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    Log.i("TAG", "Place: " + place.name + ", " + place.id)
                    latLng = place.latLng
                    pickupLongitude = latLng!!.longitude
                    pickupLatitude = latLng!!.latitude
                    binding.fromLocation.text = place.name
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("TAG", status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }
        else if (requestCode == AUTOCOMPLETE_TO_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    Log.i("TAG", "Place: " + place.name + ", " + place.id)
                    latLng = place.latLng
                    dropLongitude = latLng!!.longitude
                    dropLatitude = latLng!!.latitude
                    binding.dropLocation.text = place.name
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("TAG", status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            requireContext(),R.style.DatePickerTheme, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                binding.tvSelectedDate.text = simpleDateFormat.format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }




}