package estadisticasMadrid

import model.Dato

class StreamController: EstadisticasController<MutableList<Dato>> {

    override fun isPrecipitaciones(t: MutableList<Dato>): Boolean {
        return getSumaPrecipitacion(t) != 0F
    }

    override fun getSumaPrecipitacion(t: MutableList<Dato>): Float? {
        var precipitacion = 0f
        t.stream().forEach { precipitacion = precipitacion + it.precipitacion!! }
        return precipitacion
    }

    override fun getTemperaturaMedia(t: MutableList<Dato>): Float? {
        var temperaturaTotal = 0F
        t.stream().forEach{temperaturaTotal = temperaturaTotal + (it.temperaturaMinima?:0F) + (it.temperaturaMaxima?:0F)}
        return (temperaturaTotal/(2))/t.stream().count()
    }

    override fun getTemperaturaMinimaMomento(t: MutableList<Dato>): String? {
        return t.stream().filter{it.temperaturaMinima.toString().equals(getTemperaturaMinima(t).toString(),true)}.findFirst().get().horaDeTemperaturaMinima.toString()
    }

    override fun getTemperaturaMaxima(t: MutableList<Dato>): Float? {
        return t.stream().map { it.temperaturaMaxima ?: 0F }.min { o1, o2 -> (o1-o2).toInt() }.get()
    }

    override fun getTemperaturaMaximaMomento(t: MutableList<Dato>): String? {
        return t.stream().filter{it.temperaturaMaxima.toString().equals(getTemperaturaMaxima(t).toString(),true)}.findFirst().get().horaDeTemperaturaMaxima.toString()
    }

    override fun getTemperaturaMaximaLugar(t: MutableList<Dato>): String? {
        return t.stream().filter{it.temperaturaMaxima.toString().equals(getTemperaturaMaxima(t).toString(),true)}.findFirst().get().estacion.toString()
    }

    override fun getTemperaturaMinimaLugar(t: MutableList<Dato>): String? {
        return t.stream().filter{it.temperaturaMinima.toString().equals(getTemperaturaMinima(t).toString(),true)}.findFirst().get().estacion.toString()
    }

    override fun getTemperaturaMinima(t: MutableList<Dato>): Float? {
        return t.stream().map { it.temperaturaMinima ?: 0F }.min { o1, o2 -> (o1-o2).toInt() }.get()
    }
}