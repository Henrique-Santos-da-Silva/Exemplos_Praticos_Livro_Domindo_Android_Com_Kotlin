package dominando.android.livros.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dominando.android.livros.R
import dominando.android.livros.databinding.ItemBookBinding

class BookAdapter(val books: List<Book>, private val onClick: (Book) -> Unit): RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        holder.binding?.run {
            val currentBook = books[position]
            book = currentBook
            root.setOnClickListener {
                onClick(currentBook)
            }
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = books.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemBookBinding>(view)
    }
}