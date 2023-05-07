FROM eclipse-temurin:17-jre-alpine
COPY libs libs/
ENTRYPOINT ["java", "-Xmx600m", "-Xms600m", "-XX:CompressedClassSpaceSize=40M", "-XX:MaxMetaspaceSize=220M", "-XX:ReservedCodeCacheSize=120M", "-jar", "libs/reactive-stand-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080