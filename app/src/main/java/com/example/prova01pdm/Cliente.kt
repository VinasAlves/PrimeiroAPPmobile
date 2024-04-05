package com.example.prova01pdm

class Cliente(var cpf : String, var nome : String, var telefone : String) {
    init{
        this.nome = nome
        this.cpf = cpf
        this.telefone = telefone
    }
}