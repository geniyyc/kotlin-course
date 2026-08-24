FROM eclipse-temurin:21-jre-alpine

ENV JAVA_OPTS="-Xms256m -Xmx512m"

COPY build/libs/math-face-app-kafka-*-all.jar /app/math-face-app-kafka.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app/math-face-app-kafka.jar
