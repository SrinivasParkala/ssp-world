FROM openjdk:8-jdk-alpine
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#COPY application.properties /app
RUN echo MAIN CALSS IS: $SPRING_BOOT_MAIN_CLASS
ENTRYPOINT ["java","-cp","app:/app/lib/*","com.ssp.maintainance.DemoApplication"]