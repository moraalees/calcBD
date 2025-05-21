package es.prog2425.calcBD.app


import es.prog2425.calcBD.ui.IEntradaSalida

class ProgramaManager(
    private val ui: IEntradaSalida,
    private val menuLogs: Controlador,
    private val menuBd: BDManager,
    private val args: Array<String>
) {

    fun programa(){
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.pedirInfo("Elige una opción (o escribe):")
            when (entrada){
                "1", "log" -> menuLogs.iniciar(args)
                "2", "bd" -> menuBd.programaBd()
                "3", "salir" -> salir = salirPrograma()
            }
            ui.pausar()
        }
    }

    private fun mostrarMenu(){
        ui.mostrar("""
            ----MENÚ PROGRAMA----
            1. Calculadora Log (log)
            2. Calculadora BD (bd)
            3. Salir
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
}