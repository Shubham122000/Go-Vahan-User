package com.govahan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahan.R
import com.govahan.databinding.RowTripManagementBinding
import com.govahan.model.tripmanagementpassengermodel.PassengerTripManagementData
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementData
import com.govahan.util.DateFormat

class PassengerTripManagementAdapter (

    val list: List<LoaderTripManagementData>,
    private val listener: OnClick
) : RecyclerView.Adapter<PassengerTripManagementAdapter.ViewHolder>() {
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowTripManagementBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_trip_management, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        holder.binding.tvDate.text = data.tripDetails?.bookingDateFrom
//        holder.binding.tvTime.text = data.tripDetails?.time
        holder.binding.tvTime.text = data.bookingTime?.toLong()?.let {
            DateFormat.convertTimestampToTime(
                it
            )
        }
//        holder.binding.tvPartyname.text = data.tripDetails?.driver?.name
        holder.binding.tvUsername.text = data.tripDetails?.driver?.name
        holder.binding.tvVehicleName.text = data.tripDetails?.vehicle?.vehicleName
        holder.binding.tvBodytype.text = data.tripDetails?.vehicle?.bodyType?.name
        holder.binding.tvVehicleNumber.text = data.tripDetails?.vehicle?.vehicleNumber
        holder.binding.tvFrom.text = data.tripDetails?.fromTrip
        holder.binding.tvTo.text = data.tripDetails?.toTrip
        holder.binding.tvAmount.text = data.tripDetails?.freightAmount

        /*holder.binding.llViewdetails.setOnClickListener(View.OnClickListener {
          val intent = Intent(context, TripDetailsActivity::class.java)
          intent.putExtra("orderType", "4")
            context.startActivity(intent)
        })*/

        holder.binding.llViewdetails.setOnClickListener(View.OnClickListener {

            listener.onProceedPassengerClicked(data)

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClick{
        fun onProceedPassengerClicked(passengerTripListModelData: LoaderTripManagementData)
    }

}