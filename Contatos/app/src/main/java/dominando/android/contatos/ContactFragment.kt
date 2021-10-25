package dominando.android.contatos

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import dominando.android.contatos.databinding.FragmentContactBinding
import java.io.FileNotFoundException

class ContactFragment: DialogFragment(), DialogInterface.OnClickListener {
    lateinit var binding: FragmentContactBinding
    lateinit var edtName: EditText
    lateinit var edtPhone: EditText
    lateinit var edtAddress: EditText
    lateinit var imgPhoto: ImageView
    private var selectedPhoto: Bitmap? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentContactBinding.inflate(layoutInflater)
        edtName = binding.edtName
        edtPhone = binding.edtPhone
        edtAddress = binding.edtAddress
        imgPhoto = binding.imgPhoto
        imgPhoto.setOnClickListener {
            selectPhotoFromGallery()
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok, this)
            .setNegativeButton(R.string.button_cancel, null)
            .create()

    }


    override fun onClick(dialog: DialogInterface, which: Int) {
        val photo = selectedPhoto
        if (edtName.text.isNotEmpty() && edtPhone.text.isNotEmpty() && photo != null) {
            ContactUtils.insertContact(
                requireContext(),
                edtName.text.toString(),
                edtPhone.text.toString(),
                edtAddress.text.toString(),
                photo)
        }
    }

    private val pickActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null && result.data?.data != null) {
            try {
                val options = BitmapFactory.Options()
                options.inSampleSize = 4
                selectedPhoto = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(result.data?.data as Uri), null, options)
                imgPhoto.setImageBitmap(selectedPhoto)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

    }


    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickActivityForResult.launch(Intent(intent))
    }

}