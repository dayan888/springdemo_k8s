FROM openjdk:8-jdk-alpine
ARG buildver=0.5.0

#RUN ./gradlew build -x test
COPY build/libs/springdemo-${buildver}.jar /app/springdemo.jar

ENTRYPOINT ["java", "-jar", "/app/springdemo.jar"]
