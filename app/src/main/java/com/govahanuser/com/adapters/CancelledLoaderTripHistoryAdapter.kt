package com.govahanuser.com.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahanuser.com.R
import com.govahanuser.com.databinding.RowTriphistoryListBinding
import com.govahanuser.com.model.cancelledloadertriphistorymodel.CancelledLoaderTripHistoryData
import com.govahanuser.com.util.DateFormat.Companion.convertTimestampToTime
import com.govahanuser.com.util.Utils

class CancelledLoaderTripHistoryAdapter (val list: List<CancelledLoaderTripHistoryData>,
                                         private val listener: OnClick
) : RecyclerView.Adapter<CancelledLoaderTripHistoryAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowTriphistoryListBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_triphistory_list, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        /*holder.binding.linearItem.setOnClickListener(View.OnClickListener {
           val intent = Intent(context, BookingDetailsActivity::class.java)
          intent.putExtra("orderType", "4")
            context.startActivity(intent)
        })*/

        holder.binding.tvDate.text = data.bookingDate
        holder.binding.tvTime.text = data.bookingTime?.toLong()?.let { convertTimestampToTime(it) }
        holder.binding.tvPartyname.text = data.partyName
        holder.binding.tvUserName.text = data.bookingId
        holder.binding.tvDetail.text = data.vehicleNumbers
        holder.binding.tvFrom.text = data.picupLocation
        holder.binding.tvTo.text = data.dropLocation
        holder.binding.tvVehicleNumber.text = data.vehicleNumber

        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            listener.onCancelledDetailClicked(data)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onCancelledDetailClicked(cancelledLoaderTripHistoryData: CancelledLoaderTripHistoryData)
    }


}