package model


import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.transform.Transform
import java.time.LocalDate
import java.time.LocalTime


class Dato (
    //para serializar con simpleXML
   var dia: LocalDate?,
   var estacion: String?,
  var provincia: String?,
   var temperaturaMaxima: Float?,
    var horaDeTemperaturaMaxima: LocalTime?,
    var temperaturaMinima: Float?,
    var horaDeTemperaturaMinima: LocalTime?,
    var precipitacion: Float?
) {
    override fun toString(): String {
        return "Dato(dia=$dia, estacion=$estacion, probincia=$provincia, temperaturaMaxima=$temperaturaMaxima, horaDeTemperatiraMaxima=$horaDeTemperaturaMaxima," +
                " temperaturaMinima=$temperaturaMinima, horaDeTemperatiraMinima=$horaDeTemperaturaMinima, precipitacion=$precipitacion)"
    }
}

