package es.prog2425.calcBD.model

data class Calculo(
    val numero1: Double,
    val numero2: Double,
    val operador: Operador,
    val resultado: Double
) {
    override fun toString(): String {
        return "%.2f %s %.2f = %.2f".format(numero1, operador.simboloUi, numero2, resultado)
    }
}