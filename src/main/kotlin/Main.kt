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


/**
 * Punto de entrada de la aplicación.
 *
 * Inicializa los componentes necesarios de la arquitectura:
 * - Fuente de datos para acceso a base de datos H2, en el caso dado, HikariCP.
 * - Repositorios de logs (en base de datos y archivo de texto).
 * - Servicios de cálculo y de gestión de logs.
 * - Interfaces de usuario por consola.
 *
 * Finalmente, llama a ProgramaManager, que comienza la ejecución
 * principal según los argumentos.
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