package me.surzhenko.rodion

import org.gradle.api.Plugin
import org.gradle.api.Project

class TapePlugin : Plugin<Project> {

    companion object {
        val TAG: String = TapePlugin::class.java.simpleName
    }

    override fun apply(project: Project) {
        IconProcessor().processAndroid(project)
    }
}