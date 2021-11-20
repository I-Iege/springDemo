import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("plugin.jpa") version "1.6.0"
}

group = "com.hege"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	val springVersion = "2.5.6"
	//spring
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-aop:$springVersion")
	developmentOnly("org.springframework.boot:spring-boot-devtools:$springVersion")
	runtimeOnly("org.aspectj:aspectjrt:1.9.7")
	implementation("org.aspectj:aspectjweaver:1.9.7")


	//kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")

	//test
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
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
