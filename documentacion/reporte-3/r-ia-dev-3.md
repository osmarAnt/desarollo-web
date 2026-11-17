# Reporte de IA Desarrollo en el Proyecto

**Fecha:** 16/11/2026  
**Número de reporte:** 3  

---

# 1. Breve descripción de uso

Asistencia en la incorporación y configuración de la dependencia `spring-boot-starter-security` para autenticar usuarios contra el archivo `db.json`, encriptar contraseñas con `BCrypt` y proteger los endpoints REST de tareas mediante sesiones HTTP.

---

# 2. Evidencias de uso

| Evidencia                   | Formato                                                                                                                                                                                                                                                                                                              |     |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --- |
| Herramienta                 | Gemini                                                                                                                                                                                                                                                                                                               |     |
| Modelo/temperatura          | Gemini / Estándar                                                                                                                                                                                                                                                                                                    |     |
| Prompt original             | [promp.md](promp.md)                                                                                                                                                                                                                                                                                                 |     |
| Respuesta de IA             | [respuesta.md](respuesta.md)                                                                                                                                                                                                                                                                                         |     |
| Código anterior             | 79aafe6                                                                                                                                                                                                                                                                                                              |     |
| Código posterior            | 9fbbfd3                                                                                                                                                                                                                                                                                                              |     |
| Diferencias entre versiones | [diferencia](diferencia.md)                                                                                                                                                                                                                                                                                          |     |
| Prueba de funcionamiento    | Petición `GET /api/tareas` sin sesión rechazada con código `401 Unauthorized` en JSON; registro de usuario almacenando contraseña cifrada con prefijo `$2a$10$...` en `db.json` y acceso autorizado tras iniciar sesión.                                                                                             |     |
| Código de uso               | Generación / Modificación / Diseño                                                                                                                                                                                                                                                                                   |     |
| Reflexión del estudiante    | Aprendí a integrar Spring Security con una fuente de datos personalizada en JSON mediante `UserDetailsService`, a cifrar contraseñas con `BCryptPasswordEncoder` y a configurar un `AuthenticationEntryPoint` para devolver respuestas `401` en JSON adecuadas para clientes `fetch` en lugar de redirecciones HTML. |     |

---

## Observaciones adicionales

la libreria de segurity si es bastante compleja y completa  con lo que ofrece