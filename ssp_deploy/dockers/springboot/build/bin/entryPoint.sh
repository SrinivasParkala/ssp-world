#!/bin/sh
APP_HOME=$SPRING_BOOT_APP_HOME
MAIN_JAR=$SPRING_BOOT_MAIN_JAR
echo $APP_HOME
echo $MAIN_JAR
echo java -jar -Dspring.config.location=$APP_HOME/conf/application.properties $APP_HOME/lib/$MAIN_JAR
java -jar -Dspring.config.location=$APP_HOME/conf/application.properties $APP_HOME/lib/$MAIN_JAR
