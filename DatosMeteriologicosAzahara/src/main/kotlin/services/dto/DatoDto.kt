package services.dto

import model.Dato
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

//para serializar con simpleXML
@Root(name = "dato")
class DatoDto (
    //para serializar con simpleXML
    @field:Element(name = "dia")
    @param:Element(name = "dia")
    var dia: String?,
    @field:Element(name = "estacion")
    @param:Element(name = "estacion")
    var estacion: String?,
    @field:Element(name = "provincia")
    @param:Element(name = "provincia")
    var provincia: String?,
    @field:Element(name = "temperatura_maxima")
    @param:Element(name = "temperatura_maxima")
    var temperaturaMaxima: String?,
    @field:Element(name = "hora_temperatura_maxima")
    @param:Element(name = "hora_temperatura_maxima")
    var horaDeTemperaturaMaxima: String?,
    @field:Element(name = "temperatura_minima")
    @param:Element(name = "temperatura_minima")
    var temperaturaMinima: String?,
    @field:Element(name = "hora_temperatura_minima")
    @param:Element(name = "hora_temperatura_minima")
    var horaDeTemperaturaMinima: String?,
    @field:Element(name = "precipitacion")
    @param:Element(name = "precipitacion")
    var precipitacion: String?
) {
    override fun toString(): String {
        return "Dato(dia=$dia, estacion=$estacion, probincia=$provincia, temperaturaMaxima=$temperaturaMaxima, horaDeTemperatiraMaxima=$horaDeTemperaturaMaxima," +
                " temperaturaMinima=$temperaturaMinima, horaDeTemperatiraMinima=$horaDeTemperaturaMinima, precipitacion=$precipitacion)"
    }
}
//para poder pasar dato a datodto y serializar desde aqui
fun Dato.toDatoDto()= DatoDto(
    dia = this.dia.toString(),
    estacion = this.estacion.toString(),
    provincia = this.provincia.toString(),
    temperaturaMaxima = this.temperaturaMaxima.toString(),
    horaDeTemperaturaMaxima = this.horaDeTemperaturaMaxima.toString(),
    temperaturaMinima = this.temperaturaMinima.toString(),
    horaDeTemperaturaMinima = this.horaDeTemperaturaMinima.toString(),
    precipitacion = this.precipitacion.toString()
)