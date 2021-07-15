package com.sachin.milkdistributor.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sachin.milkdistributor.R
import com.sachin.milkdistributor.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fetch_cow_buff.*
import java.text.SimpleDateFormat
import java.util.*

class FetchCowBuffFragment : Fragment() {

    //var cal = Calendar.getInstance()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fetch_cow_buff, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        //Action Bar
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Report"
        actionBar?.setDisplayHomeAsUpEnabled(true)
       // actionBar?.setHomeButtonEnabled(true)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        fetchReport()

    }


    private fun fetchReport() {

        //start_date_input.setText("2021-02-24")
        //end_date_input.setText("2021-08-20")

        setDate(start_date_input)
        setDate(end_date_input)

        milk_report.setOnClickListener {

            val startDate = start_date_input.text.toString()// "2021-02-24"
            val endDate = end_date_input.text.toString()//"2021-09-20"
            viewModel.getCowBuffReport(
                startDate, endDate
            ).observe(viewLifecycleOwner) {

                //   println(it!!.size)
                title.text = "Cow vs Buffalo Report"
                type_1.text = "Cow"
                type_1_earning.text = "Total Earning: " + it[0].total_earning
                type_1_quantity.text = "Total Quantity: " + it[0].total_quantity

                type_2.text = "Buffalo"
                type_2_earning.text = "Total Earning: " + it[1].total_earning
                type_2_quantity.text = "Total Quantity: " + it[1].total_quantity
            }
        }
    }

    private fun setDate(textView: TextView) {

        //textView.setText(SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()))

        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textView.setText(sdf.format(cal.time))

            }

        textView.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }
}