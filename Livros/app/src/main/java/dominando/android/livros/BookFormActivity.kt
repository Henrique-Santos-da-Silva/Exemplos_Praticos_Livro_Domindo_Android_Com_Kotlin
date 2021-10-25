package dominando.android.livros

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.type.Date
import dominando.android.livros.databinding.ActivityBookFormBinding
import dominando.android.livros.model.Book
import dominando.android.livros.model.MediaType
import dominando.android.livros.model.Publisher
import dominando.android.livros.viewmodel.BookFormViewModel
import kotlinx.android.synthetic.main.book_form_content.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class BookFormActivity : BaseActivity() {
    private val binding: ActivityBookFormBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_book_form)
    }

    private val viewModel: BookFormViewModel by lazy {
        ViewModelProvider(this).get(BookFormViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_book_form)

        binding.content.book = if (savedInstanceState == null) {
            intent.getParcelableExtra<Book>(EXTRA_BOOK) ?: Book()
        } else {
            savedInstanceState.getParcelable(EXTRA_BOOK)
        }

        binding.content.publishers = listOf(Publisher("1", "Novatec"), Publisher("2", "Outra"))
        binding.content.view = this
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable(EXTRA_BOOK, binding.content.book)
    }

    private val bookFormActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        // OBS: a verificação do resultCode com RC_CAMERA, não funciona, apenas o RESULT_OK
        if (result.resultCode == Activity.RESULT_OK ) {
            binding.content.book?.coverUrl = "file://${viewModel.tempImageFile?.absolutePath}"
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == RC_CAMERA) {
//            binding.content.book?.coverUrl = "file://${viewModel.tempImageFile?.absolutePath}"
//        }
//    }

    fun clickTakePhoto(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            viewModel.deleteTempPhoto()
//            val sdf = SimpleDateFormat("ddMMyyyy_hhmmss").format(Date()).toString()
            val fileName = DateFormat.format("ddMMyyy_hhmmss", Date()).toString()
            val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),  "$fileName.jpg")
            val photoUri = FileProvider.getUriForFile(this, "dominando.android.livros.fileprovider", file)
            viewModel.tempImageFile = file
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            bookFormActivityResult.launch(Intent(takePictureIntent))
//            startActivityForResult(takePictureIntent, RC_CAMERA)
        }
    }

    fun onMediaTypeChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            if (buttonView === binding.content.rbMediaEbook) {
                binding.content.book?.mediaType = MediaType.EBOOK

            } else if (buttonView === binding.content.rbMediaPaper) {
                binding.content.book?.mediaType = MediaType.PAPER
            }
        }
    }

    fun clickSaveBook(view: View) {
        val book = binding.content.book
        if (book != null) {
            try {
                viewModel.saveBook(book)
            } catch (e: Exception) {
                showMessageError()
            }
        }
    }

    override fun init() {
        viewModel.showProgress().observe(this, Observer { loading ->
            loading?.let {
                btnSave.isEnabled = !loading
                binding.content.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.savingOperation().observe(this, Observer { success ->
            success?.let {
                if (success) {
                    showMessageSuccess()
                    finish()
                } else {
                    showMessageError()
                }
            }
        })
    }

    private fun showMessageSuccess() {
        Toast.makeText(this, R.string.message_book_saved, Toast.LENGTH_SHORT).show()
    }

    private fun showMessageError() {
        Toast.makeText(this, R.string.message_error_book_saved, Toast.LENGTH_SHORT).show()

    }

    companion object {
        private const val EXTRA_BOOK = "book"
        private const val RC_CAMERA = 1

        fun start(context: Context, book: Book) {
            context.startActivity(Intent(context, BookFormActivity::class.java).apply {
                putExtra(EXTRA_BOOK, book)
            })
        }
    }
}