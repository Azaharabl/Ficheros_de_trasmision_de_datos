package services.json

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.Dato
import services.dto.DatoDto
import services.dto.toDatoDto
import services.xml.DiaEnMadrid
import services.xml.Informe
import java.io.File

class JsonService {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun crearficheroJsonConDatos(path: String, listaTotal: ArrayList<Dato>) {
        val fileJson = File(path)

        if (!fileJson.exists()) {
            fileJson.createNewFile()
        }

        val adapter: JsonAdapter<List<DatoDto>> = moshi.adapter(Types.newParameterizedType(List::class.java, DatoDto::class.java))

        //  pretty print
        val textoJson: String = adapter.indent("  ").toJson(listaTotal.stream().map { it.toDatoDto()}.toList())
        fileJson.writeText(textoJson)
    }

    fun crearInformeMadridJson(path: String, informe: Informe) {

        val fileInformeJsonStream = File(path)

        //ver si exixte y sii no crearlo
        if (!fileInformeJsonStream .exists()) {
            fileInformeJsonStream.createNewFile()
        }

        //val adapterInforme : JsonAdapter<ArrayList<DiaEnMadrid>> = moshi.adapter(Types.newParameterizedType(ArrayList::class.java, DiaEnMadrid::class.java))
        val adapterInforme : JsonAdapter<List<DiaEnMadrid>> = moshi.adapter(Types.newParameterizedType(List::class.java, DiaEnMadrid::class.java))

        //  pretty print
        var textoJsonInformeStream: String = adapterInforme.indent("  ").toJson(informe.lista )
        fileInformeJsonStream.writeText(textoJsonInformeStream)

    }


}