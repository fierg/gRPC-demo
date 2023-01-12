package org.fierg.server

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import io.grpc.ServerBuilder
import org.fierg.GameServiceGrpcKt
import org.fierg.GameStringReply
import org.fierg.GameStringRequest
import org.fierg.logger.LogConsumer
import org.fierg.logger.impl.ConsoleLogger
import org.fierg.logger.impl.MQTTLogger
import org.fierg.solver.BruteForceSolver
import org.fierg.solver.FileHandler

class HelloService : GameServiceGrpcKt.GameServiceCoroutineImplBase() {


    override suspend fun solve(request: GameStringRequest): GameStringReply {
        ConsoleLogger.info("Handling request...")
        val game = FileHandler.readEncryptedFile(request.name)
        return GameStringReply.newBuilder().setMessage(BruteForceSolver().solve(game)).build()
    }
}

fun helloServer() {
    val kodein = Kodein {
        bind<LogConsumer>("console") with singleton { ConsoleLogger }
        bind<LogConsumer>("mqtt") with singleton { MQTTLogger }
    }

    val helloService = HelloService()
    val server = ServerBuilder
        .forPort(15001)
        .addService(helloService)
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
        server.awaitTermination()
    })
    kodein.instance<LogConsumer>("mqtt").info("Starting Server ...")
    server.start()
    server.awaitTermination()
}

fun main(args: Array<String>) {
    helloServer()
}