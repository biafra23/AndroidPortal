pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        // Add a flat directory repository
        flatDir {
            dirs("libs") // Replace "libs" with the path to your local directory
        }
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://artifacts.consensys.net/public/maven/maven/")
        maven("https://artifacts.consensys.net/public/teku/maven/")
        maven("https://mvnrepository.com")
        maven("https://hyperledger.jfrog.io/artifactory/besu-maven/")
        maven("https://dl.cloudsmith.io/public/libp2p/jvm-libp2p/maven/")
    }
}

rootProject.name = "AndroidPortal"
include(":app")