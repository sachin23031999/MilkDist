package com.sachin.milkdistributor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.dataModels.Customer
import com.sachin.milkdistributor.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.customer_details_dialog.*

class CustomerDetailsFragment : DialogFragment() {

    private val args: CustomerDetailsFragmentArgs by navArgs()
    lateinit var customer:Customer
    lateinit var viewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.ThemeOverlay_Material_Dialog_Alert)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.customer_details_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customer = args.customer

        name_cus.text = "Name: " + customer.name
        mobile_cus.text = "Mobile: " + customer.mobile
        type_cus.text = "Type: " + customer.type_of_customer
        address_cus.text = "Address: " + customer.address + "\nPincode: " + customer.pincode

    }
}