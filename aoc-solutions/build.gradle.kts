import org.gradle.kotlin.dsl.register

dependencies {
    implementation(project(":aoc-library"))

    implementation("com.github.ajalt.clikt:clikt:5.0.3")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.3")
}

tasks.register<JavaExec>("solveToday") {
    group = "advent-of-code"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("io.nozemi.aoc.ChallengeSelectScreenKt")
    standardInput = System.`in`
}