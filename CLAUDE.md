# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android application called "DemoBank" (package: `cl.tinet.demobank`) - a demo banking application built with Kotlin and modern Android development practices. The app uses a navigation drawer pattern with three main sections: Home, Gallery, and Slideshow.

## Development Commands

### Build and Run
- `./gradlew build` - Build the entire project
- `./gradlew app:assembleDebug` - Build debug APK
- `./gradlew app:assembleRelease` - Build release APK
- `./gradlew app:installDebug` - Install debug build on connected device

### Testing
- `./gradlew test` - Run unit tests
- `./gradlew connectedAndroidTest` - Run instrumented tests on connected device
- `./gradlew app:testDebugUnitTest` - Run unit tests for debug variant

### Code Quality
- `./gradlew app:lintDebug` - Run lint checks on debug variant
- `./gradlew clean` - Clean build artifacts

## Architecture

### UI Structure
- **MainActivity**: Main entry point with navigation drawer using `DrawerLayout` and `NavigationView`
- **Fragment-based navigation**: Uses Android Navigation Component with two fragments:
  - `HomeFragment` - Main content area
  - `SlideshowFragment` - Slideshow functionality
- **MVVM Pattern**: Each fragment has corresponding ViewModel (e.g., `HomeViewModel`)

### Key Technologies
- **View Binding**: Enabled for type-safe view references
- **Android Navigation Component**: Handles fragment navigation
- **Material Design**: Uses Material Design components and theming
- **Kotlin**: Primary development language with Java 11 compatibility
- **Lifecycle Components**: LiveData and ViewModel for reactive UI

### Package Structure
```
cl.tinet.demobank/
├── MainActivity.kt - Main activity with drawer navigation
└── ui/
    ├── home/ - Home section (Fragment + ViewModel)
    └── slideshow/ - Slideshow section (Fragment + ViewModel)
```

### Build Configuration
- **Target SDK**: 36
- **Min SDK**: 24
- **Compile SDK**: 36
- **Build Tools**: Android Gradle Plugin 8.13.0, Kotlin 2.0.21
- **Dependencies managed via**: Gradle Version Catalog (`gradle/libs.versions.toml`)

### Testing Setup
- **Unit Tests**: JUnit 4 in `src/test/`
- **Instrumented Tests**: AndroidX Test + Espresso in `src/androidTest/`
- Uses `AndroidJUnitRunner` for instrumented test execution