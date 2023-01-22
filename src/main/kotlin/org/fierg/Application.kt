package org.fierg

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.fierg.client.GameClient
import org.fierg.logger.LogConsumer
import org.fierg.server.GameServer

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val port = 8080
        embeddedServer(Netty, port) {
            LogConsumer.getImpl().info("Starting HTTP REST Server at port $port")
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
                get("/gameserver"){
                    call.respondText("Game Server/Solver started.")
                    GameServer().serveGRPC()
                }
            }
        }.start(wait = true)
    }
}