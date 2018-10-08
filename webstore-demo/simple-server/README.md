# Java Simple Server  <!-- omit in toc -->


## Table of Contents  <!-- omit in toc -->
- [Introduction](#introduction)
- [Tools and Versions](#tools-and-versions)
- [Music](#music)
- [Gradle](#gradle)
- [SonarQube](#sonarqube)


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

You can install [SonarQube](https://www.sonarqube.org/) code quality server to analyze and visualize the code quality of the Java project. Using [Docker](https://www.docker.com/) this is pretty simple and keeps your precious workstation clean. See documentation in the [Docker repository](https://hub.docker.com/_/sonarqube/).

```bash
cd docker/orig-sonarqube-scripts
docker pull sonarqube
docker network create -d bridge sonar
./start-sonarqube-container.sh
```

Then open browser in http://localhost:9011 , login using admin/admin. Then Adminstration/Marketplace => Update SonarJava to 5.6 version (version 5.2 which comes with SonarQube 7.1 of that Docker version does not support Java 10).
Then SonarQube says: "SonarQube needs to be restarted in order to
update 1 plugins" => click "Restart".

Once you have updated and SonarQube is ready run:

```bash
./commit-docker.sh
```

Now you have committed the updated SonarQube version to a new image ss/sonarqube:1.0 (ss as Simple Server, i.e. not as Schutzstaffel).

Next move one directory up to the docker directory.

```bash
./start-sonarqube-container.sh
```

Then open browser in http://localhost:9012 (note port 9012 this time). 

Move one directory up again, to simple-server main directory. Run analysis script:

```bash
./run-ss-container-sonarqube-analysis.sh
```

You can get a bash to the container by:

```bash
exec-bash-in-sonarqube-container.sh
```

NOTE. As the SonarQube UI says: "Embedded database should be used for evaluation purpose only. The embedded database will not scale, it will not support upgrading to newer versions of SonarQube, and there is no support for migrating your data out of it into a different database engine."

So, once you delete the container, your history is gone. Basically I configured this Docker based SonarQube just to make one time quality analysis for my project. Maybe I will install another Sonarqube Docker version which supports database in a Docker volume, so that the history is not gone with the container.
