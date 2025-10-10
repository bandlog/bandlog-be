plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":bandlog-shared"))
    implementation(project(":bandlog-user"))
    implementation(project(":bandlog-auth"))
    implementation(project(":bandlog-team"))
    implementation(project(":bandlog-rehearsal"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}