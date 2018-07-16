package me.surzhenko.rodion.internal.model.android

import me.surzhenko.rodion.internal.model.Size
import me.surzhenko.rodion.internal.model.XmlObject
import me.surzhenko.rodion.internal.utils.toSizeOrNull
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = Vector.NAME, strict = Vector.STRICT)
class Vector : XmlObject {

    companion object {
        const val NAME: String = "vector"
        const val STRICT: Boolean = false

        private const val WIDTH: String = "width"
        private const val HEIGHT: String = "height"
        private const val VIEWPORT_WIDTH: String = "viewportWidth"
        private const val VIEWPORT_HEIGHT: String = "viewportHeight"
    }

    @field:Attribute(name = WIDTH, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var width: String? = null

    @field:Attribute(name = HEIGHT, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var height: String? = null

    @field:Attribute(name = VIEWPORT_WIDTH, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var viewportWidth: Double? = null

    @field:Attribute(name = VIEWPORT_HEIGHT, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var viewportHeight: Double? = null

    @field:ElementList(inline = true, required = false)
    var path: MutableList<Path>? = null

    fun getVectorSize(): Size = Size(width?.toSizeOrNull()
            ?: 0, height?.toSizeOrNull() ?: 0)
}