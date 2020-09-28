FROM adoptopenjdk/openjdk8:debian-jre

# Set timezone
ENV TZ=Europe/Helsinki
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /opt
COPY target/*.jar /opt/
COPY target/artifact /opt/
ADD deployment /deployment

EXPOSE 8080

CMD java -jar /opt/$(cat /opt/artifact)