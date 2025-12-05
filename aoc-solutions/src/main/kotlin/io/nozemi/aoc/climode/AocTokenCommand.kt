package io.nozemi.aoc.climode

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import io.nozemi.aoc.library.puzzle.inputDownloader
import java.nio.file.Files

class AocTokenCommand : CliktCommand() {
    val token: String by option(
        names = arrayOf("--token"),
        help = "The advent of code token found in your browser cookies"
    ).prompt(
        default = null
    )

    override fun run() {
        if (token.isNotBlank()) {
            Files.writeString(inputDownloader.tokenFile, token)
            echo("AOC token was written...")
        }
    }
}