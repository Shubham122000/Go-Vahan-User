package com.govahanuser.com.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.govahanuser.com.R
import com.govahanuser.com.databinding.RowPassengerAvailableVehiclesBinding
import com.govahanuser.com.model.searchvehiclemodel.SearchVehicleData

class AvailablePVehiclesAdapter(private val list: ArrayList<SearchVehicleData>
                                , private val listener: OnClick, var reviewsclick: OnClick
) : RecyclerView.Adapter<AvailablePVehiclesAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowPassengerAvailableVehiclesBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_passenger_available_vehicles, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]
        holder.binding.vehicleName.text = data.vehicle?.vehicleName
        holder.binding.tvAvailableat.text = data.time
        // holder.binding.tvAvailable.text = data.available
//        holder.binding.wheelerType.text = data.noOfTyres.toString()
        holder.binding.tvFrom.text = data.fromTrip
        holder.binding.tvTo.text = data.toTrip
        holder.binding.vehicleNumber.text = data.vehicle?.vehicleNumber
//        holder.binding.tvBodytype.text = data.vehicle?.bodyType?.name
        holder.binding.tvCapacity.text = data.vehicle?.seats
        holder.binding.tvDistance.text = data.totalDistance
        holder.binding.tvDrivername.text = data.driver?.name
        holder.binding.tvFinalFare.text = "₹" + data.freightAmount
//        holder.binding.tvCompletedtrips.text= data.driver_total_booking.toString()
//        holder.binding.tvRating.text=data.rating.toString()
//        holder.binding.tvOwnername.text=data.user?.name

        Glide.with(holder.itemView.context).load(data.vehicle?.imageUrl).into(holder.binding.vehicleImage)

        holder.binding.btnCallNow.setOnClickListener {
            data.driver?.mobileNumber.toString().let { it1 -> listener.onCallNowClicked(it1) }
        }

        holder.binding.btnProceed.setOnClickListener {
            listener.onProceedClicked(data)
        }

        holder.binding.reviews.setOnClickListener {
            data.driver?.toString()?.let { it1 -> reviewsclick.reviewsclick(it1) }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClick{

        fun onCallNowClicked(number : String)

        fun onProceedClicked(searchPassListModelData: SearchVehicleData)
        fun reviewsclick(id:String)

    }


}