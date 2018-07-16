package me.surzhenko.rodion.internal.model.svg

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = SvgGroup.NAME, strict = SvgGroup.STRICT)
class SvgGroup : XmlObject {
    companion object {
        const val NAME: String = "g"
        const val STRICT: Boolean = false

        private const val FILL_COLOR: String = "fill"
        private const val FILL_OPACITY: String = "fill-opacity"
        private const val STROKE_COLOR: String = "stroke"
        private const val STROKE_OPACITY: String = "stroke-opacity"
    }

    @field:ElementList(inline = true, required = false)
    var groups: MutableList<SvgGroup>? = null

    @field:ElementList(inline = true, required = false)
    var path: MutableList<SvgPath>? = null

    @field:Attribute(name = FILL_COLOR, required = false)
    var fillColor: String? = null

    @field:Attribute(name = FILL_OPACITY, required = false)
    var fillOpacity: Float = 0f

    @field:Attribute(name = STROKE_COLOR, required = false)
    var strokeColor: String? = null

    @field:Attribute(name = STROKE_OPACITY, required = false)
    var strokeOpacity: Float = 0f

    fun excludePath(destination: MutableList<SvgPath>) {
        if (groups != null) groups?.forEach { group -> group.excludePath(destination) }
        else if (path != null) {
            path?.forEach { path ->
                path.fillColor = fillColor
                path.fillOpacity = fillOpacity
                path.strokeColor = strokeColor
                path.strokeOpacity = strokeOpacity
            }
            path?.apply { destination.addAll(this) }
        }
    }
}