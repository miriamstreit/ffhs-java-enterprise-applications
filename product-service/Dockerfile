FROM maven:3.8.7-eclipse-temurin-17 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/
COPY preboot-commands.asadmin /build/
COPY lib/postgresql-42.5.4.jar /build/

WORKDIR /build/
RUN mvn clean package -DskipTests

FROM payara/micro:6.2023.6-jdk17

COPY --from=MAVEN_BUILD /build/postgresql-42.5.4.jar /opt/payara/appserver/glassfish/domains/domain1/lib
COPY --from=MAVEN_BUILD /build/postgresql-42.5.4.jar /opt/payara/appserver/glassfish/lib
COPY --from=MAVEN_BUILD /build/preboot-commands.asadmin $POSTBOOT_COMMANDS
COPY --from=MAVEN_BUILD /build/target/product-service-1.0.war /opt/payara/deployments/ROOT.war

EXPOSE 8080 8080
EXPOSE 8081 8081
EXPOSE 4848 4848