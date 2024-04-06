package com.example.prova01pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity()
{
    lateinit var CPFEditText: EditText
    lateinit var NomeEditText: EditText
    lateinit var TelEditText: EditText
    lateinit var listView: ListView
    lateinit var Deletarbutton:Button
    lateinit var Inserirbutton:Button
    lateinit var Atualizarbutton:Button
    lateinit var Backupbutton: Button
    lateinit var Pedidobutton:Button
    private lateinit var crud: CRUD

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crud = CRUD(this)

        Deletarbutton = findViewById(R.id.Deletarbutton)
        Inserirbutton = findViewById(R.id.Inserirbutton)
        Atualizarbutton = findViewById(R.id.Atualizarbutton)
        Pedidobutton = findViewById(R.id.Pedidobutton)
        Backupbutton = findViewById(R.id.Backupbutton)
        CPFEditText = findViewById(R.id.CPFEditText)
        NomeEditText = findViewById(R.id.NomeEditText)
        TelEditText = findViewById(R.id.TelEditText)
        listView = findViewById(R.id.listView)

        //Função para navegar entre telas
        fun irParaSegundaTela(cpf:String){
            val segundaTela = Intent(this,SegundaTela::class.java)
            segundaTela.putExtra("fk_cpf",cpf)
            startActivity(segundaTela)
        }


        //FAZER FUNCIONALIDADE DO BOTÃO INSERIR
        Inserirbutton.setOnClickListener {
            val cpf = CPFEditText.text.toString()
            val nome = NomeEditText.text.toString()
            val telefone = TelEditText.text.toString()

            if (cpf.isNotEmpty() && nome.isNotEmpty() && telefone.isNotEmpty()) {
                val cliente = Cliente(cpf, nome, telefone)

                crud.criarCliente(cpf,nome,telefone)
                //MOSTRAR A LISTA DE CLIENTES ADICIONADOS LOGO APÓS INSERIR UM CLIENTE
                crud.listarDados(this,listView)

            } else {
                // Exiba uma mensagem de erro se algum campo estiver vazio
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            }
            CPFEditText.text.clear()
            NomeEditText.text.clear()
            TelEditText.text.clear()

            //BOTÃO PARA PROSSEGUIR PARA O PEDIDO
            Pedidobutton.setOnClickListener {
                irParaSegundaTela(cpf)
            }
        }

        //FAZER FUNCIONALIDADE DO BOTÃO DE BACKUP
        Backupbutton.setOnClickListener(){
            crud.backup(this)
        }

        //FAZER FUNCIONALIDADE DO BOTÃO DELETAR
        Deletarbutton.setOnClickListener {
            val cpf = CPFEditText.text.toString()
            if (cpf.isNotEmpty()) {
                crud.deletarCliente(cpf)
                crud.listarDados(this, listView)
            } else {
                Toast.makeText(this, "Por favor, insira o CPF do cliente a ser deletado", Toast.LENGTH_SHORT).show()
            }
        }

        //FAZER FUNCIONALIDADE DO BOTÃO ATUALIZAR
        Atualizarbutton.setOnClickListener {
            val cpf = CPFEditText.text.toString()
            val nome = NomeEditText.text.toString()
            val telefone = TelEditText.text.toString()

            if (cpf.isNotEmpty() && nome.isNotEmpty() && telefone.isNotEmpty()) {
                crud.atualizarCliente(this, cpf, nome, telefone)
                crud.listarDados(this, listView)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }


    }
}


