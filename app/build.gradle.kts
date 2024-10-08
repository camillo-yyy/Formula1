/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    application
    id("buildlogic.java-application-conventions")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.gradleup.shadow") version "8.3.2"
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":api"))
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

application {
    // Define the main class for the application.
    mainClass = "it.unicam.cs.formula1.app.App"
}
