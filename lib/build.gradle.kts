plugins {
    id("net.rikarin.library-conventions")
    kotlin("plugin.serialization") version "1.8.0"
}

base {
    archivesName.set("core")
}

version = "0.0.1"
group = "net.rikarin"


dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.apache.commons:commons-math3:3.6.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:31.1-jre")

    implementation("de.mkammerer.snowflake-id:snowflake-id:0.0.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")
    implementation("com.charleskorn.kaml:kaml:0.49.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
}
