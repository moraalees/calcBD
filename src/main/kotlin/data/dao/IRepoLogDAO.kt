package es.prog2425.calcBD.data.dao

import es.prog2425.calcBD.model.Calculo

interface IRepoLogDAO {
    fun getAll(): List<Calculo>
    fun getInfoUltimoLog(): List<String>
    fun registrarEntradaLog(info: Any)
}