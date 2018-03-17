FROM openjdk:8-jre-alpine
MAINTAINER Robin Tegg

ENTRYPOINT ["/usr/bin/java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:MaxRAM=256m", "-jar",  "/usr/share/news-crud-application.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/news-crud-application.jar

EXPOSE 8080