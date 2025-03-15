rootProject.name = "open-weather"

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
    }
}

include(":di")
include(":common")
include(":core:db")
include(":core:auth-core")
include(":core:settings-core")
include(":core:network")
include(":core:utils")
include(":app")
include(":feature:auth")
include(":feature:weather")
include(":feature:cities")
include(":feature:search")
include(":feature:settings")