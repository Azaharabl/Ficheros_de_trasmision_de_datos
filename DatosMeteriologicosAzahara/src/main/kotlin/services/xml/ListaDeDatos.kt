package services.xml


import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import services.dto.DatoDto

@Root(name ="listaDeDatos")
class ListaDeDatos (
    @field:ElementList(entry = "dato", inline = true)
    @param:ElementList(entry = "dato", inline = true)
    var lista: List<DatoDto>
){

}