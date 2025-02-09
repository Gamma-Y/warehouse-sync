FROM eclipse-temurin:23 AS app-build
ENV RELEASE=23

LABEL authors="Gamma"

WORKDIR /opt/build
COPY ./target/*.jar ./app.jar

RUN java -Djarmode=layertools -jar app.jar extract
RUN $JAVA_HOME/bin/jlink \
         --add-modules `jdeps --ignore-missing-deps -q -recursive --multi-release ${RELEASE} --print-module-deps -cp 'dependencies/BOOT-INF/lib/*':'snapshot-dependencies/BOOT-INF/lib/*' app.jar` \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output jdk

FROM debian:buster-slim

ARG BUILD_PATH=/opt/build
ENV JAVA_HOME=/opt/jdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN groupadd --gid 1000 app \
  && useradd --uid 1000 --gid app --shell /bin/bash --create-home app

USER app:app
WORKDIR /opt/workspace

COPY --from=app-build $BUILD_PATH/jdk $JAVA_HOME
COPY --from=app-build $BUILD_PATH/spring-boot-loader/ ./
COPY --from=app-build $BUILD_PATH/dependencies/ ./
COPY --from=app-build $BUILD_PATH/snapshot-dependencies/ ./
COPY --from=app-build $BUILD_PATH/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]