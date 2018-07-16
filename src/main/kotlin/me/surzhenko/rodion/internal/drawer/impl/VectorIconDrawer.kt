package me.surzhenko.rodion.internal.drawer.impl

import me.surzhenko.rodion.internal.drawer.IconDrawer
import me.surzhenko.rodion.internal.model.Size
import me.surzhenko.rodion.internal.model.android.Path
import me.surzhenko.rodion.internal.model.android.Vector
import me.surzhenko.rodion.internal.model.svg.Svg
import me.surzhenko.rodion.internal.model.svg.SvgPath
import me.surzhenko.rodion.internal.parser.XmlProcessor
import me.surzhenko.rodion.internal.utils.toColor
import me.surzhenko.rodion.internal.utils.toHexString
import org.apache.batik.dom.svg.SVGDOMImplementation
import org.apache.batik.svggen.SVGGeneratorContext
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.svggen.SVGPath
import org.codehaus.groovy.runtime.StringBufferWriter
import org.w3c.dom.DOMImplementation
import org.w3c.dom.DocumentType
import java.awt.Graphics2D
import java.awt.Shape
import java.io.File

class VectorIconDrawer(iconFile: File) : IconDrawer(iconFile) {

    companion object {
        private const val NAMESPACE_URI = SVGDOMImplementation.SVG_NAMESPACE_URI
        private const val QUALIFIED_NAME = "svg"
        private val DOCUMENT_TYPE: DocumentType? = null
    }

    private val xmlProcessor = XmlProcessor()

    private val dom: DOMImplementation = SVGDOMImplementation.getDOMImplementation()
    private val document = dom.createDocument(NAMESPACE_URI, QUALIFIED_NAME, DOCUMENT_TYPE)
    private val context = SVGGeneratorContext.createDefault(document).apply { isEmbeddedFontsOn = true }
    private val svgGenerator: SVGGraphics2D = object : SVGGraphics2D(context, true) {

        private val pathGenerator = SVGPath(context)

        override fun draw(shape: Shape?) {
            shape?.let { localShape ->
                this.domGroupManager.addElement(pathGenerator.toSVG(localShape), 1.toShort())
            }
        }

        override fun fill(shape: Shape?) {
            shape?.let { localShape ->
                this.domGroupManager.addElement(pathGenerator.toSVG(localShape), 1.toShort())
            }
        }
    }

    override fun getGraphics(iconFile: File): Graphics2D {
        return svgGenerator
    }

    override fun getSize(iconFile: File): Size = xmlProcessor.deserialize<Vector>(iconFile).getVectorSize()

    override fun saveIcon(iconFile: File, output: File) {
        val useCSS = false

        val svgData = StringBuffer()

        StringBufferWriter(svgData).use { writer ->
            svgGenerator.stream(writer, useCSS)
        }

        val svg = xmlProcessor.deserialize<Svg>(svgData.toString())
        val vector = xmlProcessor.deserialize<Vector>(iconFile)

        val svgPaths = mutableListOf<SvgPath>().apply {
            svg.groups?.forEach { group ->
                group.excludePath(this)
            }
        }
        val androidPath = svgPaths.map { svgPath ->
            Path().also { path ->
                path.pathData = svgPath.pathData
                path.strokeWidth = "1"
                path.fillType = Path.FillType.EVENODD.value
                path.fillColor = svgPath.fillColor?.toColor((svgPath.fillOpacity * 255).toInt())?.toHexString()
                path.strokeColor = svgPath.strokeColor?.toColor((svgPath.strokeOpacity * 255).toInt())?.toHexString()
            }
        }

        vector.path?.addAll(androidPath)
        val newVector = xmlProcessor.serialize(vector)

        output.writeText(newVector)
    }
}