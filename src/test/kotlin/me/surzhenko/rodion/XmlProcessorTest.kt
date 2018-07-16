package me.surzhenko.rodion

import me.surzhenko.rodion.internal.model.android.AdaptiveIcon
import me.surzhenko.rodion.internal.model.android.Manifest
import me.surzhenko.rodion.internal.model.android.Path
import me.surzhenko.rodion.internal.model.android.Vector
import me.surzhenko.rodion.internal.model.svg.Svg
import me.surzhenko.rodion.internal.model.svg.SvgPath
import me.surzhenko.rodion.internal.parser.XmlProcessor
import me.surzhenko.rodion.internal.utils.toColor
import me.surzhenko.rodion.internal.utils.toHexString
import org.junit.Assert
import org.junit.Test
import java.io.File

class XmlProcessorTest {

    @Test
    fun manifestDeserialize() {
        val xmlProcessor = XmlProcessor()

        val manifestSource = File("test/manifest.xml")
        val manifest = xmlProcessor.deserialize<Manifest>(manifestSource)

        Assert.assertNotNull(manifest)
    }

    @Test
    fun svgDeserialize() {
        val xmlProcessor = XmlProcessor()

        val svgSource = File("test/svg.xml")
        val svg = xmlProcessor.deserialize<Svg>(svgSource)

        Assert.assertNotNull(svg)
    }

    @Test
    fun adaptiveIconDeserialize() {
        val xmlProcessor = XmlProcessor()

        val adaptiveIconSource = File("test/ic_launcher.xml")
        val adaptiveIcon = xmlProcessor.deserialize<AdaptiveIcon>(adaptiveIconSource)

        Assert.assertNotNull(adaptiveIcon)
    }

    @Test
    fun vectorDeserialize() {
        val xmlProcessor = XmlProcessor()

        val vectorSource = File("test/ic_app_icon_72.xml")
        val vector = xmlProcessor.deserialize<Vector>(vectorSource)

        Assert.assertNotNull(vector)
    }

    @Test
    fun vectorSerialize() {
        val xmlProcessor = XmlProcessor()

        val vectorSource = File("test/ic_app_icon_72.xml")
        val vector = xmlProcessor.deserialize<Vector>(vectorSource)

        val svgSource = File("test/svg.xml")
        val svg = xmlProcessor.deserialize<Svg>(svgSource)

        val svgPathes = mutableListOf<SvgPath>().apply { svg.groups?.forEach { group -> group.excludePath(this) } }
        val androidPath = svgPathes.map { svgPath ->
            Path().apply {
                this.pathData = svgPath.pathData
                this.strokeWidth = "1"
                this.fillType = Path.FillType.EVENODD.value
                this.fillColor = svgPath.fillColor?.toColor()?.toHexString()
                this.strokeColor = svgPath.strokeColor?.toColor()?.toHexString()
            }
        }

        vector.path?.addAll(androidPath)

        val vectorString = xmlProcessor.serialize(vector)
        val restoredVector = xmlProcessor.deserialize<Vector>(vectorString)

        Assert.assertEquals(vector.height, restoredVector.height)
        Assert.assertEquals(vector.width, restoredVector.width)
        Assert.assertEquals(vector.viewportHeight, restoredVector.viewportHeight)
        Assert.assertEquals(vector.viewportWidth, restoredVector.viewportWidth)
        Assert.assertEquals(vector.path?.size, restoredVector.path?.size)
    }
}