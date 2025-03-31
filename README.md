[![Experimental](https://kotl.in/badges/experimental.svg)](https://kotlinlang.org/docs/components-stability.html)
[![JetBrains incubator project](https://jb.gg/badges/incubator.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://raw.githubusercontent.com/JetBrains/lets-plot-compose-demos/master/LICENSE)


# Lets-Plot Skia Frontend in Compose Applications (Examples)

[**Lets-Plot Skia Frontend**](https://github.com/JetBrains/lets-plot-skia) is a Kotlin Multiplatform library that allows you to embed \
[Lets-Plot](https://github.com/JetBrains/lets-plot) charts in a [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform), Android or Java Swing application.


## Compose Multiplatform Demos

To run a Compose Multiplatform demo in IntelliJ IDEA simply navigate to [MinimalAppMain.kt](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-desktop/src/main/kotlin/demo/letsPlot/composeDesktop/minimal) or [MedianAppMain.kt](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-desktop/src/main/kotlin/demo/letsPlot/composeDesktop/median) and select "`Run <app>`" from the context menu.

## Android: Running Demos in IntelliJ IDEA
### Project configuration

> ### Due to [SKIKO-948](https://youtrack.jetbrains.com/issue/SKIKO-948/When-will-the-Android-artifacts-be-published-on-Maven-Central), updates for the Android version of the library are temporarily paused.
> The last library version with Android support is available with the following dependencies:  
> `implementation("org.jetbrains.skiko:skiko-android:0.8.4")`  
> `implementation("org.jetbrains.lets-plot:lets-plot-compose:2.0.0")`  
> `implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:4.8.0")`  
> `implementation("org.jetbrains.lets-plot:lets-plot-common:4.4.1")`
>
To use lets-plot-compose in an Android project, special configuration is required in `build.gradle.kts` to serve the Skia binaries:
<details>
    <summary>Click to see the code</summary>
    Code  

    ```
    ////////////////////////////////////////////////////////
    // Include the following code in your Gradle build script
    // to ensure that compatible Skiko binaries are
    // downloaded and included in your project.
    //
    // Without this, you won't be able to run your app
    // in the IDE on a device emulator.
    // //////////////////////////////////////////////////////
    
    val skikoJniLibsReleaseAssetName = "skiko-jni-libs.zip"
    val skikoJniLibsDestDir = file("${project.projectDir}/src/main/jniLibs/")
    
    tasks.register("downloadSkikoJniLibsReleaseAsset") {
        val repoUrl = "https://github.com/JetBrains/lets-plot-skia"
        val releaseTag = "v$letsPlotSkiaVersion"
    
        doLast {
            val downloadUrl = "$repoUrl/releases/download/$releaseTag/$skikoJniLibsReleaseAssetName"
            val outputFile = layout.buildDirectory.file("downloads/$skikoJniLibsReleaseAssetName").get().asFile
    
            if (outputFile.exists()) {
                println("File already exists: ${outputFile.absolutePath}")
                println("Skipping download.")
            } else {
                outputFile.parentFile?.mkdirs()
    
                println("Downloading $skikoJniLibsReleaseAssetName from $downloadUrl")
                URL(downloadUrl).openStream().use { input ->
                    outputFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                println("Download completed: ${outputFile.absolutePath}")
            }
        }
    }
    
    tasks.register<Copy>("unzipSkikoJniLibsReleaseAsset") {
        dependsOn("downloadSkikoJniLibsReleaseAsset")
        from(zipTree(layout.buildDirectory.file("downloads/$skikoJniLibsReleaseAssetName")))
        into(skikoJniLibsDestDir)
        doFirst {
            delete(skikoJniLibsDestDir)
        }
    }
    
    tasks.register("cleanSkikoJniLibs") {
        doLast {
            delete(skikoJniLibsDestDir)
        }
    }
    
    tasks.named("clean") {
        dependsOn("cleanSkikoJniLibs")
    }
    
    tasks.withType<MergeSourceSetFolders>().configureEach {
        dependsOn("unzipSkikoJniLibsReleaseAsset")
    }
    
    ////////////////////////////////////////////////////////
    ```
</details>




### Setting up the Environment

- #### Android Plugin

Make sure the [Android plugin](https://plugins.jetbrains.com/plugin/22989-android) is installed.

- #### Android SDK

From menu `Tools -> Android -> SDK Manager` setup an Android SDK.

The `local.properties` file will be automatically generated.
In case the `local.properties` file didn't appear in the project root:
- create it manually
- add property `sdk.dir` pointing to the location of the Android SDK on your system.

For example:
```
sdk.dir=/Users/john/Library/Android/sdk
```

- #### Android Device

From menu `Tools -> Android -> Device Manager` setup Android device.

For example, Nexus 10 with Android 12 works well.

### Running a Demo
       
Build the project:
```Kotlin
./gradlew build
```

In the `Run configurations` toolbar:
- Select `compose-android-min` or `compose-android-median` application
- Select the `Android` device
- Click `Run`

## Code of Conduct

This project and the corresponding community are governed by the
[JetBrains Open Source and Community Code of Conduct](https://confluence.jetbrains.com/display/ALL/JetBrains+Open+Source+and+Community+Code+of+Conduct).
Please make sure you read it.

## License

Code and documentation released under
the [MIT license](https://github.com/JetBrains/lets-plot-compose-demos/blob/master/LICENSE).
Copyright © 2023-2024, JetBrains s.r.o.
