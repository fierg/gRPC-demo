package org.fierg

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.fierg.client.GameClient
import org.fierg.logger.LogConsumer
import org.fierg.server.GameServer

open class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val port = 8080
            embeddedServer(Netty, port) {
                LogConsumer.getImpl().info("Starting HTTP REST Server at port $port")
                LogConsumer.getImpl().info("Call HTTP:GET at localhost:$port/gameserver to start the server.")
                LogConsumer.getImpl().info("Call HTTP:GET at localhost:$port/gameclient to start the client.")
                LogConsumer.getImpl().info("Call HTTP:GET at localhost:$port/logger/{type} to change the logger impl. available types are: console, mqtt, file")
                routing {
                    get("/") {
                        call.respondText("Hello, world!")
                    }
                    get("/logger/{type}") {
                        LogConsumer.changeImpl(call.parameters["type"]!!)
                        call.respondText("Logger impl changed to ${call.parameters["type"]}")
                        LogConsumer.getImpl().info("Logger impl changed to ${call.parameters["type"]}")
                    }
                    get("/gameclient") {
                        call.respondText("Game Client started.")
                        GameClient().sendRequest()
                    }
                    get("/gameserver") {
                        call.respondText("Game Server/Solver started.")
                        GameServer().serveGRPC()
                    }
                }
            }.start(wait = true)
        }
    }
}