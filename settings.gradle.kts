pluginManagement {
    repositories {
        google()
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

rootProject.name = "AeroTune"
include(":app")

// Setup Compose compiler build to allow local compose version overrides
gradle.beforeProject {
    if (project.name == "composecompiler") {
        return@beforeProject
    }
}
