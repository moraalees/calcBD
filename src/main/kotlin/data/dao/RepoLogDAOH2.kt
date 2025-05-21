package es.prog2425.calcBD.data.dao

import es.prog2425.calcBD.model.Calculo
import es.prog2425.calcBD.model.Operador
import javax.sql.DataSource

class RepoLogDAOH2(private val ds: DataSource) : IRepoLogDAO {

    override fun getAll(): List<Calculo> {
        val lista = mutableListOf<Calculo>()
        val sql = "SELECT numero1, numero2, operador, resultado FROM Operaciones ORDER BY fecha DESC"

        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val num1 = rs.getDouble("numero1")
                val num2 = rs.getDouble("numero2")
                val op = rs.getString("operador").firstOrNull()
                val operador = Operador.getOperador(op) ?: continue
                val resultado = rs.getDouble("resultado")

                lista.add(Calculo(num1, num2, operador, resultado))
            }
        }
        return lista
    }

    override fun getInfoUltimoLog(): List<String> {
        val lista = mutableListOf<String>()
        val sql = "SELECT fecha, numero1, operador, numero2, resultado FROM Operaciones ORDER BY fecha DESC LIMIT 5"

        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val linea = "${rs.getTimestamp("fecha")} -> ${rs.getDouble("numero1")} ${rs.getString("operador")} ${rs.getDouble("numero2")} = ${rs.getDouble("resultado")}"
                lista.add(linea)
            }
        }
        return lista
    }

    override fun registrarEntradaLog(info: Any) {
        val sql = "INSERT INTO Operaciones (numero1, operador, numero2, resultado) VALUES (?, ?, ?, ?)"
        ds.connection.use { conn ->
            if (info is Calculo) {
                val stmt = conn.prepareStatement(sql)
                stmt.setDouble(1, info.numero1)
                stmt.setString(2, info.operador.simboloUi.toString().take(1))
                stmt.setDouble(3, info.numero2)
                stmt.setDouble(4, info.resultado)
                stmt.executeUpdate()
            } else if (info is String) {
                val sqlError = "INSERT INTO Errores (mensaje) VALUES (?)"
                val stmt = conn.prepareStatement(sqlError)
                stmt.setString(1, info)
                stmt.executeUpdate()
            }
        }
    }
}