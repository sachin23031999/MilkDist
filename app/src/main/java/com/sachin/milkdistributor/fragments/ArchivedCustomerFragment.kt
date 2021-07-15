package com.sachin.milkdistributor.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.adapters.ArchivedCustomerAdapter
import com.sachin.milkdistributor.utils.SwipeAction
import com.sachin.milkdistributor.adapters.RVInterface
import com.sachin.milkdistributor.dataModels.Customer
import com.sachin.milkdistributor.dataModels.CustomerList
import com.sachin.milkdistributor.viewModels.MainViewModel
import kotlinx.android.synthetic.main.archive_customer_fragment.*
import kotlinx.android.synthetic.main.archive_customer_fragment.view.*

class ArchivedCustomerFragment : Fragment(), RVInterface {

    private val adapter = ArchivedCustomerAdapter()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Archived"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // actionBar?.setHomeButtonEnabled(true)

        val view = inflater.inflate(R.layout.archive_customer_fragment, container, false)
        view.archived_customer_rv.layoutManager = LinearLayoutManager(context)
        view.archived_customer_rv.adapter = adapter
        view.archived_customer_rv.setHasFixedSize(true)

        adapter.listener = this //setting context

        //Action Bar
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Archived"
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return view
    }

    //override fun onCreate(savedInstanceState: Bundle?)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCustomerToRV()

        onSwipe()

        refreshRV()

    }

    private fun onSwipe() {
        val item = object : SwipeAction(requireContext(), 0, ItemTouchHelper.RIGHT) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: kotlin.Int) {

                val lists: MutableList<Customer>? = adapter.getArchivedCustomerList()

                val alertDialog = AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.unArchive_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                        viewModel.unArchiveCustomer(lists!![viewHolder.adapterPosition].id)
                        adapter.deleteCustomerFromList(viewHolder.adapterPosition)

                    }
                    it.setNegativeButton(R.string.no) { dialog, which ->
                        setCustomerToRV()
                        it.setCancelable(false)
                    }
                }.create()
                alertDialog.show()
                alertDialog.setCanceledOnTouchOutside(false)

            }

        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(archived_customer_rv)
    }

    private fun setCustomerToRV() {

        viewModel.getArchivedCustomerList().observe(viewLifecycleOwner, Observer<CustomerList> {
            if (it == null)
                Toast.makeText(context, "no results found", Toast.LENGTH_SHORT).show()
            else {
                if (it.isEmpty()) {
                    archive_default_text!!.setText("No customers")
                }
                adapter.setArchivedCustomers(it)
            }
        })
    }

    private fun refreshRV() {
        swipeRefresh_archived?.setOnRefreshListener {

            setCustomerToRV()
            swipeRefresh_archived?.isRefreshing = false
        }
    }

    override fun onInfoClickListener(customer: Customer) {
        // Toast.makeText(context, "item $pos clicked", Toast.LENGTH_SHORT).show()
        //CustomerDetailsFragment().show(childFragmentManager, "")
        val directions =
            ArchivedCustomerFragmentDirections.actionArchivedCustomerFragmentToCustomerDetailsFragment(
                customer
            )
        findNavController().navigate(directions)
    }

    override fun onSchedularClickListener(customerID: kotlin.Int) {
        TODO("Not yet implemented")
    }

    override fun onAddClickLisener(customerID: kotlin.Int) {
        TODO("Not yet implemented")
    }

    override fun onCardClickListener(customerID: kotlin.Int) {
        TODO("Not yet implemented")
    }

    override fun onDeliveryCheckedChangeListener(
        isChecked: Boolean,
        dist: Int,
        buttonview: CompoundButton
    ) {
        TODO("Not yet implemented")
    }
}