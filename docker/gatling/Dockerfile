FROM gradle:7.6.1-jdk17-alpine
COPY . project/
WORKDIR project/
ENTRYPOINT ["gradle", "gatlingRun-ComputerDatabaseSimulation"]
#ENTRYPOINT ["java", "-jar", "libs/TestStand-0.0.1-SNAPSHOT.jar"]
#EXPOSE 8080