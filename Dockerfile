FROM openjdk:8u131-jre

WORKDIR /app

COPY target/geneatool-0.0.1-SNAPSHOT.jar /app/records-ms.jar

ENTRYPOINT [ "java", "-jar", "records-ms.jar" ]