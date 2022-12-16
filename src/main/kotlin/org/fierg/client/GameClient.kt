package org.fierg.client

import io.grpc.ManagedChannelBuilder
import org.fierg.GameServiceGrpcKt
import org.fierg.GameStringRequest
import org.fierg.logger.Logger
import java.io.File

suspend fun helloClient() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()
    val stub = GameServiceGrpcKt.GameServiceCoroutineStub(channel)
    val response = stub.solve(GameStringRequest.newBuilder().setName(File("data/encryptedFile.txt").readText()).build())
    Logger.info(response.message)
}

suspend fun main(args: Array<String>) {
    helloClient()
}