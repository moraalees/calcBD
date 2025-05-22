package es.prog2425.calcBD.service

import es.prog2425.calcBD.data.dao.IRepoLogDAO
import es.prog2425.calcBD.model.Calculo
import es.prog2425.calcBD.model.Operador

/**
 * Implementación del servicio de lógica para el manejo de consultas de BD.
 *
 * Esta clase actúa como intermediario entre la lógica de negocio y el repositorio de datos (DAO)
 * encargado de almacenar información de logs y operaciones matemáticas.
 *
 * @property dao Instancia del repositorio que implementa [IRepoLogDAO] para el acceso a datos en la BD.
 */
class ServicioLogDAO(private val dao: IRepoLogDAO): IServicioLogDAO {

    /**
     * Obtiene todas las operaciones registradas en la BD.
     *
     * @return Lista de objetos [Calculo] almacenados.
     */
    override fun obtenerTodos(): List<Calculo> = dao.getAll()

    /**
     * Obtiene información de los últimos registros de la BD.
     *
     * @param limit Número máximo de registros a devolver. Debe ser mayor que 0.
     * @return Lista de cadenas con información de log.
     * @throws IllegalArgumentException si limit es menor o igual a 0.
     */
    override fun obtenerInfoUltimoLog(limit: Int): List<String>{
        require(limit > 0){ "El número de columnas a obtener debe ser mayor que 0." }
        return dao.getInfoUltimoLog(limit)
    }

    /**
     * Registra una nueva entrada en el log.
     *
     * - Si se proporciona un [Calculo], se valida que no sea una división por cero.
     * - Si se proporciona un [String], se valida que no esté vacío o en blanco.
     * - Si se proporciona otro tipo, se registra un mensaje genérico informando que el tipo no es soportado.
     *
     * @param info Objeto a registrar en el log.
     * @throws IllegalArgumentException si se intenta dividir por cero o el mensaje está vacío.
     */
    override fun registrarEntradaLog(info: Any) {
        when (info) {
            is Calculo -> {
                require(!(info.operador == Operador.DIVISION && info.numero2 == 0.0)) { "No se puede dividir por 0..." }
                dao.registrarEntradaLog(info)
            }

            is String -> {
                require(info.isNotBlank()) { "El mensaje no puede estar vacío..." }
                dao.registrarEntradaLog(info)
            }

            else -> {
                dao.registrarEntradaLog("Tipo no soportado para registrar en log: ${info::class.simpleName}")
            }
        }
    }

    /**
     * Obtiene los últimos errores registrados en la BD.
     *
     * @param limit Número de errores a obtener. Debe ser mayor que 0.
     * @return Lista mensajes de error.
     * @throws IllegalArgumentException si [limit] es menor o igual a 0.
     */
    override fun obtenerUltimosErrores(limit: Int): List<String> {
        require(limit > 0){ "Debes elegir más de 1 dato a revisar." }
        return dao.getUltimosErrores(limit)
    }

    /**
     * Elimina todos los errores almacenados en el log.
     */
    override fun deleteAllErrores() = dao.borrarErrores()

    /**
     * Elimina todas las operaciones registradas.
     */
    override fun deleteAllOperaciones() = dao.borrarOperaciones()
}