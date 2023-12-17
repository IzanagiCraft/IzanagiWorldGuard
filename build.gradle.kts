import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import org.cadixdev.gradle.licenser.LicenseExtension
import org.cadixdev.gradle.licenser.Licenser

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")

    alias(libs.plugins.shadow)
    alias(libs.plugins.licenser)

    eclipse
    idea
}

group = "com.izanagicraft.guard"
version = "1.0-SNAPSHOT"
description = "Protects the IzanagiCraft worlds."

repositories {
    mavenCentral()
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://artifactory.izanagicraft.tech/repository/maven-snapshots/") }
    mavenLocal()
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
        maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
        maven { url = uri("https://artifactory.izanagicraft.tech/repository/maven-snapshots/") }
        mavenLocal()
    }

    apply {
        plugin<JavaPlugin>()
        plugin<JavaLibraryPlugin>()
        plugin<MavenPublishPlugin>()
        plugin<ShadowPlugin>()
        plugin<Licenser>()
        plugin<EclipsePlugin>()
        plugin<IdeaPlugin>()
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
        compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
        implementation(platform("com.intellectualsites.bom:bom-newest:1.39")) // Ref: https://github.com/IntellectualSites/bom
        compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
        compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }
    }

    tasks.compileJava.configure {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    configurations.all {
        attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 17)
    }

    configure<LicenseExtension> {
        header(rootProject.file("HEADER.txt"))
        include("**/*.java")
        newLine.set(true)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        withSourcesJar()
        withJavadocJar()
    }

    val javaComponent = components["java"] as AdhocComponentWithVariants
    javaComponent.withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
        skip()
    }

    publishing {
        (components["java"] as AdhocComponentWithVariants).withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
            skip()
        }

        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])

                pom {
                    name.set(project.name)
                    url.set("https://github.com/IzanagiCraft/data-storage")
                    properties.put("inceptionYear", "2023")

                    licenses {
                        license {
                            name.set("General Public License (GPL v3.0)")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
                            distribution.set("repo")
                        }
                    }

                    developers {
                        developer {
                            id.set("sanguine6660")
                            name.set("Sanguine")
                            email.set("sanguine6660@gmail.com")
                            url.set("https://github.com/sanguine6660")
                        }
                    }
                }
            }
        }

        repositories {
            mavenLocal()

            if (System.getProperty("publishName") != null && System.getProperty("publishPassword") != null) {
                maven("https://artifactory.izanagicraft.tech/repository/maven-snapshots/") {
                    this.name = "artifactory-izanagicraft-snapshots"
                    credentials {
                        this.password = System.getProperty("publishPassword")
                        this.username = System.getProperty("publishName")
                    }
                }
            }
        }

    }

    tasks {

        withType<ProcessResources> {
            doLast {
                filesMatching("*") {
                    expand(project.properties)
                    expand() {
                        filter { key ->
                            project.findProperty(key)?.toString() ?: "\${${key}}"
                        }
                    }
                }
            }
        }

        compileJava {
            options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "1000"))
            options.compilerArgs.add("-Xlint:all")
            for (disabledLint in arrayOf("processing", "path", "fallthrough", "serial")) options.compilerArgs.add("-Xlint:$disabledLint")
            options.isDeprecation = true
            options.encoding = Charsets.UTF_8.name()
        }

        jar {
            this.archiveClassifier.set(null as String?)
            this.archiveFileName.set("${project.name}-${project.version}-unshaded.${this.archiveExtension.getOrElse("jar")}")
            this.destinationDirectory.set(file("$projectDir/../out/unshaded"))
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }

        named<ShadowJar>("shadowJar") {
            this.archiveClassifier.set(null as String?)
            this.archiveFileName.set("${project.name}-${project.version}.${this.archiveExtension.getOrElse("jar")}")
            this.destinationDirectory.set(file("$projectDir/../out"))
        }

        named("build") {
            dependsOn(named("shadowJar"))
        }
    }

}
