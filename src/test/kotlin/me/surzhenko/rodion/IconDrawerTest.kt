package me.surzhenko.rodion

import me.surzhenko.rodion.internal.drawer.impl.RasterIconDrawer
import me.surzhenko.rodion.internal.drawer.impl.VectorIconDrawer
import org.junit.Test
import java.io.File

class IconDrawerTest {

    @Test
    fun rasterDraw() {
        val drawer = RasterIconDrawer(File("test/ic_launcher.png"))
        drawer.drawOverIcon(TapeSettings.DEFAULT.apply {
            text = arrayOf("Prod", "Debug")
            stripeColor = 0x99991155.toInt()
            textColor = 0xFFDDAA11.toInt()
        }, File("raster.png").apply { if (!exists()) createNewFile() })
    }

    @Test
    fun vectorDraw() {
        val drawer = VectorIconDrawer(File("test/ic_app_icon_72.xml"))
        drawer.drawOverIcon(TapeSettings.DEFAULT.apply {
            text = arrayOf("Prod", "Debug")
            stripeColor = 0x99991155.toInt()
            textColor = 0x99DDAA11.toInt()
        }, File("vector.xml").apply { if (!exists()) createNewFile() })
    }

}