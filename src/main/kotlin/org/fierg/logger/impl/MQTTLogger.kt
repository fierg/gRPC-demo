package org.fierg.logger.impl

import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.fierg.logger.LogConsumer
import java.util.*


object MQTTLogger: LogConsumer {

    val TOPIC = "TestTopic"

    private fun getPublisher(): IMqttClient {
        val publisherId = UUID.randomUUID().toString()
        val publisher: IMqttClient = MqttClient("tcp://iot.eclipse.org:1883", publisherId)
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        options.connectionTimeout = 10
        publisher.connect(options)
        return publisher
    }

    override fun info(msg: String) {
        getPublisher().publish(TOPIC, MqttMessage("test".toByteArray()))
    }

    override fun debug(msg: String) {
        TODO("Not yet implemented")
    }

    override fun error(msg: String) {
        TODO("Not yet implemented")
    }
}