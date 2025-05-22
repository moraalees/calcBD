package es.prog2425.calcBD.data.dao

import es.prog2425.calcBD.model.Calculo
import es.prog2425.calcBD.model.Operador
import javax.sql.DataSource

/**
 * Implementación del repositorio de logs usando una base de datos H2.
 *
 * Utiliza una conexión proporcionada por un [DataSource], idealmente gestionado con un pool de conexiones.
 *
 * @property ds Fuente de datos para la conexión con la base de datos.
 */
class RepoLogDAOH2(private val ds: DataSource) : IRepoLogDAO {

    /**
     * Obtiene todas las operaciones registradas, ordenadas por fecha descendente.
     *
     * @return Lista de objetos [Calculo] desde la base de datos.
     */
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
                val operador = Operador.getOperador(op) ?: continue // Si el operador es nulo o no coincide con ninguno de los definidos, se omite este registro
                val resultado = rs.getDouble("resultado")

                lista.add(Calculo(num1, num2, operador, resultado))
            }
        }
        return lista
    }

    /**
     * Obtiene información formateada de las últimas operaciones registradas.
     *
     * @param limit Número máximo de entradas a recuperar.
     * @return Lista de String que contiene las operaciones con su fecha y datos.
     */
    override fun getInfoUltimoLog(limit: Int): List<String> {
        val lista = mutableListOf<String>()
        val sql = "SELECT fecha, numero1, operador, numero2, resultado FROM Operaciones ORDER BY fecha DESC LIMIT ?"

        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.setInt(1, limit)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val linea = "${rs.getTimestamp("fecha")} -> ${rs.getDouble("numero1")} ${rs.getString("operador")} ${rs.getDouble("numero2")} = ${rs.getDouble("resultado")}"
                lista.add(linea)
            }
        }
        return lista
    }

    /**
     * Registra una entrada en el log.
     *
     * - Si [info] es un [Calculo], lo inserta en la tabla Operaciones.
     * - Si [info] es un [String], lo registra como mensaje en la tabla Errores.
     * - Si [info] es cualquier otra cosa no hace nada.
     *
     * @param info Objeto a registrar (operación o mensaje de error).
     */
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

    /**
     * Obtiene los errores más recientes registrados en la base de datos.
     *
     * @param limit Cantidad máxima de errores a recuperar.
     * @return Lista de String que contienen fecha y mensaje del error.
     */
    override fun getUltimosErrores(limit: Int): List<String> {
        val lista = mutableListOf<String>()
        val sql = "SELECT fecha, mensaje FROM Errores ORDER BY fecha DESC LIMIT ?"

        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.setInt(1, limit)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val linea = "${rs.getTimestamp("fecha")} ---> ${rs.getString("mensaje")}"
                lista.add(linea)
            }
        }
        return lista
    }

    /**
     * Elimina todos los campos de la tabla Errores.
     */
    override fun borrarErrores() {
        val sql = "DELETE FROM Errores"
        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.executeUpdate()
        }
    }

    /**
     * Elimina todos los campos de la tabla Operaciones.
     */
    override fun borrarOperaciones() {
        val sql = "DELETE FROM Operaciones"
        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.executeUpdate()
        }
    }
}