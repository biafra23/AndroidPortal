package com.jaeckel.androidportal

import android.app.Application
import com.arcao.slf4j.timber.BuildConfig
import org.apache.logging.log4j.LogManager
import org.slf4j.LoggerFactory
import timber.log.Timber


class AndroidPortalApplication : Application() {
    val logger = LoggerFactory.getLogger(AndroidPortalApplication::class.java)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.DebugTree())
//            Timber.plant(CrashReportingTree())
        }
        logger.debug("-----> Hello DEBUG")
        logger.info("-----> Hello INFO")
        logger.warn("-----> Hello WARN")
        logger.error("-----> Hello ERROR")

//        val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("bootnodes.json")
//        val content = inputStream?.bufferedReader().use { it?.readText() }
//        logger.error("-----> inputStream: $content")

//        val otherLogger = LogManager.getLogger()
//        otherLogger.error("Hello World")
    }
}
