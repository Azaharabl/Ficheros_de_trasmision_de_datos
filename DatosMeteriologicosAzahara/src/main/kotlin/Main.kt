
import estadisticasDatosGenerales.ControllerEstadisticas
import estadisticasMadrid.ColecionsController
import estadisticasMadrid.DataframeController
import estadisticasMadrid.StreamController
import model.*
import org.jetbrains.kotlinx.dataframe.api.*
import services.csv.CsvService
import services.json.JsonService
import services.xml.*
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.DriverManager
import java.time.LocalDate
import java.util.Properties
import java.util.stream.Collectors
import kotlin.collections.ArrayList


fun main(args: Array<String>) {

    var csvServices =  CsvService()
    var jsonServices = JsonService()
    var xmlServices = XmlService()

    // args desde parametros
    // var pats = obtenerPathDesdeArgs(args)
    //var pats = obtenerPathDesdeArgs(args)
    //var pathProvisionalEntrada = pats.get(0)
    //var pathProvisionalSalida = pats.get(1)

    //args desde properties
    var p = Properties()
        p.load(FileInputStream(ClassLoader.getSystemResource("config.properties").file))
    var pathProvisionalEntrada = System.getProperty("user.dir") + File.separator +Path.of( p.getProperty("pathDeOriguenDatos"))
    var pathProvisionalSalida = System.getProperty("user.dir") + File.separator +Path.of( p.getProperty("pathDeDestinoDatos"))


    comprobamosPathEntrada(pathProvisionalEntrada)
    println(pathProvisionalEntrada)
    comprobamosPathSalida(pathProvisionalSalida)
    println(pathProvisionalSalida)


    //                      ¡¡Paso uno del ejercicio - obtener datos y crear csv!!



    val ficheros = obtenerFicherosDesdePaths(pathProvisionalEntrada)

    //por cada uno tenemos que leerlo y añadir el dato a la lista

    var listaTotal =csvServices.obtenerDatosDesdeFicheros(ficheros)

    //creamos archivo y introducimos datos en csv
    csvServices.crearFicheroCsvTotal(pathProvisionalSalida, listaTotal,"DatosTotales.csv" )


    //                             ¡¡Paso dos del ejercicio - pasar datos a Xml y Json!!


    //realizar los ficheros en Xml usamos simplexml
    xmlServices.crearficheroXmlConDatos(pathProvisionalSalida + File.separator + "DatosTotales.xml", listaTotal)


    //realizar los ficheros en json moshi
    jsonServices.crearficheroJsonConDatos(pathProvisionalSalida + File.separator + "DatosTotales.json", listaTotal)


    //                             ¡¡Paso tres del ejercicio - Hacer calculos!!


    ControllerEstadisticas(listaTotal).imprimirEstadisticas()


    //                             ¡¡Paso cuatro del ejercicio - hacer informe en xml!!
    //                                       por streme y por colecciones



   var listaDeDatosParaInformePadrid = ArrayList<DiaEnMadrid>()

    var sC = StreamController()
    listaDeDatosParaInformePadrid.clear() //limpiamos lista de los datos antiguos
    //sacamos todos los de madrid con streame
    listaTotal.stream()
        .filter{ x -> x.provincia.equals("madrid", true)}   //filtramos madrid
        .collect(Collectors.groupingBy<Dato?, LocalDate?> { it.dia })//agrupamos por dia
        .forEach{ it ->

            //hacemos dia por cada uno


            listaDeDatosParaInformePadrid.add( DiaEnMadrid(   //al informe le añadimos el dia
                fecha = it.key.toString(),
                datosInforme = DatosDeDiaEnMadrid(    //que tiene los siquientes datso,
                    // esto lo hacemos para que la extructura sea tal cual se pidio en el xml
                    temperaturaMedia = sC.getTemperaturaMedia(it.value).toString(),
                    temperaturaMáxima = sC.getTemperaturaMaxima(it.value).toString(),
                    temperaturaMáximaLugar = sC.getTemperaturaMaximaLugar(it.value).toString(),
                    temperaturaMáximaMomento = sC.getTemperaturaMaximaMomento(it.value).toString(),
                    temperaturaMinima = sC.getTemperaturaMinima(it.value).toString(),
                    temperaturaMinimaLugar = sC.getTemperaturaMinimaLugar(it.value).toString(),
                    temperaturaMinimaMomento = sC.getTemperaturaMinimaMomento(it.value).toString(),
                    siHuboPrecipitación = sC.isPrecipitaciones(it.value).toString(),
                    precipitacion = sC.getSumaPrecipitacion(it.value).toString()
                )
            )
            )
        }

    var informeStream = Informe(lista =listaDeDatosParaInformePadrid.toList() )

    xmlServices.crearInformeMadridXml(pathProvisionalSalida + File.separator + "InformeXmlStream.xml",informeStream)
    jsonServices.crearInformeMadridJson(pathProvisionalSalida + File.separator + "InformeJsonStream.json",informeStream)


    listaDeDatosParaInformePadrid.clear()//limpiamos por si hay datos antiguos


    var cc = ColecionsController()

    //sacamos todos los de madrid con coleciones
   listaTotal.filter { it.provincia.equals("madrid", true) }
        .groupBy { x -> x.dia }.forEach{

           //hacemos dia por cada uno

           listaDeDatosParaInformePadrid.add( DiaEnMadrid(   //al informe le añadimos el dia
               fecha = it.key.toString(),
               datosInforme = DatosDeDiaEnMadrid(    //que tiene los siquientes datso,
                   // esto lo hacemos para que la extructura sea tal cual se pidio en el xml
                   temperaturaMedia = cc.getTemperaturaMedia(it.value).toString(),
                   temperaturaMáxima = cc.getTemperaturaMaxima(it.value).toString(),
                   temperaturaMáximaLugar = cc.getTemperaturaMaximaLugar(it.value).toString(),
                   temperaturaMáximaMomento = cc.getTemperaturaMaximaMomento(it.value).toString(),
                   temperaturaMinima = cc.getTemperaturaMinima(it.value).toString(),
                   temperaturaMinimaLugar = cc.getTemperaturaMinimaLugar(it.value).toString(),
                   temperaturaMinimaMomento = cc.getTemperaturaMinimaMomento(it.value).toString(),
                   siHuboPrecipitación = cc.isPrecipitaciones(it.value).toString(),
                   precipitacion = cc.getSumaPrecipitacion(it.value).toString().toString()
               )
           )
           )
       }

    var informeColeciones = Informe(lista =listaDeDatosParaInformePadrid.toList() )

    xmlServices.crearInformeMadridXml(pathProvisionalSalida + File.separator + "InformeXmlColeciones.xml",informeColeciones)
    jsonServices.crearInformeMadridJson(pathProvisionalSalida + File.separator + "InformeJsonColeciones.json",informeColeciones)


    //hacer el informe con dataframe

    var dfController = DataframeController()

    var df = listaTotal.toDataFrame().cast<Dato>()
        .filter { x -> x.getValue<String>("provincia").equals("madrid",true)}
        .groupBy("dia")
        .forEach { group ->
            //hacemos dia por cada uno

            listaDeDatosParaInformePadrid.add( DiaEnMadrid(   //al informe le añadimos el dia
                fecha = group.key.toString(),
                datosInforme = DatosDeDiaEnMadrid(    //que tiene los siquientes datso,
                    // esto lo hacemos para que la extructura sea tal cual se pidio en el xml
                    temperaturaMáxima = dfController.getTemperaturaMaxima(group).toString(),
                    temperaturaMáximaMomento = dfController.getTemperaturaMaximaMomento(group),
                    temperaturaMáximaLugar= dfController.getTemperaturaMaximaLugar(group),

                    temperaturaMinima = dfController.getTemperaturaMinima(group).toString(),
                    temperaturaMinimaLugar = dfController.getTemperaturaMinimaLugar(group).toString(),
                    temperaturaMinimaMomento = dfController.getTemperaturaMinimaMomento(group).toString(),

                    temperaturaMedia = dfController.getTemperaturaMedia(group).toString(),
                    siHuboPrecipitación = dfController.isPrecipitaciones(group).toString(),
                    precipitacion = dfController.getSumaPrecipitacion(group).toString()
                )
            )
            )
        }


    var informeDataframe = Informe(lista =listaDeDatosParaInformePadrid.toList() )

    xmlServices.crearInformeMadridXml(pathProvisionalSalida + File.separator + "InformeXmlDataF.xml",informeDataframe)
    jsonServices.crearInformeMadridJson(pathProvisionalSalida + File.separator + "InformeJsonDataF.json",informeDataframe)


}




fun obtenerFicherosDesdePaths(pathProvisionalEntrada: String): MutableList<Path>? {
    val ficheros = Files.walk(Path.of(pathProvisionalEntrada))
        .filter { Files.isRegularFile(it) && it.toString().endsWith(".csv") }.toList()
    return ficheros
}

fun obtenerPathDesdeArgs(argsFalsos1: Array<String>): List<String> {
    var pathProvisionalEntrada = ""
    var pathProvisionalSalida = ""

    if(argsFalsos1.size != 4) {
        //comprobamos que hay 4 parametros en los agrs, si no estará incorrecto
        println("los agrs no son correctos")
        System.exit(0)
    }else {
        //comproibamos si el primero es un -d o -o o si no es ninguno entonces será incorrecto
        if (argsFalsos1.get(0) == "-d" && argsFalsos1.get(2) == "-o") {

            pathProvisionalSalida = argsFalsos1[1]  //destino
            pathProvisionalEntrada = argsFalsos1[3] //origen

        } else if (argsFalsos1.get(2) == "-d" && argsFalsos1.get(0) == "-o") {
            pathProvisionalEntrada = argsFalsos1[1] //origen
            pathProvisionalSalida = argsFalsos1[3]  //destino
        } else {
            println("los agrs no son correctos")
            System.exit(0)
        }

    }
    println("directorio de entrada es : " +pathProvisionalEntrada )
    println("directorio de salida es : " +pathProvisionalSalida )
    return listOf(pathProvisionalEntrada,pathProvisionalSalida)
}

fun comprobamosPathSalida(pathProvisionalSalida: String) {
    println(File(pathProvisionalSalida))
    if (Path.of(pathProvisionalSalida).toFile().exists()
        && Path.of(pathProvisionalSalida).toFile().isDirectory
        && Path.of(pathProvisionalSalida).toFile().canWrite()  ){
        //el directorio exixte y es un directrio y se puede escribir
    }else{
        try {
            Files.createDirectory(Path.of(pathProvisionalSalida))
        }catch (e : Exception) {
            println("el directorio de origen de datos no se puede crear")
            System.err.println("el directorio de origen de datos no se puede crear")

            System.exit(0)
        }
    }
}

fun comprobamosPathEntrada(pathProvisionalEntrada : String) {
    println(Path.of(pathProvisionalEntrada).toString())
    if (Path.of(pathProvisionalEntrada).toFile().exists()
        && Path.of(pathProvisionalEntrada).toFile().isDirectory
        && Path.of(pathProvisionalEntrada).toFile().canRead() ){
        //el directorio exixte y es un directrio y se puede leer
    }else{
        println("el directorio de origen de datos no existe o no tiene permiso de lectura")
        System.exit(0)
    }
}



