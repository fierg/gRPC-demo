package org.fierg.server

import org.fierg.solver.FileHandler
import org.fierg.solver.ILPSolver
import org.fierg.logger.Logger
import io.grpc.ServerBuilder
import org.fierg.GameServiceGrpcKt
import org.fierg.GameStringReply
import org.fierg.GameStringRequest

class HelloService : GameServiceGrpcKt.GameServiceCoroutineImplBase() {
    override suspend fun solve(request: GameStringRequest): GameStringReply {
        Logger.info("Handling request...")
        val game = FileHandler.readString(request.name)
        return GameStringReply.newBuilder().setMessage(ILPSolver().solve(game).second).build()
    }
}

fun helloServer() {
    val helloService = HelloService()
    val server = ServerBuilder
        .forPort(15001)
        .addService(helloService)
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
        server.awaitTermination()
    })
    Logger.info("Starting Server ...")
    server.start()
    server.awaitTermination()
}

fun main(args: Array<String>) {
    helloServer()
}