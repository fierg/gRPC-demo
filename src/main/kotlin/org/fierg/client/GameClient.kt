package org.fierg.client

import io.grpc.ManagedChannelBuilder
import org.fierg.GameServerGrpcKt
import org.fierg.GameStringRequest
import org.fierg.logger.LogConsumer
import java.io.File

class GameClient {
    suspend fun sendRequest() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15001).usePlaintext().build()
        val stub = GameServerGrpcKt.GameServerCoroutineStub(channel)
        val response = stub.solve(GameStringRequest.newBuilder().setName(File("data/encryptedFile.txt").readText()).build())
        LogConsumer.getImpl().info(response.message)
    }
}

suspend fun main(args: Array<String>) {
    GameClient().sendRequest()
}
