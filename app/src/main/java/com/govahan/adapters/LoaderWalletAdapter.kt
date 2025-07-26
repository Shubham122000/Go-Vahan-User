package com.govahan.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahan.R
import com.govahan.customclick.walletcustomclick
import com.govahan.databinding.RowWalletListBinding
import com.govahan.model.loaderwalletlistmodel.LoaderWalletListData

class LoaderWalletAdapter (val list: List<LoaderWalletListData>,var wallet_customclick:walletcustomclick
) : RecyclerView.Adapter<LoaderWalletAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowWalletListBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_list, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        try {
           holder.binding.transactionId.text=data.transactionId.toString()


             if(data.transactionType =="1"){
//                if (data.referal_type ==1){
                    holder.binding.tvName.text = "Amount Credited"
                    holder.binding.tvAmount.text = "+₹${data.amount}"
                    holder.binding.tvAmount.setTextColor(Color.parseColor("#3CB878"))
//                }
//                else if(data.referal_type==2){
//                    holder.binding.tvName.text = "Refund amount."
//                    holder.binding.tvAmount.text = "+₹${data.amount}"
//                    holder.binding.tvAmount.setTextColor(Color.parseColor("#3CB878"))
//                }
//                else {
//                    holder.binding.tvName.text = "Money added to wallet"
//                    holder.binding.tvAmount.text = "+₹${data.amount}"
//                    holder.binding.tvAmount.setTextColor(Color.parseColor("#3CB878"))
//                }

            } else{
//                if (data.transaction_type== "2") {
                    holder.binding.tvAmount.text = "-₹${data.amount}"
                    holder.binding.tvAmount.setTextColor(Color.parseColor("#FF0000"))
                    holder.binding.tvName.text = "Money Paid"
//                }
//                else {
//
//                }
            }


           holder.binding.tvDate.text = data.transactionDate
           if (holder.binding.tvDate.text==""){
               holder.binding.tvDate.text = data.transactionDate

           }

           holder.binding.linearItem.setOnClickListener {
               wallet_customclick.onItemClick(data.userId.toString())

           }
       }catch (e:Exception){
           e.printStackTrace()
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}