plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.22"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation(kotlin("stdlib-jdk8"))
    //SQLite
    implementation("org.jetbrains.exposed:exposed-core:0.35.1")
    implementation ("org.jetbrains.exposed:exposed-jdbc:0.35.1")
    implementation("org.xerial:sqlite-jdbc:3.34.0")


}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}