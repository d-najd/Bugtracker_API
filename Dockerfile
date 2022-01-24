FROM openjdk:11
ADD target/bugtracker_server.jar bugtracker_server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/bugtracker_server.jar"]