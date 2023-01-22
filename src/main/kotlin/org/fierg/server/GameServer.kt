package org.fierg.server

import io.grpc.ServerBuilder
import org.fierg.GameServerGrpcKt
import org.fierg.GameStringReply
import org.fierg.GameStringRequest
import org.fierg.logger.LogConsumer
import org.fierg.logger.impl.ConsoleLogger
import org.fierg.solver.BruteForceSolver
import org.fierg.solver.FileHandler

class GameServer : GameServerGrpcKt.GameServerCoroutineImplBase() {

    override suspend fun solve(request: GameStringRequest): GameStringReply {
        ConsoleLogger.info("Handling request...")
        val game = FileHandler.readEncryptedFile(request.name)
        return GameStringReply.newBuilder().setMessage(BruteForceSolver().solve(game)).build()
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