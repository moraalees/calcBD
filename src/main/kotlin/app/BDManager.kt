package es.prog2425.calcBD.app

import es.prog2425.calcBD.model.Operador
import es.prog2425.calcBD.service.ServicioCalc
import es.prog2425.calcBD.service.IServicioLogDAO
import es.prog2425.calcBD.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase encargada de gestionar el menú de operaciones relacionadas con la base de datos.
 *
 * @property ui Interfaz de entrada y salida con el usuario.
 * @property calculadora Servicio encargado de realizar cálculos matemáticos.
 * @property servicioLog Servicio encargado de registrar y recuperar datos desde una base de datos.
 */
class BDManager(
    private val ui: IEntradaSalida,
    private val calculadora: ServicioCalc,
    private val servicioLog: IServicioLogDAO
) {

    /**
     * Función principal del menú de base de datos.
     *
     * Ejecuta un bucle que muestra opciones al usuario y responde según la entrada .
     */
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
                "4", "borra1" -> borrarTablaOperaciones()
                "5", "borra2" -> borrarTablaErrores()
                "6", "salir" -> salir = salirPrograma()
                else -> ui.mostrarError("Opción inválida")
            }
        }
    }

    /**
     * Muestra el menú de opciones disponibles para las operaciones con base de datos.
     */
    private fun mostrarMenu(){
        ui.mostrar("""
            ----MENÚ BD----
            1. Listar operaciones pasadas (historial)
            2. Hacer operación (operar)
            3. Consultar errores (error)
            4. Borrar Operaciones (borra1)
            5. Borrar Errores (borra2)
            6. Salir
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

    /**
     * Pregunta al usuario cuántas operaciones desea consultar y muestra los registros más recientes.
     *
     * Maneja errores comunes como argumentos inválidos o fallos de base de datos.
     */
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
                ui.mostrarError("${e.message}")
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("${e.message}")
            }
        }
    }

    /**
     * Solicita al usuario cuántos errores desea consultar y muestra los mensajes más recientes.
     *
     * Maneja excepciones para asegurar que el programa no se termine.
     */
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
                ui.mostrarError("${e.message}")
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("${e.message}")
            }
        }
    }

    /**
     * Solicita al usuario los datos necesarios para realizar una operación matemática.
     *
     * Maneja excepciones para asegurar que el programa no se termine.
     */
    private fun operar(){
        do {
            try {
                val numero1 = ui.pedirDouble("Introduce el primer número: ") ?: throw InfoCalcException("Número no válido")
                val simbolo = ui.pedirInfo("Introduce el operador (+, -, x, /): ").firstOrNull()
                val operador = Operador.getOperador(simbolo) ?: throw InfoCalcException("Operador no válido")
                val numero2 = ui.pedirDouble("Introduce el segundo número: ") ?: throw InfoCalcException("Número no válido")

                if (operador == Operador.DIVISION && numero2 == 0.0) {
                    val error = "No se puede dividir por 0..."
                    ui.mostrarError(error)
                    servicioLog.registrarEntradaLog(error)
                } else {
                    val resultado = calculadora.realizarCalculo(numero1, operador, numero2)
                    ui.mostrar("Resultado: $resultado")
                    servicioLog.registrarEntradaLog(resultado)
                }
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

    /**
     * Solicita confirmación al usuario y elimina todos los errores registrados en la base de datos.
     *
     * Muestra mensajes según el resultado de la operación.
     */
    private fun borrarTablaErrores(){
        val decision = ui.preguntar("¿Seguro que desa eliminar todo error registrado? ")
        if (decision){
            try {
                servicioLog.deleteAllErrores()
                ui.mostrar("Errores eliminados.")
            } catch (e: SQLException) {
                ui.mostrarError("${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("${e.message}")
            }
        }
    }

    /**
     * Solicita confirmación al usuario y elimina todas las operaciones almacenadas en la base de datos.
     *
     * Muestra mensajes según el resultado de la operación.
     */
    private fun borrarTablaOperaciones(){
        val decision = ui.preguntar("¿Seguro que desa eliminar toda operación registrada? ")
        if (decision){
            try {
                servicioLog.deleteAllOperaciones()
                ui.mostrar("Operaciones eliminadas.")
            } catch (e: SQLException) {
                ui.mostrarError("${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("${e.message}")
            }
        }
    }
}