FROM gradle:7.6.1-jdk17-alpine
COPY . project/
WORKDIR project/
ENTRYPOINT ["gradle", "gatlingRun-ExampleSimulation"]