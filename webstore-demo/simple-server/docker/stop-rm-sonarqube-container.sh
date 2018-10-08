#!/bin/bash

docker stop $(docker ps -a -q  --filter ancestor=ss/sonarqube:1.0) ; docker rm $(docker ps -a -q  --filter ancestor=ss/sonarqube:1.0)
