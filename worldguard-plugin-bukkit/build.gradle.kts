import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    api(project(":worldguard-api"))
}

tasks.named<ShadowJar>("shadowJar") {
    this.archiveClassifier.set(null as String?)
    this.archiveFileName.set("${project.name}-${project.version}.${this.archiveExtension.getOrElse("jar")}")
    this.destinationDirectory.set(file("$projectDir/../out"))
    // Get rid of all the libs which are 100% unused.
    minimize()
    mergeServiceFiles()
}

tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
tasks.withType<PublishToMavenLocal>().configureEach { enabled = false }
