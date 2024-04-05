import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.ArrayAdapter
import android.widget.ListView

class BancoIFTM(context: Context) : SQLiteOpenHelper(context, "MeubancoIFTM", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val nomeTabela = "Cliente"
        val cpf = "cpf"
        val nome = "nome"
        val telefone = "telefone"
        val SQL_criacao =
            "CREATE TABLE ${nomeTabela} (" +
                    "${cpf} INTEGER PRIMARY KEY," +
                    "${nome} TEXT," +
                    "${telefone} TEXT)"
        db.execSQL(SQL_criacao)

        //CRIAR TABELA PARA OS PEDIDOS
        val nomeTabelaPedido = "Pedido"
        val IDChocolate = "IDChocolate"
        val tipoDeChocolate = "tipoDeChocolate"
        val tipoDeCastanha = "tipoDeCastanha"
        val porcentagemLeite = "porcentagemLeite"
        val fk_cpf = "fk_cpf"

        val SQL_Pedido =
            "CREATE TABLE $nomeTabelaPedido (" +
                    "$IDChocolate INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$tipoDeChocolate TEXT," +
                    "$tipoDeCastanha TEXT," +
                    "$porcentagemLeite TEXT," +
                    "$fk_cpf TEXT," +
                    "FOREIGN KEY($fk_cpf) REFERENCES Cliente(cpf))" //Chave Estrangeira

        db.execSQL(SQL_Pedido)

    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAntiga: Int, novaVersao: Int) {
        val SQL_exclusao = "DROP TABLE IF EXISTS Cliente"
        db.execSQL(SQL_exclusao)

        val SQL_exclusao_Pedido = "DROP TABLE IF EXISTS Pedido"
        db.execSQL(SQL_exclusao_Pedido)
        onCreate(db)
    }


}
