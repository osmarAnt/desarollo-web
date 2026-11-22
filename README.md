# Desarollo-Web
Este repositorio es el trabajo final de la materia de desarrollo web de mi quito semestre 2026

---
## Requisitos Previos 

Antes de ejecutar el proyecto,
se necesita contar con:
	- Java JDK 17 o superior 
     - Git 
    -  MongoDB Community Server corriendo en el puerto por defecto 27017 (mongod --version)
    -  Maven (opcional, el proyecto incluye el wrapper ./mvnw)

## Instalacion y ejecucion del proyecto

git clone https://github.com/osmarAnt/desarollo-web.git
cd desarrollo web

>se pueden cambiar los puertos de mongo y del sistema desde el archivo [configuracion](backend/src/main/resources/application.properties)

sudo systemctl start mongod 

cd backend

(opcional) ./mvnw test 

./mvnw spring-boot:run

## ir al sistema
http://localhost:8080

---
