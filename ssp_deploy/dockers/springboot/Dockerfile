FROM openjdk:8-jdk-alpine
RUN apk --no-cache add curl
COPY ./build/bin/entryPoint.sh /usr/bin/entryPoint.sh
RUN chmod +x /usr/bin/entryPoint.sh
WORKDIR /usr/bin
EXPOSE 1025
ENTRYPOINT ["sh","/usr/bin/entryPoint.sh"]
