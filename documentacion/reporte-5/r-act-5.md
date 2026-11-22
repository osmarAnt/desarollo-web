# Reporte de Avance del Proyecto

**Equipo:** 11  
**Fecha:** 22/11/26
**Número de reporte:** 5
**Repositorio GitHub:** [desarrollo-web](https://github.com/osmarAnt/desarollo-web)

---

# 1. Actividades realizadas

Desde el cierre de la Entrega 4, se completó la estabilización, migración de base de datos, cobertura de pruebas automatizadas y documentación definitiva del sistema:

* **Delegación colaborativa de tareas entre usuarios:** Se implementaron los endpoints `POST /api/tareas/enviar` y `PUT /api/tareas/{id}/responder` junto con los campos `id_remitente`, `nombre_remitente` y `estado_invitacion` (`pendiente_aceptacion` / `aceptada`), permitiendo enviar actividades a otros usuarios registrados por correo y que el destinatario decida si las acepta o rechaza desde una bandeja dedicada en el dashboard.
* **Suite de pruebas automatizadas con IA (`SistemaTareasTests.java`):** Se crearon e integraron pruebas de integración de extremo a extremo con **JUnit 5** y **`MockMvc`**, validando tanto casos de éxito como casos de error (`400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, `409 Conflict`).
* **Migración de persistencia a MongoDB:** Se reemplazó el almacenamiento en archivo plano (`database/db.json` / `Json.java`) por una base de datos documental **MongoDB** (`localhost:27017`) mediante **Spring Data MongoDB** (`UsuarioRepository` y `TareaRepository`), adaptando los controladores y las pruebas automatizadas.
* **Refinamiento del frontend y documentación final:** Se corrigió en `frontend/js/app.js` el filtrado de tareas activas frente a solicitudes pendientes de aceptación, se unificó el envío del campo `passwordHash` en el registro y se redactó el `README.md` definitivo con instrucciones paso a paso para clonar y ejecutar el proyecto en WSL.

---

# 2. Evidencias de trabajo

| Actividad                                                                         | Integrante responsable | Enlace a commit |
| :-------------------------------------------------------------------------------- | :--------------------- | :-------------- |
| Implementación de propuesta y aceptación de tareas entre usuarios                 | Osmar                  | ac012a1         |
| Creación e integración de suite de pruebas automatizadas con IA                   | Osmar                  | b63e331         |
| Migración de persistencia de archivo plano JSON a MongoDB y adaptación de pruebas | Osmar                  | fed2a21         |
| Integración final de cambios en frontend y filtrado de solicitudes pendientes     | Osmar                  | 6fa6395         |
| Redacción de instrucciones de instalación, MongoDB y ejecución en WSL en README   | Osmar                  | 36f159e         |

---

# 3. Problemas encontrados

* **Incompatibilidad de imports en Spring Boot 4 durante las pruebas:** Al compilar `SistemaTareasTests.java`, las anotaciones `@AutoConfigureMockMvc` y `com.fasterxml.jackson.databind.ObjectMapper` generaron errores de resolución por la reorganización modular de Spring Boot 4.1.2.
  * *Solución:* Se inicializó `MockMvc` directamente mediante `MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build()` y se implementó extracción de campos JSON con expresiones regulares nativas de Java.
* **Error `401 Unauthorized` en pruebas de inicio de sesión tras registro:** Las pruebas enviaban `password_hash` en `snake_case`, mientras que `Usuario.java` y `AuthController.java` serializan `passwordHash` en `camelCase`.
  * *Solución:* Se alineó el payload JSON a `passwordHash` tanto en `SistemaTareasTests.java` como en `frontend/js/app.js`.
* **Colisión `409 Conflict` en pruebas tras migrar a MongoDB:** Al sustituir `db.json` por MongoDB (`localhost:27017`), el método `@BeforeEach` seguía limpiando el archivo plano en lugar de las colecciones de Mongo, provocando errores por correos duplicados entre ejecuciones.
  * *Solución:* Se inyectaron `UsuarioRepository` y `TareaRepository` en `SistemaTareasTests.java` para respaldar en memoria los datos reales antes de cada prueba, limpiar las colecciones para el test y restaurar los registros originales en `@AfterEach`.

---

# 4. Avance funcional

  * Registro e inicio de sesión con contraseñas cifradas y sesiones HTTP protegidas por Spring Security.
  * Operaciones CRUD completas de tareas con aislamiento por usuario
  * Envío de tareas a otros usuarios registrados y flujo de aceptación/rechazo desde interfaz web.
  * Filtrado combinado por búsqueda, materia y estado (incluyendo detección automática de tareas vencidas) y autocompletado inteligente de materias con `<datalist>`.
  -  Persistencia real en MongoDB.
  * Ejecución limpia de pruebas automatizadas con ./mvnw test.
* **Funcionalidades pendientes:** Ninguna. El sistema se encuentra listo para evaluación final.

---

# 5. Uso de IA generativa

* **Herramienta utilizada:** Gemini
* **Actividad para la que se utilizó:** Diseño e integración de la suite de pruebas automatizadas
* **Código o contenido generado:** Clase SistemaTareasTests.java con 5 pruebas de integración de casos de éxito y error, interfaces UsuarioRepository y TareaRepository.
* **Modificaciones realizadas por el equipo:** Ubicación del archivo de pruebas en backend/src/test/java/mx/uv/desarrolloweb/, ajuste del atributo passwordHash conforme a Usuario.java e integración de respaldos con MongoRepository en @BeforeEach/@AfterEach.
* **Forma en que se verificó el resultado:** Ejecución de `./mvnw test` en terminal WSL verificando BUILD SUCCESS con 6 pruebas aprobadas.

---

# 6. Participación individual

* **Osmar:** Implementación de delegación de tareas (`ac012a1`), integración y depuración de pruebas automatizadas con IA (`b63e331`), migración de persistencia a MongoDB (`fed2a21`), ajustes de frontend (`6fa6395`) y documentación en `README.md` (`36f159e`).

---

# 7. Próximas actividades

* Verificar el documento `documentación/proyecto.pdf` con el enlace al video demostrativo en YouTube.

## Observaciones adicionales

Para reproducir el sistema en WSL es indispensable iniciar el servicio de MongoDB y talvez cambiar la ip o puerto dependiendo donde este corriendo el servicio