package me.surzhenko.rodion.internal.model.android

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Attribute

class Icon : XmlObject {
    companion object {
        private const val DRAWABLE: String = "drawable"
    }

    @field:Attribute(name = DRAWABLE, required = false)
    var drawable: String? = null

    fun getIconFilename(): String? = drawable?.split("/")?.get(1)
}