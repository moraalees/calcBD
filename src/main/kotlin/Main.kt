package es.prog2425.calcBD

import es.prog2425.calcBD.app.BDManager
import es.prog2425.calcBD.app.Controlador
import es.prog2425.calcBD.app.ProgramaManager
import es.prog2425.calcBD.data.RepoLogTxt
import es.prog2425.calcBD.data.dao.RepoLogDAOH2
import es.prog2425.calcBD.data.db.DatasourceFactory
import es.prog2425.calcBD.data.db.Mode
import es.prog2425.calcBD.service.ServicioCalc
import es.prog2425.calcBD.service.ServicioLog
import es.prog2425.calcBD.service.ServicioLogDAO
import es.prog2425.calcBD.ui.Consola
import es.prog2425.calcBD.utils.GestorFichTxt


//Controlar errores de las conexiones como en las ramas

/**
 * Punto de entrada de la aplicación.
 *
 * Inicializa los componentes necesarios de la arquitectura (UI, repositorio, servicio, lógica de negocio)
 * y delega el control al controlador principal de la aplicación.
 */
fun main(args: Array<String>) {
    val ds = DatasourceFactory.getDataSource(Mode.HIKARI)

    val repoLogDAOH2 = RepoLogDAOH2(ds)
    val servicioLog = ServicioLogDAO(repoLogDAOH2)
    val repoLog = RepoLogTxt(GestorFichTxt())

    ProgramaManager(Consola(),
        Controlador(Consola(), ServicioCalc(), ServicioLog(repoLog)),
        BDManager(Consola(), ServicioCalc(), servicioLog),
        args).programa()

}