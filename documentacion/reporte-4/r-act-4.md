# Reporte de Avance del Proyecto

**Equipo:** 11
**Fecha:** 20/11/2026
**Número de reporte:** 4
**Repositorio GitHub:** [desarrollo-web](https://github.com/osmarAnt/desarollo-web "null")

# 1. Actividades realizadas

- Implementación de búsqueda por identificador (`buscarTareaPorId`) en `Json.java` y control de propiedad en `TareaController.java` (`PUT` y `DELETE`), verificando que cada tarea pertenezca al usuario autenticado (`403 Forbidden`) y validando campos obligatorios en `POST` (`400 Bad Request`).
- Incorporación de un panel de métricas en tiempo real en el dashboard (`Total`, `Pendientes`, `Vencidas` y `Completadas`) con detección automática de tareas vencidas según la fecha y hora actual del sistema.
- Implementación de búsqueda por texto, filtrado combinado por materia y estado, ordenamiento cronológico por fecha límite más próxima y notificaciones flotantes tipo _toast_.
- Desarrollo de un modal de edición completa de tareas conectado al endpoint `PUT /api/tareas/{id}` e integración de sugerencias desplegables mediante HTML5 `<datalist>` (`#materias-sugeridas`) para reutilizar materias existentes o registrar nuevas.

# 2. Evidencias de trabajo

|                                                                                                                               |                            |                     |
| ----------------------------------------------------------------------------------------------------------------------------- | -------------------------- | ------------------- |
| **Actividad**                                                                                                                 | **Integrante responsable** | **Enlace a commit** |
| Validación de propiedad (403/404) y campos obligatorios en backend y Panel de métricas, filtros combinados y modal de edición | Osmar                      | e3fba81             |
| Selector híbrido de materias con `<datalist>` y notificaciones _toast_                                                        | Osmar                      | c58e403             |


# 3. Problemas encontrados

- **Duplicación de campos en el modal de edición:** Al integrar el atributo `list="materias-sugeridas"` en el formulario de edición, se generó inicialmente un campo `<input id="edit-materia">` duplicado debajo de los botones de acción del modal. Se corrigió unificando un único campo dentro del flujo del formulario.
- **Superposición del historial nativo del navegador:** Al desplegar las sugerencias del `<datalist>`, el autocompletado propio del navegador interfería con las materias dinámicas. Se solucionó añadiendo `autocomplete="off"` en los campos `#materia` y `#edit-materia`.
- **Modificación cruzada de recursos entre usuarios:** Antes de esta entrega, cualquier sesión válida podía enviar un `PUT` o `DELETE` con el UUID de una tarea ajena. Se resolvió validando el `id_usuario` contra el objeto `Authentication` de Spring Security antes de persistir cambios.

# 4. Avance funcional

- **Operativo:**
    
    - Sistema integrado de extremo a extremo operando bajo Spring Boot en `http://localhost:8080`.
    - Registro e inicio de sesión con contraseñas cifradas (`BCrypt`), manejo de sesiones y control de acceso por propietario en todas las operaciones CRUD de tareas.
    - Interfaz completa con métricas en vivo, detección visual de tareas vencidas, búsqueda, filtros dinámicos, autocompletado de materias y edición mediante modal.
- **Pendiente:**
    - Ejecución de pruebas finales de casos borde, redacción de manual de instalación/uso en el `README.md` y publicación del _Release_ final de la Entrega 5.

# 5. Uso de IA generativa

- **Herramienta utilizada:** Gemini (promp.md / respuesta.md)
- **Actividad para la que se utilizó:** Diseño de una solución nativa en HTML5 y JavaScript para permitir al usuario seleccionar materias previamente registradas en otras tareas o escribir una nueva al crear y editar actividades.
- **Código o contenido generado:** [respuesta](respuesta.md)
- **Modificaciones realizadas por el equipo:** Integración del `<datalist>` tanto en el formulario de creación (`#materia`) como en el modal de edición (`#edit-materia`), limpieza de nodos duplicados y escape de caracteres HTML (`escapeHtml`).
- **Forma en que se verificó el resultado:** Pruebas funcionales en el navegador creando tareas con materias nuevas, comprobando su aparición inmediata en el desplegable al crear o editar otra tarea y verificando su persistencia en `database/db.json`.

# 6. Participación individual

- **Osmar:** Implementación de reglas de propiedad y validación en el backend, desarrollo de métricas, filtros, modal de edición, integración de `<datalist>` y pruebas funcionales del sistema integrado.    

# 7. Próximas actividades

- Realizar pruebas integrales de validación y estabilidad para el cierre del proyecto.
- Documentar los requisitos, arquitectura y guía de ejecución en el `README.md` principal.
- Generar el _Release_ final en GitHub correspondiente a la Entrega 5.

## Observaciones adicionales

ninguna