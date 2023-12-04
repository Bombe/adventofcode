plugins {
	kotlin("jvm") version "1.9.21"
	java
}

group = "net.pterodactylus"

repositories {
	mavenCentral()
}

kotlin {
	jvmToolchain(21)
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation(kotlin("test"))
	implementation("org.junit.jupiter", "junit-jupiter-api", "5.10.1")
	implementation("org.hamcrest", "hamcrest", "2.2")
	implementation("javax.xml.bind", "jaxb-api", "2.3.1")
	implementation("com.fasterxml.jackson.core", "jackson-databind", "2.16.0")
}
