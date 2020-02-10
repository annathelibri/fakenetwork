import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    maven
    `maven-publish`
    id("com.github.ben-manes.versions") version "0.21.0"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "pw.aru.libs"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val sourceJar = task("sourceJar", Jar::class) {
    dependsOn(tasks["classes"])
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    publications.create("mavenJava", MavenPublication::class.java) {
        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()

        from(components["java"])
        artifact(sourceJar)
    }
}

fun findProperty(s: String) = project.findProperty(s) as String?
bintray {
    user = findProperty("bintrayUsername")
    key = findProperty("bintrayApiKey")
    publish = true
    setPublications("mavenJava")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = project.name
        userOrg = "arudiscord"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/arudiscord/fakenetwork.git"
    })
}

tasks.withType<BintrayUploadTask> {
    dependsOn("build", "publishToMavenLocal")
}
