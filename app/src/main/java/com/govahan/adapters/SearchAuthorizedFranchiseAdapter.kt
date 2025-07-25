package com.govahan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.govahan.R
import com.govahan.activities.authorizedfranchise.AuthorizedFranchisesActivity
import com.govahan.customclick.franchiseclick
import com.govahan.databinding.RowAuthorizedFranchiseBinding
import com.govahan.model.searchauthorisedfranchisesmodel.SearchAuthorisedFranchisesData

class SearchAuthorizedFranchiseAdapter(
    val list: List<SearchAuthorisedFranchisesData>
    , private val listener: AuthorizedFranchisesActivity,var franchiseclick: franchiseclick
) : RecyclerView.Adapter<SearchAuthorizedFranchiseAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowAuthorizedFranchiseBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_authorized_franchise, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]


        holder.binding.tvEmail.text = data.emailId
        holder.binding.tvOwnerName.text = data.ownerName
        holder.binding.tvPhn.text = data.mobileNo
        holder.binding.numnber.text = "Total Vehicles :" + "${data.vehicle_numbers}"
       // holder.binding.tvAvailable.text = data.mobileNo
        Glide.with(holder.itemView.context).load(data.logo).into(holder.binding.ivCompanyLogo)


        holder.binding.btnCall.setOnClickListener {
            data.mobileNo.toString().let { it1 -> listener.onCallNowClicked(it1) }
        }
        holder.binding.linearItem.setOnClickListener {
            franchiseclick.franchiseclick(data.id.toString())
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{

        fun onCallNowClicked(number : String)

    }


}