package com.sachin.milkdistributor.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.dataModels.Customer
import com.sachin.milkdistributor.dataModels.ReportTotalEarning
import kotlinx.android.synthetic.main.customer_adapter.view.*

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    private var listCustomers = mutableListOf<Customer>()
    private var listEarning = mutableListOf<ReportTotalEarning>()
    var listener: RVInterface? = null

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.textView_rv
        val card = itemView.card_rv
        val scheduler = itemView.scheduler
        val info = itemView.customer_info
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.customer_adapter,
            parent, false
        )
        return CustomerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = listCustomers[position]
        // val earning = listEarning[position]
        holder.name?.text = customer.name
        holder.info.setOnClickListener {
            listener?.onInfoClickListener(listCustomers[position])
            Log.d("ClickListen", "item $position clicked")
        }
        holder.card.setOnClickListener {
            listener?.onCardClickListener(listCustomers[position].id)
        }
//        holder.scheduler.setOnClickListener {
//            listener?.onSchedularClickListener(listCustomers[position].id)
//        }

    }

    override fun getItemCount(): Int {
        return listCustomers.size
    }

    fun setCustomers(listCustomers: MutableList<Customer>) {
        this.listCustomers = listCustomers
        notifyDataSetChanged()
    }

    fun getCustomerList(): MutableList<Customer> = listCustomers

    fun setEarning(listEarning: List<ReportTotalEarning>) {
        this.listEarning = listEarning as MutableList<ReportTotalEarning>
        notifyDataSetChanged()
    }

    fun deleteCustomerFromList(position: Int) {

        listCustomers.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateList(list: List<Customer>) {
        this.listCustomers = list as MutableList<Customer>
        notifyDataSetChanged()
    }

    fun searchCustomer(name: String): ArrayList<Customer> {
        var results = ArrayList<Customer>()
        listCustomers.forEach {
            if(it.name.contains(name)) {
                results.add(it)
            }
        }
        return results
    }

    fun clearList() {
        listCustomers.clear()
        notifyDataSetChanged()
    }
}