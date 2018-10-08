#!/bin/bash

docker run -d --name ss-sonarqube --network=sonar -p 9012:9000 -p 9092:9092 ss/sonarqube:1.0
sleep 2
echo "Open browser in http://localhost:9012 (give SonarQube some 10 seconds to start...)"
echo "Credentials: admin/admin"
echo "See more instructions in: https://hub.docker.com/_/sonarqube/"


