# ---- build stage ------------------------------------------------------------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copie des descripteurs d'abord pour profiter du cache
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw -q -B -DskipTests dependency:go-offline

# Copie du code et build
COPY src ./src
RUN ./mvnw -q -B -DskipTests clean package

# ---- runtime stage (JRE léger) ---------------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Ajout d'un user non-root (bonnes pratiques)
RUN useradd -ms /bin/bash spring && chown -R spring:spring /app
USER spring

# Copie du jar (spring-boot repackage)
COPY --from=build /app/target/*.jar app.jar

# Port exposé par l'app
EXPOSE 8080

# Démarrage; profil "docker" activé
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
