import org.gradle.kotlin.dsl.register
import java.time.MonthDay
import java.time.Year

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(project(":aoc-library"))

    implementation("com.github.ajalt.clikt:clikt:5.0.3")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.3")
}

val currentYear = Year.now().value
val currentDay = MonthDay.now().dayOfMonth

tasks.register<JavaExec>("solveToday") {
    group = "advent-of-code"
    workingDir = rootDir
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ChallengeSelectScreenKt")
    standardInput = System.`in`
    args = listOf(
        "-y$currentYear",
        "-d$currentDay"
    )
}