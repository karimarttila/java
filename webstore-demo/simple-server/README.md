# Java Simple Server  <!-- omit in toc -->


## Table of Contents  <!-- omit in toc -->
- [Introduction](#introduction)
- [Tools and Versions](#tools-and-versions)
- [Music](#music)
- [Gradle](#gradle)
- [SonarQube](#sonarqube)
- [Java REPL](#java-repl)


## Introduction

I have been programming Java some 20 years, so I didn't do this exercise to learn Java. I mainly wanted to implement the Simple Server using Java just to compare the Java implementation with previous Clojure and Javascript implementations and document my experiences between these three languages. Maybe later I will implement the Simple Server also using Python and Go. Another reason was to study the new Java 10 features. I would have used Java 11 but for some reason couldn't make it work in Gradle - maybe trying to fix that later and upgrade to Java 11.

## Tools and Versions

I have used the following tools and versions:

- [Java](https://www.oracle.com/java/): [openjdk](http://openjdk.java.net/) 10.0.2 2018-07-17 / OpenJDK Runtime Environment 18.3 (build 10.0.2+13) / OpenJDK 64-Bit Server VM 18.3 (build 10.0.2+13, mixed mode).
- [Gradle](https://gradle.org/) 4.10.2.
- [Spring Boot](http://spring.io/projects/spring-boot) 2.0.5.RELEASE.

## Music

A lot of Blues was consumed during the programming sessions. Blues was provided by various online radio stations, but [Blues Radio](https://www.internet-radio.com/station/bluesradio/) deserves a special mentioning. If I could play blues guitar like those guys I wouldn't hack a day in my life but play blues all day long.

## Gradle

You can create the gradle wrapper using command:

```bash
gradle wrapper
```

Gradle wrapper is also provided in this Git repo in [gradle](gradle) directory.

## SonarQube

I created a [Simple SonarQube](https://github.com/karimarttila/docker/tree/master/simple-sonarqube) container for Java 10 projects. I have usually configured [Jenkins](https://jenkins.io/) (see my blog article [Jenkins on AWS](https://medium.com/tieto-developers/jenkins-on-aws-49133e011ac5) if you are interested) and SonarQube servers for my Java projects in which I have been working as a Software Architect - now I wanted to see how easy [SonarQube](https://www.sonarqube.org/) was to used in my local Ubuntu 18 workstation using as a [Docker](https://www.docker.com/) container.


The [build.gradle](build.gradle) file shows how to configure the sonarqube plugin:

```gradle
plugins {
  id "org.sonarqube" version "2.6.2"
}
```

And we also need to configure the SonarQube properties in  [gradle.properties](gradle.properties) file.

```gradle
# SonarQube
systemProp.sonar.host.url=http://localhost:9012
systemProp.sonar.login=admin
systemProp.sonar.password=admin
systemProp.sonar.languages=java
systemProp.sonar.java.source=10
systemProp.sonar.java.target=10
```

Then you are ready to run the analyses.

Example script [run-ss-container-sonarqube-analysis.sh](run-ss-container-sonarqube-analysis.sh) :

```bash
./run-ss-container-sonarqube-analysis.sh
```

## Java REPL

Java 9 introduced a shiny Java REPL for the Java developers)! This is nothing compared to Lisp REPLs but anyway it's nice to have a Java REPL at last. You can start a Java REPL session with command "jshell". 

Here a short Java REPL session to explore how to convert Java HashMap to JSONObject.

```bash
jshell> /env -class-path /home/kari/.m2/repository/org/json/json/20140107/json-20140107.jar
|  Setting new options and restoring state.
jshell> import org.json.JSONObject;
jshell> Map<String, String> pg = new HashMap<>();
pg ==> {}
jshell> pg.put("1", "Movies");
$3 ==> null
jshell> pg.put("2", "Books");
$4 ==> null
jshell> pg
pg ==> {1=Movies, 2=Books}
jshell> JSONObject jPg = new JSONObject(pg);
jPg ==> {"1":"Movies","2":"Books"}
```

