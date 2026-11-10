Un archivo JSON (JavaScript Object Notation) se estructura utilizando una sintaxis de **pares clave-valor**, organizados mediante llaves `{}` para objetos y corchetes `[]` para arreglos o listas.
Para tu proyecto en específico, diseñamos el archivo `db.json` para que simule tener dos "tablas" relacionales.
JSON

```
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

Aquí te desgloso cómo se lee esta estructura a nivel de base de datos:
- **El objeto principal (`{ ... }`):** Todo el contenido del archivo está envuelto en unas llaves principales de apertura y cierre. Esto representa la totalidad de tu base de datos.
- **Los arreglos (`[ ... ]`):** Equivalen a tus tablas. Tienes una lista llamada `"usuarios"` y otra llamada `"tareas"`.
- **Los registros (`{ ... }` dentro de los arreglos):** Cada par de llaves individuales que pongas adentro de los corchetes representa un registro único (un usuario o una tarea específica). Van separados por comas.
- **Clave-Valor (`"campo": "dato"`):** Equivalen a tus columnas y sus datos. Las claves siempre deben ir entre comillas dobles (ej. `"materia"`). Si el valor es texto o una fecha, también lleva comillas; si fuera un número entero (ej. `25`) o un booleano (`true`), iría sin comillas.