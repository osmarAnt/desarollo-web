# Reporte de Avance del Proyecto

**Equipo:** 11
**Fecha:** 09/11/26
**Número de reporte:** 1
**Repositorio GitHub:** [desarrollo-web](https://github.com/osmarAnt/desarollo-web)
  

---

# 1. Actividades realizadas

Ya que este es el primer reporte se realizo el análisis y el diseño básico, básicamente lo que es la estructura que va a tener el proyecto, los requerimientos, la estructura de la base de datos y los endpoints REST

---

# 2. Evidencias de trabajo

| Actividad                      | Integrante responsable | Enlace a commit |
| ------------------------------ | ---------------------- | --------------- |
| Estructura del proyecto        | Osmar                  | d359515         |
| Estructura de la base de datos | Osmar                  | 70e9d84         |



---

# 3. Problemas encontrados

No se encontro ningun problema realmente

---

# 4. Avance funcional

como endpoints se definieron los siguientes:
- POST `/api/auth/login` (Autenticación) 
- GET `/api/tareas` (Listar tareas del usuario) 
- POST `/api/tareas` (Crear tarea nueva) 
- PUT `/api/tareas/{id}` (Actualizar estado) 
- DELETE `/api/tareas/{id}` (Eliminar tarea)

---

# 5. Uso de IA generativa

Documentar el uso de herramientas de inteligencia artificial durante el desarrollo del proyecto.

- **Herramienta utilizada:** gemini
- **Actividad para la que se utilizó:** estructuracion de la base de datos 
- **Código o contenido generado:** 
```json
{
  "usuarios": [
    {
      "id_usuario": "user_123",
      "nombre": "Osmar",
      "email": "correo@ejemplo.com",
      "password_hash": "hash_del_password",
      "telegram_chat_id": "123456789"
    }
  ],
  "tareas": [
    {
      "id_tarea": "task_001",
      "id_usuario": "user_123",
      "materia": "Ingeniería de Software",
      "titulo": "Entregable 1 Análisis",
      "fecha_limite": "2026-11-10T23:59:00",
      "estado": "pendiente"
    }
  ]
}
```
- **Modificaciones realizadas por el equipo:**
```json
{
	"usuarios": [
		{
			"id_usuario": "1",
			"nombre": "Osmar",
			"email": "correo@ejemplo.com",
		"password_hash":
"03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"
		}
	],
	"tareas": [
		{
		"id_tarea": "1",
		"id_usuario": "1",
		"materia": "Desarrollo Web",
		"titulo": "prueba",
		"fecha_limite": "2026-11-10T23:59:00",
		"estado": "pendiente"
		}
	]
}
```
- **Forma en que se verificó el resultado:** con observacion logica

---

# 6. Participación individual

Describir brevemente la contribución de cada integrante.

- **Integrante 1:** basicamente todo al ser el unico integrante desde diseño de la estructura del diseño, estructuracion de la base de datos y decicion de los endpoints


---

# 7. Próximas actividades

Indicar las tareas que se realizarán antes del siguiente reporte.

- Actividad 1
	- se van a crear los modelos de las clases java para representar en el json
- Actividad 2
	- se va a implementar la libreria jackson para editar el db.json
- Actividad 3
	- se implementara algunos controladores REST
- Actividad 4
	- comprobacion de funcionamiento

---

## Observaciones adicionales

Esto fue solo un poco de estructuracion y planeacion para las siguientes entregar, ya en las proximas entregas se crearan como minimo los enlaces