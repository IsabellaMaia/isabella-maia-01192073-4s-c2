package com.example.isabella_maia_4s_c2
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PrimeiraTela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun irTela2(view: View) {

        val tela2 = Intent(this, Tela2::class.java)

        startActivity(tela2)

    }

    fun irTela3(view: View) {

        val tela3 = Intent(this, Tela3::class.java)

        startActivity(tela3)

    }
}