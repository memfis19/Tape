package me.surzhenko.rodion.internal.utils

import java.awt.Color
import java.util.regex.Pattern

fun Int.toColor(): Color {
    val blue = this and 0xFF
    val green = this shr 8 and 0xFF
    val red = this shr 16 and 0xFF
    val alpha = this shr 24 and 0xFF

    return Color(red, green, blue, if (alpha == 0) 255 else alpha)
}

fun String.toColor(alpha: Int = 255): Color? {
    val colorPattern = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)")
    val colorMatcher = colorPattern.matcher(this)

    if (colorMatcher.matches()) {
        Color(1, 1, 1, alpha).toString()

        return Color(Integer.valueOf(colorMatcher.group(1)), // r
                Integer.valueOf(colorMatcher.group(2)), // g
                Integer.valueOf(colorMatcher.group(3)), // b
                alpha) //a
    }
    return null
}

fun String.toSizeOrNull(): Int? = this.replace("[^0-9.]".toRegex(), "").toIntOrNull()

fun Color.toHexString(): String = String.format("#%02x%02x%02x%02x", alpha, red, green, blue)