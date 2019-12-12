package me.surzhenko.rodion

import org.gradle.api.internal.file.collections.ImmutableFileCollection
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test
import java.io.File

class TapePluginTest {

    @Test
    fun applyPlugin() {
        val project = ProjectBuilder.builder().build()

        project.pluginManager.apply("com.android.application")
        project.pluginManager.apply("icon-tape")

        val extension = project.extensions.findByName(TapeSettings.TAPE)

        Assert.assertTrue(extension is TapeSettings)
    }

    @Test
    fun findIconFile() {
        val icons = mutableListOf<File>()
        IconProcessor().findFileByName(ImmutableFileCollection.of(listOf(File("test/src"))), "ic_launcher") {
            icons.add(it)
        }
        Assert.assertTrue(icons.isNotEmpty())
    }

}
