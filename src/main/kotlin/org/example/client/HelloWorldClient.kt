package org.example.client

import io.grpc.ManagedChannelBuilder
import org.example.HelloRequest
import org.example.HelloServiceGrpcKt

suspend fun helloClient() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()
    val stub = HelloServiceGrpcKt.HelloServiceCoroutineStub(channel)
    val response = stub.hello(HelloRequest.newBuilder().setName("test").build())
    println(response.message)
}

suspend fun main(args: Array<String>) {
    helloClient()
}