plugins {
    java
    id("io.freefair.lombok") version "8.4" apply false
}

allprojects {
    group = "com.round6.event"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
