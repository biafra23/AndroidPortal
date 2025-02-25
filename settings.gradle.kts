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
        // Add a flat directory repository
        flatDir {
            dirs("libs") // Replace "libs" with the path to your local directory
        }
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
        maven { url = uri("https://artifacts.consensys.net/public/maven/maven/") }
        maven { url = uri("https://artifacts.consensys.net/public/teku/maven/") }
        maven { url = uri("https://mvnrepository.com") }
        maven { url = uri("https://hyperledger.jfrog.io/artifactory/besu-maven/") }
        maven { url = uri("https://dl.cloudsmith.io/public/libp2p/jvm-libp2p/maven/") }
    }
}

rootProject.name = "AndroidPortal"
include(":app")
//include(":trin-jni-wrapper")
