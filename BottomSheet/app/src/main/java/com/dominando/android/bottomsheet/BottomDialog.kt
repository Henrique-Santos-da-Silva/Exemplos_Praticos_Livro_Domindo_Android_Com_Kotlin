package com.dominando.android.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_dialog.view.*

class BottomDialog : BottomSheetDialogFragment() {
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_dialog, null)
        view.btnConfirm.setOnClickListener {
            Toast.makeText(context, "Confirmadaço", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        view.btnCancel.setOnClickListener {
            Toast.makeText(context, "A que pena tu cancelou :)", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.setContentView(view)
    }
}