package com.sachin.milkdistributor.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.dataModels.DailyDistList
import kotlinx.android.synthetic.main.filter_dialog.*

class FilterDialog : DialogFragment() {

    private val args: FilterDialogArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.ThemeOverlay_Material_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val a1 = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            args.milkTypeList.distinct()
        )
        filter_milk_type_dropdown!!.adapter = a1

        val a2 = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            args.dateList.distinct()
        )
        filter_date_dropdown!!.adapter = a2

//        val milk = spinnerSelectItemListener(filter_milk_type_dropdown)
//        val date = spinnerSelectItemListener(filter_date_dropdown)

        filter_button.setOnClickListener {

            val milk = filter_milk_type_dropdown.selectedItem.toString()
            val date = filter_date_dropdown.selectedItem.toString()
            Log.d("selectedItem", milk + " " + date)
            val directions = FilterDialogDirections.actionFilterDialogToDailyDistributionFragment(
                DailyDistList(), milk, date
            )

            NavHostFragment.findNavController(this).navigate(directions)
        }
    }


//    private fun spinnerSelectItemListener(spinner: Spinner): String {
//        //var temp = ""
////        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                temp = parent?.getItemAtPosition(position).toString()
//                Log.d("itemSelect", temp)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        return spinner.selectedItem.toString()
//    }


}
