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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }
    }
}

rootProject.name = "OverrideLogistics"
include(":app")
include(":features:haulier:presentation")
include(":features:wharehouse:presentation")
include(":core:common")
include(":core:ui")
include(":features:wharehouse:domain")
include(":features:login:presentation")
include(":features:login:domain")
include(":data:auth:api")
include(":data:session:api")
include(":data:auth:impl")
include(":data:session:impl")
include(":features:map:presentation")
