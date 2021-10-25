package dominando.android.basico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val editTexto = findViewById<EditText>(R.id.editTexto)
//        val button = findViewById<Button>(R.id.buttonToast)
//        button.setOnClickListener {
//            val texto = editTexto.text.toString()
//            Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
//        }

        // TODO -> With Kotlin Extensions
        buttonToast.setOnClickListener {
            val texto = editTexto.text.toString()
            Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
        }

        buttonTela2.setOnClickListener {
            val intent = Intent(this, Tela2Activity::class.java)
            intent.putExtra("nome", "Fulano")
            intent.putExtra("idade", 45)
            startActivity(intent)
        }

//        buttonParcel.setOnClickListener {
//            val cliente = Cliente(1, "Fulano")
//            val intent = Intent(this, Tela2Activity::class.java)
//            intent.putExtra("cliente", cliente)
//            startActivity(intent)
//        }

        // TODO -> utilizando a biblioteca externa de Parceler
        buttonParcel.setOnClickListener {
            val cliente = Cliente(1, "Fulano")
            val intent = Intent(this, Tela2Activity::class.java)
            intent.putExtra("cliente", Parcels.wrap(cliente))
            startActivity(intent)
        }

        buttonSerializable.setOnClickListener {
            val intent = Intent(this, Tela2Activity::class.java)
            intent.putExtra("pessoa", Pessoa("Nelson", 35))
            startActivity(intent)
        }

        Log.i("NGVL", "Tela1::onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.i("NGVL", "Tela1::onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("NGVL", "Tela1::onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("NGVL", "Tela1::onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("NGVL", "Tela1::onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.i("NGVL", "Tela1::onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("NGVL", "Tela1::onDestroy")
    }
}