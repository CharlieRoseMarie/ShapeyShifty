FROM openjdk:8-alpine

COPY target/uberjar/shapey-shifty.jar /shapey-shifty/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/shapey-shifty/app.jar"]
