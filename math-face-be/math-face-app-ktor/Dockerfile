FROM eclipse-temurin:21-jre-alpine

ENV JAVA_OPTS="-Xms256m -Xmx512m"

COPY build/libs/math-face-app-ktor-*-all.jar /app/math-face-app-ktor.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar /app/math-face-app-ktor.jar
