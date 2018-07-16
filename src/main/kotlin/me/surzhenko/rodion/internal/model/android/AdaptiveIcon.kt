package me.surzhenko.rodion.internal.model.android

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = AdaptiveIcon.NAME, strict = AdaptiveIcon.STRICT)
class AdaptiveIcon : XmlObject {
    companion object {
        const val NAME: String = "adaptive-icon"
        const val STRICT: Boolean = false

        private const val BACKGROUND: String = "background"
        private const val FOREGROUND: String = "foreground"
    }

    @field:Element(name = BACKGROUND, required = false)
    var background: Icon? = null

    @field:Element(name = FOREGROUND, required = false)
    var foreground: Icon? = null
}