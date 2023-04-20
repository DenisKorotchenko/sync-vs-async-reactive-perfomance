import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.10"
}

allprojects.forEach {
	it.group = "org.dksu"
	it.version = "0.0.1-SNAPSHOT"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}