package com.govahan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahan.R
import com.govahan.databinding.RowLoaderComplaintboxlistItemBinding
import com.govahan.model.passengerComplaintlistmodel.PassengerComplaintData

class PassengerComplaintBoxAdapter (private val list: List<PassengerComplaintData>,
                                    private val listener : OnClick
) : RecyclerView.Adapter<PassengerComplaintBoxAdapter.ViewHolder>() {

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
        holder.binding.tvDate.text = data.bookingDate
        holder.binding.tvFrom.text = data.picupLocation
        holder.binding.tvTo.text = data.dropLocation

        holder.binding.btnViewdetails.setOnClickListener {
            listener.onViewDetail(data  )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onViewDetail(passengerComplaintData: PassengerComplaintData)
    }


}