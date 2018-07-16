package me.surzhenko.rodion.internal.model.svg

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = Svg.NAME, strict = Svg.STRICT)
class Svg : XmlObject {
    companion object {
        const val NAME: String = "svg"
        const val STRICT: Boolean = false
    }

    @field:ElementList(inline = true, required = false)
    var groups: MutableList<SvgGroup>? = null
}