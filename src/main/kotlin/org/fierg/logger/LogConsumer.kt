package org.fierg.logger

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import org.fierg.logger.impl.ConsoleLogger
import org.fierg.logger.impl.FileLogger
import org.fierg.logger.impl.MQTTLogger

interface LogConsumer {

  companion object {
      val kodein = Kodein {
          bind<LogConsumer>("console") with singleton { ConsoleLogger }
          bind<LogConsumer>("mqtt") with singleton { MQTTLogger }
          bind<LogConsumer>("file") with singleton { FileLogger }
      }
      var currentTag = "console"

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