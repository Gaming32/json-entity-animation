plugins {
    id("fabric-loom") version "1.1.+"
    id("io.github.juuxel.loom-quiltflower") version "1.8.0"
}

version = "0.2+1.19.2"
group = "io.github.gaming32"

repositories {
    maven {
        name = "ParchmentMC"
        url = uri("https://maven.parchmentmc.org")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.19.2")
    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.19.2:2022.11.27@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:0.14.21")

    include(modImplementation(fabricApi.module("fabric-resource-loader-v0", "0.76.0+1.19.2"))!!)

}

tasks.processResources {
    inputs.property("version", project.version)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

java {
    withSourcesJar()
}
