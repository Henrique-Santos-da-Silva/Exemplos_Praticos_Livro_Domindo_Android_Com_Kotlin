package dominando.android.testedatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dominando.android.testedatabinding.databinding.ActivityMain2Binding
import dominando.android.testedatabinding.model.User

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main2)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        binding.user = User("Pedro Henrique", "Alvarez Cabral")


    }
}