package me.surzhenko.rodion.internal.drawer

import me.surzhenko.rodion.TapeSettings
import me.surzhenko.rodion.internal.model.Size
import me.surzhenko.rodion.internal.utils.toColor
import java.awt.Font
import java.awt.Graphics2D
import java.io.File


abstract class IconDrawer(protected val iconFile: File) {

    fun drawOverIcon(tapeSettings: TapeSettings, output: File = iconFile) {
        draw(getGraphics(iconFile), getSize(iconFile), tapeSettings)
        saveIcon(iconFile, output)
    }

    protected abstract fun getGraphics(iconFile: File): Graphics2D

    protected abstract fun getSize(iconFile: File): Size

    protected abstract fun saveIcon(iconFile: File, output: File = iconFile)

    private fun draw(graphics: Graphics2D, size: Size, tapeSettings: TapeSettings) {
        val imageWidth: Int = size.first
        val imageHeight: Int = size.second

        val linePadding = tapeSettings.verticalLinePadding
        val fontSize = tapeSettings.fontSize
        val lineCount: Int = tapeSettings.text.size

        val totalLineHeight: Int = (fontSize * lineCount) + ((linePadding) * lineCount)

        graphics.color = tapeSettings.stripeColor.toColor()
        graphics.fillRect(0, imageHeight - totalLineHeight, imageWidth, totalLineHeight)

        // Draw each line of our text
        graphics.font = Font(Font.SANS_SERIF, Font.PLAIN, fontSize)
        graphics.color = tapeSettings.textColor.toColor()
        tapeSettings.text.reversed().forEachIndexed { index, line ->
            val strWidth = graphics.fontMetrics.stringWidth(line)

            var x = 0;
            if (imageWidth >= strWidth) {
                x = ((imageWidth - strWidth) / 2)
            }

            val y = imageHeight - (fontSize * index) - ((index + 1) * linePadding)

            graphics.drawString(line, x, y)
        }
    }
}