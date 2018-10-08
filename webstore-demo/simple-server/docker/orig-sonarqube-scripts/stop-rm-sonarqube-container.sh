#!/bin/bash

docker stop $(docker ps -a -q  --filter ancestor=sonarqube) ; docker rm $(docker ps -a -q  --filter ancestor=sonarqube)
