package io.github.fierg.logger.impl

import io.github.fierg.logger.LogConsumer
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

object ConsoleLogger : LogConsumer {
    var LOGGER: Logger? = null

    init {
        try {
            LogManager.getLogManager().readConfiguration(this.javaClass.classLoader.getResource("logging.properties").openStream())
        } catch (ex: Exception) {
            Logger.getLogger(Logger::class.java.name).log(Level.SEVERE, "Failed to read logging.properties file", ex)
        } catch (ex: SecurityException) {
            Logger.getLogger(Logger::class.java.name).log(Level.SEVERE, "Failed to read logging.properties file", ex)
        } catch (ex: ExceptionInInitializerError) {
            Logger.getLogger(Logger::class.java.name).log(Level.SEVERE, "Failed to read logging.properties file", ex)
        }
        LOGGER = Logger.getLogger(Logger::class.java.name)
    }

    override fun debug(msg:String) {
        LOGGER!!.log(Level.FINE, msg)
    }

    override fun info(msg:String) {
        LOGGER!!.log(Level.INFO, msg)
    }

    override fun error(msg:String) {
        LOGGER!!.log(Level.SEVERE, msg)
    }

    fun warn(msg:String) {
        LOGGER!!.log(Level.WARNING, msg)
    }

    fun setLogLevelToDebug() {
        LOGGER!!.level = Level.ALL
    }

    fun setLogLevelToQuiet() {
        LOGGER!!.level = Level.OFF
    }
}