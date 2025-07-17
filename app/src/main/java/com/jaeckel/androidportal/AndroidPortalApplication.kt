package com.jaeckel.androidportal

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.android.LogcatAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kubo.Kubo
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import timber.log.Timber
import java.io.File
import java.security.Security


class AndroidPortalApplication : Application() {
    val logger = LoggerFactory.getLogger(AndroidPortalApplication::class.java)

    override fun onCreate() {
        super.onCreate()
        initLogging()
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        // We need to add the provider at the end to make sure discovery uses "AndroidOpenSSL" and not "BC"
        // As the AES Cipher provided by BC is not compatible :-(
        Security.addProvider(BouncyCastleProvider())

        for (provider in Security.getProviders()) {
            logger.info("Security provider: " + provider.name)
        }
        val filesDir = applicationContext.filesDir

        logger.info("path: ${filesDir.absolutePath}")
        logger.debug("<DEBUG>")
        logger.info("<INFO> {}", logger)
        logger.warn("<WARN>")
        logger.error("<ERROR>")

        val ipfsRepoPath = filesDir.absolutePath + "/ipfs_repo"
        if (!File(ipfsRepoPath).exists()) {
            File(ipfsRepoPath).mkdir()
        }

        val sambaDir = filesDir.absolutePath + "/samba"
        if (File(sambaDir).exists()) {
            logger.error("Deleting samba directory")
            File(sambaDir).deleteRecursively()
        }

        // Example usage (in an Activity or Fragment):
        if (hasNetworkStatePermission(this)) {
            // Proceed with accessing network state information
            logger.info("Network state permission granted")
            CoroutineScope(Dispatchers.IO).launch {
                logger.info("--> Running ipfs daemon...")
                try {
                    // start Kubo as a daemon and initialize repo if necessary
                    Kubo.runCli(ipfsRepoPath, "INFO", "daemon --init")
                } catch (e: Exception) {
                    Timber.e("Error starting IPFS daemon: ${e.message}")
                    e.printStackTrace()
                }
            }
        } else {
            logger.info("Network state permission granted")
            // Handle the case where the permission is not granted (though unlikely on modern Android)
            // You might log a warning or inform the user that the app might not be able to
            // function correctly without the permission.
        }
    }

    fun hasNetworkStatePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            "android.permission.ACCESS_NETWORK_STATE"
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initLogging() {
        val context = LoggerFactory.getILoggerFactory() as ch.qos.logback.classic.LoggerContext

        val filePattern = PatternLayoutEncoder()
        filePattern.context = context
        filePattern.pattern = "%d{HH:mm:ss,UTC} [%thread] %logger{0} - %msg%n"
        filePattern.start()

        val logcatTagPattern = PatternLayoutEncoder()
        logcatTagPattern.context = context
        logcatTagPattern.pattern = "%logger{0}"
        logcatTagPattern.start()

        val logcatPattern = PatternLayoutEncoder()
        logcatPattern.context = context
        logcatPattern.pattern = "[%thread] %msg%n"
        logcatPattern.start()

        val logcatAppender = LogcatAppender()
        logcatAppender.context = context
        logcatAppender.tagEncoder = logcatTagPattern
        logcatAppender.encoder = logcatPattern
        logcatAppender.start()

        val log = context.getLogger(Logger.ROOT_LOGGER_NAME)
        log.addAppender(logcatAppender)
        log.level = Level.INFO
//        log.level = Level.DEBUG
//        log.level = Level.TRACE
    }

}
