package estadisticasMadrid

import model.Dato

class ColecionsController: EstadisticasController<List<Dato>> {



    override fun isPrecipitaciones(t: List<Dato>): Boolean {
        return (getSumaPrecipitacion(t)!=0F )
    }


    override fun getSumaPrecipitacion(t: List<Dato>): Float {
        var precipitacion = 0F
        t.forEach { precipitacion = precipitacion + it.precipitacion!! }
        return precipitacion
    }

    override fun getTemperaturaMedia(t: List<Dato>): Float ? {
        var totalTemperaturas = 0F
        t.forEach { x ->totalTemperaturas =
            ((x.temperaturaMaxima?.plus(x.temperaturaMinima!!))?.div(2))?.plus(totalTemperaturas) ?: 0F }
        return ( totalTemperaturas / t.size)
    }

    override fun getTemperaturaMinimaMomento(t: List<Dato>): String? {
        return t.minByOrNull { it.temperaturaMinima?: 0F }?.horaDeTemperaturaMinima.toString()
    }

    override fun getTemperaturaMaxima(t : List<Dato>): Float? {
        return t.maxByOrNull { it.temperaturaMaxima ?: 0F }?.temperaturaMaxima
    }

    override fun getTemperaturaMaximaMomento(t : List<Dato>): String {
      return t.maxByOrNull { it.temperaturaMaxima?: 0F }?.horaDeTemperaturaMaxima.toString()
    }

    override fun getTemperaturaMaximaLugar(t : List<Dato>): String {
        return  t.maxByOrNull { it.temperaturaMaxima ?: 0F }?.estacion.toString()
    }

    override fun getTemperaturaMinimaLugar(t : List<Dato>): String? {
        return t.minByOrNull { it.temperaturaMinima ?: 0F }?.estacion.toString()
    }

    override fun getTemperaturaMinima(t : List<Dato>): Float? {
        return  t.minByOrNull { it.temperaturaMinima ?: 0F }?.temperaturaMinima
    }


}