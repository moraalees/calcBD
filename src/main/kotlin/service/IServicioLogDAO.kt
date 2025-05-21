package es.prog2425.calcBD.service

import es.prog2425.calcBD.model.Calculo

interface IServicioLogDAO {
    fun obtenerTodos(): List<Calculo>
    fun obtenerInfoUltimoLog(): List<String>
    fun registrarEntradaLog(info: Any)
}