# Reporte de Avance del Proyecto

**Equipo:** 11  
**Fecha:** 16/11/2026  
**Número de reporte:** 3  
**Repositorio GitHub:** [desarrollo-web](https://github.com/osmarAnt/desarollo-web)  

---

# 1. Actividades realizadas

- Completado del CRUD REST en `TareaController` y la capa de persistencia JSON (`PUT /api/tareas/{id}` y `DELETE /api/tareas/{id}`) con generación automática de identificadores UUID.
- Incorporación de la dependencia `spring-boot-starter-security` e implementación de `CustomUserDetailsService`, `SecurityConfig` y `AuthController` (`/api/auth/register`, `/api/auth/login`, `/api/auth/me`) con cifrado `BCrypt` y sesiones `JSESSIONID`.
- Configuración de `VistaController` y `WebConfig` para servir la interfaz web desde el mismo servidor Spring Boot (`localhost:8080`) y redirigir `GET /` hacia `/index.html`.
- Desarrollo del frontend (`index.html`, `styles.css`, `app.js`) conectado a la API REST mediante `fetch`, incluyendo registro, inicio de sesión, validación visual de correo, filtrado por estado, creación, actualización y eliminación de tareas.

---

# 2. Evidencias de trabajo

| Actividad                                                                  | Integrante responsable | Enlace a commit |
| -------------------------------------------------------------------------- | ---------------------- | --------------- |
| Endpoints PUT/DELETE y blindaje contra nulos en JSON                       | Osmar                  | 79aafe6         |
| Incorporación de Spring Security con BCrypt, sesiones y frontend funcional | Osmar                  | 9fbbfd3         |


---

# 3. Problemas encontrados

- **NullPointerException al actualizar tareas (`PUT`):** Al ejecutar `t.getId_tarea().equals(idTarea)`, registros previos con `id_tarea` nulo provocaban una excepción en tiempo de ejecución. Se solucionó invirtiendo la comparación (`idTarea.equals(t.getId_tarea())`) y normalizando los atributos a `snake_case`.

---

# 4. Avance funcional

- **Operativo:**
  - Registro e inicio de sesión con contraseñas encriptadas (`BCrypt`) y protección de rutas `/api/tareas/**` mediante Spring Security.
  - Redirección automática desde `http://localhost:8080/` hacia la interfaz web principal.
  - Creación, listado filtrado por usuario autenticado y estado, cambio de estado (`pendiente` / `completada`) y eliminación de tareas desde el navegador persistiendo en `database/db.json`.
- **Pendiente:**
  - Pruebas finales de integración completa y empaquetado para la entrega final.

---

# 5. Uso de IA generativa

- **Herramienta utilizada:** Gemini ([promp.md](promp.md) / [respuesta.md](respuesta.md))
- **Actividad para la que se utilizó:** Incorporación y configuración de la dependencia `spring-boot-starter-security` para autenticar usuarios contra `db.json`, cifrar contraseñas con `BCrypt` y proteger los endpoints de tareas mediante sesiones.
- **Código o contenido generado:** Dependencia Maven de Spring Security, clase `CustomUserDetailsService.java`, actualización de `SecurityConfig.java`, endpoints de `AuthController.java` e inyección de `Authentication` en `TareaController.java`.
- **Modificaciones realizadas por el equipo:** Adaptación de los nombres de paquetes (`mx.uv.desarrolloweb`) y mapeo de métodos de acceso en `snake_case` (`getPassword_hash()`, `getId_usuario()`, `setId_usuario()`) para empatar con los modelos del proyecto.
- **Forma en que se verificó el resultado:** Pruebas con `curl` verificando rechazo `401 Unauthorized` en `/api/tareas` sin sesión activa, inspección de hashes `$2a$10$...` generados en `database/db.json` y flujo completo de login/logout desde el navegador.

---

# 6. Participación individual

- **Osmar:** Desarrollo integral del backend REST, configuración de seguridad y sesiones con Spring Security, unificación del servidor estático, desarrollo del frontend y pruebas funcionales de extremo a extremo.

---

# 7. Próximas actividades

- Realizar pruebas de validación de casos borde en formularios y endpoints protegidos.
- Revisar documentación técnica y manual de ejecución del proyecto.

---

## Observaciones adicionales

Toda la aplicación opera bajo un único proceso en el puerto `8080` de Spring Boot manteniendo la arquitectura de tres capas: presentación (`frontend/`), lógica de negocio y seguridad (`backend/`), y persistencia en archivo plano (`database/db.json`).