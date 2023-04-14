package services.xml

import model.Dato
import org.simpleframework.xml.core.Persister
import services.dto.toDatoDto
import java.io.File

class XmlService {
    fun crearficheroXmlConDatos(nombreFichero: String, datos: ArrayList<Dato>){
        val fileXml = File(nombreFichero)

        //ver si exixte y sii no crearlo
        if (!fileXml.exists()) {
            fileXml.createNewFile()
        }

        val serializer = Persister()
        //como no me serializa la lista simplemente , creo una lista propia con anotaciones xml
        var litaNormalConDatosDto =datos.map {x -> x.toDatoDto()}
        var lisaDtoConDatosDto = ListaDeDatos(litaNormalConDatosDto)
        serializer.write(lisaDtoConDatosDto, fileXml)
    }

    fun crearInformeMadridXml(path: String, informeStream: Informe) {

        val fileInformeXmlStream = File(path)

        //ver si exixte y sii no crearlo
        if (!fileInformeXmlStream .exists()) {
            fileInformeXmlStream.createNewFile()
        }

        val serializer = Persister()
        serializer.write(informeStream, fileInformeXmlStream )
    }
}