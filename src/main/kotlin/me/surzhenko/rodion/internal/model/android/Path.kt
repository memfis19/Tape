package me.surzhenko.rodion.internal.model.android

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = Path.NAME, strict = Path.STRICT)
class Path : XmlObject {

    enum class FillType(val value: String) {
        EVENODD("evenOdd"), NONZERO("nonZero")
    }

    companion object {
        const val NAME: String = "path"
        const val STRICT: Boolean = false

        private const val PATH_DATA: String = "pathData"
        private const val STROKE_COLOR: String = "strokeColor"
        private const val FILL_TYPE: String = "fillType"
        private const val FILL_COLOR: String = "fillColor"
        private const val STROKE_WIDTH: String = "strokeWidth"
    }

    @field:Attribute(name = PATH_DATA, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var pathData: String? = null

    @field:Attribute(name = STROKE_COLOR, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var strokeColor: String? = null

    @field:Attribute(name = FILL_TYPE, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var fillType: String? = null

    @field:Attribute(name = FILL_COLOR, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var fillColor: String? = null

    @field:Attribute(name = STROKE_WIDTH, required = false)
    @field:Namespace(prefix = "android", reference = "http://schemas.android.com/apk/res/android")
    var strokeWidth: String? = null
}