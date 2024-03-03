FROM openjdk:17
WORKDIR /opt
ENV LANG=en_US.utf8
COPY  target/awsdemo-0.0.1-SNAPSHOT.jar  ./awsdemo.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","./awsdemo.jar"]