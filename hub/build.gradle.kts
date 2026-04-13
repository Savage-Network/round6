plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "rip.rafa.event"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.viaversion.com/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") {
        exclude(group = "net.md-5", module = "bungeecord-chat")
    }
    compileOnly("net.md-5:bungeecord-chat:1.16-R0.4")
    compileOnly("com.viaversion:viaversion-api:4.9.2")
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks {
    jar {
        archiveFileName.set("Hub.jar")
        
        // Incluir dependências no JAR
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
            exclude("META-INF/*.SF")
            exclude("META-INF/*.DSA")
            exclude("META-INF/*.RSA")
        }
        
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
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
