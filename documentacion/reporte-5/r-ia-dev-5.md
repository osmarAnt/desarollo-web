# Reporte de IA Desarrollo en el Proyecto

**Fecha:**  22/11/26
**Número de reporte:** 5  

---

# 1. Breve descripción de uso

Se utilizó para generar e integrar la suite de pruebas automatizadas con JUnit 5 y MockMvc, adaptar el código de pruebas a Spring Boot 4, alinear el campo `passwordHash` con los controladores y actualizar el respaldo de datos en las pruebas tras migrar de archivo plano JSON a MongoDB.

---

# 2. Evidencias de uso

### Uso 01 — Creación de suite de pruebas automatizadas (JUnit 5 + MockMvc)

| Evidencia                   | Formato                                                                                                              |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| Herramienta                 | Gemini                                                                                                               |
| Modelo/temperatura          | Gemini / Estándar                                                                                                    |
| Prompt original             | [promp1](promp1.md)                                                                                                  |
| Respuesta de IA             | [respuesta1](respuesta1.md)                                                                                          |
| Código anterior             | Commit (ac012a1)                                                                                                     |
| Código posterior            | Commit (b63e331)                                                                                                     |
| Diferencias entre versiones | 16      10      backend/pom.xml<br>234     0       backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java |
| Prueba de funcionamiento    | [resultado](resultado.txt)                                                                                           |
Código de uso | Generación / Pruebas
Reflexión del estudiante | Con base en el documento del proyecto final la IA generó la clase `SistemaTareasTests.java` con 5 pruebas de integración para probar casos de éxito y error en registro, login, CRUD y envío de tareas entre usuarios.

---

### Uso 02 — Refactorización de MockMvc para Spring Boot 4

| Evidencia                   | Formato                                                                                                              |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| Herramienta                 | Gemini                                                                                                               |
| Modelo/temperatura          | Gemini / Estándar                                                                                                    |
| Prompt original             | [promp5](promp5.png)                                                                                                 |
| Respuesta de IA             | [respuesta5](respuesta5.md)                                                                                          |
| Código anterior             | Commit (ac012a1)                                                                                                     |
| Código posterior            | Commit (b63e331)                                                                                                     |
| Diferencias entre versiones | 16      10      backend/pom.xml<br>234     0       backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java |
| Prueba de funcionamiento    | [resultado](resultado.txt)                                                                                           |
Código de uso | Modificación / Depuración / Pruebas
Reflexión del estudiante | Como en Spring Boot 4 cambiaron los paquetes de Jackson y `@AutoConfigureMockMvc` marcaba error, la IA reescribió las pruebas inicializando `MockMvc` manualmente con `MockMvcBuilders` y agregando el filtro de Spring Security.

---

### Uso 03 — Alineación del atributo `passwordHash` en pruebas y frontend

| Evidencia                   | Formato                                                                                                              |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| Herramienta                 | Gemini                                                                                                               |
| Modelo/temperatura          | Gemini / Estándar                                                                                                    |
| Prompt original             | [promp7](promp7.md)                                                                                                  |
| Respuesta de IA             | [respuesta7](respuesta7.md)                                                                                          |
| Código anterior             | Commit (ac012a1)                                                                                                     |
| Código posterior            | Commit (b63e331)                                                                                                     |
| Diferencias entre versiones | 16      10      backend/pom.xml<br>234     0       backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java |
| Prueba de funcionamiento    | [resultado](resultado.txt)                                                                                           |
Código de uso | Modificación / Depuración / Pruebas
Reflexión del estudiante | Al pasarle `AuthController.java` y `Usuario.java` la IA detectó que el test mandaba `password_hash` en lugar de `passwordHash`, lo que dejaba la contraseña en null y provocaba el 401 al iniciar sesión.

---

### Uso 04 — Adaptación de pruebas tras migración de JSON a MongoDB

| Evidencia                   | Formato                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Herramienta                 | Gemini                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Modelo/temperatura          | --- (si aplica)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| Prompt original             | [promp8](promp8.md)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Respuesta de IA             | [respuesta 8](respuesta 8.md)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| Código anterior             | Commit (b63e331)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Código posterior            | Commit (fed2a21)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Diferencias entre versiones | <br>1       0       backend/database/db.json<br>6       0       backend/pom.xml<br>5       6       backend/src/main/java/mx/uv/desarrolloweb/AuthController.java<br>3       3       backend/src/main/java/mx/uv/desarrolloweb/CustomUserDetailsService.java<br>0       24      backend/src/main/java/mx/uv/desarrolloweb/Database.java<br>0       157     backend/src/main/java/mx/uv/desarrolloweb/Json.java<br>6       1       backend/src/main/java/mx/uv/desarrolloweb/Tarea.java<br>54      27      backend/src/main/java/mx/uv/desarrolloweb/TareaController.java<br>10      0       backend/src/main/java/mx/uv/desarrolloweb/TareaRepository.java<br>4       1       backend/src/main/java/mx/uv/desarrolloweb/Usuario.java<br>8       0       backend/src/main/java/mx/uv/desarrolloweb/UsuarioRepository.java<br>1       0       backend/src/main/resources/application.properties<br>25      20      backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java |
| Prueba de funcionamiento    | [resultado](resultado2.txt)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
Código de uso | Modificación / Pruebas
Reflexión del estudiante | Al cambiar `db.json` por MongoDB las pruebas fallaban con 409 porque seguían limpiando el archivo plano en vez de la base de datos; se modificaron `@BeforeEach` y `@AfterEach` usando `UsuarioRepository` y `TareaRepository` para respaldar y limpiar las colecciones.


---

## Observaciones adicionales

ninguna