package me.surzhenko.rodion.internal.model.android

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = Application.NAME, strict = Application.STRICT)
open class Application : XmlObject {
    companion object {
        const val NAME: String = "application"
        const val STRICT: Boolean = false

        private const val ICON: String = "icon"
    }

    @field:Attribute(name = ICON)
    open var icon: String? = null

    fun getIconFilename(): String? = icon?.split("/")?.get(1)
}