import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.10"
	id("io.gatling.gradle") version "3.9.3.1"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springdoc:springdoc-openapi-ui:1.6.3")
//
//	//implementation(platform("org.testcontainers:testcontainers-bom:1.17.3"))
//	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.boot:spring-boot-starter-web-services")
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
//
//	implementation("io.micrometer", "micrometer-registry-prometheus")
//
//	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//	implementation("org.jetbrains.kotlin:kotlin-reflect")
//	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//	//implementation("io.klogging:klogging-jvm:0.4.13")
//	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
//
//	implementation("com.zaxxer", "HikariCP")
//
//	implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
//	implementation("org.postgresql", "postgresql")
//	implementation("org.testcontainers", "postgresql", "1.17.3")
//	implementation("org.liquibase", "liquibase-core")
//
//	implementation("org.testcontainers", "testcontainers", "1.17.3")
//
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks {
	val copyDockerfile = register<Copy>("copyDockerfile") {
		from("${rootProject.rootDir}/docker/gatling/Dockerfile")
		into("${projectDir}")
	}

	register<Exec>("buildDockerImage") {
		workingDir("$projectDir")
		executable("docker")
		args(listOf("build", "-t", "teststand-gatling", "."))

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
