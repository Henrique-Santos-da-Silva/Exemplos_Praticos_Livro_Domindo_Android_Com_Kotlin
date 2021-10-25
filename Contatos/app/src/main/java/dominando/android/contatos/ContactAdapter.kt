package dominando.android.contatos

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.squareup.picasso.Picasso
import dominando.android.contatos.databinding.ItemContactBinding

class ContactAdapter(context: Context, c: Cursor?): CursorAdapter(context, c, 0) {
    lateinit var indexes: IntArray

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        indexes = intArrayOf(
            cursor.getColumnIndex(ContactsContract.Contacts._ID),
            cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY),
            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

        return binding.root
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val binding = ItemContactBinding.bind(view)
        val txtNome = binding.txtName
        val qcbBadge = binding.qcbPhoto
        val contactUri = ContactsContract.Contacts.getLookupUri(
            cursor.getLong(indexes[0]),
            cursor.getString(indexes[1]))
        txtNome.text = cursor.getString(indexes[2])
        qcbBadge.assignContactUri(contactUri)
        Picasso.get()
            .load(contactUri)
            .placeholder(R.drawable.ic_the_flash_sign)
            .into(qcbBadge)
    }

}