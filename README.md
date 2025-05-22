# Calculadora Básica

---

## Descripción

Este proyecto emplea el uso de una Base de Datos (BD) para poder guardar y almacenar en esta operaciones y/o errores que van sucediendo a lo largo de la interacción entre el programa y el usuario. Para conectar con la base de datos de forma eficiente, se emplea un DataSource, que gestiona las conexiones sin que el desarrollador tenga que abrir y cerrar manualmente cada una. En este caso, se utiliza una implementación basada en HikariCP, aunque se puede modificar para que use una implementación simple.

---

## Formas

Dicho programa contiene 2 formas de guardar información sobre las operaciones y errores:
- Sistema de Logs: Se guardan en una ruta (`/logs`) archivos `.txt` que, como es natural, contienen textos e información sobre los cálculos y dificultades que se han encontrado en el flujo del programa.
- Sistema de BD: Se guardan en dos tablas diferentes, según el caso, la operación, junto con los números, operador y resultado, o el error, con una pequeña descripción. En ambas tablas también se guardan las fechas en las que se guardó el campo o registro. Todo esto es posible gracias a varias clases, como [`RepoLogDAOH2`](https://github.com/moraalees/calcBD/blob/main/src/main/kotlin/data/dao/RepoLogDAOH2.kt) o [`DatasourceFactory`](https://github.com/moraalees/calcBD/blob/main/src/main/kotlin/data/db/DatasourceFactory.kt), que vienen documentadas correctamente en el propio código.

---

## Estructura

Dicha app, sigue una estructura de capetas y clases cumpliendo correctamente con los patrones DAO y/o los principios SOLID:

- [`Directorio App`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/app): Este directorio es el que se encarga de facilitarle al usuario ver e interactuar con la información que se va mostrando a través de la consola. Básicamente contiene el flujo del programa, es decir, los diferentes menús, que se encuentran en las clases `ProgramaManager` y `BDManager`. También hay otras clases, como el `Controlador`, que maneja las operaciones que son guardadas en archivos `.txt`.
- [`Directorio Data`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/data): Este directorio contiene la lógica de ambas formas del programa. Este contiene las clases `RepoLogTxt` y `IRepoLog`, encargadas del guardado en local. Además posee otros dos directorios:
    - [`Subdirectorio Dao`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/data/dao): Este subdirectorio contiene las clases que se encargan de         realizar operaciones sobre la base de datos. Estas clases siguen el patrón DAO y están implementadas específicamente para la base de datos H2. Contiene las        clases  `RepoLogDAOH2` y la interfaz `IRepoLogDAO`. Cada clase se conecta a la BD gracias al uso de un Data Source.
    - [`Subdirectorio Db`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/data/db): Este subdirectorio posee la inicialización de la conexión a la       BD gracias a un object `DataSourceFactory`. Este se encarga de proporcionar el Data Source a las demás clases. También hay una enum class `Mode` que               dictamina tipos de bases de datos soportadas por la aplicación.
- [`Directorio Model`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/model): Contiene la lógica de negocio, es decir, las clases sobre las que se van a manipular datos. Estas son `Calculo` y `Operador`.
- [`Directorio Service`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/service): Este directorio contiene las clases de servicio. Estas, actúan como intermediarias entre los directorios App y Dao, ya que las clases de servicio implementan las funciones de las clases DAOH2 y la app llama a las de este directorio. Aquí, existen varias clases, como pueden ser `ServicioLogDAO` o `ServicioCalc`. Algunas de estas clases no implementan DAO ya que no guardan en la BD datos, sino que guardan en una ruta.
- [`Directorio Ui`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/ui): Este directorio contiene la clase responsable de la interacción entre consola y usuario, `Consola`. Dicha clase es usada en el programa principal (Directorio App) para manejar diferentes eventos, como pueden ser proporcionar una pausa al programa o limpiar la terminal.
- [`Directorio Utils`](https://github.com/moraalees/calcBD/tree/main/src/main/kotlin/utils): Este último directorio se encarga de ayudar a las clases de `Data` para, en este caso, proporcionar las rutas y leer los ficheros guardados. Esto es posible gracias a las clases `GestorFichTxt` cuya implementación es `IUtilFich`.
- [`Main`](https://github.com/moraalees/calcBD/blob/main/src/main/kotlin/Main.kt): Es el archivo principal que llama a la App para que funcione el programa. En esta clase, se crean instancias de todos los DAO y todos los Servicios para luego inyectarlos al programa principal, además de todas las clases convenientes a la hora de la creación de ficheros. Una vez se implementen, empieza el programa.
