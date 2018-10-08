#!/bin/bash

docker run -d --name sonarqube --network=sonar -p 9011:9000 -p 9092:9092 sonarqube
sleep 2
echo "Open browser in http://localhost:9011 (give SonarQube some 10 seconds to start...)"
echo "Credentials: admin/admin"
echo "See more instructions in: https://hub.docker.com/_/sonarqube/"

