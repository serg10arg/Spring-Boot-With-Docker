# 1. Definir la imagen base con el entorno de ejecución de Java.
FROM openjdk:17-slim

# 2. Establecer el directorio de trabajo dentro del contenedor para mantener las cosas organizadas.
WORKDIR /app

# 3. Exponer el puerto en el que se ejecuta la apliación
EXPOSE 8081

# 4. Copiar el artefacto JAR a la imagen.
COPY target/app.jar app.jar

# 5. Especificar el comando para ejecutar la apliacion al inicar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]