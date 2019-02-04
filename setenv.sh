#!/bin/bash

# Project: Simple Server Java version
# File: setenv.sh
# Description: Sets JAVA_HOME, M2_HOME and sets PATH.
# NOTE: Nothing.
# Copyright (c) 2018 Kari Marttila
# Author: Kari Marttila
# Version history:
# - 2018-10-08: First version.

# Let's start using OpenJDK since Oracle has commercialized its own JDK.
# Let's try version 10 (version 11 had some issues with Gradle).
export JAVA_HOME=/mnt/local/openjdk-10
echo "JAVA_HOME="$JAVA_HOME
export GRADLE_HOME=/mnt/local/gradle-5.1.1
echo "GRADLE_HOME="$GRADLE_HOME
export M2_HOME=/mnt/local/maven-3.5.4
echo "M2_HOME="$M2_HOME
export GROOVY_HOME=/mnt/local/groovy-2.4.15
echo "GROOVY_HOME="$GROOVY_HOME
export ANT_HOME=/mnt/local/apache-ant-1.10.1
echo "ANT_HOME="$ANT_HOME
export PATH=$JAVA_HOME/bin:$GRADLE_HOME/bin:$M2_HOME/bin:$GROOVY_HOME/bin:$ANT_HOME/bin:$PATH
echo "PATH="$PATH

