FROM openjdk:17-jdk-alpine
MAINTAINER jsanchez2726@alumno.uned.es
ARG JAR_FILE=*.jar
COPY ./uned-educa-analisis-0.0.2-SNAPSHOT.jar uned-educa-analisis.jar
ENTRYPOINT ["java", "-Djava.awt.headless=true" ,"-jar","/uned-educa-analisis.jar"]
