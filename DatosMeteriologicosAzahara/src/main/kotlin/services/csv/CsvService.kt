package services.csv

import model.Dato
import java.io.File
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.LocalTime
import kotlin.io.path.exists
import kotlin.io.path.writeText

class CsvService {

    fun obtenerDatosDesdeFicheros(ficheros: MutableList<Path>?): ArrayList<Dato> {
        var listaTotal = ArrayList<Dato>()

        //obtener los datos de cada ficnero
        if (ficheros != null) {
            ficheros.forEach { f -> obtenerDatos(f, listaTotal) }
        }
        return listaTotal
    }

    fun obtenerDatos(f: Path?, listaTotal: ArrayList<Dato>) {
        if (f != null) {
            var lineas = f.toFile().readLines()
            lineas.forEach {
                    l -> var campos = l.split(";")
                listaTotal.add(
                    Dato(
                    obtenerFecha(f),
                    campos[0],
                    campos[1],
                    campos[2].toFloat(),
                    obtenerHora(campos[3]),
                    campos[4].toFloat(),
                    obtenerHora(campos[5]),
                    campos[6].toFloat(),
                )
                )

            }
        }
    }

    fun obtenerFecha(f: Path): LocalDate? {

        var año = f.toFile().name.trim().substring(5,9).toIntOrNull()
        var mes = f.toFile().name.trim().substring(9,11).toIntOrNull()
        var dia = f.toFile().name.trim().substring(11,13).toIntOrNull()
        if (año!= null && mes!= null && dia!= null) {
            return LocalDate.of(año, mes, dia)
        }
        return null
    }

    fun obtenerHora(string: String): LocalTime? {
        var hora =  string.split(":").get(0).toIntOrNull()
        var minuto =  string.split(":").get(1).toIntOrNull()
        if (hora == null || minuto == null) {
            return null
        }
        return  LocalTime.of(hora, minuto)
    }

    fun crearFicheroCsvTotal(pathProvisionalSalida: String, listaTotal: ArrayList<Dato>, nombreFichero : String) {
        val archivo = Path.of(pathProvisionalSalida + File.separator + nombreFichero)

        //miramos si exixte y si no se crea
        if (!archivo.exists()) {
            Files.createFile(archivo)
        }


        //hacemos un string con los datos de la cavecera
        var stringProbisional = StringBuilder(
            "fecha,estacion,provincia,temperaturaMaxima,horaDeTemperaturaMaxima," +
                    "temperaturaMinima,horaDeTemperaturaMinima,precipitacion\n"
        )

        //añadimos una linea por cada dato
        listaTotal.forEach {
            stringProbisional.append(
                it.dia.toString() + "," +
                        it.estacion + "," +
                        it.provincia + "," +
                        it.temperaturaMaxima.toString() + "," +
                        it.horaDeTemperaturaMaxima.toString() + "," +
                        it.temperaturaMinima.toString() + "," +
                        it.horaDeTemperaturaMinima.toString() + "," +
                        it.precipitacion.toString() + "\n"
            )
        }

        //escribir el archivo en csv
        archivo.writeText(stringProbisional.toString())
    }
}