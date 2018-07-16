package me.surzhenko.rodion

class TapeSettings {

    companion object {
        const val TAPE = "tape"

        val DEFAULT: TapeSettings = TapeSettings()
    }

    @JvmField
    var enabled: Boolean = true

    @JvmField
    var buildTypes: Array<String> = arrayOf("debug")

    @JvmField
    var stripeColor: Int = 0xFFFFFF

    @JvmField
    var textColor: Int = 0x000000

    @JvmField
    var fontSize: Int = 14

    @JvmField
    var verticalLinePadding: Int = 5

    @JvmField
    var text: Array<String> = emptyArray()

    override fun toString(): String {
        return "TapeSettings:" +
                " \n enabled = $enabled" +
                " \n stripeColor = $stripeColor" +
                " \n textColor = $textColor" +
                " \n fontSize = $fontSize" +
                " \n buildTypes = ${buildTypes.asList()}" +
                " \n text = ${text.asList()}"
    }
}