package org.fierg.logger

interface LogConsumer {

    fun info(msg:String)
    fun debug(msg:String)
    fun error(msg:String)
}