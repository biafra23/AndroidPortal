plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
//    alias(libs.plugins.rustAndroidGradle)
}

android {
    namespace = "com.jaeckel.androidportal"
    compileSdk = 35
    ndkVersion = "23.0.7599858"

    defaultConfig {
        applicationId = "com.jaeckel.androidportal"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            android.packagingOptions.jniLibs.keepDebugSymbols += "**/*.so"
        }
        debug {
            android.packagingOptions.jniLibs.keepDebugSymbols += "**/*.so"
//            debuggable = true
//            jniDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/**"
            excludes += "log4j2.xml"
            excludes += "kzg-trusted-setups/mainnet.txt"
        }
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //implementation(libs.kethereum.rpc)
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

//    implementation("com.github.biafra23.samba:core:main-SNAPSHOT") {

//    implementation("com.github.biafra23.samba:core:main-SNAPSHOT") {
//    implementation("com.github.biafra23.samba:core:main-b4264b49f5-1") {
    implementation("com.github.biafra23.samba:core:b4264b49f5") {
        exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j2-impl")
        exclude(group = "io.tmio", module = "tuweni-rlp")
        exclude(group = "io.tmio", module = "tuweni-crypto")
        exclude(group = "io.tmio", module = "tuweni-bytes")
        exclude(group = "io.tmio", module = "tuweni-units")
        exclude(group = "io.tmio", module = "tuweni-io")
        exclude(group = "org.bouncycastle", module = "bcprov-jdk18on")
        exclude(group = "org.bouncycastle", module = "bcutil-jdk18on")
        exclude(group = "org.hyperledger.besu.internal", module = "crypto")
    }
    //implementation("com.github.biafra23q:samba:main-SNAPSHOT")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}

//cargo {
//    module = "../trin-jni-wrapper"       // Or whatever directory contains your Cargo.toml
//    libname = "trin_jni_wrapper"          // Or whatever matches Cargo.toml's [package] name.
//    targets = listOf("arm64")  // See bellow for a longer list of options
//    //prebuiltToolchains = true
//    //verbose = true
//    apiLevel = 24
////    profile = "debug"
//    profile = "release"
//    //extraCargoBuildArguments = listOf("+1.76-")
//}

//project.afterEvaluate {
//    tasks.withType(com.nishtahir.CargoBuildTask::class)
//        .forEach { buildTask ->
//            tasks.withType(com.android.build.gradle.tasks.MergeSourceSetFolders::class)
//                .configureEach {
//                    this.inputs.dir(
//                        layout.buildDirectory.dir("rustJniLibs" + File.separatorChar + buildTask.toolchain!!.folder)
//                    )
//                    this.dependsOn(buildTask)
//                }
//        }
//}

//configurations.all {
//    resolutionStrategy {
////        force("org.apache.logging.log4j:log4j-core:2.23.1")
//        force("org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1")
////        force("org.apache.logging.log4j:log4j-slf4j2-api:2.23.1")
//    }
//}
configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor( 0, "seconds")
    }
}
