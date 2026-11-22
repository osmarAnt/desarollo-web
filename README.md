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
./mvnw spring-boot:run

## ir al sistema
http://localhost:8080

---

## Arquitectura y Tecnologías

El sistema sigue una arquitectura cliente-servidor desacoplada servida desde una única instancia de Spring Boot:

* **Frontend:** HTML5, CSS3 y JavaScript Vanilla con consumo asíncrono mediante Fetch API.
* **Backend:** Java + Spring Boot 4.1.2 (API REST, Spring Security con sesiones HTTP JSESSIONID y cifrado BCrypt).
* **Base de Datos:** MongoDB (localhost:27017) integrado mediante Spring Data MongoDB (MongoRepository).
* **Pruebas:** JUnit 5 y MockMvc para pruebas automatizadas de integración.

## Estructura del proyecto

```text
desarrollo-web/
├── backend/                  # Proyecto Maven con Spring Boot
│   ├── src/main/java/        # Controladores REST, modelos, seguridad y repositorios MongoDB
│   ├── src/main/resources/   # Configuración (application.properties)
│   └── src/test/java/        # Pruebas automatizadas (SistemaTareasTests.java)
├── frontend/                 # Interfaz web estática (index.html, css/, js/)
├── documentación/            # Documentación de los reportes (reporte-*)
└── README.md                 # Guía de instalación y ejecución
```

## Endpoints Principales de la API REST

### Autenticación (`/api/auth`)

|Método|Ruta|Descripción|
|---|---|---|
|`POST`|`/api/auth/register`|Registra un nuevo usuario encriptando su contraseña con `BCrypt`.<br><br>AuthController.java|
|`POST`|`/api/auth/login`|Inicia sesión y genera la cookie de sesión `JSESSIONID`.<br><br>AuthController.java|
|`GET`|`/api/auth/me`|Devuelve los datos del usuario autenticado actualmente.<br><br>AuthController.java|
|`POST`|`/api/auth/logout`|Cierra la sesión activa.|

### Gestión de Tareas (`/api/tareas`) — _Requiere sesión activa_

|Método|Ruta|Descripción|
|---|---|---|
|`GET`|`/api/tareas`|Lista todas las tareas y solicitudes recibidas del usuario autenticado.|
|`POST`|`/api/tareas`|Crea una nueva tarea personal.|
|`POST`|`/api/tareas/enviar`|Envía una solicitud de tarea a otro usuario mediante su correo.|
|`PUT`|`/api/tareas/{id}`|Actualiza los datos o el estado (`pendiente`/`completada`) de una tarea propia.|
|`PUT`|`/api/tareas/{id}/responder`|Permite al destinatario `aceptar` o `rechazar` una tarea enviada.|
|`DELETE`|`/api/tareas/{id}`|Elimina una tarea propiedad del usuario.|

## ## Equipo de Desarrollo

- **Osmar** — Desarrollo Full Stack (Backend Spring Boot, Seguridad, Persistencia MongoDB, Frontend y Pruebas)