FROM amazoncorretto:21-alpine
WORKDIR bff-service
COPY rest/target/rest-0.0.1-SNAPSHOT.jar /bff-service/bff.jar
ENTRYPOINT ["java","-jar","/bff-service/bff.jar"]