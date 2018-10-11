// See: https://logback.qos.ch/translator/asGroovy.html

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.WARN

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] DEV %-5level %logger{35} - %msg %n"
    }
}

logFilePath = "logs"
logFileName = "simple-server"

appender("FILE", RollingFileAppender) {
    file = "${logFilePath}/${logFileName}.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${logFilePath}/${logFileName}-%d{yyyy-MM-dd}.log"
        maxHistory = 5
        totalSizeCap = "1KB"
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss} %-4relative [%thread] DEV %-5level %logger{35} - %msg %n"
    }
}

root(WARN, ["STDOUT"])
root(WARN, ["FILE"])
logger("simpleserver", DEBUG, ["STDOUT", "FILE"], false)
