package io.nozemi.aoc.library.cli.ascii

import io.nozemi.aoc.library.cli.ansi.AnsiColor

data class AsciiArtRenderOptionsBuilder(
    var charSpacing: Int? = null,
    var color: AnsiColor? = null,
    var bold: Boolean = false
) {
    fun build(): AsciiArtRenderOptions = AsciiArtRenderOptions(
        charSpacing = charSpacing,
        color = color,
        bold = bold
    )
}