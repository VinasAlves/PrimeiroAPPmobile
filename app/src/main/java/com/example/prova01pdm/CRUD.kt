package com.example.prova01pdm

import BancoIFTM
import android.R
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CRUD(private val context: Context) {

    fun criarCliente(cpf: String, nome: String, telefone: String) {
        val meuBanco = BancoIFTM(context)
        val db = meuBanco.writableDatabase

        val values = ContentValues().apply {
            put("cpf", cpf)
            put("nome", nome)
            put("telefone", telefone)
        }

        val newRowId = db.insert("Cliente", null, values)

        if (newRowId == -1L) {
            Log.e("CriarCliente", "Falha ao inserir cliente no banco de dados.")
        } else {
            Log.i("CriarCliente", "Cliente inserido com sucesso. ID: $newRowId")
        }

        db.close()
    }

    //MOSTRAR CLIENTES
    fun listarDados(context: Context, listView: ListView) {
        try {
            val linhas = ArrayList<String>()
            val meuAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, linhas)
            listView.adapter = meuAdapter

            val meuBanco = BancoIFTM(context)
            val db = meuBanco.readableDatabase

            val cursor = db.rawQuery("SELECT * FROM Cliente", null)

            while (cursor.moveToNext()) {
                val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))

                val cliente = "CPF: $cpf\nNome: $nome\nTelefone: $telefone\n"
                linhas.add(cliente)
            }

            cursor.close()
            db.close()
            meuAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun backupClientes(context: Context) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.readableDatabase

            val cursor: Cursor = db.rawQuery("SELECT * FROM Cliente", null)

            val diretorioInterno = context.filesDir
            val arquivoTxt = File(diretorioInterno, "clientes_backup.txt")


            val outputStream = FileOutputStream(arquivoTxt)
            val writer = OutputStreamWriter(outputStream) 

            while (cursor.moveToNext()) {
                val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))

                val cliente = "CPF: $cpf\nNome: $nome\nTelefone: $telefone\n"

                writer.write(cliente)
            }

            writer.close()
            outputStream.close()
            cursor.close()

            Log.e("BackupClientes", "Caminho do arquivo de backup: ${arquivoTxt.absolutePath}")
            Toast.makeText(context, "Backup dos clientes realizado com sucesso!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao realizar o backup dos clientes: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //DELETAR CLIENTES
    fun deletarCliente(cpf: String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val resultado = db.delete("Cliente", "cpf = ?", arrayOf(cpf))

            if (resultado != -1) {
                Log.i("DeletarCliente", "Cliente deletado com sucesso")
            } else {
                Log.e("DeletarCliente", "Falha ao deletar cliente")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("DeletarCliente", "Erro ao deletar cliente: ${e.message}")
        }
    }
    //ATUALIZAR CLIENTES
    fun atualizarCliente(context: Context, cpf: String, novoNome: String, novoTelefone: String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val valores = ContentValues().apply {
                put("nome", novoNome)
                put("telefone", novoTelefone)
            }

            db.update("Cliente", valores, "cpf = ?", arrayOf(cpf))

            Toast.makeText(context, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao atualizar o cliente: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //CRUD PARA OS PEDIDOS///////////////////////////////////////////////////////////////////////////////////////////////////
    //INSERIR
    fun criarPedido(tipoChocolate: String, tipoCastanha: String, porcentagemLeite: String, fk_cpf:String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val values = ContentValues().apply {
                put("tipoDeChocolate", tipoChocolate)
                put("tipoDeCastanha", tipoCastanha)
                put("porcentagemLeite", porcentagemLeite)
                put("fk_cpf", fk_cpf)
            }

            val resultado = db.insert("Pedido", null, values)

            if (resultado == -1L) {
                Log.e("CRUD", "Erro ao inserir pedido no banco de dados")
            } else {
                Log.d("CRUD", "Pedido inserido com sucesso. ID: $resultado")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("CRUD", "Erro ao inserir pedido no banco de dados: ${e.message}")
        }
    }


    //MOSTRAR PEDIDOS
    fun listarPedidos(context: Context, listView: ListView) {
        try {
            val linhas = ArrayList<String>()
            val meuAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, linhas)
            listView.adapter = meuAdapter

            val meuBanco = BancoIFTM(context)
            val db = meuBanco.readableDatabase

            val cursor = db.rawQuery("SELECT * FROM Pedido", null)

            while (cursor.moveToNext()) {
                val IDChocolate = cursor.getString(cursor.getColumnIndexOrThrow("IDChocolate"))
                val fk_cpf = cursor.getString(cursor.getColumnIndexOrThrow("fk_cpf"))
                val tipoChocolate = cursor.getString(cursor.getColumnIndexOrThrow("tipoDeChocolate"))
                val tipoCastanha = cursor.getString(cursor.getColumnIndexOrThrow("tipoDeCastanha"))
                val porcentagemLeite = cursor.getString(cursor.getColumnIndexOrThrow("porcentagemLeite"))

                val pedido = "ID Pedido : $IDChocolate \nID do cliente : $fk_cpf \nTipo do Chocolate: $tipoChocolate\nTipo da Castanha: $tipoCastanha\nPorcentagem do Leite: $porcentagemLeite\n"
                linhas.add(pedido)
            }
            cursor.close()
            db.close()
            meuAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deletarPedido(id:String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val resultado = db.delete("Pedido", "IDChocolate = ?", arrayOf(id))

            if (resultado != -1) {
                Log.i("DeletarPedido", "Pedido deletado com sucesso")
            } else {
                Log.e("DeletarPedido", "Falha ao deletar pedido")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("DeletarPedido", "Erro ao deletar pedido: ${e.message}")
        }
    }

    //ATUALIZAR CLIENTES
    fun atualizarPedido(context: Context, id:String, novotipoChoc: String, novotipoCast: String, novaPorcentagem: String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val valores = ContentValues().apply {
                put("tipoDeChocolate", novotipoChoc)
                put("tipoDeCastanha", novotipoCast)
                put("porcentagemLeite", novaPorcentagem)
            }

            db.update("Pedido", valores, "IDChocolate = ?", arrayOf(id))

            Toast.makeText(context, "Pedido atualizado com sucesso!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao atualizar o pedido: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



}
