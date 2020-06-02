# build gradle project in first step
FROM openjdk:8 AS BUILD_IMAGE
LABEL maintainer = "Tobias Mathony <mathony.tobias@gmail.com>"
COPY . .
RUN ./gradlew build -x test --no-daemon

FROM ubuntu:18.04
RUN apt-get -qq update && apt-get install -qqy software-properties-common openjdk-8-jdk wget

# setup SWI prolog
RUN apt-get update && apt-get install -qqy swi-prolog swi-prolog-java
ENV SWI_HOME_DIR /usr/bin/swipl

COPY --from=BUILD_IMAGE build/libs/0.0.1-SNAPSHOT.jar build/libs/ToPS.jar

EXPOSE 9090

CMD ["java", "-jar", "build/libs/ToPS.jar"]


