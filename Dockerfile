# Usar a imagem oficial do OpenJDK 17 como imagem base
FROM openjdk:17-slim

# Adicionar um argumento para o arquivo JAR
ARG JAR_FILE=target/*.jar

# Copiar o arquivo JAR para o contêiner
COPY ${JAR_FILE} app.jar

# Expor a porta que a aplicação usará
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
