name: Build APK
on:
  push:
  workflow_dispatch:
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: unzip -q GeoTools.zip
      - run: sed -i 's/^import androidx.compose.foundation.layout.\*/import androidx.compose.foundation.isSystemInDarkTheme\nimport androidx.compose.foundation.layout.*/' GeoTools/app/src/main/java/com/geotools/app/MainActivity.kt
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: gradle/actions/setup-gradle@v4
        with:
          gradle-version: 8.9
      - run: gradle assembleDebug
        working-directory: GeoTools
      - uses: actions/upload-artifact@v4
        with:
          name: GeoTools-apk
          path: GeoTools/app/build/outputs/apk/debug/app-debug.apk
