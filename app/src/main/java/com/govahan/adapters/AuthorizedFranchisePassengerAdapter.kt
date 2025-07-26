package com.govahan.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahan.R
import com.govahan.databinding.NoOfVehiclesBinding
import com.govahan.model.PassengerName
import java.util.ArrayList

class AuthorizedFranchisePassengerAdapter (val mcontext : Context, val list: ArrayList<PassengerName>):
    RecyclerView.Adapter<AuthorizedFranchisePassengerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: NoOfVehiclesBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.no_of_vehicles, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.vehicleName.text = data.vehicle_name

//             Glide.with(mcontext).load(data.image).into(holder.binding.image)


    }

    override fun getItemCount(): Int {
        return list.size
    }
}