FROM tomcat:9.0-jdk17-openjdk-slim
EXPOSE 8081
COPY target/mpec-0.0.3.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]


#FROM openjdk:21
#LABEL maintainer="javaguides.net"
##EXPOSE 8081
#ADD target/mpec-0.0.3.war mpec.jar
#ENTRYPOINT ["java", "-jar", "mpec.jar"]


#FROM tomcat:10.1-jdk21
#EXPOSE 8081
#COPY target/mpec-0.0.3.war /usr/local/tomcat/webapps/ROOT.war
#CMD ["catalina.sh", "run"]

