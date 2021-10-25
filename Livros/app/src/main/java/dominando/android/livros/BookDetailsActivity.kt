package dominando.android.livros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dominando.android.livros.databinding.ActivityBookDetailsBinding
import dominando.android.livros.model.Book
import dominando.android.livros.model.MediaType
import dominando.android.livros.model.Publisher
import dominando.android.livros.viewmodel.BookDetailsViewModel
import dominando.android.livros.viewmodel.BookFormViewModel

class BookDetailsActivity : BaseActivity() {
    private val binding: ActivityBookDetailsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_book_details)
    }

    private val viewModel: BookDetailsViewModel by lazy {
        ViewModelProvider(this).get(BookDetailsViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_book_details)
        val book = intent.getParcelableExtra<Book>(EXTRA_BOOK)

        if (book != null) {
            binding.book = book
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.book_details, menu)
//        return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit_book) {
            binding.book?.let {
                BookFormActivity.start(this, it)
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun init() {
        binding.book?.let { book ->
            viewModel.getBook(book.id).observe(this, Observer {
                binding.book = it
            })
        }
    }

    companion object{
        private const val EXTRA_BOOK = "book"

        fun start(context: Context, book: Book) {
            context.startActivity(Intent(context, BookDetailsActivity::class.java).apply {
                putExtra(EXTRA_BOOK, book)
            })
        }
    }
}