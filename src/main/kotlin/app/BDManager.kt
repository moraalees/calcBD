package es.prog2425.calcBD.app

import es.prog2425.calcBD.service.ServicioCalc
import es.prog2425.calcBD.service.IServicioLogDAO
import es.prog2425.calcBD.ui.IEntradaSalida

class BDManager(
    private val ui: IEntradaSalida,
    private val calculadora: ServicioCalc,
    private val servicio: IServicioLogDAO
) {

    fun programaBd() {

    }

    fun mostrarMenu(){

    }

}