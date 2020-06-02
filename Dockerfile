FROM openjdk:8 AS BUILD_IMAGE
LABEL maintainer = "Tobias Mathony <mathony.tobias@gmail.com>"
COPY . /temp
WORKDIR /temp
RUN ./gradlew build -x test --no-daemon
COPY /build/libs/ToPS-0.0.1-SNAPSHOT.jar /build/libs/ToPS-0.0.1-SNAPSHOT.jar
EXPOSE 8090

CMD ["java", "-jar", "/build/libs/ToPS-0.0.1-SNAPSHOT.jar"]


