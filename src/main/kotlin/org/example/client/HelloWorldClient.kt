package org.example.client

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import org.example.HelloReply
import org.example.HelloRequest
import org.example.HelloServiceGrpcKt

fun helloClient() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()
    val stub = HelloServiceGrpcKt.newBlockingStub(channel)
    val response = stub.hello(HelloRequest.newBuilder().setName("Baeldung").build())
    println(response.message)
}

fun asyncHelloClient() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()
    HelloServiceGrpcKt.newStub(channel).hello(
        HelloRequest.newBuilder().setName("Baeldung").build(), object : StreamObserver<HelloReply> {
            override fun onNext(response: HelloReply?) {
                println(response?.message)
            }

            override fun onError(throwable: Throwable?) {
                throwable?.printStackTrace()
            }

            override fun onCompleted() {
                println("Completed!")
            }
        }
    )
}

fun main(args: Array<String>) {
    helloClient()
    asyncHelloClient()
}