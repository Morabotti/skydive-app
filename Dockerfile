FROM adoptopenjdk/maven-openjdk11 AS builder

RUN curl -sL https://deb.nodesource.com/setup_15.x | bash - \
  && apt-get install -y nodejs git build-essential \
  && mkdir -p /opt/build/front /opt/build/lib

ENV TZ=Europe/Helsinki
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY pom.xml /opt/build/
RUN cd /opt/build \
  && mvn dependency:copy-dependencies dependency:resolve dependency:resolve-plugins dependency:go-offline -B

COPY front/package.json /opt/build/front/
COPY front/package-lock.json /opt/build/front/
RUN cd /opt/build/front \
  && npm ci

COPY ./ /opt/build/

ARG VERSION_STRING=latest

RUN cd /opt/build \
  && export ARTIFACT=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.artifactId}' --non-recursive exec:exec) \
  && mvn versions:set -DnewVersion=${VERSION_STRING} \
  && mvn clean package -Dsnoozy.linter.execPhase=none -P shade \
  && mv target/${ARTIFACT}-${VERSION_STRING}.jar /opt/${ARTIFACT}-${VERSION_STRING}.jar \
  && cd /opt \
  && rm -rf /opt/build \
  && chmod a+x /opt/${ARTIFACT}-${VERSION_STRING}.jar \
  && echo "${ARTIFACT}-${VERSION_STRING}.jar" > /opt/artifact

FROM adoptopenjdk/openjdk11:debian-jre

RUN mkdir -p /opt
COPY --from=0 /opt/*.jar /opt/
COPY --from=0 /opt/artifact /opt/

EXPOSE 8080

CMD java -jar /opt/$(cat /opt/artifact)
