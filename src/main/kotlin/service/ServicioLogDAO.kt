package es.prog2425.calcBD.service

import es.prog2425.calcBD.data.dao.IRepoLogDAO
import es.prog2425.calcBD.model.Calculo
import es.prog2425.calcBD.model.Operador

class ServicioLogDAO(private val dao: IRepoLogDAO): IServicioLogDAO {
    override fun obtenerTodos(): List<Calculo> = dao.getAll()

    override fun obtenerInfoUltimoLog(): List<String> = dao.getInfoUltimoLog()

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
}