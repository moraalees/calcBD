package es.prog2425.calcBD.service

import es.prog2425.calcBD.model.Calculo

interface IServicioLog {
    fun registrarEntradaLog(mensaje: String)
    fun registrarEntradaLog(calculo: Calculo)
    fun getInfoUltimoLog(): List<String>
    fun crearNuevoLog()
    fun crearRutaLog(ruta: String): Boolean
}