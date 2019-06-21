FROM openjdk:8-jre-alpine
EXPOSE 8080
ADD /target/cashier.jar cashier.jar
ENTRYPOINT ["java","-jar","cashier.jar"]