# Java Simple Server  <!-- omit in toc -->


# Table of Contents  <!-- omit in toc -->
- [Prologue](#prologue)
- [Introduction](#introduction)
- [Tools and Versions](#tools-and-versions)
- [Gradle](#gradle)
- [Spring Boot 2.0 and Spring 5.0](#spring-boot-20-and-spring-50)
- [SonarQube](#sonarqube)
- [SonarQube](#sonarqube-1)
- [IDE](#ide)
- [Java Static Code Analysis](#java-static-code-analysis)
- [Java REPL](#java-repl)
- [Logging](#logging)
- [Spring Profiles](#spring-profiles)
- [JUnit5](#junit5)
- [Code Coverage](#code-coverage)
- [Java Verbosity](#java-verbosity)
- [Conclusions](#conclusions)
  - [Spring and Spring Boot](#spring-and-spring-boot)
  - [Java as a Language](#java-as-a-language)
- [Epilogue](#epilogue)


# Prologue

"Oh, god. Have I been using the wrong language all these years?"


# Introduction

I have been programming Java some 20 years, so I didn't do this exercise to learn Java. I mainly wanted to implement the Simple Server using Java just to compare the Java implementation with previous [Clojure](https://github.com/karimarttila/clojure/tree/master/clj-ring-cljs-reagent-demo/simple-server) and [Javascript](https://github.com/karimarttila/javascript/tree/master/webstore-demo/simple-server) implementations and document my experiences between these three languages. Maybe later I will implement the Simple Server also using Python and Go. Another reason was to study the new Java 10 features. I would have used Java 11 but for some reason I couldn't make it work in Gradle - maybe trying to fix that later and upgrade to Java 11.


# Tools and Versions

I have used the following tools and versions:

- [Java](https://www.oracle.com/java/): [openjdk](http://openjdk.java.net/) 10.0.2 2018-07-17 / OpenJDK Runtime Environment 18.3 (build 10.0.2+13) / OpenJDK 64-Bit Server VM 18.3 (build 10.0.2+13, mixed mode).
- [Gradle](https://gradle.org/) 4.10.2.
- [Spring Boot](http://spring.io/projects/spring-boot) 2.0.5.RELEASE.


# Gradle

You can create the gradle wrapper using command:

```bash
gradle wrapper
```

Gradle wrapper is also provided in this Git repo in [gradle](gradle) directory.


# Spring Boot 2.0 and Spring 5.0

I remember back in mid 2000 when [Java EE](https://en.wikipedia.org/wiki/Java_Platform,_Enterprise_Edition) was really bloated and [Spring Framework](https://en.wikipedia.org/wiki/Spring_Framework) came with its dependency injection and autowiring and made things easier. Well, Spring itself seems to be rather bloated nowadays and therefore we have [Spring Boot](http://spring.io/projects/spring-boot) which considerably makes building Spring applications easier.

Simple Server is implemented using [Spring Boot](http://spring.io/projects/spring-boot) v. 2.0.5 which uses [Spring Framework](https://en.wikipedia.org/wiki/Spring_Framework) v. 5.0.9 (see [Spring Boot Documentation - Appendix F. Dependency versions](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-dependency-versions.html). If you are familiar with Spring applications you notice that Simple Server also uses autowiring extensively.

Spring makes things easier but it also causes quite a lot of issues. E.g. if you just forget one annotation you may spend a couple of hours figuring out why some property is not injected properly. Spring is more or less a black box and if everything is configured properly you are good to go. If not, then it is at times pretty frustrating to find the root cause from the black box. I rarely had this kind of configuration issues with Clojure or Python.

One example of the Spring issues was when I decided not to use the JUnit4 testing framework which comes with Spring Boot 2 but to use JUnit5. Everything went well until I introduced configuration autowiring from application.properties file - suddenly the Spring Rest Controller tests started to fail (couldn't find the application.properties file from the servlet context...). I had to google quite a while until I figured out how to setup the Spring Rest Controller tests and where to put the damn application.properties file so that Spring can find them in those tests. The lesson of the story is: If you use Spring and Spring Boot exactly as it is configured by default everything (mostly) works just fine. But if you want to do something a bit differently - you may need to google quite a bit before you figure out how to make everything wired to run the tests and the application jar.


# SonarQube

I created a [Simple SonarQube](https://github.com/karimarttila/docker/tree/master/simple-sonarqube) container for Java 10 projects. I have usually configured [Jenkins](https://jenkins.io/) (see my blog article [Jenkins on AWS](https://medium.com/tieto-developers/jenkins-on-aws-49133e011ac5) if you are interested) and SonarQube servers for my Java projects in which I have been working as a Software Architect - now I wanted to see how easy [SonarQube](https://www.sonarqube.org/) was to use in my local Ubuntu 18 workstation using as a [Docker](https://www.docker.com/) container.


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


# IDE

[IntelliJ IDEA](https://www.jetbrains.com/idea/) is my favorite Java IDE. I used for years [Eclipse](http://www.eclipse.org/) since it is free and very widely used with our offshore developers (and I was working as an onsite architect at that time - clear benefits to use the same IDE and provide examples for developers using the common IDE - using the same IDE is not so important any more). I switched a few years ago to IntelliJ IDEA and have never missed bloated Eclipse ever since. I  use [PyCharm](https://www.jetbrains.com/pycharm) for Python programming and since PyCharm and IDEA are provided by the same company (JetBrains) they provide very similar look-and-feel. I also use IntelliJ IDEA with [Cursive](https://cursive-ide.com/) plugin for Clojure programming and it also provides very similar look-and-feel. For my previous Javascript exercise I used [Visual Studio Code](https://code.visualstudio.com/) (and with my personal tweakings I managed to make it give pretty same feel, though the look is different, of course).


# Java Static Code Analysis

IntelliJ IDEA supports two good static code analysis tools:

**[Analyze / Inspect code](https://www.jetbrains.com/help/idea/code-inspection.html).** This is an IntelliJ IDEA build in inspection tool. It can detect various language and runtime errors, suggest various corrections etc. It's actually a pretty good tool for clean your code and also learn new language features. You can configure the tool pretty much you like, and create your own project specific profiles. E.g. I supressed warning related weaker access for DTO classes (i.e. fields are intentionally public and Code Inspection suggested that the fields should be private - you can easily supress these warning by adding annotation```java @SuppressWarnings("WeakerAccess")``` before the class defintion in the source file).

**[SonarLint](https://plugins.jetbrains.com/plugin/7973-sonarlint).** There are quite a few linters for Java code but one of the most used is SonarLint. IntelliJ IDEA provides a live SonarLint integration to the Java class you are editing at the moment. You can also run SonarLint for the whole project. 


# Java REPL

Java 9 introduced a shiny Java REPL! This is nothing compared to Lisp REPLs but anyway it's nice to have a Java REPL at last. You can start a Java REPL session with command "jshell". 

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

Another example to generate the hash for example passwords:
```bash
jshell> /env -class-path /home/kari/.m2/repository/commons-codec/commons-codec/1.11/commons-codec-1.11.jar
|  Setting new options and restoring state.
jshell> import org.apache.commons.codec.digest.DigestUtils;
jshell> DigestUtils.md5Hex("Kari").toUpperCase();
$2 ==> "87EE0597C41D7AB8C074D7DC4794716D"
```

This is actually pretty nice and a wellcome addition to Java 9 - now we can test small code snippets like that without creating a bigger testing context (like in Python or Node).

But you can mostly use it for small simple code snippet explorations. If you want to do anything more complex you soon find that you have to set jshell classpath this jar, and this jar, and then this jar and so on:

```bash
jshell> /env -class-path /home/kari/.gradle/caches/modules-2/files-2.1/io.jsonwebtoken/jjwt-api/0.10.5/4bcf9036f62a404d6abecfffb37eae9033248933/jjwt-api-0.10.5.jar:/home/kari/.gradle/caches/modules-2/files-2.1/io.jsonwebtoken/jjwt-impl/0.10.5/f6d6dc168128f40652ca973b212f483f0e0765e3/jjwt-impl-0.10.5.jar
jshell> Key key1 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
jshell> String jws1 = Jwts.builder().setSubject("TODO").signWith(key1).compact();
|  java.lang.IllegalStateException thrown: Unable to discover any JSON Serializer implementations on the classpath.
...
find ~/.gradle/caches -iname "jackson*.jar" | wc -l
25
... right... let's forget that exploration for now.
```

**But IntelliJ IDEA to the rescue!** IntelliJ IDEA implements a nice Java REPL integration. Check it in Tools / JShell Console. It is able to load all your dependencies to the JShell (also application classes), and the REPL editor is pretty nice. See following documents how to use it:

- [Java 9 and IntelliJ IDEA](https://blog.jetbrains.com/idea/2017/09/java-9-and-intellij-idea/) => The chapter "**JShell**".
- [StackOverFlow - How to add application classes to IntelliJ IDEA JShell](https://stackoverflow.com/questions/48143960/how-to-import-a-custom-class-in-intellij-jshell-console). NOTE: Remember to start JShell again once you have added your output classes to IDEA libraries! 

Once you are ready you can write code snippets to IntelliJ IDEA JShell REPL. E.g.:

```java
import simpleserver.util.SSPropertiesImpl;
import simpleserver.webserver.SessionImpl;
SessionImpl session = new SessionImpl(new SSPropertiesImpl());
String jwt = session.createJsonWebToken("jamppa.jamppanen@foo.com");
System.out.println("jwt: " + jwt);

```

... and you get output in JShell console:

```text
import simpleserver.util.SSPropertiesImpl
import simpleserver.webserver.SessionImpl

field SessionImpl session = simpleserver.webserver.SessionImpl@2d0399f4

field String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7IFwiZW1haWxcIjogXCJqYW1wcGEuamFtcHBhbmVuQGZvby5jb21cIiB9IiwiZXhwIjoxNTQwNDgzNDAzfQ.YnWPFow6xZ1pmvaWU03hGllKCj0pNiu-fxuA32H9nh0"
19:03:23.947 [main] DEBUG simpleserver.webserver.SessionImpl - ENTER
19:03:23.947 [main] DEBUG simpleserver.webserver.SessionImpl - EXIT

System.out.println("jwt: " + jwt)
jwt: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7IFwiZW1haWxcIjogXCJqYW1wcGEuamFtcHBhbmVuQGZvby5jb21cIiB9IiwiZXhwIjoxNTQwNDgzNDAzfQ.YnWPFow6xZ1pmvaWU03hGllKCj0pNiu-fxuA32H9nh0
```

Another example to experiment JSON Web Token creation and parsing:

```java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
String jws = Jwts.builder().setSubject("Jamppa").signWith(key).compact();
String name = Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject();
```

JShell console:

```text
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key
field Key key = javax.crypto.spec.SecretKeySpec@5883e04
field String jws = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKYW1wcGEifQ.PjF1ilMY_iq96wTE8ptDRH_zGaIrTU-mYmjy3SZmnos"
field String name = "Jamppa"
```

Pretty nice? And how long did we have to wait for the Java REPL? Just some 20 years? And remember: This is just a code snippet REPL like Python or Node REPL, not a real REPL like a Lisp REPL, e.g. a Clojure REPL (see e.g. [Programming at the REPL: Introduction](https://clojure.org/guides/repl/introduction)). With a real Lisp REPL you can interact with the real program as it is and explore the program in a way no code snippet REPL or debugger can do. It's pretty impossible to explain it, you just have to learn Lisp (e.g. Clojure) and try it out.


# Logging

Spring Boot comes with Logback out of the box. Spring Boot should support groovy configuration, and in main side it worked. For some reason in the test side when testing the domain layer [DomainTest.java](src/test/java/simpleserver/domaindb/DomainTest.java) Spring Boot does not recognize logback-test.groovy file and I had to create equivalent logback-test.xml file which was recognized - real weird. Especially really weird when the groovy configuration gets recognized in the [ServerTest.java](src/test/java/simpleserver/webserver/ServerTest.java). Probably I miss to pass some Spring context to the DomainTest class. If someone figures out the reason for this please tell me. This is a bit of a nuisance since groovy configuration is so much more concise than xml configuration.


# Spring Profiles

Just for demonstration purposes I created two Spring profiles: dev and prod. You can start the script e.g. with dev profile like:

```bash
java -Dspring.profiles.active=dev --illegal-access=deny -jar build/libs/simple-server-0.1.jar
```

Dev profile uses file [application-dev.properties](src/main/resources/application-dev.properties) and you run above command you get logs denoting that we are running dev profile ("DEV" in log before "DEBUG", see [logback-env-dev.groovy](src/main/resources/logback-env-dev.groovy) configuration):

```bash
2018-10-11 23:28:23 1612 [main] DEV DEBUG simpleserver.Core - Running with Spring Boot v2.0.5.RELEASE, Spring v5.0.9.RELEASE 
20
```


# JUnit5

Latest Spring Boot that I used writing this was version 2.0.5 which comes with JUnit 4.12. There were major changes in the new JUnit5 and I wanted to try those, so I configured [build.gradle](build.gradle) to use JUnit5.

You can run the tests as previously:

```bash
./gradlew test
```

If there are no changes in the files, the tests are not run again (unless you give clean task). One way to rerun tests is:

```bash
./gradlew --rerun-tasks test
```

Using JUnit5 it is pretty nice to test e.g. exceptions:

```java
        // Trying to add the same email again.
        Executable codeToTest = () -> {
            User failedUser = users.addUser("jamppa.jamppanen@foo.com", "Jamppa", "Jamppanen", "JampanSalasana");
        };
        SSException ex = assertThrows(SSException.class, codeToTest);
        assertEquals("Email already exists: jamppa.jamppanen@foo.com", ex.getMessage());
```


The tests take considerably more time in Java and Clojure than in Javascript/Node (because OS loads JVM, and JVM loads application and test classes and only then JVM lets testing framework to start the actual testing...):

```bash
$ time ./gradlew --rerun-tasks test
Test result: SUCCESS
Test summary: 15 tests, 15 succeeded, 0 failed, 0 skipped
BUILD SUCCESSFUL in 5s
5 actionable tasks: 5 executed
real    0m5.757s
user    0m1.080s
sys	0m0.131s
```

In the Javascript side:

```bash
time ./run-tests-with-trace.sh
  28 passing (94ms)
real	0m0.775s
user	0m0.683s
sys	0m0.083s
```

Javascript: 0.8 secs  -  Java: 5.8 secs. Doesn't look good for Java.


# Code Coverage

Code coverage is a pretty easy to use in IntelliJ idea. In this new IntelliJ version IDEA recommends to use IntelliJ IDEA's own code coverage runner as instructed in the [IntelliJ IDEA Code Coverage](https://www.jetbrains.com/help/idea/code-coverage.html) documentation.


# Java Verbosity

Let's have an example of Java verbosity and complexity related to other languages I used to implement the Simple Server.

Here we test API /product-groups which returns a simple JSON map. See how complex the testing is to implement in Java:


```java
    @Test
    void getProductGroupsTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        HashMap<String, String> productGroups = new HashMap<>();
        productGroups.put("1", "Books");
        productGroups.put("2", "Movies");
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("ret", "ok");
        expectedResult.put("product-groups", productGroups);
        String expectedResultJson = new JSONObject(expectedResult).toString();
        String encodedJwt = getEncodedJwt();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/product-groups")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Basic " + encodedJwt)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResultJson))
                .andReturn();
        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());
    }
```

The same in Clojure:

```clojure
(deftest get-product-groups-test
  (log/trace "ENTER get-product-groups-test")
  (testing "GET: /product-groups"
    (let [req-body {:email "kari.karttinen@foo.com", :password "Kari"}
          login-ret (-call-request ws/app-routes "/login" :post nil req-body)
          dummy (log/trace (str "Got login-ret: " login-ret))
          login-body (:body login-ret)
          json-web-token (:json-web-token login-body)
          params (-create-basic-authentication json-web-token)
          get-ret (-call-request ws/app-routes "/product-groups" :get params nil)
          dummy (log/trace (str "Got body: " get-ret))
          status (:status get-ret)
          body (:body get-ret)
          right-body {:ret :ok, :product-groups {"1" "Books", "2" "Movies"}}
          ]
      (is (not (nil? json-web-token)))
      (is (= status 200))
      (is (= body right-body)))))
```

... and Javascript:

```javascript
  describe('GET /product-groups', function () {
    let jwt;
    it('Get Json web token', async () => {
      // Async example in which we wait for the Promise to be
      // ready (that i.e. the post to get jwt has been processed).
      const jsonWebToken = await getJsonWebToken();
      logger.trace('Got jsonWebToken: ', jsonWebToken);
      assert.equal(jsonWebToken.length > 20, true);
      jwt = jsonWebToken;
    });
    it('Successful GET: /product-groups', function (done) {
      logger.trace('Using jwt: ', jwt);
      const authStr = createAuthStr(jwt);
      supertest(webServer)
        .get('/product-groups')
        .set('Accept', 'application/json')
        .set('Authorization', authStr)
        .expect('Content-Type', /json/)
        .expect(200, {
          ret: 'ok',
          'product-groups': { 1: 'Books', 2: 'Movies' }
        }, done);
    });
  });
```

As you can see from the example in Clojure and Javascript we can treat data as data, in Java not so much. In Java if you want to create Java-ish code you have to implement class this and class that for your data. In Clojure you can just use Clojure native data structures in code, and in Javascript JSON data structures, which makes much more sense and makes the code more readable as well.

Let's also compare the lines of code between these three languages:

**Java:**

```text
$ find src/main/ -name "*.java" | xargs wc -l
  108 src/main/java/simpleserver/userdb/UsersImpl.java
   50 src/main/java/simpleserver/userdb/Users.java
   36 src/main/java/simpleserver/userdb/dto/User.java
  156 src/main/java/simpleserver/domaindb/DomainImpl.java
   34 src/main/java/simpleserver/domaindb/dto/ProductGroups.java
   23 src/main/java/simpleserver/domaindb/dto/Info.java
   47 src/main/java/simpleserver/domaindb/dto/Product.java
   45 src/main/java/simpleserver/domaindb/Domain.java
   21 src/main/java/simpleserver/util/SSErrorCode.java
   11 src/main/java/simpleserver/util/SSConsts.java
   19 src/main/java/simpleserver/util/SSProperties.java
   29 src/main/java/simpleserver/util/SSPropertiesImpl.java
   70 src/main/java/simpleserver/util/SSException.java
   29 src/main/java/simpleserver/webserver/Session.java
  289 src/main/java/simpleserver/webserver/Server.java
   23 src/main/java/simpleserver/webserver/response/Response.java
   43 src/main/java/simpleserver/webserver/response/LoginOkResponseImpl.java
   43 src/main/java/simpleserver/webserver/response/ProductGroupsOkResponseImpl.java
   41 src/main/java/simpleserver/webserver/response/ProductFailedResponseImpl.java
   46 src/main/java/simpleserver/webserver/response/LoginFailedResponseImpl.java
   42 src/main/java/simpleserver/webserver/response/ProductsFailedResponseImpl.java
   42 src/main/java/simpleserver/webserver/response/SigninOkResponseImpl.java
   46 src/main/java/simpleserver/webserver/response/SigninFailedResponseImpl.java
   40 src/main/java/simpleserver/webserver/response/ProductGroupsFailedResponseImpl.java
   57 src/main/java/simpleserver/webserver/response/ProductOkResponseImpl.java
   58 src/main/java/simpleserver/webserver/response/ProductsOkResponseImpl.java
   24 src/main/java/simpleserver/webserver/dto/LoginData.java
   32 src/main/java/simpleserver/webserver/dto/SigninData.java
   81 src/main/java/simpleserver/webserver/SessionImpl.java
   27 src/main/java/simpleserver/Core.java
 1612 total
```

**Javascript:**

```text
$ find src -name "*.js" | xargs wc -l
  126 src/userdb/users.js
  108 src/domaindb/domain.js
   10 src/core.js
   33 src/util/prop.js
   29 src/util/logger.js
   79 src/webserver/session.js
  289 src/webserver/server.js
  674 total

```

**Clojure:**

```text
$ find src -name "*.clj" | xargs wc -l
   65 src/simpleserver/userdb/users.clj
   36 src/simpleserver/core.clj
   80 src/simpleserver/domaindb/domain.clj
   99 src/simpleserver/util/prop.clj
   89 src/simpleserver/webserver/session.clj
  243 src/simpleserver/webserver/server.clj
  612 total

```

| Language      | Files  | LoC    |
| ------------- |-------:| ------:|
| Java          |     30 |   1612 |
| Javascript    |      7 |    674 |
| Clojure       |      6 |    612 |

So, it's pretty clear that dynamic languages are easier to work with. 


# Conclusions


## Spring and Spring Boot

Spring Boot makes creating server / microservice applications using Java much easier. Spring provides a great framework which glues your components together and provides an overall framework to make things easier. It's hard to find a reason not to use Spring (i.e. to create a pure EE Java app). I guess hardly no-one nowadays uses pure EE without Spring.

But remember: if you want to deviate from the default Spring Boot configuration (as I did when I used JUnit5 instead of JUnit4) you are pretty soon googling why some autowiring or servlet context or something is not working as it should.


## Java as a Language

**Verbosity.** Java is really, really verbose if you compare it to dynamic languages like Python, Javascript or Clojure.

**Object oriented paradigm.** Object oriented paradigm was something cool in mid 1990's but nowadays seems more or less an unholy mess of classes having data, method and other classes having other data, methods and other classes... For data oriented applications I would rather use functional paradigm which makes a better separation between data and functions that operate on data. 

**Getters and Setters**. For simple data classes in which the fields are the interface it is stupid to make the fields private and provide a huge list of getters and setters. In those cases I think it is better just to make the fields public. If there is a reason to hide some internal structure, then you should make that private, of course. The getters/setters is definitely something that was not properly designed in the Java language. 

**Checked exceptions**. Another (possible) design flaw was the checked exceptions which some consider as a failed experiment in the language. If you use checked exceptions you should understand to use them only in those situations in which you want to enforce the calling code to deal with a possible exception and the calling code really can do something with it.

**Safety.** If you need a statically typed safe language and runtime environment (JVM - some 20 years of solid testing in enterprise world) with a great ecosystem for a big enterprise project with tens of developers - Java is the solution. 

**IDE tooling.** IDE tooling is of course great since we are using statically typed language. [IntelliJ IDEA](https://www.jetbrains.com/idea/) (my favorite Java IDE) provides exact suggestions for methods when it recognizes which class we are dealing with.

**Learning curve.** A bit difficult to say something about this since I've been programming Java some 20 years now (first Java project was actually in year 1998). But after this exercise I have a feeling that for a newbie programmer Java basic stuff cannot be learned in a couple of days and then start being productive and learn the new stuff on the way as you can do e.g. with Python and Javascript. Also the frameworks take some time to learn (even a hard-core Java programmer like me forgot some peculiarities regarding Spring when I have not done serious Java/Spring programming about in 1,5 years). I think it is a lot easier for a Java guy to start using Javascript or Python than a Javascript guy start to use Java. Now after implementing the same web server using Javascript/Node and Java I can better understand why those Javascript guys despise Java so much - Java is far from Javascript/Node in developer productivity. But you don't have to leave JVM for Java's sins - you can start using Clojure and get the best of both worlds - a battle tested runtime (JVM) with a great functional and immutable language (Clojure).

**Rigidness.** I have been programming Java some 20 years - I'm pretty seasoned Java programmer. But even I myself was quite amazed regarding the productivity between Javascript/Node vs Java. Just for curiosity I looked my Github commits regarding the Javascript/Node implementation of this server exercise, and Java commits: I did both implementations in some 3 weeks (only evenings, not every evening). So, how is it possible that you implement the same server in about the same time with a language you just learned on the fly while implementing the server compared to a language you have used 20 years? Wtf? Have I really used such a non-productive language all these years? I think one of the main reasons for this low productivity is that Java is so verbose and rigid. You have to implement class this and class that to make even minor functionality (if you want to make the implementation Java-ish). In the Javascript, Python and Clojure side you have a lot more freedom because the languages are dynamically typed and you can treat data as data and not as an unholy mix of classes with data and references to other classes.

**Summa summarum.** Ok, ok, I have been hitting Java with a stick in this chapter but after all Java is not that bad if you use it in the right project. There are a lot of pros in Java: type safety, great ecosystem, JVM is battle tested runtime, great tooling, a huge developer pool etc. All these reasons make Java a great enterprise language for big critical enterprise projects when many developers need to work on the same code base. But the price is a verbose and rigid language with a rather slow development cycle. E.g. programming in Java really requires TDD since you can easily develop your system in small increments when you use a good unit/integration test as a development bench (and you get a test suite as a by-product) - not a bad thing per se. Something you don't need e.g. in Clojure because you have a real Lisp REPL and you can grow your system in a more organic way with the REPL (but you still can create a good test suite, and of course you should).


# Epilogue

In the beginning of this story I asked myself: "Have I been using the wrong language all these years?" Well, yes and no. No in that sense that 20 years ago Java was really something new. You had an enterprise language with garbage collection, which was really good since most of the C / C++ programmers really never learned memory management properly. In those days there were not that much of a choice since customers wanted the projects to be implemented using Java, and Java we used. Yes, in that sense that nowadays we have so much more choices. You really don't have to use Java for small microservice type applications. And I wouldn't use Java in those kind of small projects unless the customer required so. It is much better to implement small microservices using dynamic productive languages like Javascript/Node, Python or Clojure. And for data oriented projects I would use Clojure since Clojure really shines with data processing. I would use Java only in some really, really big projects with a lot of developers working on the same code base and the static language protects developers from trivial errors assuming something about the parameters and return types. 

All right. My homecoming to Java land was rather short, and next I will move to Python land, and then to Go land. Let's see what kind of reflections those languages will give when comparing the experiences to the languages I already have used to implement the Simple Server. 

