# Calculadora Básica

---

## Descripción

Este proyecto emplea el uso de una Base de Datos (BD) para poder guardar y almacenar en esta operaciones y/o errores que van sucediendo a lo largo de la interacción entre el programa y el usuario. Para conectar con la base de datos de forma eficiente, se emplea un DataSource, que gestiona las conexiones sin que el desarrollador tenga que abrir y cerrar manualmente cada una. En este caso, se utiliza una implementación basada en HikariCP, aunque se puede modificar para que use una implementación simple.

---

## Formas

Dicho programa contiene 2 formas de guardar información sobre las operaciones y errores:
- Sistema de Logs: Se guardan en una ruta (`/logs`) archivos `.txt` que, como es natural, contienen textos e información sobre los cálculos y dificultades que se han encontrado en el flujo del programa.
- Sistema de BD: Se guardan en dos tablas diferentes, según el caso, la operación, junto con los números, operador y resultado, o el error, con una pequeña descripción. En ambas tablas también se guardan las fechas en las que se guardó el campo o registro. Todo esto es posible gracias a varias clases, como [`RepoLogDAOH2`](https://github.com/moraalees/calcBD/blob/main/src/main/kotlin/data/dao/RepoLogDAOH2.kt) o [`DatasourceFactory`](https://github.com/moraalees/calcBD/blob/main/src/main/kotlin/data/db/DatasourceFactory.kt), que vienen documentadas correctamente en el propio código.
