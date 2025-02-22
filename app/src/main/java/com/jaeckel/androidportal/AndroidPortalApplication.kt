package com.jaeckel.androidportal

import android.app.Application
import com.arcao.slf4j.timber.BuildConfig
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
        logger.error("-----> Hello ERROR")
        logger.warn("-----> Hello WARN")

    }
}
