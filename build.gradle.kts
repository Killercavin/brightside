val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    kotlin("jvm") version "2.1.20"
    id("io.ktor.plugin") version "3.1.1"
    kotlin("plugin.serialization") version "1.9.0"
}

group = "com.brightside"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Default ktor dependencies
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:0.43.0") // time stamp
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.8.0")
    implementation("io.ktor:ktor-server-core:2.3.7")
    implementation("io.ktor:ktor-server-netty:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:11.8.2") // Latest stable Flyway core
    implementation("org.flywaydb:flyway-database-postgresql:11.8.2") // Flyway PostgresSQL plugin
    implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("com.auth0:java-jwt:4.5.0")
    implementation("io.lettuce:lettuce-core:6.6.0.RELEASE") // Lettuce core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.10.2") // Coroutine support for Java CompletableFuture interop
    implementation("io.ktor:ktor-server-status-pages")
}

// configuration for JDK version
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
