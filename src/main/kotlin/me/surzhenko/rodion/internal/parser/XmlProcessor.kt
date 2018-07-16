package me.surzhenko.rodion.internal.parser

import me.surzhenko.rodion.internal.model.XmlObject
import org.codehaus.groovy.runtime.StringBufferWriter
import org.simpleframework.xml.core.Persister
import java.io.File

class XmlProcessor {

    val processor = Persister()

    inline fun <reified V> deserialize(xmlFile: File): V where V : XmlObject {
        return processor.read(V::class.java, xmlFile)
    }

    inline fun <reified V> deserialize(xmlSource: String): V where V : XmlObject {
        return processor.read(V::class.java, xmlSource)
    }

    fun <V> serialize(vector: V): String where V : XmlObject {
        val stringBuffer = StringBuffer()

        StringBufferWriter(stringBuffer).use { writer ->
            processor.write(vector, writer)
        }

        return stringBuffer.toString()
    }
}