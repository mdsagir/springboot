FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/docker-boot.jar docker-boot.jar
ENTRYPOINT ["sh","-c","java -jar /docker-boot.jar"]
