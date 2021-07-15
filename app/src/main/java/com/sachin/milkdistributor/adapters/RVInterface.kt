package com.sachin.milkdistributor.adapters

import android.widget.CompoundButton
import com.sachin.milkdistributor.dataModels.Customer

interface RVInterface {

    fun onInfoClickListener(customer: Customer)
    fun onSchedularClickListener(customerID : kotlin.Int)
    fun onAddClickLisener(customerID: kotlin.Int)
    fun onCardClickListener(customerID: kotlin.Int)
    fun onDeliveryCheckedChangeListener(
        isChecked: Boolean,
        dist_id: Int,
        buttonview: CompoundButton
    )
}