plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.2"
}

group = "com.github.xtestw"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

intellij {
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))
    plugins.set(listOf("java"))
    downloadSources.set(true)
    updateSinceUntilBuild.set(true)
    instrumentCode.set(false)
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    patchPluginXml {
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))
    }
}

fun properties(key: String) = providers.gradleProperty(key).get()
