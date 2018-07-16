package me.surzhenko.rodion.internal.utils

import java.text.SimpleDateFormat
import java.util.*

object Log {

    private val DATE_FORMAT = SimpleDateFormat("dd MM yyyy, HH:mm:ss")

    private val date: String
        get() = DATE_FORMAT.format(Date())

    private fun log(tag: String, message: String) {
        println("[$date][$tag]: $message")
    }

    fun i(tag: String, message: String) {
        log("[INFO][$tag]", message)
    }

    fun d(tag: String, message: String) {
        log("[DEBUG][$tag]", message)
    }

    fun w(tag: String, message: String) {
        log("[WARNING][$tag]", message)
    }

    fun e(tag: String, message: String) {
        log("[ERROR][$tag]", message)
    }
}