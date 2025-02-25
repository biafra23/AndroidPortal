package com.jaeckel.androidportal

import android.app.Application
import com.arcao.slf4j.timber.BuildConfig
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.slf4j.LoggerFactory
import timber.log.Timber
import java.io.File
import java.security.KeyPairGenerator
import java.security.Security


class AndroidPortalApplication : Application() {
    val logger = LoggerFactory.getLogger(AndroidPortalApplication::class.java)

    override fun onCreate() {
        super.onCreate()
        // Set the library path
        val libDir = File(applicationContext.filesDir, "lib")
        if (!libDir.exists()) {
            libDir.mkdirs()
        }
//        System.setProperty("java.library.path", "/system/lib64/")
//
//        // Load the native library
//        System.loadLibrary("lz4")

        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.DebugTree())
//            Timber.plant(CrashReportingTree())
        }

        logger.debug("<DEBUG>")
        logger.info("<INFO>")
        logger.warn("<WARN>")
        logger.error("<ERROR>")

        val filesDir: File = applicationContext.filesDir
        val absolutePath: String = filesDir.absolutePath
        val sambaDir = absolutePath + "/samba"
        if (File(sambaDir).exists()) {
            logger.info("Deleting samba directory")
            File(sambaDir).deleteRecursively()
        }
    }
}
