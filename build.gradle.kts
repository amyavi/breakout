plugins {
    checkstyle
    java
}

group = project.property("group")!!
version = project.property("version")!!

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(libs.fabric.loader)
    implementation(libs.paper.api)
}

java {
    val javaVersion = JavaVersion.toVersion(libs.versions.java.get())
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "com.github.amyavi.breakout.java.BreakoutMain")
    }
}

tasks.processResources {
    dependsOn("breakout-native:build")
    from("breakout-native/build/lib/main/release/libbreakout-native.so")

    val properties = mapOf(
        "version" to version,
        "java_version" to libs.versions.java.get(),
    )

    inputs.properties(properties)
    filesMatching(listOf("fabric.mod.json", "paper-plugin.yml", "plugin.yml")) {
        expand(properties)
    }
}
