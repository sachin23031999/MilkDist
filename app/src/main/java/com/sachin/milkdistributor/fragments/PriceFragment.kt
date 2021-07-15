package com.sachin.milkdistributor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.dataModels.Price
import com.sachin.milkdistributor.viewModels.MainViewModel
import kotlinx.android.synthetic.main.price_fragment.*

class PriceFragment : DialogFragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.ThemeOverlay_Material_Dialog_Alert)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.price_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var prices = emptyArray<Price>()

        viewModel.getPrices().observe(viewLifecycleOwner) {

            prices = it.toTypedArray()
            cow_price.setText(it[0].price.toString())
            buffalo_price.setText(it[1].price.toString())
        }

        update_price.setOnClickListener {

            viewModel.updatePrice(prices[0].id, Price(prices[0].id, prices[0].milk_type, cow_price.text.toString().toDouble()))
            viewModel.updatePrice(prices[1].id, Price(prices[1].id, prices[1].milk_type, buffalo_price.text.toString().toDouble()))

            dismiss()
        }


    }

}