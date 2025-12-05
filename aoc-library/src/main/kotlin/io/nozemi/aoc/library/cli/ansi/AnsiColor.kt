package io.nozemi.aoc.library.cli.ansi

enum class AnsiColor(val style: String) {
    BLACK(ANSI_BLACK),
    RED(ANSI_RED),
    GREEN(ANSI_GREEN),
    YELLOW(ANSI_YELLOW),
    BLUE(ANSI_BLUE),
    PURPLE(ANSI_PURPLE),
    CYAN(ANSI_CYAN),
    WHITE(ANSI_WHITE)
    ;

    override fun toString(): String = style
}