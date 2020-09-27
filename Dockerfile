FROM openjdk:8-jdk-alpine
WORKDIR /
ADD chat-server-0.0.1.jar chat-server-0.0.1.jar
CMD java -jar chat-server-0.0.1.jar
