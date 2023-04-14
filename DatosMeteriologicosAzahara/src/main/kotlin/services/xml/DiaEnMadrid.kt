package services.xml

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "DiaMadrid")
class DiaEnMadrid (
    @field:Element(name = "fecha")  //getter
    @param:Element(name = "fecha")  //seter
    var fecha : String = "",
    @field:Element(name = "DatosInforme")
    @param:Element(name = "DatosInforme")
    var datosInforme : DatosDeDiaEnMadrid?,

    ){

}
