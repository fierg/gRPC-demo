package org.fierg.client

import io.grpc.ManagedChannelBuilder
import org.fierg.GameServiceGrpcKt
import org.fierg.GameStringRequest
import org.fierg.logger.Logger

suspend fun helloClient() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()
    val stub = GameServiceGrpcKt.GameServiceCoroutineStub(channel)
    val response = stub.solve(GameStringRequest.newBuilder().setName("40 + 10 = 50\n9 + 16 = 25\n49 + 26 = 75").build())
    Logger.info(response.message)
}

suspend fun main(args: Array<String>) {
    helloClient()
}