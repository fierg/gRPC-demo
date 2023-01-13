#!/bin/bash

docker run -it -p 8883:8883 -p 9001:9001 -v $(pwd)/mosquitto.conf:/mosquitto/config/mosquitto.conf -v /mosquitto/data eclipse-mosquitto