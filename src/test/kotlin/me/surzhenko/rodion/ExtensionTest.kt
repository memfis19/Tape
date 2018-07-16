package me.surzhenko.rodion

import me.surzhenko.rodion.internal.utils.toColor
import me.surzhenko.rodion.internal.utils.toHexString
import org.junit.Assert
import org.junit.Test
import java.awt.Color

class ExtensionTest {

    @Test
    fun colorConverting() {
        val alphaColor = 0x77991155.toColor()
        val color = 0x991155.toColor()

        Assert.assertNotEquals(color, alphaColor)
    }

    @Test
    fun colorParsing() {
        val color1 = "rgb(221,170,17)"
        val color2 = "rgb(153,17,85)"

        Assert.assertEquals(color1.toColor(), Color(221, 170, 17))
        Assert.assertEquals(color2.toColor(), Color(153, 17, 85))

        System.out.println("Hex1 value = ${color1.toColor()?.toHexString()}")
        System.out.println("Hex2 value = ${color2.toColor()?.toHexString()}")

    }

    @Test
    fun wrongColorParsing() {
        val wrongColor1 = "rgb(221,170,17, 12, 12)"
        val wrongColor2 = "rgb(221,170,17, 12)"
        val wrongColor3 = "1rgb(221,170,17, 12, 12)"
        val wrongColor4 = "rgb1(221,170,17, 12)"
        val wrongColor5 = "rb(221,170,17)"

        Assert.assertNull(wrongColor1.toColor())
        Assert.assertNull(wrongColor2.toColor())
        Assert.assertNull(wrongColor3.toColor())
        Assert.assertNull(wrongColor4.toColor())
        Assert.assertNull(wrongColor5.toColor())
    }

}