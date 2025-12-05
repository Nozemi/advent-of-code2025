package io.nozemi.aoc.library.puzzle

import io.github.classgraph.ClassGraph
import io.nozemi.aoc.library.puzzle.exceptions.HasAlreadyDownloadedException
import io.nozemi.aoc.library.puzzle.exceptions.InputFileDownloadFailedException
import io.nozemi.aoc.library.puzzle.exceptions.NoDataProvidedException
import io.nozemi.aoc.library.puzzle.exceptions.NoDownloadTokenProvidedException
import java.lang.reflect.InvocationTargetException
import kotlin.collections.set
import kotlin.time.measureTime

typealias PuzzleMap = MutableMap<Int, MutableMap<Int, String>>

class PuzzleResolver(private val basePackage: String = "io.nozemi.aoc.solutions") {
    private val dayAndYearRegex = "$basePackage\\.year(\\d{4})\\.day(\\d{2})".toRegex()

    private val puzzleMap: PuzzleMap = mutableMapOf()

    fun resolvePuzzles() {
        val duration = measureTime {
            ClassGraph().enableAllInfo().acceptPackages(basePackage).scan()
                .getSubclasses(AbstractPuzzle::class.java).forEach {
                    val result = dayAndYearRegex.find(it.packageName) ?: return@forEach
                    val year = result.groupValues[1].toInt()
                    val day = result.groupValues[2].toInt()
                    val yearsPuzzles = puzzleMap[year] ?: mutableMapOf()
                    yearsPuzzles[day] = it.name
                    puzzleMap[year] = yearsPuzzles
                }
        }

        println("Resolved ${puzzleMap.flatMap { it.value.values }.size} puzzles in $duration.")
        println()
    }

    val allPuzzles: List<String> = puzzleMap.flatMap { it.value.values }

    fun hasPuzzlesForYear(year: Int): Boolean {
        return puzzleMap.containsKey(year)
    }

    fun getPuzzle(year: Int, day: Int): AbstractPuzzle<*>? {
        val puzzleClass = puzzleMap[year]?.get(day)
            ?: return null

        val puzzleInstance: AbstractPuzzle<*>
        try {
            puzzleInstance = Class.forName(puzzleClass)
                .getDeclaredConstructor()
                .newInstance() as AbstractPuzzle<*>
        } catch(exception: InvocationTargetException) {
            if (exception.cause?.message?.contains("No data provided") == true) {
                throw NoDataProvidedException()
            }

            if (exception.cause?.message?.contains("provided for downloading") == true) {
                throw NoDownloadTokenProvidedException()
            }

            if (exception.cause?.message?.contains("attempt to download has already") == true) {
                throw HasAlreadyDownloadedException()
            }

            if (exception.cause != null && exception.cause.toString().contains("InputFileDownloadFailedException")) {
                throw InputFileDownloadFailedException(exception.cause?.message ?: "Downloading input file failed.")
            }

            throw exception
        }

        return puzzleInstance
    }
}