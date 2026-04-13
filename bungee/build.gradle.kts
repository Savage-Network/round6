plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "rip.rafa.event"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.21-R0.1")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks {
    jar {
        archiveFileName.set("EventBungee.jar")
    }
    
    build {
        dependsOn(jar)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
