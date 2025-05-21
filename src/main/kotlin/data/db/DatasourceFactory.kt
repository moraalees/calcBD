package es.prog2425.calcBD.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource

object DatasourceFactory {
    private const val JDBC_URL = "jdbc:h2:file:./data/calculadora"
    private const val USER = "cristian"
    private const val PASSWORD = ""

    /**
     * Retorna un [DataSource] según el modo escogido.
     *
     * @param mode El modo de conexión a utilizar (Hikari / Simple).
     * @return Una instancia de [DataSource] configurada.
     */
    fun getDataSource(mode: Mode = Mode.HIKARI): DataSource {
        //Dependiendo el modo llama a la BD de una forma u otra
        return when (mode) {
            Mode.HIKARI -> {
                val config = HikariConfig().apply {
                    jdbcUrl = JDBC_URL
                    username = USER
                    password = PASSWORD
                    driverClassName = "org.h2.Driver"
                    maximumPoolSize = 10
                }
                HikariDataSource(config)
            }
            Mode.SIMPLE -> {
                JdbcDataSource().apply {
                    setURL(JDBC_URL)
                    user = USER
                    password = PASSWORD
                }
            }
        }
    }
}