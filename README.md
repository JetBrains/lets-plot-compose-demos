[![Experimental](https://kotl.in/badges/experimental.svg)](https://kotlinlang.org/docs/components-stability.html)
[![JetBrains incubator project](https://jb.gg/badges/incubator.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://raw.githubusercontent.com/JetBrains/lets-plot-compose-demos/master/LICENSE)


# Lets-Plot Compose Examples

[**Lets-Plot Compose Frontend**](https://github.com/JetBrains/lets-plot-compose) is a Kotlin Multiplatform library that allows you to embed \
[Lets-Plot](https://github.com/JetBrains/lets-plot) charts in a [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) (Desktop, Android) application.


## Desktop

To run a Compose Desktop demo in IntelliJ IDEA, navigate to a `<demo name>AppMain.kt` file in the 
[compose-desktop](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-desktop/src/main/kotlin/demo/letsPlot) or 
[compose-multiplatform/src/desktopMain](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-multiplatform/src/desktopMain/kotlin/demo/letsPlot) 
folder and select "`Run <app>`" from the context menu.


## WasmJS

The WasmJS demo is located in the
[compose-multiplatform/src/wasmJsMain](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-multiplatform/src/wasmJsMain/kotlin/demo/letsPlot)
folder. Unlike the Desktop demo, run it in the browser using the Gradle command:

```shell
./gradlew compose-multiplatform:wasmJsBrowserDevelopmentRun
```


## Android (in IntelliJ IDEA)

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

In the `Run Configurations` toolbar:
- Select `compose-android-min`, `compose-android-median`, or other _AndroidApp Run Configuration_
- Select the `Android` device
- Click `Run`

## Code of Conduct

This project and the corresponding community are governed by the
[JetBrains Open Source and Community Code of Conduct](https://confluence.jetbrains.com/display/ALL/JetBrains+Open+Source+and+Community+Code+of+Conduct).
Please make sure you read it.

## License

Code and documentation released under
the [MIT license](https://github.com/JetBrains/lets-plot-compose-demos/blob/master/LICENSE).
Copyright © 2023, JetBrains s.r.o.
