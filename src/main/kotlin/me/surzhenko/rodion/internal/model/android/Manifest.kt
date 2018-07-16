package me.surzhenko.rodion.internal.model.android

import me.surzhenko.rodion.internal.model.XmlObject
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = Manifest.NAME, strict = Manifest.STRICT)
class Manifest : XmlObject {
    companion object {
        const val NAME: String = "manifest"
        const val STRICT: Boolean = false
    }

    @field:Element
    var application: Application? = null
}