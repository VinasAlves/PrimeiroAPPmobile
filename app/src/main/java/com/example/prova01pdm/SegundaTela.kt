package com.example.prova01pdm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SegundaTela : AppCompatActivity() {

    lateinit var et_chocolate: EditText
    lateinit var et_castanha: EditText
    lateinit var et_porcentagem: EditText
    lateinit var Finalizarbutton: Button
    lateinit var bt_delete: Button
    lateinit var bt_atualizar: Button
    lateinit var id_text: EditText
    lateinit var lv_pedido: ListView
    private lateinit var crud: CRUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_segunda_tela)
        crud = CRUD(this)

        val fk_cpf = intent.getStringExtra("fk_cpf")

        et_chocolate = findViewById(R.id.et_chocolate)
        et_castanha = findViewById(R.id.et_castanha)
        et_porcentagem = findViewById(R.id.et_porcentagem)
        Finalizarbutton = findViewById(R.id.Finalizarbutton)
        lv_pedido = findViewById(R.id.lv_pedido)
        bt_delete = findViewById(R.id.bt_delete)
        bt_atualizar = findViewById(R.id.bt_atualizar)
        id_text = findViewById(R.id.id_text)


        //FAZER FUNCIONALIDADE DO BOTÃO INSERIR
        Finalizarbutton.setOnClickListener {
            val tipoChocolate = et_chocolate.text.toString()
            val tipoCastanha = et_castanha.text.toString()
            val porcentagemLeite = et_porcentagem.text.toString()

            if (tipoChocolate.isNotEmpty() && tipoCastanha.isNotEmpty() && porcentagemLeite.isNotEmpty()) {
                    // Chamar o método de inserção de pedido no banco de dados, passando também o CPF do cliente
                crud.criarPedido(tipoChocolate, tipoCastanha, porcentagemLeite,fk_cpf!!)
                //MOSTRAR A LISTA DE PEDIDOS ADICIONADOS LOGO APÓS INSERIR UM PEDIDO
                crud.listarPedidos(this, lv_pedido)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            et_porcentagem.text.clear()
            et_castanha.text.clear()
            et_porcentagem.text.clear()
        }

        //FAZER FUNCIONALIDADE DO BOTÃO DELETAR
        bt_delete.setOnClickListener {
            val id = id_text.text.toString()
            if(id.isNotEmpty()){
                crud.deletarPedido(id)
                crud.listarPedidos(this,lv_pedido)
            }else{
                Toast.makeText(this, "Por favor, preencha o campo ID para deletar.",Toast.LENGTH_SHORT).show()
            }
            id_text.text.clear()
            et_porcentagem.text.clear()
            et_castanha.text.clear()
            et_porcentagem.text.clear()
        }
        //FAZER FUNCIONALIDADE DO BOTÃO ATUALIZAR
        bt_atualizar.setOnClickListener {
            val id = id_text.text.toString()
            val tipoChocolate = et_chocolate.text.toString()
            val tipoCastanha = et_castanha.text.toString()
            val porcentagemLeite = et_porcentagem.text.toString()

            if (id.isNotEmpty() && tipoChocolate.isNotEmpty() && tipoCastanha.isNotEmpty() && porcentagemLeite.isNotEmpty()){
                crud.atualizarPedido(this, id, tipoChocolate, tipoCastanha, porcentagemLeite)
                crud.listarPedidos(this,lv_pedido)
            }else{
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            id_text.text.clear()
            et_porcentagem.text.clear()
            et_castanha.text.clear()
            et_porcentagem.text.clear()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}