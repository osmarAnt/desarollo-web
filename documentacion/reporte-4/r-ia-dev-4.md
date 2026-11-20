# Reporte de IA Desarrollo en el Proyecto

**Fecha:** 20/11/2026

**Número de reporte:** 4

# 1. Breve descripción de uso

Asistencia en la implementación de un selector híbrido de materias mediante HTML5 `<datalist>` y JavaScript puro, permitiendo reutilizar nombres de materias ya registradas en tareas anteriores o ingresar materias nuevas sin librerías externas.

# 2. Evidencias de uso


| **Evidencia**               | **Formato**                                                                                                                                                                                                                                      |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Herramienta                 | Gemini                                                                                                                                                                                                                                           |
| Modelo/temperatura          | Gemini / Estándar                                                                                                                                                                                                                                |
| Prompt original             | promp.md                                                                                                                                                                                                                                         |
| Respuesta de IA             | respuesta.md                                                                                                                                                                                                                                     |
| Código anterior             | e3fba81                                                                                                                                                                                                                                          |
| Código posterior            | c58e403                                                                                                                                                                                                                                          |
| Diferencias entre versiones | 129     185     frontend/index.html<br>294     338     frontend/js/app.js_                                                                                                                                                                       |
| Prueba de funcionamiento    | Al hacer clic en el campo "Materia" , se despliega la lista ordenada alfabéticamente con las materias únicas ya registradas y permite filtrarlas al escribir o ingresar una nueva.                                                               |
| Código de uso               | Generación / Modificación                                                                                                                                                                                                                        |
| Reflexión del estudiante    | Aprendí a utilizar la etiqueta nativa `<datalist>` de HTML5 en combinación con `Set` en JavaScript para ofrecer autocompletado dinámico sin restringir la entrada libre del usuario ni requerir tablas catálogo adicionales en la base de datos. |

## Observaciones adicionales

ninguna