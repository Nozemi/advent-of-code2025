package io.nozemi.aoc

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.terminal.Terminal
import io.nozemi.aoc.climode.AocTokenCommand
import io.nozemi.aoc.climode.SelectPuzzleCommand
import io.nozemi.aoc.library.cli.ansi.*
import io.nozemi.aoc.library.puzzle.InputDownloader.Companion.inputDownloader
import io.nozemi.aoc.library.puzzle.PuzzleResolver
import java.nio.file.Path
import kotlin.io.path.Path

private val fontsDir: Path = Path("data", "fonts")


private const val heading1 = """
===================================================================================================================
||                                                          ,, gp                                                ||                                               
|| `7MN.   `7MF'                                            db \/                  db                 .g8""${'"'}bgd  ||
||   MMN.    M                                                 `'                 ;MM:              .dP'     `M  ||
||   M YMb   M  ,pW"Wq.  M""${'"'}MMV .gP"Ya `7MMpMMMb.pMMMb.  `7MM   ,pP"Ybd         ,V^MM.    ,pW"Wq.  dM'       `  ||
||   M  `MN. M 6W'   `Wb '  AMV ,M'   Yb  MM    MM    MM    MM   8I   `"        ,M  `MM   6W'   `Wb MM           ||
||   M   `MM.M 8M     M8   AMV  8M""${'"'}${'"'}${'"'}${'"'}  MM    MM    MM    MM   `YMMMa.        AbmmmqMA  8M     M8 MM.          ||
||   M     YMM YA.   ,A9  AMV  ,YM.    ,  MM    MM    MM    MM   L.   I8       A'     VML YA.   ,A9 `Mb.     ,'  ||
|| .JML.    YM  `Ybmd9'  AMMmmmM `Mbmmd'.JMML  JMML  JMML..JMML. M9mmmP'     .AMA.   .AMMA.`Ybmd9'    `"bmmmd'   ||
===================================================================================================================
"""
private const val heading2 = """
d8b   db  .d88b.  d88888D d88888b .88b  d88. d888888b Cb .d8888.       .d8b.   .d88b.   .o88b. 
888o  88 .8P  Y8. YP  d8' 88'     88'YbdP`88   `88'   `D 88'  YP      d8' `8b .8P  Y8. d8P  Y8 
88V8o 88 88    88    d8'  88ooooo 88  88  88    88     ' `8bo.        88ooo88 88    88 8P      
88 V8o88 88    88   d8'   88~~~~~ 88  88  88    88         `Y8b.      88~~~88 88    88 8b      
88  V888 `8b  d8'  d8' db 88.     88  88  88   .88.      db   8D      88   88 `8b  d8' Y8b  d8 
VP   V8P  `Y88P'  d88888P Y88888P YP  YP  YP Y888888P    `8888Y'      YP   YP  `Y88P'   `Y88P' 
"""

private const val g = "$ANSI_RESET$ANSI_RED"
private const val t = "$ANSI_BOLD$ANSI_GREEN"
private const val r = ANSI_RESET

private const val heading3 = """$r$g
     .-') _                 .-') _   ('-.  _   .-')                 .-')            ('-.                             
    ( OO ) )               (  OO) )_(  OO)( '.( OO )_         $t,--.$g ( OO ).         ( OO ).-.$t                         
,--.$g/$t ,--,$g'  .-')$t,-----. ,$g(_)$t----.$g($t,------.,--.   ,--.$g)$t ,-.-$g')$t\  |$g(_)$t---$g\_)$t        $g/$t . --. $g/ .-')$t,-----.    .-----.  
|   \ |  |$g\ ( OO$t'  .-.  '|       | |  .---'|   `.'   |  |  |${g}OO)$t`-'/    _ |         | \-.  \ $g( OO'$t  .-.  '  '  .--./  
|    \|  |$g )/   $t|  | |  |'--.   /  |  |    |         |  |  |  $g\$t   \  :` `.       .-'-'  |  |$g/$t   |  | |  |  |  |$g('-.$t  
|  .     |$g/ \_) $t|  |$g\$t|  |  /   /   |  '--. |  |'.'|  |  |  |$g(_/$t    '..`''.        \| |_.'  |$g\_)$t |  |$g\$t|  | $g/_)$t |${g}OO  )$t 
|  |\    |    $g\$t |  | |  | /   /___ |  .--' |  |   |  | $g,$t|  |${g}_.'$t   .-._)   \        |  .-.  |  $g\$t |  | |  | $g|$t|  |$g`-'|$t  
|  | \   |     $g`$t'  '-'  '|        ||  `---.|  |   |  |$g(_$t|  |      \       /        |  | |  |   $g`$t'  '-'  '$g(_$t'  '--'\  
`--'  `--'       `-----' `--------'`------'`--'   `--'  `--'       `-----'         `--' `--'     `-----'    `-----'$r
$ANSI_BOLD$ANSI_RED====================================================================================================================$r
"""

fun main(args: Array<String>) {
    println("$ANSI_BOLD$ANSI_RED$heading3$ANSI_RESET")

    val resolver = PuzzleResolver()

    resolver.resolvePuzzles()

    if (!inputDownloader.hasAccessToken)
        AocTokenCommand().main(emptyList())

    SelectPuzzleCommand(resolver)
        .context {
            terminal = Terminal(ansiLevel = AnsiLevel.TRUECOLOR, interactive = true)
        }.main(args)
}


//val rendered = text.asAsciiArt(fraktur) {
//    charSpacing = 0
//    color = AnsiColor.RED
//    bold = true
//}