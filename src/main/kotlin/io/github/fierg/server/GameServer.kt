package io.github.fierg.server

import io.github.fierg.GameServerGrpcKt
import io.github.fierg.GameStringReply
import io.github.fierg.GameStringRequest
import io.github.fierg.logger.LogConsumer
import io.github.fierg.logger.impl.ConsoleLogger
import io.github.fierg.solver.BruteForceSolver
import io.github.fierg.solver.FileHandler
import io.grpc.ServerBuilder

class GameServer : GameServerGrpcKt.GameServerCoroutineImplBase() {

    override suspend fun solve(request: GameStringRequest): GameStringReply {
        ConsoleLogger.info("Handling request...")
        val game = FileHandler.readEncryptedFile(request.payload)
        return GameStringReply.newBuilder().setPayload(BruteForceSolver().solve(game)).build()
    }

    fun serveGRPC() {
        val gameService = GameServer()
        val port = 15001
        val server = ServerBuilder
            .forPort(port)
            .addService(gameService)
            .build()

        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
            server.awaitTermination()
        })
        LogConsumer.getImpl().info("Starting gRPC Server at port $port ...")
        server.start()
        server.awaitTermination()
    }
}

fun main(args: Array<String>) {
    GameServer().serveGRPC()
}