package org.fierg.solver
import org.fierg.extensions.length
import org.fierg.logger.Logger
import org.fierg.model.GameInstance
import com.google.gson.Gson
import java.io.File

class FileHandler {
    companion object{
        private val regex = Regex("((?:\\d+)|(?:x)) \\+ ((?:\\d+)|(?:x)) = ((?:\\d+)|(?:x))")
        fun readBasicFile(file: String): GameInstance {
            Logger.info("Reading file from $file")
            val game = File(file).readLines().map { line ->
                if (regex.matches(line)) {
                    val groups = regex.find(line)!!.groupValues.filterIndexed{ i, _ -> i > 0}
                    groups.map { it.toIntOrNull() }.toTypedArray()
                } else throw IllegalArgumentException("Game Instance is malformed!")
            }.toTypedArray()

            return getGame(game)
        }


        fun getJSON(gameInstance: GameInstance): String? {
            Logger.info("Generating JSON from object ...")
            return Gson().toJson(gameInstance)
        }

        fun fromJSON(json: String): GameInstance? {
            Logger.info("Reading Instance from JSON ...")
            return Gson().fromJson(json, GameInstance::class.java)
        }

        fun readString(gameString :String): GameInstance {
            Logger.info("Reading game from string $gameString")
            val game = gameString.split("\n").map { line ->
                if (regex.matches(line)) {
                    val groups = regex.find(line)!!.groupValues.filterIndexed{ i, _ -> i > 0}
                    groups.map { it.toIntOrNull() }.toTypedArray()
                } else throw IllegalArgumentException("Game Instance is malformed!")
            }.toTypedArray()

            return getGame(game)
        }

        private fun getGame(game: Array<Array<Int?>>): GameInstance {
            val nrOfDigits = game.maxOf { line -> line.maxOf { it.length() } }

            if (game.map { line -> line.size }.toSet()
                    .first() != 3
            ) throw IllegalArgumentException("Game Instance is malformed!")

            Logger.info("Game Instance has $nrOfDigits digits at each position.")
            return GameInstance(game, nrOfDigits)
        }
    }
}