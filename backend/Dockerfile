# Imagen base de Java 21
FROM openjdk:21-jdk-slim

# Crea un directorio dentro del contenedor para tu aplicación
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/Spotify-BT-0.0.1-SNAPSHOT.jar /app/backend.jar

# Expone el puerto que usará tu aplicación
EXPOSE 9099

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "backend.jar"]
