FROM container-registry.cpqd.com.br/dockerhub/maven:3.8.3-openjdk-17 AS MAVEN_BUILD

WORKDIR /usr/src/app

COPY src ./src
COPY pom.xml ./

RUN mvn clean package -DskipTests 

FROM container-registry.cpqd.com.br/dockerhub/openjdk:17

COPY --from=MAVEN_BUILD /usr/src/app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8081
