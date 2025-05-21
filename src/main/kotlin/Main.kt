package es.prog2425.calcBD

import es.prog2425.calcBD.app.Controlador
import es.prog2425.calcBD.data.RepoLogTxt
import es.prog2425.calcBD.service.ServicioCalc
import es.prog2425.calcBD.service.ServicioLog
import es.prog2425.calcBD.ui.Consola
import es.prog2425.calcBD.utils.GestorFichTxt


/**
 * Punto de entrada de la aplicación.
 *
 * Inicializa los componentes necesarios de la arquitectura (UI, repositorio, servicio, lógica de negocio)
 * y delega el control al controlador principal de la aplicación.
 */
fun main(args: Array<String>) {

    val repoLog = RepoLogTxt(GestorFichTxt())
    Controlador(Consola(), ServicioCalc(), ServicioLog(repoLog)).iniciar(args)

}