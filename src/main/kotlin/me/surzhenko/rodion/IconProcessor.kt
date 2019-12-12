package me.surzhenko.rodion

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask
import me.surzhenko.rodion.internal.drawer.impl.RasterIconDrawer
import me.surzhenko.rodion.internal.drawer.impl.VectorIconDrawer
import me.surzhenko.rodion.internal.model.android.AdaptiveIcon
import me.surzhenko.rodion.internal.model.android.Manifest
import me.surzhenko.rodion.internal.parser.XmlProcessor
import me.surzhenko.rodion.internal.utils.Log
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.file.collections.ImmutableFileCollection
import java.io.File

class IconProcessor {

    private var xmlProcessor = XmlProcessor()
    private val variantMap = mutableMapOf<String, File>()
    private val cacheVectors = mutableMapOf<String, File>()

    fun processAndroid(project: Project) {
        if (!project.plugins.hasPlugin(AppPlugin::class.java)) {
            return
        }

        val ribbonSettings = TapeSettings.DEFAULT
        project.extensions.add(TapeSettings.TAPE, ribbonSettings)

        project.afterEvaluate { evaluatedProject ->

            if (!ribbonSettings.enabled) return@afterEvaluate

            Log.i(TapePlugin.TAG, "$ribbonSettings")

            val appExtension = evaluatedProject.extensions.findByType(AppExtension::class.java)
            appExtension?.applicationVariants?.all { applicationVariant ->

                if (!ribbonSettings.buildTypes.contains(applicationVariant.buildType.name)) {
                    Log.w(TapePlugin.TAG, "Build type '${applicationVariant.buildType.name}' does not supported.")
                }

                val sources = appExtension.sourceSets?.asMap?.values?.map { it.res.srcDirs }?.flatten()
                val existedManifestFiles = appExtension.sourceSets?.map { it.manifest.srcFile }?.filter { it.exists() }
                val applicationIconsFiles = mutableListOf<File>()

                existedManifestFiles?.forEach { manifestFile ->
                    getIconName(manifestFile)?.let { iconFileName ->
                        val resDir = ImmutableFileCollection.of(sources?.toMutableList()?.also { it.add(manifestFile.parentFile.parentFile) }
                                ?: emptyList())
                        applicationIconsFiles.addAll(findIcons(iconFileName, resDir as FileCollection))
                    }
                }

                variantMap[applicationVariant.name] = createResDir(project, applicationVariant).apply {
                    appExtension.sourceSets?.findByName(applicationVariant.name)?.res?.srcDir(this)
                }

                applicationVariant.outputs.forEach { output ->
                    if (ribbonSettings.buildTypes.contains(applicationVariant.buildType.name)) {
                        output.processResourcesProvider.get().doFirst { task ->
                            (task as? LinkApplicationAndroidResourcesTask)?.let { processAndroidResource ->
                                Log.i(TapePlugin.TAG, "Draw for variant: ${processAndroidResource.variantName}")

                                variantMap[processAndroidResource.variantName]?.also {
                                    applicationIconsFiles.forEach { iconFile -> draw(processAndroidResource.variantName, iconFile, createOutputFile(iconFile, it), ribbonSettings) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    internal fun createResDir(project: Project, applicationVariant: ApplicationVariant): File {
        return File(project.buildDir, "generated/tape/res/${applicationVariant.name}")
    }

    private fun createOutputFile(icon: File, output: File): File {
        val iconName = icon.name
        val resType = icon.parentFile.name

        val newFile = File(output, "$resType/$iconName")
        newFile.parentFile.mkdirs()

        return newFile
    }

    private fun draw(variantName: String, iconFile: File, output: File, tapeSettings: TapeSettings) {
        Log.i(TapePlugin.TAG, "Drawing over icon: ${iconFile.absolutePath}")

        if (isVectorDrawable(iconFile)) {
            if (cacheVectors.containsKey(variantName)) cacheVectors[variantName]?.readText()?.also { text -> output.writeText(text) }
            else VectorIconDrawer(iconFile).drawOverIcon(tapeSettings, output).apply { cacheVectors[variantName] = output }
        } else {
            RasterIconDrawer(iconFile).drawOverIcon(tapeSettings, output)
        }
    }

    private fun findIcons(iconName: String, where: FileCollection): List<File> {
        val iconFiles = mutableListOf<File>()

        findFileByName(where, iconName) { icon ->
            Log.i(TapePlugin.TAG, "Found icon: ${icon.absolutePath}")

            if (isAdaptiveIcon(icon)) {
                val adaptiveIcon = xmlProcessor.deserialize<AdaptiveIcon>(icon)
                adaptiveIcon.foreground?.getIconFilename()?.let { foregroundIcon ->
                    findFileByName(where, foregroundIcon) { iconFiles.add(it) }
                }
            } else iconFiles.add(icon)
        }

        Log.i(TapePlugin.TAG, "Found icons: $iconFiles")

        return iconFiles
    }

    internal fun getIconName(manifestFile: File): String? {
        if (manifestFile.isDirectory || !manifestFile.exists()) {
            return null
        }
        val manifest = xmlProcessor.deserialize<Manifest>(manifestFile)

        Log.i(TapePlugin.TAG, "Application icon name: ${manifest.application?.getIconFilename()}")

        return manifest.application?.getIconFilename()
    }

    internal fun findFileByName(where: FileCollection, filename: String, action: (file: File) -> Unit) {
        where.files.forEach { file ->
            if (file.isDirectory) findFileByName(ImmutableFileCollection.of(file.listFiles()?.asList()
                    ?: emptyList()), filename, action)
            else if (file.nameWithoutExtension == filename) action.invoke(file)
        }
    }

    private fun isAdaptiveIcon(icon: File): Boolean = icon.extension == "xml"

    private fun isVectorDrawable(icon: File): Boolean = icon.extension == "xml"
}
