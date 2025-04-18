package com.jaeckel.androidportal

import android.app.Application
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.android.LogcatAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
        val path = getApplicationContext().getFilesDir().getAbsolutePath()
        logger.info("path: $path")
        logger.debug("<DEBUG>")
        logger.info("<INFO> {}", logger)
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
