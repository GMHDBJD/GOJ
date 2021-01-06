FROM node:10.23.0 as build_front_end
WORKDIR /usr/src/front_end
COPY front_end/package*.json ./
RUN npm install
COPY front_end/ .
RUN npm run build

FROM adoptopenjdk/maven-openjdk11:latest as build_back_end
WORKDIR /usr/src/back_end
COPY back_end/ .
COPY --from=build_front_end /usr/src/front_end/dist ./restservice/src/main/resources/public
RUN cd ./restservice && mvn clean package -DskipTests

FROM adoptopenjdk/maven-openjdk11:latest
COPY --from=build_back_end /usr/src/back_end/restservice/target/goj.jar .
CMD ["java", "-jar", "goj.jar"]