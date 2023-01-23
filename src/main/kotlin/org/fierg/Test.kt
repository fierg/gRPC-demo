package org.fierg

import com.google.gson.Gson
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.fierg.logger.impl.MQTTLogger
import org.fierg.model.EncryptedGameInstance
import org.fierg.solver.Generator

class Test {
}

fun main(){
    val game = Generator.generateRandom(3)
    val gameDTO = EncryptedGameInstance.fromGameInstance(game).toGameDTO("test-server", 0)
    MQTTLogger.getMQTTServer().publish("GAME", MqttMessage( Gson().toJson(gameDTO).toByteArray()))
}