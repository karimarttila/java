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



## Spring Boot 2.0 and Spring 5.0

I remember back in mid 2000 when [Java EE](https://en.wikipedia.org/wiki/Java_Platform,_Enterprise_Edition) was really bloated and [Spring Framework](https://en.wikipedia.org/wiki/Spring_Framework) came with its dependency injection and autowiring and made things easier. Well, Spring itself seems to be rather bloated nowadays and therefore we have [Spring Boot](http://spring.io/projects/spring-boot) which considerably makes building Spring applications easier.

Simple Server is implemented using [Spring Boot](http://spring.io/projects/spring-boot) v. 2.0.5 which uses [Spring Framework](https://en.wikipedia.org/wiki/Spring_Framework) v. 5.0.9 (see [Spring Boot Documentation - Appendix F. Dependency versions](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-dependency-versions.html). If you are familiar with Spring application Simple Server also uses autowiring extensively.


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

This is actually pretty nice and a wellcome addition to Java 9 - now we can test small code snippets like that without creating a bigger testing context.

## Logging

Spring Boot comes with Logback out of the box. Spring Boot should support groovy configuration, and in main side it worked. For some reason in the test side when testing the domain layer [DomainTest.java](src/test/java/simpleserver/domaindb/DomainTest.java) Spring Boot does not recognize logback-test.groovy file and I had to create equivalent logback-test.xml file which was recognized - real weird. Especially really weird when the groovy configuration gets recognized in the [ServerTest.java](src/test/java/simpleserver/webserver/ServerTest.java). Probably I miss to pass some Spring context to the DomainTest class. If someone figures out the reason for this please tell me. This is a bit of a nuisance since groovy configuration is so much more concise than xml configuration.

## Spring Profiles

Just for demonstration purposes I created two Spring profiles: dev and prod. You can start the script e.g. with dev profile like:

```bash
java -Dspring.profiles.active=prod --illegal-access=deny -jar build/libs/simple-server-0.1.jar
```

Dev profile uses file [application-dev.properties](src/main/resources/application-dev.properties) and you run above command you get logs denoting that we are running dev profile ("DEV" in log before "DEBUG", see [logback-env-dev.groovy](src/main/resources/logback-env-dev.groovy) configuration):

```bash
2018-10-11 23:28:23 1612 [main] DEV DEBUG simpleserver.Core - Running with Spring Boot v2.0.5.RELEASE, Spring v5.0.9.RELEASE 
20
```

## JUnit5

Latest Spring Boot that I used writing this was version 2.0.5 which comes with JUnit 4.12. There were major changes in new JUnit5 and I wanted to try those, so I configured [build.gradle](build.gradle) to use JUnit5.

You can run the tests as previously:

```bash
./gradlew test
```

If there are no changes in the files, the tests are not run again (unless you give clean task). One way to rerun tests is:

```bash
./gradlew --rerun-tasks test
```


# Conclusions

## Spring and Spring Boot

Spring Boot makes creating server / microservice applications using Java much easier. Spring provides a great framework which glues your components together and provides an overall framework to make things easier. It's hard to find a reason not to use Spring (i.e. to create a pure EE Java app). 

## Java as a Language

**Verbosity.** Java is really, really verbose if you compare it to dynamic languages like Python, Javascript or Clojure.

**Object oriented paradigm.** Object oriented paradigm was something cool in mid 1990's but nowadays seems more or less an unholy mess of classes having data, method and other classes having other data, methods and other classes... For data oriented applications I would rather use functional paradigm which makes a better separation between data and functions that operate on data. 

**Safety.** If you need a staticly typed safe language and runtime environment (JVM - some 20 years of solid testing in enterprise world) with a great ecosystem for a big enterprise project with tens of developers - Java is the solution. 

**Learning curve.** Difficult to say anything about this since I've been programming Java some 20 years now (first Java project was actually in year 1998). But after this exercise I have a feeling that for a newbie Java basic stuff cannot be learned in a couple of days and start being productive and learn the new stuff on the way as you can do e.g. with Python and Javascript. Also the frameworks take some time to learn (even a hard-core Java programmer like me forgot some peculiarities regarding Spring when I have not done serious Java/Spring programming about in 1,5 years).

**Summa summarum.** Java is not that bad. There are a lot of cons in Java: type safety, great ecosystem, JVM is battle tested runtime, great tooling, a huge developer pool etc. All these reasons make Java a great enterprise language for big critical enterprise projects. But the price is a verbose language with a rather slow development cycle. E.g. programming in Java really requires TDD since you can more easily develop your system in small increments when you use a good unit/integration test as development bench (and you get a test suite as a by-product) - not a bad thing per se. Something you don't need e.g. in Clojure because you have a real Lisp REPL and you can grow your system in a more organic way with the REPL (and you still can create a good test suite).