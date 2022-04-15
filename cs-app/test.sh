#!/bin/bash

#mvn clean compile assembly:single

JAVA_HOME=/opt/java/jdk-17.0.2 mvn clean test ||
cat target/surefire-reports/com.cs.app.AppTest.txt
