package com.sachin.milkdistributor.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import android.widget.SearchView
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
import com.sachin.milkdistributor.utils.SwipeAction
import com.sachin.milkdistributor.adapters.CustomerAdapter
import com.sachin.milkdistributor.adapters.RVInterface
import com.sachin.milkdistributor.dataModels.Customer
import com.sachin.milkdistributor.dataModels.CustomerList
import com.sachin.milkdistributor.viewModels.MainViewModel
import kotlinx.android.synthetic.main.customer_fragment.*
import kotlinx.android.synthetic.main.customer_fragment.view.*

class CustomerFragment : Fragment(), RVInterface {

    private val adapter = CustomerAdapter()
    private var customerList = ArrayList<Customer>()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Action Bar
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Customers"
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.customer_fragment, container, false)
        view.customer_rv.layoutManager = LinearLayoutManager(context)
        view.customer_rv.adapter = adapter
        view.customer_rv.setHasFixedSize(true)

        adapter.listener = this //setting context


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.customer_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_archived).setVisible(true)
        super.onPrepareOptionsMenu(menu)
    }

    //override fun onCreate(savedInstanceState: Bundle?)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCustomerToRV()

//        val start = "2021-02-24"
//        val end = "2021-08-20"

        val item = object : SwipeAction(requireContext(), 0, ItemTouchHelper.RIGHT) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: kotlin.Int) {

                val lists: MutableList<Customer>? = adapter.getCustomerList()

                val alertDialog = AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.archive_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                        viewModel.archiveCustomer(lists!![viewHolder.adapterPosition].id)
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
        itemTouchHelper.attachToRecyclerView(customer_rv)

        refreshRV()

        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_customerFragment_to_fetchCowBuffFragment2)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_archived -> {

                findNavController().navigate(R.id.action_customerFragment_to_archivedCustomerFragment)
            }

            R.id.action_price -> {

                PriceFragment().show(childFragmentManager, "")
                //findNavController().navigate(R.id.action_customerFragment_to_priceFragment)
            }
            R.id.action_search -> {

                val searchView = item.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: kotlin.String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: kotlin.String?): Boolean {
                        //adapter.clearList()
                        if (newText!!.isNotEmpty()) {
                            adapter.updateList(customerList)
                            val results = adapter.searchCustomer(newText)
//                            if(results.isEmpty()) {
//                                customer_default_text!!.setText("Not found, search again")
//                            } else {
//                                customer_default_text.isVisible = false
//                            }
                            //Log.d("textchange", newText)
                            adapter.updateList(results)
                        } else {
                            adapter.updateList(customerList)
                        }

                        return false
                    }
                })
            }
        }

        return true
    }

    private fun setCustomerToRV() {
        viewModel.getCustomerList().observe(viewLifecycleOwner, Observer<List<Customer>> {
            if (it == null)
                Toast.makeText(context, "no results found", Toast.LENGTH_SHORT).show()
            else {
                adapter.setCustomers(it as MutableList<Customer>)
                customerList = it as ArrayList<Customer>
            }
        })
    }

    private fun refreshRV() {
        swipeRefresh.setOnRefreshListener {
            setCustomerToRV()

            swipeRefresh.isRefreshing = false
        }
    }

    override fun onInfoClickListener(customer: Customer) {
        // Toast.makeText(context, "item $pos clicked", Toast.LENGTH_SHORT).show()
        //CustomerDetailsFragment().show(childFragmentManager, "")
        val directions =
            CustomerFragmentDirections.actionCustomerFragmentToCustomerDetailsFragment(customer)
        findNavController().navigate(directions)
    }

    override fun onCardClickListener(customerID: kotlin.Int) {

        viewModel.getDailyDistByCustomerID(customerID.toString()).observe(viewLifecycleOwner) {

            val directions =
                CustomerFragmentDirections.actionCustomerFragmentToDailyDistributionFragment(it,"","")
            findNavController().navigate(directions)
        }
    }

    override fun onDeliveryCheckedChangeListener(
        isChecked: Boolean,
        dist: Int,
        buttonview: CompoundButton
    ) {
        TODO("Not yet implemented")
    }

    override fun onSchedularClickListener(customerID: kotlin.Int) {
        TODO("Not yet implemented")
    }

    override fun onAddClickLisener(customerID: kotlin.Int) {
        TODO("Not yet implemented")
    }

}