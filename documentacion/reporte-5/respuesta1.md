De acuerdo con el PDF del proyecto final, la entrega definitiva es el **23 de noviembre** y **el uso de IA generativa es explícitamente obligatorio para crear e integrar las pruebas**, donde cada integrante debe hacerse responsable de las suyas.


Lo que exige la rúbrica para esta fase de pruebas y cierre:

  

- **Ubicación en el repositorio:** Las pruebas automatizadas deben ir dentro de `backend/src/test/java/mx/uv/desarrolloweb/`.
    
- **Cobertura de casos:** Se deben probar tanto **casos de éxito como casos de error**, verificando su ejecución desde WSL.
    
- **Documentación de IA (`Código de uso: Pruebas`):** Hay que registrar el prompt, la respuesta, los commits antes/después con la salida de `git diff --numstat`, la reflexión y evidencia de ejecución (salida de terminal o captura) siguiendo la estructura `documentación/IAG/integrante_01/Uso_XX/` (`prompt.md`, `respuesta.md`, `código_antes.java`, `código_despues.java`, `cambios.diff`, `reflexion.md`).
    

### Suite de Pruebas Automatizadas (JUnit 5 + `MockMvc`)

Crea el archivo **`backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java`**. Esta clase prueba todo tu backend de extremo a extremo (casos de éxito y de error) y respalda/restaura automáticamente tu `db.json` antes y después de cada prueba para no borrar tus datos reales:

  

Java

```
package mx.uv.desarrolloweb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SistemaTareasTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Path dbPath;
    private byte[] respaldoOriginal;

    @BeforeEach
    void prepararEntorno() throws Exception {
        Path rutaRelativa = Paths.get("../database/db.json");
        dbPath = Files.exists(rutaRelativa) ? rutaRelativa : Paths.get("database/db.json");

        if (Files.exists(dbPath)) {
            respaldoOriginal = Files.readAllBytes(dbPath);
        } else {
            Files.createDirectories(dbPath.getParent());
            respaldoOriginal = null;
        }
        // Inicia cada prueba con una base de datos limpia y controlada
        Files.writeString(dbPath, "{\"usuarios\":[],\"tareas\":[]}");
    }

    @AfterEach
    void restaurarEntorno() throws Exception {
        if (respaldoOriginal != null) {
            Files.write(dbPath, respaldoOriginal);
        }
    }

    // Helper para registrar e iniciar sesión devolviendo la cookie JSESSIONID
    private MockHttpSession registrarYLoguear(String nombre, String email, String password) throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "nombre", nombre,
                                "email", email,
                                "password_hash", password
                        ))))
                .andExpect(status().isCreated());

        MvcResult loginRes = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "email", email,
                                "password", password
                        ))))
                .andExpect(status().isOk())
                .andReturn();

        return (MockHttpSession) loginRes.getRequest().getSession(false);
    }

    @Test
    @DisplayName("1. Registro exitoso, rechazo de correo duplicado (409) y login inválido (401)")
    void pruebaAutenticacionExitoYError() throws Exception {
        // Caso de éxito: registro de usuario nuevo
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "nombre", "Osmar",
                                "email", "osmar@uv.mx",
                                "password_hash", "clave123"
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("osmar@uv.mx"));

        // Caso de error: correo duplicado -> 409 Conflict
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "nombre", "Osmar Clon",
                                "email", "osmar@uv.mx",
                                "password_hash", "otraClave"
                        ))))
                .andExpect(status().isConflict());

        // Caso de error: contraseña incorrecta -> 401 Unauthorized
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "email", "osmar@uv.mx",
                                "password", "incorrecta"
                        ))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("2. Bloqueo de endpoints protegidos sin sesión activa (401 Unauthorized)")
    void pruebaAccesoSinSesionBloqueado() throws Exception {
        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("3. CRUD completo de tareas y validación de campos vacíos (400 Bad Request)")
    void pruebaCrudTareasExitoYValidacion() throws Exception {
        MockHttpSession sesion = registrarYLoguear("Osmar", "osmar@uv.mx", "clave123");

        // Caso de error: crear tarea con campos vacíos -> 400 Bad Request
        mockMvc.perform(post("/api/tareas")
                        .session(sesion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "materia", "",
                                "titulo", "Sin materia",
                                "fecha_limite", "2026-11-25T23:59"
                        ))))
                .andExpect(status().isBadRequest());

        // Caso de éxito: crear tarea válida -> 201 Created
        MvcResult createRes = mockMvc.perform(post("/api/tareas")
                        .session(sesion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "materia", "Desarrollo Web",
                                "titulo", "Entrega Final",
                                "fecha_limite", "2026-11-23T23:59",
                                "estado", "pendiente"
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode tareaCreada = objectMapper.readTree(createRes.getResponse().getContentAsString());
        String idTarea = tareaCreada.get("id_tarea").asText();

        // Caso de éxito: listar tareas -> 200 OK
        mockMvc.perform(get("/api/tareas").session(sesion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Entrega Final"));

        // Caso de éxito: actualizar estado -> 200 OK
        mockMvc.perform(put("/api/tareas/" + idTarea)
                        .session(sesion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\":\"completada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("completada"));

        // Caso de error: actualizar tarea inexistente -> 404 Not Found
        mockMvc.perform(put("/api/tareas/uuid-inexistente")
                        .session(sesion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\":\"completada\"}"))
                .andExpect(status().isNotFound());

        // Caso de éxito: eliminar tarea -> 200 OK
        mockMvc.perform(delete("/api/tareas/" + idTarea).session(sesion))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("4. Control de propiedad: impedir modificar o borrar tareas de otro usuario (403 Forbidden)")
    void pruebaAislamientoEntreUsuarios() throws Exception {
        MockHttpSession sesionUsuarioA = registrarYLoguear("Usuario A", "a@uv.mx", "pass123");
        MockHttpSession sesionUsuarioB = registrarYLoguear("Usuario B", "b@uv.mx", "pass123");

        // Usuario A crea su tarea
        MvcResult res = mockMvc.perform(post("/api/tareas")
                        .session(sesionUsuarioA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "materia", "Seguridad",
                                "titulo", "Tarea Privada de A",
                                "fecha_limite", "2026-11-24T12:00"
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        String idTareaDeA = objectMapper.readTree(res.getResponse().getContentAsString())
                .get("id_tarea").asText();

        // Usuario B intenta modificarla -> 403 Forbidden
        mockMvc.perform(put("/api/tareas/" + idTareaDeA)
                        .session(sesionUsuarioB)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\":\"completada\"}"))
                .andExpect(status().isForbidden());

        // Usuario B intenta eliminarla -> 403 Forbidden
        mockMvc.perform(delete("/api/tareas/" + idTareaDeA)
                        .session(sesionUsuarioB))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("5. Envío de tarea a otro usuario y aceptación de la solicitud")
    void pruebaEnvioYAceptacionDeTarea() throws Exception {
        MockHttpSession sesionRemitente = registrarYLoguear("Osmar", "osmar@uv.mx", "pass123");
        MockHttpSession sesionDestino = registrarYLoguear("Companero", "compa@uv.mx", "pass123");

        // Caso de error: enviar a correo no registrado -> 404 Not Found
        mockMvc.perform(post("/api/tareas/enviar")
                        .session(sesionRemitente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "destinatario_email", "noexiste@uv.mx",
                                "materia", "Redes",
                                "titulo", "Práctica",
                                "fecha_limite", "2026-11-25T18:00"
                        ))))
                .andExpect(status().isNotFound());

        // Caso de éxito: enviar tarea a compañero -> 201 Created
        MvcResult envioRes = mockMvc.perform(post("/api/tareas/enviar")
                        .session(sesionRemitente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "destinatario_email", "compa@uv.mx",
                                "materia", "Desarrollo Web",
                                "titulo", "Grabar video de demostración",
                                "fecha_limite", "2026-11-23T18:00"
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado_invitacion").value("pendiente_aceptacion"))
                .andReturn();

        String idInvitacion = objectMapper.readTree(envioRes.getResponse().getContentAsString())
                .get("id_tarea").asText();

        // Destinatario acepta la tarea -> 200 OK
        mockMvc.perform(put("/api/tareas/" + idInvitacion + "/responder")
                        .session(sesionDestino)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accion\":\"aceptar\"}"))
                .andExpect(status().isOk());
    }
}
```

#### Cómo ejecutar las pruebas y sacar la evidencia para tu reporte

En tu terminal dentro de `backend/`:

  

Bash

```
./mvnw test
```

Verás en consola `Tests run: 5, Failures: 0, Errors: 0` (toma captura de esa salida para tu carpeta de evidencias `Uso_XX` y para `r-ia-dev-5.md`).

  

### Checklist rápido de entregables obligatorios para el lunes 23 de noviembre

Además del código y las pruebas, verifica que no te falte ninguno de estos elementos que pide el PDF:

1. **Video en YouTube (máximo 15–20 min):** Demostración general del sistema funcionando desde WSL, con participación de los integrantes y cámara abierta de preferencia; configurado como **Público** o **No listado** (nunca privado).
    
2. **Documento `documentación/proyecto.pdf`:** Debe incluir portada (nombre de la app, integrantes y docente), introducción, problemática que resuelve, una página con los enlaces al video de YouTube y al repositorio GitHub (`desarrollo-web`), y diagrama/explicación de la arquitectura.
    
3. **Declaración firmada + Carpeta `documentación/IAG/`:** Declaración firmada por los integrantes sobre el uso de IA generativa y los respaldos organizados por integrante (`integrante_01/Uso_01/...`) con `prompt.md`, `respuesta.md`, `código_antes.java`, `código_despues.java`, `cambios.diff` y `reflexion.md`.
    
4. **`README.md` y Release final en GitHub:** Instrucciones claras para clonar y ejecutar el proyecto desde WSL y el _Release_ final de la Entrega 5 publicado en GitHub.