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
