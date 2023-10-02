[![Experimental](https://kotl.in/badges/experimental.svg)](https://kotlinlang.org/docs/components-stability.html)
[![JetBrains incubator project](https://jb.gg/badges/incubator.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://raw.githubusercontent.com/JetBrains/lets-plot-compose-demos/master/LICENSE)


# Lets-Plot Skia Frontend in Compose Applications (Examples)

[**Lets-Plot Skia Frontend**](https://github.com/JetBrains/lets-plot-skia) is a Kotlin Multiplatform library that allows you to embed \
[Lets-Plot](https://github.com/JetBrains/lets-plot) charts in a [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) or Java Swing application.


## Compose Desktop Demos

To run a Composer Desktop demo in IntelliJ IDEA simply navigate to [MinimalAppMain.kt](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-desktop/src/main/kotlin/demo/letsPlot/composeDesktop/minimal) or [MedianAppMain.kt](https://github.com/JetBrains/lets-plot-compose-demos/tree/main/compose-desktop/src/main/kotlin/demo/letsPlot/composeDesktop/median) and select "`Run <app>`" from the context menu.

## Compose Android: Running Demos in IntelliJ IDEA

### Setting up the Environment

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

### Running an Android Demo
       
Build the project:
```Kotlin
./gradlew build
```

In the `Run configurations` drop-down menu:
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
Copyright © 2019-2023, JetBrains s.r.o.
