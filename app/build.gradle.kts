plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // These dependencies are used by the application.
    implementation(libs.guava)
    implementation(libs.argparse4j)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("com.laracey.lox.interpreter.Lox")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

// https://discuss.gradle.org/t/right-way-to-generate-sources-in-gradle-7/41141/3
val genAst = tasks.register<com.laracey.lox.codegen.GenerateAstTask>("genAst") {
    outputDir = layout.buildDirectory.dir("generated/sources/ast/main/java")
    description = "Generate AST sources."
}

sourceSets {
   main {
      java {
        // https://discuss.gradle.org/t/right-way-to-generate-sources-in-gradle-7/41141/3
        // https://docs.gradle.org/current/dsl/org.gradle.api.tasks.SourceSetOutput.html
        // This appears to create a dependency between the uberJar (and probably the
        // default jar) and the genAst task, since I didn't need to add a dependsOn()
        // to the jar or uberJar tasks.
        srcDir(genAst)
      }
   }
}

// https://discuss.gradle.org/t/how-can-i-execute-a-java-application-that-asks-for-user-input/3264
// https://stackoverflow.com/questions/13172137/console-application-with-java-and-gradle
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// Allows for java -cp app/build/libs/app-uber.jar com.laracey.lox.interpreter.Lox (as well as
// the executable jar, see below) to work.
// Using the regular app.jar fails - argparse4j is not available.
tasks.register<Jar>("uberJar") {
    archiveClassifier = "uber"

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    manifest {
        attributes(
            // This makes the JAR an executable jar, so that
            // `java -jar app/build/libs/app.jar` works.
            "Main-Class" to application.mainClass
        )
    }
}