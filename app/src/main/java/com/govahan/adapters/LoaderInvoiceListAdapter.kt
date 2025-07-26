package com.govahan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahan.R
import com.govahan.databinding.RowInvoiceListBinding
import com.govahan.model.loaderinvoicelistmodel.LoaderInvoiceData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData


class LoaderInvoiceListAdapter (val list: List<LoaderTripManagementData>,
                                private val listener: OnClick
) : RecyclerView.Adapter<LoaderInvoiceListAdapter.ViewHolder>() {


    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowInvoiceListBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_invoice_list, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]
        /*holder.binding.btnViewdetails.setOnClickListener(View.OnClickListener {
          val intent = Intent(context, InvoiceSummaryActivity::class.java)
          intent.putExtra("orderType", "4")
            context.startActivity(intent)
        })*/


        holder.binding.tvDate.text = data.tripDetails?.bookingDateFrom
       holder.binding.tvInvoicenumber.text = data.paymentDetails.get(0).invoice
        holder.binding.tvFrom.text = data.tripDetails?.fromTrip
        holder.binding.tvTo.text = data.tripDetails?.toTrip
        holder.binding.tvBookingid.text = data.bookingId



        holder.binding.btnViewdetails.setOnClickListener(View.OnClickListener {

            listener.onInvoiceClicked(data)

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }



    interface OnClick{
        fun onInvoiceClicked(loaderInvoiceData: LoaderTripManagementData)
    }

}