import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"
    kotlin("kapt") version "1.3.20"
    application
    id("com.google.cloud.tools.jib") version "1.0.0"
    id("com.diffplug.gradle.spotless") version "3.18.0"
}

val versionObj = Version(major = 3, minor = 0, revision = 0)

group = "com.grosslicht"
version = "$versionObj"

repositories {
    mavenCentral()
    jcenter()
}

val requeryVersion = "1.5.1"
val log4jVersion = "2.11.1"

dependencies {
    compile(kotlin("stdlib-jdk8"))

    compile("net.dv8tion:JDA:3.8.1_437") {
        exclude(module = "opus-java")
    }

    compile("org.slf4j:slf4j-api:1.7.25")
    compile("org.apache.logging.log4j:log4j-api:$log4jVersion")
    compile("org.apache.logging.log4j:log4j-core:$log4jVersion")
    compile("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    compile("io.github.microutils:kotlin-logging:1.6.22")

    compile("com.google.code.gson:gson:2.8.5")
    compile("com.github.kittinunf.fuel:fuel:2.0.0")
    compile("com.github.kittinunf.fuel:fuel-gson:2.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.named<ProcessResources>("processResources") {
    filesMatching("**/build.properties") {
        expand(project.properties)
    }
}

application {
    mainClassName = "com.grosslicht.unitbot.UnitBotKt"
}

val dockerImage = System.getenv("CONTAINER_NAME")

jib.to {
    image = dockerImage
    tags = setOf("latest", "${project.version}")
    auth {
        username = "pdgwien"
        password = System.getenv("DOCKER_PASSWORD")
    }
}

spotless {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint()
    }
}

data class Version(val major: Int, val minor: Int, val revision: Int) {
    private val buildNumber: String = System.getenv("TRAVIS_BUILD_NUMBER") ?: "dev"

    override fun toString(): String = "$major.$minor.$revision-$buildNumber"
}