package com.sachin.milkdistributor.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.SPINNER_DEFAULT
import com.sachin.milkdistributor.adapters.DailyDistAdapter
import com.sachin.milkdistributor.adapters.RVInterface
import com.sachin.milkdistributor.dataModels.Customer
import com.sachin.milkdistributor.dataModels.DailyDist
import com.sachin.milkdistributor.viewModels.MainViewModel
import kotlinx.android.synthetic.main.daily_dist_fragment.*
import kotlinx.android.synthetic.main.daily_dist_fragment.view.*

class DailyDistributionFragment : Fragment(), RVInterface {

    private val args: DailyDistributionFragmentArgs by navArgs()
    private val adapter = DailyDistAdapter()
    private lateinit var viewModel: MainViewModel

    var listMilkType = ArrayList<String>()
    var listDate = ArrayList<String>()
    //private var dailyDistList = ArrayList<DailyDist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Action Bar
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Distribution"
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val view = inflater.inflate(R.layout.daily_dist_fragment, container, false)
        view.daily_dist_rv.layoutManager = LinearLayoutManager(context)
        view.daily_dist_rv.adapter = adapter
        view.daily_dist_rv.setHasFixedSize(true)
        adapter.listener = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listMilkType.add(SPINNER_DEFAULT)
        listDate.add(SPINNER_DEFAULT)

        args.dailyDistList.forEach {

            if (it.milk_type != null) listMilkType.add(it.milk_type)
            if (it.delivered_at != null) listDate.add(it.delivered_at)
        }
        setSpinnerData(milk_filter, listMilkType)
        setSpinnerData(delivered_at_filter, listDate)
        setSpinnerData(delivery_filter, arrayListOf(SPINNER_DEFAULT, "Delivered", "Undelivered"))

        milk_filter.setSelection(0)
        delivered_at_filter.setSelection(0)
        delivery_filter.setSelection(0)

        spinnerOnChange()


        //Log.d("argsValue", args?.milkTypeFilter + " " + args?.dateFilter)
        //setFilteredData()
    }

    private fun setSpinnerData(spinner: Spinner, data: ArrayList<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            data?.distinct().sorted()
        )
        spinner!!.adapter = adapter
    }

    private fun spinnerOnChange() {
        milk_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                setFilteredData(
                    parent?.getItemAtPosition(position).toString(),
                    delivered_at_filter.selectedItem.toString()
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        delivered_at_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                setFilteredData(
                    milk_filter.selectedItem.toString(),
                    parent?.getItemAtPosition(position).toString()
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }



    private fun setFilteredData(milk: String, date: String) {
        Log.d("filteredArgs", args.milkTypeFilter + args.dateFilter)

//        if(milk_filter.selectedItem == listMilkType[0] && delivered_at_filter.selectedItem == listDate[0]) {
//            adapter.setDailyDistList(args.dailyDistList)
//            return
//        }

        val filtered: List<DailyDist> =
            if (milk != listMilkType[0] && date != listDate[0]) {
            //Log.d("Input", milk + " " + date)
            args.dailyDistList.filter {
                it.milk_type == milk && it.delivered_at == date
            }
        } else if (milk != listMilkType[0] && date == listDate[0]) {
            args.dailyDistList.filter { it.milk_type == milk }
        } else if (milk == listMilkType[0] && date != listDate[0]) {
            args.dailyDistList.filter { it.delivered_at == date }

        } else {
            args.dailyDistList
        }

        Log.d("filteredVal", filtered.toString())
        adapter.setDailyDistList(filtered)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.daily_dist_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_filter).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

//            R.id.action_filter -> {
//                var listMilkType = ListString()
//                var listDate = ListString()
//
//                args.dailyDistList.forEach {
//
//                    if (it.milk_type != null) listMilkType.add(it.milk_type)
//                    if (it.delivered_at != null) listDate.add(it.delivered_at)
//                }
//                //Log.d("listDaily", args.dailyDistList.toString())
//                Log.d("listsDaily", listMilkType!!.toString() + listDate!!.toString())
//
//
//                val directions =
//                    DailyDistributionFragmentDirections.actionDailyDistributionFragmentToFilterDialog(
//                        listMilkType,
//                        listDate
//                    )
//                findNavController().navigate(directions)
//            }
        }
        return true
    }

    override fun onInfoClickListener(customer: Customer) {
        TODO("Not yet implemented")
    }

    override fun onSchedularClickListener(customerID: Int) {
        TODO("Not yet implemented")
    }

    override fun onAddClickLisener(customerID: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardClickListener(customerID: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeliveryCheckedChangeListener(
        isChecked: Boolean,
        dist_id: Int,
        buttonview: CompoundButton
    ) {
        if (isChecked) {
            viewModel.setDelivered(dist_id.toString())
        }
        else {
            alertDialog(dist_id, buttonview)
        }
    }

    private fun alertDialog(dist_id: Int, buttonView: CompoundButton) {
       val alterDialog = AlertDialog.Builder(requireContext()).also {
            it.setTitle(getString(R.string.DeliveryConfirmation))
            it.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                viewModel.setUndelivered(dist_id.toString())

            }
            it.setNegativeButton(R.string.no) { dialog, which ->
                viewModel.setDelivered(dist_id.toString())
                buttonView.isChecked = true
                it.setCancelable(false)
            }
        }.create()

        alterDialog.show()
        alterDialog.setCanceledOnTouchOutside(false)


    }
}