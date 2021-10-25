package com.dominando.android.hotel.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dominando.android.hotel.R

class AboutDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { _, i ->
            if (i == DialogInterface.BUTTON_NEGATIVE) {
                val intent =  Intent(Intent.ACTION_CALL, Uri.parse("http://www.nglauber.com"))
                startActivity(intent)
            }
        }

        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_message)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(R.string.about_button_site, listener)
                .create()

    }
}