plugins {
    id("net.rikarin.application-conventions")
}

dependencies {
//    implementation("org.apache.commons:commons-text")
    implementation(project(":lib"))
}

application {
    // Define the main class for the application.
    mainClass.set("net.rikarin.testApp.AppKt")
}
