package io.github.fierg.client

import io.github.fierg.GameServerGrpcKt
import io.github.fierg.GameStringRequest
import io.github.fierg.logger.LogConsumer
import io.grpc.ManagedChannelBuilder
import java.io.File

class GameClient {
    suspend fun sendRequest() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15001).usePlaintext().build()
        val stub = GameServerGrpcKt.GameServerCoroutineStub(channel)
        val response = stub.solve(GameStringRequest.newBuilder().setPayload(File("data/encryptedFile.txt").readText()).build())
        LogConsumer.getImpl().info(response.payload)
    }
}

suspend fun main(args: Array<String>) {
    GameClient().sendRequest()
}
