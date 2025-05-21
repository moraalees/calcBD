package es.prog2425.calcBD

import es.prog2425.calcBD.app.Controlador
import es.prog2425.calcBD.data.dao.RepoLogDAOH2
import es.prog2425.calcBD.data.db.DatasourceFactory
import es.prog2425.calcBD.data.db.Mode
import es.prog2425.calcBD.service.ServicioCalc
import es.prog2425.calcBD.service.ServicioLogDAO
import es.prog2425.calcBD.ui.Consola

//Menu de mirar logs
//menu de usar o el programa de diego o con bd

/**
 * Punto de entrada de la aplicación.
 *
 * Inicializa los componentes necesarios de la arquitectura (UI, repositorio, servicio, lógica de negocio)
 * y delega el control al controlador principal de la aplicación.
 */
fun main(args: Array<String>) {
    val ds = DatasourceFactory.getDataSource(Mode.HIKARI)

    val repoLogDAO = RepoLogDAOH2(ds)                  // DAO que usa H2
    val servicioLog = ServicioLogDAO(repoLogDAO)

    Controlador(Consola(), ServicioCalc(), servicioLog).iniciar(args)

}