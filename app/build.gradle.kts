plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    jacoco
}

android {
    namespace = "cl.tinet.demobank"
    compileSdk = 36

    defaultConfig {
        applicationId = "cl.tinet.demobank"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"
    
    productFlavors {
        create("local") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080/\"")
        }
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080/\"")
        }
        create("uat") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://uat-api.demobank.cl/\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.demobank.cl/\"")
        }
    }
    
    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

jacoco {
    toolVersion = "0.8.10"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    
    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    
    // Dagger
    implementation("com.google.dagger:dagger:2.48")
    implementation("com.google.dagger:dagger-android:2.48")
    implementation("com.google.dagger:dagger-android-support:2.48")
    kapt("com.google.dagger:dagger-compiler:2.48")
    kapt("com.google.dagger:dagger-android-processor:2.48")
    
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// JaCoCo Tasks Configuration
tasks.register<JacocoReport>("jacocoTestReport") {
    description = "Generate Jacoco coverage reports for Debug build"
    group = "reporting"
    
    dependsOn("testDevDebugUnitTest")
    
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
        
        xml.outputLocation.set(file("${layout.buildDirectory.get()}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"))
        html.outputLocation.set(file("${layout.buildDirectory.get()}/reports/jacoco/jacocoTestReport/html"))
    }
    
    val javaClasses = fileTree("${layout.buildDirectory.get()}/intermediates/javac/devDebug/classes") {
        exclude(getExcludedFiles())
    }
    
    val kotlinClasses = fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/devDebug") {
        exclude(getExcludedFiles())
    }
    
    classDirectories.setFrom(files(javaClasses, kotlinClasses))
    
    sourceDirectories.setFrom(files(
        "${projectDir}/src/main/java",
        "${projectDir}/src/main/kotlin"
    ))
    
    executionData.setFrom(fileTree("${layout.buildDirectory.get()}/outputs/unit_test_code_coverage/devDebugUnitTest") {
        include("testDevDebugUnitTest.exec")
    })
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    description = "Verify Jacoco coverage for Debug build"
    group = "verification"
    
    dependsOn("jacocoTestReport")
    
    violationRules {
        rule {
            element = "CLASS"
            
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal() // 80% minimum line coverage
            }
            
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.75".toBigDecimal() // 75% minimum branch coverage
            }
        }
        
        // Higher coverage for critical business logic
        rule {
            element = "CLASS"
            includes = listOf(
                "cl.tinet.demobank.ui.login.presentation.*",
                "cl.tinet.demobank.ui.login.domain.*",
                "cl.tinet.demobank.data.session.*"
            )
            
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal() // 90% for critical components
            }
        }
    }
    
    val javaClasses = fileTree("${layout.buildDirectory.get()}/intermediates/javac/devDebug/classes") {
        exclude(getExcludedFiles())
    }
    
    val kotlinClasses = fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/devDebug") {
        exclude(getExcludedFiles())
    }
    
    classDirectories.setFrom(files(javaClasses, kotlinClasses))
    
    executionData.setFrom(fileTree("${layout.buildDirectory.get()}/outputs/unit_test_code_coverage/devDebugUnitTest") {
        include("testDevDebugUnitTest.exec")
    })
}

// Helper function for exclusions
fun getExcludedFiles() = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "**/databinding/**/*.*",
    "**/di/module/**/*.*", // Dagger modules
    "**/di/*Component*.*", // Dagger components
    "**/*Activity.*", // Activities (UI layer)
    "**/*Activity\$*.*", // Activity inner classes (lambdas, etc.)
    "**/*Fragment.*", // Fragments (UI layer)
    "**/*Fragment\$*.*", // Fragment inner classes (lambdas, etc.)
    "**/DemoBankApplication.*", // Application class
    "**/*ViewModel.*", // ViewModels (UI layer)
    "**/*Navigator.*", // Navigator classes (UI navigation)
    "**/adapter/**/*.*", // RecyclerView adapters (UI layer)
    "**/model/**/*.*", // Simple data models without business logic
    "**/ui/home/**/*.*", // Home module (not critical business logic)
    "**/ui/cardonoff/**/*.*", // Card on/off module (not critical)
    "**/ui/slideshow/**/*.*" // Slideshow module (not critical)
)

// Enable test coverage for unit tests
android.buildTypes.forEach { buildType ->
    if (buildType.name == "debug") {
        buildType.enableUnitTestCoverage = true
    }
}