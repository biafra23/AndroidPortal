package com.jaeckel.androidportal

import android.util.Log
import androidx.compose.ui.layout.layout
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.Core
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.Layout
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.Property
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.config.plugins.PluginAttribute
import org.apache.logging.log4j.core.config.plugins.PluginElement
import org.apache.logging.log4j.core.config.plugins.PluginFactory
import org.apache.logging.log4j.core.config.plugins.PluginValue
import org.apache.logging.log4j.core.layout.PatternLayout
import timber.log.Timber
import java.io.Serializable

@Plugin(name = "Timber", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
class TimberAppender(
    name: String,
    filter: Filter?,
    layout: Layout<out Serializable>?,
    ignoreExceptions: Boolean,
    properties: Array<Property?>?
) : AbstractAppender(name, filter, layout, ignoreExceptions, properties) {

    override fun append(event: LogEvent) {
        val formattedMessage = layout?.toByteArray(event)?.toString(Charsets.UTF_8) ?: event.message.formattedMessage
        when (event.level.intLevel()) {
            in 0..200 -> Timber.v(formattedMessage)
            in 200..300 -> Timber.d(formattedMessage)
            in 300..400 -> Timber.i(formattedMessage)
            in 400..500 -> Timber.w(formattedMessage)
            else -> Timber.e(formattedMessage)
        }
    }

    companion object {
        @PluginFactory
        @JvmStatic
        fun createAppender(
            @PluginAttribute("name") name: String,
            @PluginElement("Layout") layout: Layout<out Serializable>?,
            @PluginElement("Filter") filter: Filter?,
            @PluginAttribute("ignoreExceptions") ignoreExceptions: Boolean = true,
            @PluginElement("Properties") properties: Array<Property?>?
        ): TimberAppender {
            val actualLayout = layout ?: PatternLayout.createDefaultLayout()
            return TimberAppender(name, filter, actualLayout, ignoreExceptions, properties)
        }
    }
}