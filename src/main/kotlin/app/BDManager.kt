package es.prog2425.calcBD.app

import es.prog2425.calcBD.model.Operador
import es.prog2425.calcBD.service.ServicioCalc
import es.prog2425.calcBD.service.IServicioLogDAO
import es.prog2425.calcBD.ui.IEntradaSalida
import java.sql.SQLException

class BDManager(
    private val ui: IEntradaSalida,
    private val calculadora: ServicioCalc,
    private val servicioLog: IServicioLogDAO
) {

    fun programaBd() {
        var salir = false
        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.pedirInfo("Elige una opción (o escribe): ")
            when (entrada){
                "1", "historial" -> mostrarUltimosLogs()
                "2", "operar" -> operar()
                "3", "error" -> mostrarUltimosErrores()
                "4", "salir" -> salir = salirPrograma()
            }
        }
    }

    private fun mostrarMenu(){
        ui.mostrar("""
            ----MENÚ BD----
            1. Listar operaciones pasadas (historial)
            2. Hacer operación (operar)
            3. Consultar errores (error)
            4. Salir
        """.trimIndent())
    }

    /**
     * Marca la bandera de salida y muestra un mensaje de cierre del programa.
     */
    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del programa...")
        return true
    }

    private fun mostrarUltimosLogs() {
        val num = ui.pedirEntero("Ingrese el número de errores que desa consultar: ")
        if (num != null){
            try {
                val logs = servicioLog.obtenerInfoUltimoLog(num)
                if (logs.isEmpty()) {
                    ui.mostrar("No hay registros anteriores en la base de datos.")
                } else {
                    ui.mostrar("Últimos registros en la base de datos:")
                    logs.forEach { ui.mostrar(it) }
                }
            } catch (e: SQLException) {
                ui.mostrarError("Error en la BD: ${e.message}")
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Hubo un error: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    private fun mostrarUltimosErrores(){
        val num = ui.pedirEntero("Ingrese el número de errores que desa consultar: ")
        if (num != null){
            try {
                val errores = servicioLog.obtenerUltimosErrores(num)
                if (errores.isEmpty()) {
                    ui.mostrar("No hay errores registrados.")
                } else {
                    ui.mostrar("Último errores:")
                    errores.forEach { ui.mostrar(it) }
                }
            } catch (e: SQLException) {
                ui.mostrarError("Error en la BD: ${e.message}")
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Hubo un error: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }


    private fun operar(){
        do {
            try {
                val numero1 = ui.pedirDouble("Introduce el primer número: ") ?: throw InfoCalcException("Número no válido")
                val simbolo = ui.pedirInfo("Introduce el operador (+, -, x, /): ").firstOrNull()
                val operador = Operador.getOperador(simbolo) ?: throw InfoCalcException("Operador no válido")
                val numero2 = ui.pedirDouble("Introduce el segundo número: ") ?: throw InfoCalcException("Número no válido")

                val resultado = calculadora.realizarCalculo(numero1, operador, numero2)
                ui.mostrar("Resultado: $resultado")
                servicioLog.registrarEntradaLog(resultado)

            } catch (e: InfoCalcException) {
                val msj = "Error al ingresar un número: ${e.message}"
                ui.mostrarError(msj)
                servicioLog.registrarEntradaLog(msj)
            } catch (e: Exception) {
                val msj = "Error inesperado: ${e.message}"
                ui.mostrarError(msj)
                servicioLog.registrarEntradaLog(msj)
            }
        } while (ui.preguntar())
    }
}