FROM openjdk:8
LABEL maintainer="com.gamingreservation"
ADD target/gammingdockerimage.jar gammingdockerimage.jar
ENTRYPOINT ["java", "-jar", "gammingdockerimage.jar"]