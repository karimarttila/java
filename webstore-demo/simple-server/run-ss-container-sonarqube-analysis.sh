#!/bin/bash

echo "See gradle.properties for SonarQube properties."
echo "Start the SonarQube container first using script:"
echo "start-sonarqube-container.sh"
sleep 1
./gradlew sonarqube

