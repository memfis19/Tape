package me.surzhenko.rodion.internal.drawer.impl

import me.surzhenko.rodion.internal.drawer.IconDrawer
import me.surzhenko.rodion.internal.model.Size
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class RasterIconDrawer(iconFile: File) : IconDrawer(iconFile) {

    private val bufferedImage: BufferedImage by lazy { ImageIO.read(iconFile) }

    init {
        // We want our font to come out looking pretty
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Fix for Android Studio issue: Could not find class: apple.awt.CGraphicsEnvironment
        try {
            Class.forName(System.getProperty("java.awt.graphicsenv"))
        } catch (error: ClassNotFoundException) {
            System.err.println("[WARN] java.awt.graphicsenv: " + error)
            System.setProperty("java.awt.graphicsenv", "sun.awt.CGraphicsEnvironment")
        }

        //  Fix for AS issue: Toolkit not found: apple.awt.CToolkit
        try {
            Class.forName(System.getProperty("awt.toolkit"))
        } catch (error: ClassNotFoundException) {
            System.err.println("[WARN] awt.toolkit: " + error)
            System.setProperty("awt.toolkit", "sun.lwawt.macosx.LWCToolkit")
        }
    }

    override fun getGraphics(iconFile: File): Graphics2D {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().createGraphics(bufferedImage)
                .apply { setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON) }
    }

    override fun getSize(iconFile: File): Size {
        return Size(bufferedImage.width, bufferedImage.height)
    }

    override fun saveIcon(iconFile: File, output: File) {
        ImageIO.write(bufferedImage, "png", output)
    }
}