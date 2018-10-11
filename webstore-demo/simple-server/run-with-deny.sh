#!/bin/bash

java -Dspring.profiles.active=dev --illegal-access=deny -jar build/libs/simple-server-0.1.jar
