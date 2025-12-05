package io.nozemi.aoc.library.cli.ascii

import io.nozemi.aoc.library.cli.ansi.ANSI_BOLD
import io.nozemi.aoc.library.cli.ansi.ANSI_RESET

class AsciiArtText(private val asciiText: String, private val font: AsciiFont? = null) : Iterable<String> {
    val rows get() = asciiText.lines().size
    val columns get() = asciiText.lines().maxByOrNull { it.length } ?: 0

    fun appendRaw(text: String, options: AsciiArtRenderOptionsBuilder.() -> Unit = {}): AsciiArtText {
        val options = AsciiArtRenderOptionsBuilder().apply(options ).build()

        val textRows = text.split("\n").size
        var text = text
        if (textRows < rows) {
            repeat((rows - textRows) - 1) {
                text += "\n$text"
            }
        } else if (textRows > rows)
            text = text.split("\n").take(rows).joinToString("\n")


        text = text.split("\n")
            .joinToString("\n") {
                val sb = StringBuilder()
                val padding = StringBuilder()

                repeat(options.charSpacing ?: font?.spacing ?: 0) {
                    padding.append(' ')
                }

                sb.append(padding)
                sb.append(options.color ?: "")

                if (options.bold)
                    sb.append(ANSI_BOLD)

                sb.append(it)
                sb.append(padding)

                sb.append(ANSI_RESET)

                sb.toString()
            }

        return this.append(AsciiArtText(text))
    }

    fun append(text: AsciiArtText): AsciiArtText {
        val textLines = this.toString().split("\n").toMutableList()
        for (i in 0..<this.rows) {
            textLines[i] += text[i]
        }

        return AsciiArtText(textLines.joinToString("\n"))
    }

    operator fun get(row: Int): String = asciiText.split("\n")[row]

    override fun iterator(): Iterator<String> = asciiText.lines().iterator()

    override fun toString(): String = asciiText
}