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
}

dependencies {
	implementation("org.springdoc:springdoc-openapi-ui:1.6.3")

	//implementation(platform("org.testcontainers:testcontainers-bom:1.17.3"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	implementation("io.micrometer", "micrometer-registry-prometheus")
	implementation("com.google.code.gson", "gson", "2.10")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//implementation("io.klogging:klogging-jvm:0.4.13")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	implementation("com.zaxxer", "HikariCP")

	implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
	implementation("org.postgresql", "postgresql")
	implementation("org.testcontainers", "postgresql", "1.17.6")
	implementation("org.liquibase", "liquibase-core")

	implementation("org.testcontainers", "testcontainers", "1.17.6")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("space.kscience:kmath-core:0.3.1-dev-RC")
	implementation("space.kscience:kmath-stat:0.3.1-dev-RC")
}

tasks {
	val copyDockerfile = register<Copy>("copyDockerfile") {
		from("${rootProject.rootDir}/docker/Dockerfile")
		into("$buildDir")
	}

	register<Exec>("buildDockerImage") {
		workingDir("$buildDir")
		executable("docker")
		args(listOf("build", "-t", "teststand", "."))

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

tasks.withType<Test> {
	useJUnitPlatform()
}
