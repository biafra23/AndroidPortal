plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.jaeckel.androidportal"
    compileSdk = 35
//    ndkVersion = "23.0.7599858"

    sourceSets {
        getByName("main") {
            resources.srcDirs("src/main/resources")
        }
    }

    defaultConfig {
        applicationId = "com.jaeckel.androidportal"
        minSdk = 34
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
           // proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            android.packagingOptions.jniLibs.keepDebugSymbols += "**/*.so"
        }
        debug {
            isMinifyEnabled = false
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
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/DISCLAIMER"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/NOTICE.md"
            excludes += "/META-INF/io.netty.versions.properties"
            excludes += "/META-INF/versions/9/OSGI-INF/MANIFEST.MF"
//            excludes += "'META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat"
//            excludes += "META-INF/**"
            excludes += "kzg-trusted-setups/mainnet.txt"

            pickFirsts += "log4j2.xml"
            pickFirsts += "META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat"
        }
    }
}

dependencies {
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))

    coreLibraryDesugaring(libs.desugar.jdk.libs)

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
    implementation("io.projectreactor:reactor-core:3.6.7")
    implementation("io.netty:netty-all:4.1.115.Final")

    implementation("com.jaeckel.samba:samba-android-wrapper:1.0.0-SNAPSHOT")

    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")
    implementation("org.bouncycastle:bcutil-jdk18on:1.78.1")

    // SLF4J API
    implementation( "org.slf4j:slf4j-api:2.0.16")
//    implementation("org.slf4j:slf4j-simple:2.0.16")
//    implementation("com.arcao:slf4j-timber:3.1@aar")
    implementation("com.jakewharton.timber:timber:4.7.1")
//    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.24.2")
//    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.24.2")
    implementation("org.apache.logging.log4j:log4j-core:2.24.2")
    implementation("com.github.tony19:logback-android:3.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.github.biafra23:trueblocks-kotlin:main-SNAPSHOT") {
        exclude(group = "ch.qos.logback", module = "logback-classic")
    }
//    implementation("com.github.biafra23:ipfs-api-kotlin:master-SNAPSHOT")
    implementation("com.github.komputing.kethereum:model:0.86.0")
    implementation("com.github.komputing.khex:core:1.1.2")
    implementation("com.github.komputing.khex:extensions:1.1.2")

    implementation("io.consensys.tuweni:tuweni-bytes:2.7.0")
    implementation("tech.pegasys.teku.internal:unsigned:24.8.0") {
        exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j-impl")
    }
    implementation("org.hyperledger.besu:evm:25.2.1") {
        exclude(group = "org.apache.tuweni", module = "tuweni-bytes")
        exclude(group = "org.apache.tuweni", module = "tuweni-rlp")
        exclude(group = "org.apache.tuweni", module = "tuweni-crypto")
        exclude(group = "org.apache.tuweni", module = "tuweni-units")
        exclude(group = "org.apache.tuweni", module = "tuweni-io")
        exclude(group = "io.tmio", module = "tuweni-bytes")
        exclude(group = "io.tmio", module = "tuweni-rlp")
        exclude(group = "io.tmio", module = "tuweni-crypto")
        exclude(group = "io.tmio", module = "tuweni-units")
        exclude(group = "io.tmio", module = "tuweni-io")

        exclude(group = "net.java.dev.jna", module = "jna")
        exclude(group = "net.java.dev.jna", module = "jna-platform")

        exclude(group = "tech.pegasys", module = "jc-kzg-4844")
        exclude(group = "tech.pegasys.discovery", module = "discovery")

        exclude("org.hyperledger.besu.internal", "algorithms")
        exclude("org.hyperledger.besu.internal", "crypto")
        exclude("org.hyperledger.besu.internal", "core")
    }
    implementation("org.hyperledger.besu:plugin-api:25.2.1") {
        exclude(group = "org.apache.tuweni", module = "tuweni-bytes")
        exclude(group = "org.apache.tuweni", module = "tuweni-rlp")
        exclude(group = "org.apache.tuweni", module = "tuweni-crypto")
        exclude(group = "org.apache.tuweni", module = "tuweni-units")
        exclude(group = "org.apache.tuweni", module = "tuweni-io")
        exclude(group = "io.tmio", module = "tuweni-bytes")
        exclude(group = "io.tmio", module = "tuweni-rlp")
        exclude(group = "io.tmio", module = "tuweni-crypto")
        exclude(group = "io.tmio", module = "tuweni-units")
        exclude(group = "io.tmio", module = "tuweni-io")

        exclude(group = "net.java.dev.jna", module = "jna")
        exclude(group = "net.java.dev.jna", module = "jna-platform")

        exclude(group = "tech.pegasys", module = "jc-kzg-4844")
        exclude(group = "tech.pegasys.discovery", module = "discovery")

        exclude("org.hyperledger.besu.internal", "algorithms")
        exclude("org.hyperledger.besu.internal", "crypto")
        exclude("org.hyperledger.besu.internal", "core")
    }

    implementation("org.hyperledger.besu:besu-datatypes:25.2.1") {
        exclude(group = "org.apache.tuweni", module = "tuweni-bytes")
        exclude(group = "org.apache.tuweni", module = "tuweni-rlp")
        exclude(group = "org.apache.tuweni", module = "tuweni-crypto")
        exclude(group = "org.apache.tuweni", module = "tuweni-units")
        exclude(group = "org.apache.tuweni", module = "tuweni-io")
        exclude(group = "io.tmio", module = "tuweni-bytes")
        exclude(group = "io.tmio", module = "tuweni-rlp")
        exclude(group = "io.tmio", module = "tuweni-crypto")
        exclude(group = "io.tmio", module = "tuweni-units")
        exclude(group = "io.tmio", module = "tuweni-io")

        exclude(group = "net.java.dev.jna", module = "jna")
        exclude(group = "net.java.dev.jna", module = "jna-platform")

        exclude(group = "tech.pegasys", module = "jc-kzg-4844")
        exclude(group = "tech.pegasys.discovery", module = "discovery")

        exclude("org.hyperledger.besu.internal", "algorithms")
        exclude("org.hyperledger.besu.internal", "crypto")
        exclude("org.hyperledger.besu.internal", "core")
    }
}

configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor( 0, "seconds")
    }
}
