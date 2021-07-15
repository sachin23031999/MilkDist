package com.sachin.milkdistributor.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.dataModels.Customer
import kotlinx.android.synthetic.main.archived_customer_adapter.view.*

class ArchivedCustomerAdapter : RecyclerView.Adapter<ArchivedCustomerAdapter.CustomerViewHolder>() {

    private var listCustomers = mutableListOf<Customer>()
    var listener: RVInterface? = null

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.archive_tv
        val card = itemView.archived_card_rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.archived_customer_adapter,
            parent, false
        )
        return CustomerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = listCustomers[position]
        holder.name?.text = customer.name
        holder.card.setOnClickListener {
            listener?.onInfoClickListener(listCustomers[position])
            Log.d("ClickListen", "item $position clicked")
        }

    }

    override fun getItemCount(): Int {
        return listCustomers.size
    }

    fun setArchivedCustomers(listCustomers: MutableList<Customer>) {
        this.listCustomers = listCustomers
        notifyDataSetChanged()
    }

    fun getArchivedCustomerList(): MutableList<Customer> = listCustomers


    fun deleteCustomerFromList(position: Int) {

        listCustomers.removeAt(position)
        notifyDataSetChanged()
    }

}