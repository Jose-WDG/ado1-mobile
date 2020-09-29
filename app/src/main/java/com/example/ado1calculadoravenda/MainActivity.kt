package com.example.ado1calculadoravenda

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var nomeProduto: EditText
    lateinit var precoVenda: EditText
    lateinit var precoCusto: EditText
    lateinit var btnCalcular: Button
    lateinit var btnSalvar: Button
    lateinit var btnRecuperar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListeners()
    }

    private fun initListeners() {
        val sharedPreferences = getSharedPreferences("calculadora", Context.MODE_PRIVATE)

        btnCalcular.setOnClickListener { v ->
            val valorPrecoCusto = precoCusto.text.toString().toFloat()
            val valorPrecoVenda = precoVenda.text.toString().toFloat()

            if (valorPrecoCusto < valorPrecoVenda) {
                Toast.makeText(
                    this,
                    "Lucro: " + (valorPrecoVenda - valorPrecoCusto),
                    Toast.LENGTH_LONG
                ).show()
            } else if (valorPrecoCusto == valorPrecoVenda) {
                Toast.makeText(
                    this,
                    "Sem lucro e Sem prejuízo ",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Prejuizo: " + (valorPrecoCusto - valorPrecoVenda),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnSalvar.setOnClickListener { v ->
            val nomeProdutoTexto = nomeProduto.text.toString()
            val valorPrecoCusto = precoCusto.text.toString()
            val valorPrecoVenda = precoVenda.text.toString()

            sharedPreferences.edit().putString(nomeProdutoTexto, "$valorPrecoCusto;$valorPrecoVenda").apply()
            Toast.makeText(this, "Produto Salvo", Toast.LENGTH_SHORT).show()

            nomeProduto.setText("")
            precoCusto.setText("")
            precoVenda.setText("")
        }

        btnRecuperar.setOnClickListener { v ->
            val nomeProdutoTexto = nomeProduto.text.toString()
            val result = sharedPreferences.getString(nomeProdutoTexto, "")

            if (result!!.isNotEmpty()) {
                val lista = result.split(";")
                val precoCustoLabel = lista.get(0)
                val precoVendaLabel = lista.get(1)

                precoCusto.setText(precoCustoLabel)
                precoVenda.setText(precoVendaLabel)


                Toast.makeText(this, "Produto Encontrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Produto Não Encontrado", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initViews() {
        nomeProduto = findViewById(R.id.txtNomeProduto)
        precoVenda = findViewById(R.id.txtPrecoVenda)
        precoCusto = findViewById(R.id.txtPrecoCusto)
        btnCalcular = findViewById(R.id.btCalcular)
        btnSalvar = findViewById(R.id.btSalvar)
        btnRecuperar = findViewById(R.id.btRecuperar)
    }
}