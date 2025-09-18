FROM openjdk:24-jdk-slim
RUN apt-get update && \
        apt-get install -y postgresql-client && \
        rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
