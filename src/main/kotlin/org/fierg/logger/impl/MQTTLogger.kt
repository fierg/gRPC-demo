package org.fierg.logger.impl

import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.fierg.logger.LogConsumer
import java.util.*


object MQTTLogger: LogConsumer {

    val TOPIC = "Solver"
    private var publisher: IMqttClient? = null

    private fun getPublisher(): IMqttClient {
        if (publisher != null) return publisher!!

        val publisherId = UUID.randomUUID().toString()
        val publisher1: IMqttClient = MqttClient("tcp://localhost:8883", publisherId)
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        options.connectionTimeout = 10
        publisher1.connect(options)
        publisher = publisher1
        return publisher1
    }

    override fun info(msg: String) {
        getPublisher().publish(TOPIC, MqttMessage("INFO: $msg".toByteArray()))
    }

    override fun debug(msg: String) {
        getPublisher().publish(TOPIC, MqttMessage("DEBUG: $msg".toByteArray()))
    }

    override fun error(msg: String) {
        getPublisher().publish(TOPIC, MqttMessage("ERROR: $msg".toByteArray()))
    }
}