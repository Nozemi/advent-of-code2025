package io.nozemi.aoc

import io.nozemi.aoc.library.cli.ansi.AnsiColor
import io.nozemi.aoc.library.cli.ascii.AsciiFont
import java.nio.file.Path
import kotlin.io.path.Path

private val fontsDir: Path = Path("data", "fonts")

fun main(args: Array<String>) {
    val fonts = listOf(
        //AsciiFont(fontsDir.resolve("fraktur.txt")),
        //AsciiFont(fontsDir.resolve("basic.txt")),
        AsciiFont(fontsDir.resolve("big_ascii_12.txt")),
        //AsciiFont(fontsDir.resolve("nscript.txt")),
    )

    fonts.forEach {
        val twenty = it.parse("20") {
            bold = true
            color = AnsiColor.RED
        }
        val twentyFive = it.parse("25") {
            bold = true
            color = AnsiColor.RED
        }
        //twenty.appendRaw("|##|") {
        //    color = AnsiColor.YELLOW
        //    bold = true
        //    charSpacing = 2
        //}

        val outputText = twenty.append(twentyFive)

        println(outputText)
        println()
    }

    fonts.forEach {
        val he = it.parse("He") {
            color = AnsiColor.GREEN
        }

        val ll = it.parse("ll") {
            color = AnsiColor.GREEN
        }

        val `o!` = it.parse("o!") {
            color = AnsiColor.GREEN
            bold = true
        }

        val output = he.append(ll)
            .append(`o!`)

        println(output)
        println()
    }
}


//val rendered = text.asAsciiArt(fraktur) {
//    charSpacing = 0
//    color = AnsiColor.RED
//    bold = true
//}


//val resolver = PuzzleResolver()

//resolver.resolvePuzzles()

//if (!inputDownloader.hasAccessToken)
//    AocTokenCommand().main(emptyList())

//ChallengeSelectScreen(resolver)
//    .context {
//        terminal = Terminal(ansiLevel = AnsiLevel.TRUECOLOR, interactive = true)
//    }.main(args)