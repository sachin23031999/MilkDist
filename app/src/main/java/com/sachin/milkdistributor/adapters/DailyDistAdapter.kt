package com.sachin.milkdistributor.adapters

import com.sachin.milkdistributor.dataModels.DailyDist
import kotlinx.android.synthetic.main.daily_dist_adapter.view.*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sachin.milkdistributor.R

class DailyDistAdapter : RecyclerView.Adapter<DailyDistAdapter.DailyDistViewHolder>() {

    private var listDistribution = mutableListOf<DailyDist>()
    var listener: RVInterface? = null

    inner class DailyDistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val milk_type = itemView.milk_type
        val quantity = itemView.quantity
        val time_of_delivery = itemView.time_of_delivery
        val deliveryStatus = itemView.is_delivered_checkbox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.daily_dist_adapter,
            parent, false
        )
        return DailyDistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DailyDistViewHolder, position: Int) {
        val dist = listDistribution[position]
        holder.milk_type.text = "Milk Type: " + dist.milk_type
        holder.quantity.text = "Quantity: " + dist.quantity.toString() + " L"
        holder.time_of_delivery.text = "Delivery Time: " + dist.delivered_at
        holder.deliveryStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            listener?.onDeliveryCheckedChangeListener(isChecked, dist.id, buttonView)
        }
    }

    override fun getItemCount(): Int = listDistribution.size


    fun setDailyDistList(list: List<DailyDist>) {
        listDistribution = list as MutableList<DailyDist>
        notifyDataSetChanged()
    }

    fun getDailyDistList(): List<DailyDist> = listDistribution
}