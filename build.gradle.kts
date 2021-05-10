plugins {
    groovy
    java
}

group = "tools"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
        vendor.set(JvmVendorSpec.ADOPTOPENJDK)
    }

    version = JavaVersion.VERSION_16
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testImplementation("org.spockframework:spock-core:2.0-M4-groovy-3.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.19.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}