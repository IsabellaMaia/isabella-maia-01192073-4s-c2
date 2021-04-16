package com.example.isabella_maia_4s_c2

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Tela3 : AppCompatActivity() {

    var contador = 0

    lateinit var preferencias: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela3)


        //Usando shared preferences para bustar o último usuário.
        preferencias = getSharedPreferences("AUTENTICACAO", MODE_PRIVATE)

        val ultimoUsuario = preferencias.getString("usuario", null)

        fun irTela2(ultimoUsuario: String) {
            val tela2 = Intent(this, Tela2::class.java)
            tela2.putExtra("usuario", ultimoUsuario)
            startActivity(tela2)

            if (ultimoUsuario != null) {
                irTela2(ultimoUsuario)
            }


        }


        fun entrar(view: View) {
            val etLogin: EditText = findViewById(R.id.et_nome)
            val etSenha: EditText = findViewById(R.id.et_senha)
            val login = etLogin.text.toString()

            if (login.isNotEmpty() && etSenha.text.isNotEmpty()) {

                val editor = preferencias.edit()
                editor.putString("usuario", login)
                editor.commit()

                irTela2(login)
            } else {
                Toast.makeText(this, "Falha de autenticação!", Toast.LENGTH_SHORT).show()
            }

            val layoutLista: LinearLayout = findViewById(R.id.layout_lista)


            val apiFilmes = ConexãoApiCachorro.criar()


            apiFilmes.get().enqueue(object : Callback<List<Cachorro>> {


                override fun onResponse(
                    call: Call<List<Cachorro>>,
                    response: Response<List<Cachorro>>
                ) {


                    response.body()?.forEach {


                        val tvCachorro = TextView(baseContext)
                        tvCachorro.text = "id: ${it.id} - Tipo: ${it.nome} "
                        tvCachorro.setTextColor(Color.parseColor("#9911AA"))


                        layoutLista.addView(tvCachorro)
                    }
                }


                override fun onFailure(call: Call<List<Cachorro>>, t: Throwable) {
                    Toast.makeText(
                        baseContext,
                        "Erro na chamada: ${t.message!!}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        }

        fun consultarFilme(view: View) {
            val apiCachorro = ConexãoApiCachorro.criar()

            val tvConsulta: TextView = findViewById(R.id.tv_consulta)
            val etId: EditText = findViewById(R.id.et_id)
            val id = etId.text.toString().toInt()

            apiCachorro.get(id).enqueue(object : Callback<Cachorro> {

                override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {


                    if (response.code() == 404) {
                        tvConsulta.text = "Cachorro $id não encontrado!"
                    } else {
                        val cachorro = response.body()!!
                        tvConsulta.text =
                            "Cachorro:  ${cachorro.id}  Tipo: ${cachorro.nome} Indicado para crianças: ${cachorro.indicadoCrianca}"

                    }
                }

                override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                    Toast.makeText(
                        baseContext,
                        "Erro na chamada: ${t.message!!}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        }


    }

}




