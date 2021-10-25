package com.dominando.android.adapter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Manufacturer Id 0=VW; 1=GM; 2=Fiat; 3=Ford

    private val vehicles = mutableListOf(
        Vehicle("Onix", 2018, 1, true, true),
        Vehicle("Uno", 1998, 2, true, false),
        Vehicle("Focus", 2010, 3, true, false),
        Vehicle("Parati", 1988, 0, false, true)
    )

    private lateinit var txtFooter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val listView = ListView(this)
        listView.emptyView = findViewById(android.R.id.empty)
        // setContentView(listView)
        val adapter = VehicleAdapter(this, vehicles)

        listView.adapter = adapter
        initHeaderAndFooter(listView, adapter)

//        listView.setOnItemClickListener { parent, _, position, _ ->
//            // val(model, year) = vehicles[position]
//            val vehicle = parent.getItemAtPosition(position) as? Vehicle
//            if (vehicle != null) {
//                val(model, year) = vehicle
//                Toast.makeText(this, "$model $year", Toast.LENGTH_SHORT).show()
//            }
//        }

        listView.setOnItemClickListener { parent, _, position, _ ->
            val vehicle = parent.getItemAtPosition(position) as? Vehicle
            if (vehicle != null) {
                val(model, year) = vehicle
                Toast.makeText(this, "$model $year", Toast.LENGTH_SHORT).show()
                vehicles.remove(vehicle)
                adapter.notifyDataSetChanged()
                txtFooter.text = resources.getQuantityString(R.plurals.footer_text, adapter.count, adapter.count)
            }
        }
    }

    private fun initHeaderAndFooter(listView: ListView, adapter: VehicleAdapter) {
        val padding = 8
        val txtHeader = TextView(this)
        txtHeader.setBackgroundColor(Color.GRAY)
        txtHeader.setTextColor(Color.WHITE)
        txtHeader.setText(R.string.header_text)
        txtHeader.setPadding(padding, padding, 0, padding)
        listView.addHeaderView(txtHeader)

        txtFooter = TextView(this)
        txtFooter.text = resources.getQuantityString(R.plurals.footer_text, adapter.count, adapter.count)
        txtFooter.setBackgroundColor(Color.LTGRAY)
        txtFooter.gravity = Gravity.END
        txtFooter.setPadding(0, padding, padding, padding)
        listView.addFooterView(txtFooter)
    }
}