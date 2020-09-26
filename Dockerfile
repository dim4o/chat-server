FROM openjdk:11
WORKDIR /
ADD chat-server-new-0.0.1.jar chat-server-new-0.0.1.jar
CMD java -jar chat-server-new-0.0.1.jar
