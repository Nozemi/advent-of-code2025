package io.nozemi.aoc.library.puzzle

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class InputDownloader {
    private val downloadUrlFormat: String = "https://adventofcode.com/{year}/day/{day}/input"
    private val inputDir = Path("./data").resolve("inputs")

    val tokenFile = Path("./data").resolve(".aoctoken")

    private var _aocToken: String? = null
    private val aocToken: String?
        get() = _aocToken ?: loadAocToken()

    val hasAccessToken: Boolean get() = aocToken != null

    private fun loadAocToken(): String? {
        if (!tokenFile.exists())
            return null

        _aocToken = tokenFile.readText()
        return _aocToken
    }

    private fun inputFile(year: Int, day: Int): Path =
        inputDir.resolve("year$year").resolve("day$day.txt")

    @OptIn(ExperimentalTime::class)
    fun downloadInput(year: Int? = null, day: Int? = null): Result<Path, Error> {
        if (!hasAccessToken)
            return Err(MissingTokenError())

        val now = Clock.System.now()
        val date = now.toLocalDateTime(TimeZone.currentSystemDefault())

        val year = year ?: date.year
        val day = day ?: date.day

        val inputFile = inputFile(year, day)

        if (aocToken == null)
            return Err(MissingTokenError())

        val url = URI.create(
            downloadUrlFormat
                .replace("{year}", "$year")
                .replace("{day}", "$day")
        ).toURL()

        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Cookie", "session=$aocToken")

            if (connection.responseCode != HttpURLConnection.HTTP_OK)
                return Err(DownloadInputError(connection.responseMessage))

            if (!inputFile.parent.exists())
                inputFile.parent.createDirectories()

            Files.newBufferedWriter(inputFile).use {
                it.write(connection.inputStream.bufferedReader().readText())
            }

            return Ok(inputFile)
        } catch (ex: IOException) {
            return Err(Error(ex.message, ex))
        }
    }

    companion object {
        val inputDownloader = InputDownloader()
    }

    class MissingTokenError : Error("No AoC token was provided")
    class DownloadInputError(override val message: String) : Error(message)
}