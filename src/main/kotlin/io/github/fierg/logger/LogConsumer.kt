package io.github.fierg.logger

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.github.fierg.logger.impl.ConsoleLogger
import io.github.fierg.logger.impl.FileLogger
import io.github.fierg.logger.impl.MQTTLogger

interface LogConsumer {

  companion object {
      private var currentTag = "console"
      private val kodein = Kodein {
          bind<LogConsumer>("console") with singleton { ConsoleLogger }
          bind<LogConsumer>("mqtt") with singleton { MQTTLogger }
          bind<LogConsumer>("file") with singleton { FileLogger }
      }
      fun getImpl(): LogConsumer {
          return kodein.instance(currentTag)
      }
      fun changeImpl(newTag: String) {
          currentTag = newTag
      }
  }

    fun info(msg:String)
    fun debug(msg:String)
    fun error(msg:String)

}