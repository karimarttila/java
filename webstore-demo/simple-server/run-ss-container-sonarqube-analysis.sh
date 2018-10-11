#!/bin/bash

echo "See gradle.properties for SonarQube properties."
echo "Start the SonarQube container first!"
echo "See project Simple SonarQube TODO: link!"
sleep 1
./gradlew sonarqube

