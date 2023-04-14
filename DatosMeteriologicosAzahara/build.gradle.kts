
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    //MOSHI para JSON
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    //PARA USAR NOSHI EN KOTLIN
    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")

    //SIMPLEXML
    implementation ("org.simpleframework:simple-xml:2.7.1")

    //DATAFRAME
    implementation("org.jetbrains.kotlinx:dataframe:0.9.0-dev-1130-0.11.0.165")



}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}


//JAR
tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}