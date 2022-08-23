import proguard.gradle.ProGuardTask
import java.nio.file.Files
import java.util.zip.ZipFile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.2.2")
    }
}

plugins {
    id("java")
}

group = "xland.ioutils"
version = "0.1.0"

object Constants {
    const val mainClass = "ModWarn"
}

repositories {
    mavenCentral()
}

task("shrink", ProGuardTask::class) {
    injars(buildDir.resolve("libs/${project.name}-${project.version}.jar"))
    outjars(buildDir.resolve("proguard-shrunk.jar"))
    doFirst {
        buildDir.resolve("proguard-shrunk.jar").delete()
    }
    if (JavaVersion.current().isJava9Compatible) {
        listOf("java.base", "java.desktop", "java.prefs", "java.datatransfer", "java.xml").forEach {
            this.libraryjars("${System.getProperty("java.home")}/jmods/$it.jmod")
        }
    } else {
        this.libraryjars("${System.getProperty("java.home")}/lib/rt.jar")
    }
    printmapping(file("pro-shrunk-output/mapping.txt"))
    keepattributes("Exceptions,RuntimeVisible*Annotation*")
    keepclasseswithmembers("public class * {\n" +
            "    public static void main(java.lang.String[]);\n" +
            "}")
    keepclasseswithmembernames("public class * {\n" +
            "    public static void main(java.lang.String[]);\n" +
            "}")
    target("1.8")

    dependsOn("jar")
}

task("extractShrunkJar") {
    dependsOn("shrink")
    val input = buildDir.resolve("proguard-shrunk.jar")
    val output = file("pro-shrunk-output/class")
    doLast {
        output.deleteRecursively()
        val zipFile = ZipFile(input)
        val entry = zipFile.getEntry("${Constants.mainClass.replace('.', '/')}.class")
        output.resolve(entry.name).parentFile.mkdirs()
        zipFile.getInputStream(entry).use { inputStream ->
            Files.copy(inputStream, output.resolve(entry.name).toPath())
        }
    }
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8
