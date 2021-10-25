package com.dominando.android.adapter

import android.content.Context
import android.content.res.TypedArray
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_vehicle.view.*

class VehicleAdapter(private val context: Context, private val vehicles: List<Vehicle>) : BaseAdapter() {
    private val logos: TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.logos)
    }

    override fun getCount(): Int = vehicles.size

    override fun getItem(position: Int) = vehicles[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vehicle = vehicles[position]
        val holder: ViewHolder
        val row: View

        if (convertView == null) {
            Log.d("NGVL", "View Nova => Position: $position")
            row = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false)
            holder = ViewHolder(row)
            row.tag = holder
        } else {
            Log.d("NGVL", "View Existente => Position: $position")
            row = convertView
            holder = convertView.tag as ViewHolder
        }

        holder.imgLogo.setImageDrawable(logos.getDrawable(vehicle.manufacturer))
        holder.txtModel.text = vehicle.model
        holder.txtYear.text = vehicle.year.toString()
        holder.txtFuel.text = context.getString(getFuel(vehicle))

//        val row = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false)
//
//        row.imgLogo.setImageDrawable(logos.getDrawable(vehicle.manufacturer))
//        row.txtModel.text = vehicle.model
//        row.txtYear.text = vehicle.year.toString()
//        row.txtFuel.text = context.getString(getFuel(vehicle))

        return row
    }

    companion object {
        data class ViewHolder(val view: View) {
            val imgLogo: ImageView = view.imgLogo
            val txtModel: TextView = view.txtModel
            val txtYear: TextView = view.txtYear
            val txtFuel: TextView = view.txtFuel
        }
    }

    private fun getFuel(vehicle: Vehicle): Int =
        if (vehicle.gasoline && vehicle.ethanol) R.string.fuel_flex
        else if (vehicle.gasoline) R.string.fuel_gasoline
        else if (vehicle.ethanol) R.string.fuel_ethanol
        else R.string.fuel_invalid
}