#!/usr/bin/env bash
./gradlew -q --console=plain uberJar && java -jar app/build/libs/app-uber.jar $@