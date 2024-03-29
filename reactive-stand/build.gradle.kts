import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.7.21"
    kotlin("plugin.jpa") version "1.7.21"
}

group = "org.dksu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
    maven("https://repo.spring.io/milestone/")
}

dependencies {
    val springDocVersion = "1.6.3"
    implementation("org.springdoc", "springdoc-openapi-webflux-core", springDocVersion)
    implementation("org.springdoc", "springdoc-openapi-webflux-ui", springDocVersion)

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("io.micrometer", "micrometer-registry-prometheus")
    implementation("com.google.code.gson", "gson", "2.10")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation("com.zaxxer", "HikariCP")

    implementation("org.postgresql", "postgresql")
    implementation("org.testcontainers", "postgresql", "1.17.6")
    implementation("org.testcontainers", "r2dbc", "1.17.6")
    implementation("org.liquibase", "liquibase-core")
    runtimeOnly("org.springframework", "spring-jdbc")

    implementation("org.testcontainers", "testcontainers", "1.17.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("space.kscience:kmath-core:0.3.1-dev-RC")
    implementation("space.kscience:kmath-stat:0.3.1-dev-RC")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.r2dbc", "r2dbc-spi", "1.0.0.RELEASE")
    implementation("io.r2dbc", "r2dbc-postgresql", "0.8.13.RELEASE")
    implementation("io.r2dbc", "r2dbc-pool", "0.8.2.RELEASE")
}

tasks {
    val copyDockerfile = register<Copy>("copyDockerfile") {
        from("${rootProject.rootDir}/docker/reactive/Dockerfile")
        into("$buildDir")
    }

    register<Exec>("buildDockerImage") {
        workingDir("$buildDir")
        executable("docker")
        args(listOf("build", "-t", "reactive-teststand", "."))

        dependsOn(bootJar)
        dependsOn(copyDockerfile)
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
