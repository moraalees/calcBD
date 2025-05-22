package es.prog2425.calcBD.service

import es.prog2425.calcBD.data.dao.IRepoLogDAO
import es.prog2425.calcBD.model.Calculo
import es.prog2425.calcBD.model.Operador

class ServicioLogDAO(private val dao: IRepoLogDAO): IServicioLogDAO {
    override fun obtenerTodos(): List<Calculo> = dao.getAll()

    override fun obtenerInfoUltimoLog(limit: Int): List<String>{
        require(limit > 0){ "El número de columnas a obtener debe ser mayor que 0." }
        return dao.getInfoUltimoLog(limit)
    }

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
                // No lanzar excepción, pero manejar el error de una manera más controlada
                dao.registrarEntradaLog("Tipo no soportado para registrar en log: ${info::class.simpleName}")
            }
        }
    }

    override fun obtenerUltimosErrores(limit: Int): List<String> {
        require(limit > 0){ "Debes elegir más de 1 dato a revisar." }
        return dao.getUltimosErrores(limit)
    }

    override fun deleteAllErrores() = dao.borrarErrores()

    override fun deleteAllOperaciones() = dao.borrarOperaciones()
}