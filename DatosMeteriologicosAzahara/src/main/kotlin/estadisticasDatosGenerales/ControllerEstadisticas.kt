package estadisticasDatosGenerales

import model.Dato
import java.time.LocalDate
import java.time.LocalTime

class ControllerEstadisticas(
    val listaTotal: ArrayList<Dato>
) {


    fun imprimirEstadisticas() {
        //hay varias consultas que piden agrupacion por dia y lugar, así que haremos primero esta
        println("agrupados por dia y lugar")
        var agrupadosPorDeDiaYLugar = agrupadosPorDeDiaYLugar(listaTotal)


        println("\n otro modo de hacerlo\n")
        var agrupadosPorDeDiaYLugar2 = agrupadosPorDeDiaYLugar2(listaTotal)


        println("\n maximo de temperatura agrupados por dia y lugar\n")
        maximosAgrupadosPorDeDiaYLugar(agrupadosPorDeDiaYLugar)

        println("\n otro modo de hacerlo\n")
        maximosAgrupadosPorDeDiaYLugar2(agrupadosPorDeDiaYLugar2)


        println("\nTemperatura mínima por día y lugar\n")
        temeperaturaMinimaAgrupadaPOrFechaYLugar(agrupadosPorDeDiaYLugar)


        println("\n otro modo de hacerlo\n")
        temeperaturaMinimaAgrupadaPOrFechaYLugar2(agrupadosPorDeDiaYLugar2)


        println("\nTemperatura máxima por provincia (día, lugar, valor y momento)\n")
        temperaturaMaximaPorProvincia (listaTotal)


        println("para que solo salgan los datos padidos podemos crear un dto resumen de dato o hascodear la string")
        temperaturaMaximaPorProvincia2 (listaTotal)



        println("Temperatura mínima por provincia (día, lugar, valor y valor)")
        temperaturaMinimaPorProvincia(listaTotal)


        println("\nPrecipitación media por día y provincia\n")
        precipitacionMediaPorDiaYProvincia(listaTotal)


        println("\n otro modo de hacerlo\n")
        precipitacionMediaPorDiaYProvincia2(listaTotal)


        println("\nNúmero de lugares en el que llovío por día y provincia\n")
        numeroLugaresPOrDiaYProvinciaDondeLlovio(listaTotal)

        println("\n otro modo de hacerlo\n")
        numeroLugaresPOrDiaYProvinciaDondeLlovio2(listaTotal)


        println("Media de temperatura máxima total")
        mediaDeTemperaturaMaximaTotal(listaTotal)

        println("Media de temperatura minima total")
        mediaDeTemperaturaMinimaTotal(listaTotal)


        println("Lugares donde la máxima ha sido antes de las 15:00 por día")
        lugaresDondeLaMaximaHaSidoAntesDe15POrDia(listaTotal)

        println("Lugares donde la mínima ha sido después de las 17:30 por día")
        lugaresDondeLaMinimaHaSidoDespuesDe1730POrDia(listaTotal)
    }

    //todo mal
    fun precipitacionMediaPorDiaYProvincia(listaTotal: ArrayList<Dato>) {
        var precipitacionMediaPorDiaYProvincia = HashMap<Pair<LocalDate?, String?>, Float>()
        listaTotal.groupBy { dato -> Pair(dato.dia, dato.provincia)}.mapValues {
                group ->
            var sumaPrecipitacion =0F
            group.value.forEach{ x -> sumaPrecipitacion = sumaPrecipitacion + x.precipitacion!!}
            var totalDiasLlovidos = group.value.count()
            precipitacionMediaPorDiaYProvincia[group.key]=sumaPrecipitacion/totalDiasLlovidos
        }
        println(precipitacionMediaPorDiaYProvincia)


    }

    fun temperaturaMaximaPorProvincia2(listaTotal: ArrayList<Dato>) {
        listaTotal.groupBy { dato ->dato.provincia }.mapValues {
                grupo -> grupo.value.maxByOrNull { dato -> dato.temperaturaMaxima?:0F }}.forEach { grupo ->
            println(" " + grupo.key +" fecha " + grupo.value?.dia.toString() +
                    " lugar " + grupo.value?.estacion.toString() +"  temperaturaMaxima " + grupo.value?.temperaturaMaxima.toString()
                    +" momento " + grupo.value?.horaDeTemperaturaMaxima.toString())
        }.also { println(it) }

    }

    fun temperaturaMaximaPorProvincia(listaTotal: ArrayList<Dato>) {
        println( listaTotal.groupBy { dato ->dato.provincia }.mapValues {
                grupo -> grupo.value.maxByOrNull { dato -> dato.temperaturaMaxima?:0F }
        })
    }

    fun temeperaturaMinimaAgrupadaPOrFechaYLugar2(agrupadosPorDeDiaYLugar2: Map<LocalDate?, Map<String?, List<Dato>>>) {
        println( agrupadosPorDeDiaYLugar2.mapValues { (_,mapaDeAgrupadosLugar)->mapaDeAgrupadosLugar
            .mapValues { (_, lista) -> lista.minBy { it.temperaturaMinima?:0F }}  })
    }

    fun temeperaturaMinimaAgrupadaPOrFechaYLugar(agrupadosPorDeDiaYLugar: Map<Pair<LocalDate?, String?>, List<Dato>>) {
        println(agrupadosPorDeDiaYLugar.mapValues {
                agrupacion -> agrupacion.value.maxByOrNull { dato -> dato.temperaturaMinima?: 0F}
        } )
    }

    fun maximosAgrupadosPorDeDiaYLugar2(agrupadosPorDeDiaYLugar2: Map<LocalDate?, Map<String?, List<Dato>>>) {
        println( agrupadosPorDeDiaYLugar2.mapValues { (_,mapaDeAgrupadosLugar)->mapaDeAgrupadosLugar
            .mapValues { (_, lista) -> lista.maxBy { it.temperaturaMaxima?:0F }}  })
    }

    fun maximosAgrupadosPorDeDiaYLugar(agrupadosPorDeDiaYLugar: Map<Pair<LocalDate?, String?>, List<Dato>>) {
        println(agrupadosPorDeDiaYLugar.mapValues { grupo ->
            grupo.value.maxByOrNull { dato -> dato.temperaturaMaxima ?: 0F} })
    }

    fun agrupadosPorDeDiaYLugar2(listaTotal: ArrayList<Dato>): Map<LocalDate?, Map<String?, List<Dato>>> {
        return listaTotal.groupBy { it.dia }.mapValues { (_,agrupadosPorDia) ->
            agrupadosPorDia.groupBy { it.provincia }}.also { println(it) }

    }

    fun agrupadosPorDeDiaYLugar(listaTotal: ArrayList<Dato>): Map<Pair<LocalDate?, String?>, List<Dato>> {

        return listaTotal.groupBy { dato ->  Pair(dato.dia, dato.provincia) }.also { println(it) }
    }

    fun lugaresDondeLaMinimaHaSidoDespuesDe1730POrDia(listaTotal: ArrayList<Dato>) {
        println( listaTotal.groupBy { it.dia.toString() }.mapValues { (_,agrupadosPorDia) -> agrupadosPorDia.groupBy { it.provincia }
            .mapValues {(_, agrupadosPorLugar)->
                agrupadosPorLugar.maxBy { it.temperaturaMinima?:0F }
            } .filter { x-> x.value.horaDeTemperaturaMinima!!.isAfter(LocalTime.of(17,30))}})


    }

    fun lugaresDondeLaMaximaHaSidoAntesDe15POrDia(listaTotal: ArrayList<Dato>) {
        println( listaTotal.groupBy { it.dia.toString() }.mapValues { (_,agrupadosPorDia) -> agrupadosPorDia.groupBy { it.provincia }
            .mapValues {(_, agrupadosPorLugar)->
                agrupadosPorLugar.maxBy { it.temperaturaMaxima?:0F }
            } .filter { x-> x.value.horaDeTemperaturaMaxima!!.isBefore(LocalTime.of(15,0))}})

    }

    fun mediaDeTemperaturaMinimaTotal(listaTotal: ArrayList<Dato>) {
        var suma = 0F
        listaTotal.forEach {
            suma = suma + it.temperaturaMinima!!
        }
        println(suma/listaTotal.size)
    }

    fun mediaDeTemperaturaMaximaTotal(listaTotal: ArrayList<Dato>) {
        var suma = 0F
        listaTotal.forEach {
            suma = suma + it.temperaturaMaxima!!
        }
        println(suma/listaTotal.size)
    }

    fun numeroLugaresPOrDiaYProvinciaDondeLlovio2(listaTotal: ArrayList<Dato>) {
        println( listaTotal.groupBy { it.dia }.mapValues { (_,agrupadosPorDia) -> agrupadosPorDia.groupBy { it.provincia }
            .mapValues { (_,agrupadosPorDiayProvincia) -> agrupadosPorDiayProvincia.filter { it.precipitacion!! >0F }.count()} })
    }

    fun numeroLugaresPOrDiaYProvinciaDondeLlovio(listaTotal: ArrayList<Dato>) {
        println( listaTotal.groupBy { Pair(it.dia, it.provincia) }.mapValues {
                grupo -> grupo.value.filter { it.precipitacion!! >0 }.count()
        })
    }

    fun temperaturaMinimaPorProvincia(listaTotal: ArrayList<Dato>) {
        listaTotal.groupBy { dato ->dato.provincia }.mapValues {
                grupo -> grupo.value.minByOrNull { dato -> dato.temperaturaMinima?: 0F}
        }.forEach { grupo ->
            println(" " + grupo.key +" fecha " + grupo.value?.dia.toString() +
                    " lugar " + grupo.value?.estacion.toString() +"  temperaturaMaxima " + grupo.value?.temperaturaMaxima.toString()
                    +" momento " + grupo.value?.horaDeTemperaturaMaxima.toString())
        }.also { println(it) }

    }

    fun precipitacionMediaPorDiaYProvincia2(listaTotal: ArrayList<Dato>) {
        var precipitacionMediaPorDiaYProvincia =HashMap<LocalDate? , HashMap<String?,Float>>()

        var l = listaTotal.groupBy { it.dia }.mapValues { (_,agrupacionPOrDia)-> agrupacionPOrDia.groupBy { it.provincia }
            .mapValues { (_,lista)->
                var suma = 0F
                var dias = 0
                var promedio = 0F
                lista.forEach{
                    dias = lista.count()
                    suma = suma + it.precipitacion!!
                    promedio = suma/dias
                }
                lista.map { promedio }
            }
        }
        println(l)

    }
}