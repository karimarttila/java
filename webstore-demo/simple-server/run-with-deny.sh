#!/bin/bash

# --add-opens is for using JDK 11 with Spring.
java -Dspring.profiles.active=dev --add-opens java.base/java.lang=ALL-UNNAMED --illegal-access=deny -jar build/libs/simple-server-0.2.jar


#java -Dspring.profiles.active=dev --illegal-access=deny -jar build/libs/simple-server-0.2.jar
