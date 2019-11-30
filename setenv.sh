#!/bin/bash

# Project: Simple Server Java version
# File: setenv.sh
# Description: Sets JAVA_HOME, M2_HOME and sets PATH.
# NOTE: Nothing.
# Copyright (c) 2018 Kari Marttila
# Author: Kari Marttila
# Version history:
# - 2018-10-08: First version.

#export JAVA_HOME=/mnt/local/openjdk-10
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
echo "JAVA_HOME="$JAVA_HOME
export GRADLE_HOME=/mnt/ssd2/local/gradle-6.0.1
echo "GRADLE_HOME="$GRADLE_HOME
export M2_HOME=/mnt/ssd2/local/maven-3.6.3
echo "M2_HOME="$M2_HOME
export GROOVY_HOME=/mnt/ssd2/local/groovy-2.5.8
echo "GROOVY_HOME="$GROOVY_HOME
export ANT_HOME=/mnt/ssd2/local/ant-1.9.14
echo "ANT_HOME="$ANT_HOME
export PATH=$JAVA_HOME/bin:$GRADLE_HOME/bin:$M2_HOME/bin:$GROOVY_HOME/bin:$ANT_HOME/bin:$PATH
echo "PATH="$PATH

