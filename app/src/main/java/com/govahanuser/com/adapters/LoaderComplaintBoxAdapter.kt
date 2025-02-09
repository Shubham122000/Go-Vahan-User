package com.govahanuser.com.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahanuser.com.R
import com.govahanuser.com.databinding.RowLoaderComplaintboxlistItemBinding
import com.govahanuser.com.model.loaderComplaintlistmodel.LoaderComplaintData

class LoaderComplaintBoxAdapter (private val list: List<LoaderComplaintData>,
                                 private val listener : OnClick
) : RecyclerView.Adapter<LoaderComplaintBoxAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowLoaderComplaintboxlistItemBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_loader_complaintboxlist_item, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        /*holder.binding.linearItem.setOnClickListener(View.OnClickListener {
          val intent = Intent(context, TripDetailsActivity::class.java)
          intent.putExtra("orderType", "4")
            context.startActivity(intent)
        })*/

        holder.binding.tvComplaintNumber.text = data.bookingId
        holder.binding.tvBookingid.text = data.bookingId
        holder.binding.tvSubject.text = data.bookingId
        holder.binding.tvDate.text = data.tripDetails?.bookingDateFrom
        holder.binding.tvFrom.text = data.tripDetails?.fromTrip
        holder.binding.tvTo.text = data.tripDetails?.toTrip

        holder.binding.btnViewdetails.setOnClickListener {
            listener.onViewDetail(data  )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onViewDetail(loaderComplaintData: LoaderComplaintData)
    }


}