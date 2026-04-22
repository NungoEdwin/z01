FROM openjdk:11-jre-slim

LABEL maintainer="nexus-demo"
LABEL version="1.0.0"

WORKDIR /app

COPY target/nexus-web-app-*.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]
