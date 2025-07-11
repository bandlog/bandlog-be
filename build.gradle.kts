plugins {
    java
}

group = "net.effize"
version = "0.0.1-SNAPSHOT"

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }
}



