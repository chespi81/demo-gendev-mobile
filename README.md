# DemoBank Mobile Application 🏦

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://shields.io/)
[![Test Coverage](https://img.shields.io/badge/coverage-90%25-brightgreen.svg)](https://shields.io/)
[![Android API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A modern Android banking application for development demonstrating.

## 📱 Features

### Part of the POC Colombia project for Scotia Tech

### Core Functionality
- **User Authentication**: Secure login with session management
- **Session Management**: Automatic session handling and logout
- **Navigation Drawer**: Intuitive side navigation
- **Home Dashboard**: Main banking interface
- **Card Management**: Card on/off functionality
- **Slideshow**: Information and promotional content

### Technical Features
- **Multi-Environment Support**: Local, Dev, UAT, and Production builds
- **Network Security**: SSL pinning and security configurations
- **Dependency Injection**: Dagger 2 implementation
- **Offline Support**: Session persistence with SharedPreferences
- **Modern UI**: Material Design components

## 🏗️ Architecture

### Design Pattern
- **MVVM (Model-View-ViewModel)**: Clean separation of concerns
- **Clean Architecture**: Organized in layers (Data, Domain, Presentation)
- **Repository Pattern**: Centralized data access
- **Use Cases**: Business logic encapsulation

### Project Structure
```
app/src/main/java/cl/tinet/demobank/
├── data/                          # Data Layer
│   └── session/                   # Session management
├── di/                           # Dependency Injection
│   ├── component/                # Dagger components
│   └── module/                   # Dagger modules
├── ui/                          # Presentation Layer
│   ├── login/                    # Login module
│   │   ├── data/                 # Login data sources
│   │   ├── domain/               # Login business logic
│   │   └── presentation/         # Login UI and presenters
│   ├── home/                     # Home module
│   └── slideshow/                # Slideshow module
├── MainActivity.kt               # Main application activity
└── SplashActivity.kt            # App initialization
```

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Hedgehog | 2023.1.1 or newer
- **Java Development Kit**: JDK 11 or higher
- **Android SDK**: API level 24-36
- **Gradle**: 8.13.0 (included in wrapper)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-organization/demo-gendev-mobile.git
   cd demo-gendev-mobile
   ```

2. **Open in Android Studio**
   - File → Open → Select project directory
   - Wait for Gradle sync to complete

3. **Configure environment**
   - No additional configuration required for local development
   - API endpoints configured per build variant

### 🔧 Build Variants

The project supports multiple build environments:

| Variant | Description | Base URL |
|---------|-------------|----------|
| `local` | Local development | `http://10.0.2.2:8080/` |
| `dev` | Development server | `http://10.0.2.2:8080/` |
| `uat` | User Acceptance Testing | `https://uat-api.demobank.cl/` |
| `prod` | Production | `https://api.demobank.cl/` |

### 🏃‍♂️ Running the Application

#### Using Android Studio
1. Select build variant: `devDebug` (recommended for development)
2. Click **Run** (▶️) or press `Shift + F10`

#### Using Command Line
```bash
# Build debug APK
./gradlew app:assembleDebug

# Install debug APK on connected device
./gradlew app:installDebug

# Build and install
./gradlew app:assembleDebug app:installDebug
```

## 🧪 Testing

### Test Coverage Requirements
- **General Components**: 80% line coverage, 75% branch coverage
- **Critical Business Logic** (Login, Domain, Session): 90% coverage
- **Current Coverage**: 90%+ maintained across critical modules

### Running Tests

#### Unit Tests
```bash
# Run all unit tests
./gradlew testDevDebugUnitTest

# Run with coverage report
./gradlew testDevDebugUnitTest jacocoTestReport

# Verify coverage thresholds
./gradlew jacocoTestCoverageVerification

# Complete test pipeline
./gradlew clean testDevDebugUnitTest jacocoTestReport jacocoTestCoverageVerification
```

#### Instrumented Tests
```bash
# Run on connected device/emulator
./gradlew connectedAndroidTest
```

#### Test Reports
- **Unit Test Results**: `app/build/reports/tests/testDevDebugUnitTest/index.html`
- **Coverage Report**: `app/build/reports/jacoco/jacocoTestReport/html/index.html`
- **Coverage XML**: `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml`

### Test Structure
```
app/src/test/java/cl/tinet/demobank/
├── data/session/                 # Session management tests
├── ui/login/                     # Login module tests
│   ├── data/                     # Data layer tests
│   ├── domain/                   # Business logic tests
│   └── presentation/             # Presenter tests
├── MainActivityTest.kt           # Main activity tests
└── SplashActivityTest.kt         # Splash screen tests
```

## 🛠️ Development

### Code Quality
```bash
# Lint checks
./gradlew app:lintDebug

# Clean build artifacts
./gradlew clean

# Full build
./gradlew build
```

### Dependencies

#### Core Libraries
- **Kotlin**: 2.0.21
- **Android Gradle Plugin**: 8.13.0
- **AndroidX**: Latest stable versions
- **Material Design**: Latest version

#### Networking
- **Retrofit**: REST API client
- **OkHttp**: HTTP client with logging
- **Gson**: JSON serialization

#### Dependency Injection
- **Dagger 2**: 2.48

#### Testing
- **JUnit**: 4.x
- **MockK**: 1.13.8
- **Coroutines Test**: 1.7.3
- **AndroidX Test**: Latest versions

### 📐 Code Standards

#### Naming Conventions
- **Classes**: PascalCase (`LoginPresenter`)
- **Variables/Functions**: camelCase (`isUserLoggedIn`)
- **Constants**: UPPER_SNAKE_CASE (`API_BASE_URL`)
- **Test Methods**: Given-When-Then pattern with backticks

#### Test Naming Example
```kotlin
@Test
fun `GIVEN valid credentials WHEN login is called THEN should save session and navigate to home`() {
    // Test implementation
}
```

## 🚦 CI/CD

### GitHub Actions (Recommended Setup)
```yaml
# .github/workflows/android.yml
name: Android CI
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
      - name: Run tests
        run: ./gradlew testDevDebugUnitTest jacocoTestReport jacocoTestCoverageVerification
```

## 📊 Performance

### Build Performance
- **Clean Build**: ~35 seconds
- **Incremental Build**: ~5-10 seconds
- **Test Execution**: ~12 seconds (75+ tests)

### App Performance
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36
- **APK Size**: ~15-20MB (debug), ~10-12MB (release)

## 🔐 Security

### Network Security
- **SSL/TLS**: Enforced for all network connections
- **Certificate Pinning**: Configured for production
- **Network Security Config**: `app/src/main/res/xml/network_security_config.xml`

### Data Security
- **Session Storage**: Encrypted SharedPreferences
- **API Keys**: Build config fields (not in VCS)
- **ProGuard**: Enabled for release builds

## 🚀 Deployment

### Debug Builds
```bash
./gradlew app:assembleDebug
# Output: app/build/outputs/apk/dev/debug/app-dev-debug.apk
```

### Release Builds
```bash
./gradlew app:assembleRelease
# Output: app/build/outputs/apk/prod/release/app-prod-release.apk
```

### Environment-Specific Builds
```bash
# UAT build
./gradlew app:assembleUatRelease

# Production build
./gradlew app:assembleProdRelease
```

## 🤝 Contributing

### Getting Started
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes following our code standards
4. Add tests for new functionality
5. Ensure all tests pass: `./gradlew testDevDebugUnitTest`
6. Commit your changes: `git commit -m 'Add amazing feature'`
7. Push to the branch: `git push origin feature/amazing-feature`
8. Open a Pull Request

### Code Review Process
- All changes require code review
- Tests must pass and maintain coverage thresholds
- Follow existing code patterns and architecture
- Update documentation for significant changes

### Commit Message Format
```
feat: add user authentication flow
fix: resolve session timeout issue
test: add coverage for login presenter
docs: update API documentation
refactor: improve session management
```

## 📚 Additional Resources

### Documentation
- **Project Specs**: [CLAUDE.md](CLAUDE.md)
- **Testing Guide**: [test-unit.md](test-unit.md)
- **API Documentation**: Contact development team

### Tools & Links
- **Android Studio**: [Download](https://developer.android.com/studio)
- **Kotlin Documentation**: [kotlinlang.org](https://kotlinlang.org)
- **Material Design**: [material.io](https://material.io)
- **Dagger 2**: [dagger.dev](https://dagger.dev)

## 🐛 Troubleshooting

### Common Issues

#### Build Errors
```bash
# Clean and rebuild
./gradlew clean build

# Invalidate caches in Android Studio
# File → Invalidate Caches → Restart
```

#### Test Failures
```bash
# Run specific test class
./gradlew test --tests "LoginPresenterTest"

# Run tests with debug info
./gradlew test --info
```

#### Dependency Issues
```bash
# Refresh dependencies
./gradlew --refresh-dependencies
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

### Development Team
- **Project Lead**: Development Team
- **Android Developers**: Mobile Development Team
- **QA Engineers**: Quality Assurance Team

### Contact Information
- **Issues**: GitHub Issues
- **Questions**: Contact development team
- **Documentation**: This README and project wiki

---

**Built with ❤️ using Android & Kotlin**