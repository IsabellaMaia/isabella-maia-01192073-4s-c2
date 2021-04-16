package com.example.isabella_maia_4s_c2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Tela2 : AppCompatActivity() {

    lateinit var tvTotal: TextView
    var total = 0.0

    lateinit var indicandoCriancas: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        apiCachorro.get().enqueue(
            object : Callback<List<Cachorro>> {

                override fun onResponse(
                    call: Call<List<Cachorro>>,
                    response: Response<List<Cachorro>>
                ) {

                    response.body()?.forEach {

                        val tvCachorro = TextView(baseContext)
                        tvCachorro.text = "Id: ${it.id} - Nome: ${it.nome} - Indicado Crianca ${it.indicadoCrianca} "
                        tvCachorro.setTextColor(Color.parseColor("#E91E63"))

                        layoutLista.addView(tvCachorro)
                    }

                }

                override fun onFailure(call: Call<List<Cachorro>>, t: Throwable) {
                    Toast.makeText(
                        baseContext,
                        "Erro na chamada: ${t.message!!}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }


            })





        indicandoCriancas = findViewById(R.id.indicandoCriancas)

    }


    fun consultarCachorro(view: View){
        val apiCachorro = ConexãoApiCachorro.criar()
        val tvConsulta:TextView = findViewById(R.id.tv_consulta)
        val etId: EditText = findViewById(R.id.et_id)
        val id = etId.text.toString().toInt()

        apiCachorro.get(id).enqueue(object : Callback<Cachorro> {
            override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {
                // .code() -> retorna o Status HTTP da resposta
                if (response.code() == 404) {
                    tvConsulta.text = "Cachorro $id não encontrado!"
                } else {
                    val cachorro = response.body()!!
                    tvConsulta.text = "Cachorro ${cachorro.id}: ${cachorro.nome} : ${cachorro.indicadoCrianca} Cão cadastrado com sucesso"


                }
            }

            override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                Toast.makeText(baseContext, "Erro na chamada: ${t.message!!}", Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun cadastrando(view: View) {

        if (indicandoCriancas.isChecked) {
            return
        }

    }

        fun cadastrarDog(view: View) {
        val tvTitulo: TextView = findViewById(R.id.tv_titulo)
        val nomeDono = intent.getStringExtra("usuario")
        val nomeDog = intent.getStringExtra("dog")
        val senha = intent.getStringExtra("senha")

        val mensagem = "Usuário $nomeDono cadastrou seu Dog $nomeDog"

        tvTitulo.text = mensagem

    }


    val layoutLista: LinearLayout = findViewById(R.id.layout_lista)

    val apiCachorro = ConexãoApiCachorro.criar()


}