FROM maven:3-amazoncorretto-17 AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

FROM amazoncorretto:17.0.5-alpine3.15
COPY --from=build /app/target/library-app*.jar /app/library-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/library-app.jar"]