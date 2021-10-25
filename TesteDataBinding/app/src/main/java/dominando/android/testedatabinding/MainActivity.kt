package dominando.android.testedatabinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnt_bla.setOnClickListener {
            Toast.makeText(this, "hsdshhdjs", Toast.LENGTH_SHORT).show()
        }

        startActivity(Intent(this, MainActivity2::class.java))
    }
}