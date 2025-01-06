import io.izzel.taboolib.gradle.*


plugins {
    java
    id("io.izzel.taboolib") version "2.0.22"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

taboolib {
    env {
        install(Basic, Bukkit, BukkitUtil, BukkitHook, MinecraftChat, I18n, CommandHelper,
            Database, DatabasePlayer, BukkitNMSUtil, Kether, Metrics)
    }
    description {
        name = "YuVarieLevel"
        desc("Variable & level system")
        contributors {
            name("L1An")
        }
    }
    version { taboolib = "6.2.0" }
    relocate("com.github.l1an.artisan", "${project.group}.artisan")
    relocate("org.serverct.parrot.parrotx", "${project.group}.parrotx")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
    taboo("com.github.l1an.artisan:Artisan:1.0.3")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.withType<Jar> {
    destinationDirectory.set(file("/Users/yuxin/minecraft/servers/1.20.4Test/plugins"))
    //destinationDirectory.set(file("$projectDir/build-jar"))
}