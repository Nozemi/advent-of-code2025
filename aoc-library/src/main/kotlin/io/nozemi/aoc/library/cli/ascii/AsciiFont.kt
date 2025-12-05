package io.nozemi.aoc.library.cli.ascii

import io.nozemi.aoc.library.cli.ansi.ANSI_BOLD
import io.nozemi.aoc.library.cli.ansi.ANSI_RESET
import io.nozemi.aoc.library.types.matrix.VariableCharMatrix
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.text.map

class AsciiFont(file: Path) {
    private val artMap: MutableMap<Char, VariableCharMatrix> = mutableMapOf()
    private var _name: String = ""
    private var _height: Int = 0
    private var _spacing: Int = 0

    val name get() = _name
    val height get() = _height
    val spacing get() = _spacing

    init {
        var character: Char? = null
        var artMatrix: MutableList<String> = mutableListOf()

        file.readLines().forEach { line ->
            when {
                line.startsWith("FONT:") -> {
                    _name = line.substringAfter("FONT:").trim()
                    return@forEach
                }

                line.startsWith("HEIGHT:") -> {
                    _height = line.substringAfter("HEIGHT:").trim().toInt()
                    return@forEach
                }

                line.startsWith("SPACING:") -> {
                    _spacing = line.substringAfter("SPACING:").trim().toInt()
                    return@forEach
                }
            }

            if (line.startsWith("C: ")) {
                if (character != null)
                    artMap[character] = VariableCharMatrix.from(artMatrix)

                character = line.substringAfter("C: ").trim()[0]
                artMatrix = mutableListOf()

                return@forEach
            }

            artMatrix.add(line)
        }

        if (character != null)
            artMap[character] = VariableCharMatrix.from(artMatrix)
    }

    fun parse(text: String, options: AsciiArtRenderOptionsBuilder.() -> Unit = {}): AsciiArtText {
        val options = AsciiArtRenderOptionsBuilder().apply(options ).build()

        val artBased = text.map {
            artMap[it] ?: artMap["$it".lowercase()[0]] ?: artMap["$it".uppercase()[0]]
        }.filterNotNull()

        val lines = mutableListOf<MutableList<Char>>()

        repeat(height) {
            lines.add(mutableListOf())
        }

        artBased.forEach { matrix ->
            matrix.forEach { line ->
                if (line.y >= lines.size)
                    return@forEach

                lines[line.y].add(line.value ?: ' ')
            }
            lines.forEach { line ->
                repeat(options.charSpacing ?: spacing) {
                    line.add(' ')
                }
            }
        }

        val artMatrix = VariableCharMatrix.from(
            lines.map { it.joinToString("") }
        )

        val rendered = "$artMatrix".split("\n").toMutableList()

        rendered.forEachIndexed { row, line ->
            val current = rendered[row]
            rendered[row] = "${if (options.bold) ANSI_BOLD else ""}${options.color}$current$ANSI_RESET"
        }

        return AsciiArtText(rendered.joinToString("\n"))
    }
}