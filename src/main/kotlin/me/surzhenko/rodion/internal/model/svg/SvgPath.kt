package me.surzhenko.rodion.internal.model.svg

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Transient

@Root(name = SvgPath.NAME, strict = SvgPath.STRICT)
class SvgPath : XmlObject {
    companion object {
        const val NAME: String = "path"
        const val STRICT: Boolean = false

        private const val PATH_DATA: String = "d"
    }

    @field:Attribute(name = PATH_DATA, required = false)
    var pathData: String? = null

    @field:Transient
    var fillColor: String? = null

    @field:Transient
    var fillOpacity: Float = 0f

    @field:Transient
    var strokeColor: String? = null

    @field:Transient
    var strokeOpacity: Float = 0f
}