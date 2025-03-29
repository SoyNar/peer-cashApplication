
FROM eclipse-temurin:17.0.13_11-jdk AS build
LABEL authors="nar"

#directorio de trabajo
WORKDIR /app
#copiar archivos de maven
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
#DEscargar dependencias
RUN ./mvnw dependency:go-offline
#copiar codigo fuente al contenedor
COPY  ./src ./src
#Construir aplicacion sin ejecutar test
RUN ./mvnw clean package -DskipTests

#imagen solo con JRE
FROM eclipse-temurin:17-jre
WORKDIR /app

#copiar jar conmpilado desde build
COPY --from=build /app/target/*.jar app.jar
#Exponer puerto
EXPOSE 8080
#correr la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]