package com.govahan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahan.R
import com.govahan.databinding.RowTriphistoryListBinding
import com.govahan.model.ongoingloadertriphistorymodel.OngoingLoaderHistoryData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import com.govahan.util.DateFormat


class OngoingLoaderTripHistoryAdapter (val list: List<LoaderTripManagementData>,
                                       private val listener: OnClick
) : RecyclerView.Adapter<OngoingLoaderTripHistoryAdapter.ViewHolder>() {
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

        holder.binding.tvDate.text = data.tripDetails?.bookingDateFrom
        holder.binding.tvTime.text = data.bookingTime?.toLong()?.let {
            DateFormat.convertTimestampToTime(
                it
            )
        }
//        holder.binding.tvTime.text = data.tripDetails?.time
        holder.binding.tvPartyname.text = data.tripDetails?.driver?.name
        holder.binding.tvUserName.text = data.bookingId
        holder.binding.tvDetail.text = data.tripDetails?.vehicle?.vehicleName
        holder.binding.tvFrom.text = data.tripDetails?.fromTrip
        holder.binding.tvTo.text = data.tripDetails?.toTrip
        holder.binding.tvVehicleNumber.text = data.tripDetails?.vehicle?.vehicleNumber
        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            listener.onDetailClicked(data)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }



    interface OnClick{
        fun onDetailClicked(ongoingLoaderHistoryData: LoaderTripManagementData)
    }

}