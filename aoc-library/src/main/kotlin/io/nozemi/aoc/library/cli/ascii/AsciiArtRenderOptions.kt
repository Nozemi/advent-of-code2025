package io.nozemi.aoc.library.cli.ascii

import io.nozemi.aoc.library.cli.ansi.AnsiColor

data class AsciiArtRenderOptions(
    val color: AnsiColor? = null,
    val bold: Boolean = false,
    val charSpacing: Int? = null
)