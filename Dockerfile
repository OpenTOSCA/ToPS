FROM ubuntu:18.04
LABEL maintainer = "Tobias Mathony <mathony.tobias@gmail.com>"

COPY . .
RUN apt-get -qq update && apt-get install -qqy software-properties-common openjdk-8-jdk wget
RUN ./gradlew build -x test --no-daemon

# setup SWI prolog
RUN apt-get update && apt-get install -qqy swi-prolog swi-prolog-java
ENV SWI_HOME_DIR /usr/bin/swipl

RUN cd build/libs && ls -a

EXPOSE 9090

CMD ["java", "-jar", "build/libs/0.0.1-SNAPSHOT.jar"]


