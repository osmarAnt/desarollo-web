# Reporte de Avance del Proyecto

**Equipo:** 11
**Fecha:** 13/11/26
**Número de reporte:** 2
**Repositorio GitHub:** [desarrollo-web](https://github.com/osmarAnt/desarollo-web)  

---

# 1. Actividades realizadas

En Esta se realizaron las clases necesarias para interactuar con la base de datos dejando hacer un get y un post tanto para ver las tareas en base de datos como para crear tareas

---

# 2. Evidencias de trabajo

| Actividad                                     | Integrante responsable | Enlace a commit |
| --------------------------------------------- | ---------------------- | --------------- |
| Modelos y Repositorio JSON y controlador REST | Osmar                  | 60687f3         |


---

# 3. Problemas encontrados

Como se esta ocupando la dependencia de security de spring al momento de las pruebas con curl las solicitudes no pasaban entonces se tuvo que añadir el archivo de SecurityConfig.java para permitir pasar todas las solicitudes durante lo que dure el desarrollo, proximamente esto sera cambiado

---

# 4. Avance funcional

actualmente esta funcional el poder ver las tareas con un GET y crear una tarea por medio de un POST

---

# 5. Uso de IA generativa

Documentar el uso de herramientas de inteligencia artificial durante el desarrollo del proyecto.

- **Herramienta utilizada:** Gemini
- **Actividad para la que se utilizó:** Explicación técnica y diseño de un filtro de seguridad personalizado para permitir que las solicitudes CURL sobrepasen el inicio de sesión automático y los bloqueos de Spring Security durante el desarrollo local.
- **Código o contenido generado:** Configuracion de la dependencia de Segurity para dejar pasar todas las solicitudes 
- **Modificaciones realizadas por el equipo:** Adaptación del nombre del paquete base  para integrarlo correctamente en la estructura de carpetas del backend.
- **Forma en que se verificó el resultado:** comprobando los CURL con GET yPOST

---

# 6. Participación individual

Describir brevemente la contribución de cada integrante.

- **Integrante 1:** Integrante unico responsable de la implementacion del repositorio json, los endpoint REST y configuracion


---

# 7. Próximas actividades

Indicar las tareas que se realizarán antes del siguiente reporte.

- Actividad 1 añadir una interfaz para realizar los procesos
- Actividad 2 terminar de añadir los depas endpoint REST


---

## Observaciones adicionales

ninguna