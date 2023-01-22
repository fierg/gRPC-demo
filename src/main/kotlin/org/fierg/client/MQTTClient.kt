package org.fierg.client

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

object MQTTClient {
    @JvmStatic
    fun main(args: Array<String>) {
        val broker = "tcp://localhost:8883"
        val topic = "Solver"
        val clientid = "subscribe_client"
        val qos = 0
        try {
            val client = MqttClient(broker, clientid, MemoryPersistence())
            val options = MqttConnectOptions()
            options.connectionTimeout = 60
            options.keepAliveInterval = 60
            client.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable) {
                    println("connection Lost: " + cause.message)
                }

                override fun messageArrived(topic: String, message: MqttMessage) {
                    println("topic: $topic message content: ${String(message.payload)}")
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {
                    println("delivery Complete ---------" + token.isComplete)
                }
            })
            client.connect(options)
            client.subscribe(topic, qos)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}