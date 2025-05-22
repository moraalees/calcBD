package es.prog2425.calcBD.service

import es.prog2425.calcBD.model.Calculo

interface IServicioLogDAO {
    fun obtenerTodos(): List<Calculo>
    fun obtenerInfoUltimoLog(limit: Int): List<String>
    fun registrarEntradaLog(info: Any)
    fun obtenerUltimosErrores(limit: Int): List<String>
}