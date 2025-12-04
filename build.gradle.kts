plugins {
    kotlin("jvm") version "2.2.20"
}

allprojects {
    apply(plugin = "kotlin")

    group = "io.nozemi"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    kotlin {
        jvmToolchain(23)
    }

    tasks.test {
        useJUnitPlatform()
    }
}

subprojects {
    dependencies {
        implementation("com.michael-bull.kotlin-result:kotlin-result:2.1.0")
    }
}