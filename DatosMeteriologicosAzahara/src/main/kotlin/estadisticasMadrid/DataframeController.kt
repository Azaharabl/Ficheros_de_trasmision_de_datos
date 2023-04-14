package estadisticasMadrid

import model.Dato
import org.jetbrains.kotlinx.dataframe.api.*
import java.time.LocalDate

class DataframeController : EstadisticasController<GroupBy.Entry<Dato, Dato>> {

    override fun isPrecipitaciones(group: GroupBy.Entry<Dato, Dato>): Boolean {
        return group.group.sum("precipitacion")==0
    }

    override fun getSumaPrecipitacion(group: GroupBy.Entry<Dato, Dato>): Float {
        return group.group.sum("precipitacion").toFloat()

    }

    override fun getTemperaturaMedia(group: GroupBy.Entry<Dato, Dato>): Float {
        var sumMin : Float = group.group.sum("temperaturaMinima").toFloat()
        var sumMax : Float = group.group.sum("temperaturaMaxima").toFloat()
        var count : Int = group.group.rowsCount()*2
        return  (sumMin + sumMax)/count
    }

    override fun getTemperaturaMinimaMomento(group: GroupBy.Entry<Dato, Dato>): String {

        return group.group.filter { it.getValue<Float>("temperaturaMinima").toString()
            .equals(getTemperaturaMinima(group).toString())}
            .get(0).getValue<LocalDate>("dia").toString()
        //todo
        println("aqui no falla1")
    }

    override fun getTemperaturaMaxima(group: GroupBy.Entry<Dato, Dato>): Float {
        return group.group.map { it.getValue<Float>("temperaturaMaxima") }.max()
    }

    override fun getTemperaturaMaximaMomento(group: GroupBy.Entry<Dato, Dato>): String {
        return group.group
            .filter { x -> x.getValue<Float>("temperaturaMaxima").toString()
                .equals(getTemperaturaMaxima(group).toString())}.get(0).getValue<String?>("dia").toString()
        //todo
        println("aqui no falla2")
    }

    override fun getTemperaturaMaximaLugar(group: GroupBy.Entry<Dato, Dato>): String {
        return  group.group.filter { x -> x.getValue<Float>("temperaturaMaxima").toString()
            .equals(getTemperaturaMaxima(group).toString()) }.get(0).getValue<String>("estacion").toString()

    }

    override fun getTemperaturaMinimaLugar(group: GroupBy.Entry<Dato, Dato>): String {
        return group.group.filter { it.getValue<Float>("temperaturaMinima").toString()
            .equals(getTemperaturaMinima(group).toString()) }.get(0).getValue<String>("estacion").toString()
    }


    override fun getTemperaturaMinima(group: GroupBy.Entry<Dato, Dato>): Float {
        return group.group.map{ it.getValue<Float>("temperaturaMinima") }.min()
    }



}