FROM maven:3.9-eclipse-temurin-24 AS development
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
RUN mvn dependency:get -Dartifact=org.springframework.boot:spring-boot-devtools:3.2.0
RUN apt-get update && \
        apt-get install -y postgresql-client && \
        rm -rf /var/lib/apt/lists/*
EXPOSE 8080
EXPOSE 5005
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
