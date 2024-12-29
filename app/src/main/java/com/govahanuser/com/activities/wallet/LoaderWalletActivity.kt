package com.govahan.com.activities.wallet

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahan.com.R
import com.govahan.com.adapters.LoaderWalletAdapter
import com.govahan.com.adapters.LoaderWalletFilterAdapter
import com.govahan.com.baseClasses.BaseActivity

import com.govahan.com.model.loaderwalletfiltermodel.LoaderWalletFilterData
import com.govahan.com.model.loaderwalletlistmodel.LoaderWalletListData
import com.govahan.com.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatDelegate
import com.govahan.com.activities.bookingreview.BookingReviewViewModel
import com.govahan.com.customclick.walletcustomclick
import com.govahan.com.databinding.ActivityWalletBinding
import com.govahan.com.databinding.DialogAddMoneyLoaderBinding
import com.govahan.com.util.WebViewActivity
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

@AndroidEntryPoint
class LoaderWalletActivity : BaseActivity(),PopupMenu.OnMenuItemClickListener,walletcustomclick,
    /*PaymentResultListener,*/
    PaymentResultWithDataListener {
    private lateinit var binding : ActivityWalletBinding
    private val viewModel : LoaderWalletViewModel by viewModels()
    private var listData: ArrayList<LoaderWalletListData> = ArrayList()
    private var filterlistData: ArrayList<LoaderWalletFilterData> = ArrayList()
    private var loaderWalletAdapter : LoaderWalletAdapter ?= null
    private var loaderWalletFilterAdapter : LoaderWalletFilterAdapter ?= null
    lateinit var bindingDialog: DialogAddMoneyLoaderBinding
    private val viewModel1: BookingReviewViewModel by viewModels()
    var phonepayurl = ""
    var  pos = ""
    var  paymentprice = 0
    var flag1=""
    var selectedDateFormat2 = ""
    var transactionid=""
    var amount: Int = 0
    private val B2B_PG_REQUEST_CODE = 777
    var transactionId=""
    var finalPamountInt = ""
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)
        if (intent != null) {
            flag1= intent.getStringExtra("flag1").toString()
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            if (flag1.equals("1")){
                super.onBackPressed()
            }else{

            }
        })
        binding.tvHeaderText.setText("Wallet")
        binding.btnAddMoney.setOnClickListener(View.OnClickListener {
            addMoneyDialog()
        })
        viewModel1.ChecksumResponse.observe(this) {
            if (it.success == true) {
                phonepayurl = it.data.instrumentResponse.redirectInfo.url
                transactionId=it.data.merchantTransactionId
                flag1=""
                startActivity(
                    Intent(applicationContext, WebViewActivity::class.java)
                        .putExtra("phonepay", phonepayurl.toString())
                )
                }else {
                toast(it.message!!)
               }
            }
         viewModel.addmoneytowalletresponse.observe(this) {
            if (it.status == 1) {
                toast("Successfully added.")
                viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","")
            } else {
                toast(it.message!!)
            }
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        binding.btnDownload.setOnClickListener {
            viewModel.loaderWalletDownload("Bearer " + userPref.user.apiToken)
        }

        viewModel.getLoaderWalletListResponse.observe(this) {
            if (it.status == 1) {
                listData.clear()
                binding.tvBalance.text = "₹${it.TotalAmount.toString()}"
                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvWallet.visibility = View.GONE
                    binding.llDownload.visibility = View.GONE
                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvWallet.visibility = View.VISIBLE
                    binding.llDownload.visibility = View.VISIBLE
                    listData.addAll(it.data)
                    loaderWalletAdapter = LoaderWalletAdapter(listData,this)
                    binding.rvWallet.apply {
                        adapter = loaderWalletAdapter
                        layoutManager = LinearLayoutManager(this@LoaderWalletActivity)

                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()
                    }
                    //   favouriteLocationsAdapter?.notifyDataSetChanged()
                }

            } else   {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }

        viewModel.downloadloaderlist.observe(this) {
            if (it.status == 1) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                toast("Invoice Downloaded successfully!")
            } else{
                toast(it.message)
            }
        }

        viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","")
        viewModel.loaderWalletFilterListResponse.observe(this) {
            if (it.status == 1) {
                filterlistData.clear()
                // listData!!.addAll(it.getFavLocdata)

                if (it.data.isEmpty() ) {
                    binding.idNouser.visibility = View.VISIBLE
                    binding.rvWallet.visibility = View.GONE
                    binding.llDownload.visibility = View.GONE

                }
                else {
                    binding.idNouser.visibility = View.GONE
                    binding.rvWallet.visibility = View.VISIBLE
                    binding.llDownload.visibility = View.VISIBLE
                    filterlistData.addAll(it.data)
                    loaderWalletFilterAdapter = LoaderWalletFilterAdapter(filterlistData)
                    binding.rvWallet.apply {
                        adapter = loaderWalletFilterAdapter
                        layoutManager = LinearLayoutManager(this@LoaderWalletActivity)

                        // it.getFavLocdata?.let { notificationList?.addAll(it) }
                        //    favouriteLocationsAdapter?.notifyDataSetChanged()

                    }
                    //   favouriteLocationsAdapter?.notifyDataSetChanged()
                }

            } else   {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","")
    }

//    https://zaakpay.com/

    fun handleUPIIntent(intentUri: String?) {
        val uri = Uri.parse(intentUri)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        //                startActivityForResult(intent, B2B_PG_REQUEST_CODE);
        startActivityForResult(intent,B2B_PG_REQUEST_CODE)
    }

    fun showPopup(v: View?) {
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.menu_filter_wallet)
        popup.show()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_all -> {
               // Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show()
                viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","")
                true
            }

            R.id.menu_chooseDate -> {
                clickDataPicker()
                true
            }

            R.id.menu_debit -> {
                viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","2")
                true
            }
            R.id.menu_credit -> {
                viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","1")
                true
            }


            else -> false
        }
    }





    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter_wallet, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.menu_all) {
            toast("menu_all")
            return true
        } else if (item.getItemId() === R.id.menu_chooseDate) {
            toast("menu_chooseDate")
            return true
        }
        return super.onOptionsItemSelected(item)
    }*/


    private fun addMoneyDialog() {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
         bindingDialog= DataBindingUtil.inflate(
             LayoutInflater.from(this),
             R.layout.dialog_add_money_loader,
             null,
             false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        viewModel.addMoneyWalletResponse.observe(this) {
            if (it.responseCode == 200) {
                toast(it.message!!)
//                binding.tvBalance.text = it.data?.amount.toString()

            } else {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }
        bindingDialog.btnProceed.setOnClickListener {
            if(bindingDialog.edtAmount.text.toString().isBlank()){
                toast(this , "Please enter amount.")
            }
            else{
                startPayment(bindingDialog.edtAmount.text.toString())
//                paymentprice= (bindingDialog.edtAmount.text.toString()).toInt()
//                finalPamountInt=paymentprice*100
//
//                viewModel1.checkupApi("Bearer " + userPref.user.apiToken,finalPamountInt.toString(),userPref.getmobile().toString(),userPref.getUserId().toString())

            }
            cDialog.dismiss()
            viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,"","")
        }
        bindingDialog.cancel.setOnClickListener { cDialog.dismiss() }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            this,R.style.DatePickerTheme, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
               // binding.tvBookingdate.text = simpleDateFormat.format(cal.time)
                selectedDateFormat2 = simpleDateFormat2.format(cal.time)
                viewModel.loaderWalletListApi("Bearer " + userPref.user.apiToken,selectedDateFormat2,"")

            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
//        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    private fun startPayment(amountofuser:String) {
        /*
      *  You need to pass current activity in order to let Razorpay create CheckoutActivity
      * */
//        amount = (binding.payableAmount.text.toString().replace("₹", "").toFloat() * 100).toInt()
        amount = (amountofuser.toFloat() * 100).toInt()
        finalPamountInt = amountofuser.toString()
        val co = Checkout()
        co.setKeyID("rzp_test_fWTippdyDGtkYn")
        try {
            var options = JSONObject()
            options.put("name", userPref.user.name)
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "")
            options.put("currency", "INR")
            options.put("amount", amount)
            options.put("send_sms_hash", true)

            val prefill = JSONObject()
            prefill.put("email", userPref.user.email)
            prefill.put("contact", userPref.user.mobileNumber)

            options.put("prefill", prefill)

            co.open(this@LoaderWalletActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@LoaderWalletActivity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        viewModel.add_my_wallet("Bearer " + userPref.user.apiToken,finalPamountInt.toString(),p1?.paymentId.toString())
        Toast.makeText(this@LoaderWalletActivity, "Ho gai payment", Toast.LENGTH_LONG).show()

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this@LoaderWalletActivity, "Error in payment: ", Toast.LENGTH_LONG).show()

    }

    override fun onItemClick(id: String) {
        transactionid=id
    }
//
//    override fun onPaymentSuccess(p0: String?, p1: PaymentData?)
////        viewModel.my_wallet_payment(
////            "Bearer " + userPref.user.apiToken,"1",p1?.paymentId!!,bindingDialog.edtAmount.text.toString()
////
////        )
//
//    }
//
//    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
//        try {
//            Toast.makeText(
//                this,
//                "Payment Unsuccessful", Toast.LENGTH_LONG
//            ).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    override fun onBackPressed() {
        if (flag1.equals("1")){
            super.onBackPressed()
        }else{

        }

    }
    override fun onStart() {
        super.onStart()
        if(flag1.equals("1")){

        }
        else{
                viewModel.paymentcheckApi("Bearer " + userPref.user.apiToken,transactionId)
                viewModel.Paymentsuccessmsgresponse.observe(this) {
                    if (it.code == "PAYMENT_SUCCESS") {
                        toast(it.message)
                        viewModel.my_wallet_payment(
                            "Bearer " + userPref.user.apiToken,"1",transactionId,bindingDialog.edtAmount.text.toString()
                        )

                    } else {
                        toast("Payment Failed")
                    }
                }
}
    }


}

