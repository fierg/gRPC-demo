package org.fierg.logger.impl

import org.fierg.logger.LogConsumer
import java.io.File
import java.io.FileOutputStream

object FileLogger: LogConsumer {

    private val f = File("data/log.txt")
    override fun info(msg: String) {
        FileOutputStream(f, true).bufferedWriter().use { writer ->
            writer.append("INFO: $msg\n")
        }
    }

    override fun debug(msg: String) {
        FileOutputStream(f, true).bufferedWriter().use { writer ->
            writer.append("DEBUG: $msg\n")
        }
    }

    override fun error(msg: String) {
        FileOutputStream(f, true).bufferedWriter().use { writer ->
            writer.append("ERROR: $msg\n")

        }
    }
}