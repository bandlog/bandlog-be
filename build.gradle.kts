plugins {
    java
}

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



