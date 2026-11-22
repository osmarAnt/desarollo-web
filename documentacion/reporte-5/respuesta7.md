El error `401` en `/api/auth/login` ocurre porque `Usuario.java` define el atributo en `camelCase` (`passwordHash`) y `AuthController` lo lee con `nuevoUsuario.getPasswordHash()`, mientras que la prueba estaba enviando `"password_hash"` en `snake_case`. Al no coincidir el nombre de la propiedad JSON, Jackson dejaba `passwordHash` en `null` al registrar al usuario y `BCrypt` rechazaba el inicio de sesión.

  

### 1. Actualizar `SistemaTareasTests.java`

Reemplaza el contenido de `backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java` con esta versión corregida que envía `"passwordHash"` en el registro:

  

Java

```
package mx.uv.desarrolloweb;

import jakarta.servlet.Filter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SistemaTareasTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private Path dbPath;
    private byte[] respaldoOriginal;

    @BeforeEach
    void prepararEntorno() throws Exception {
        Filter springSecurityFilterChain = context.getBean("springSecurityFilterChain", Filter.class);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();

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

    private String extraerCampoJson(String json, String campo) {
        Matcher m = Pattern.compile("\"" + Pattern.quote(campo) + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        assertTrue(m.find(), "No se encontró el campo '" + campo + "' en: " + json);
        return m.group(1);
    }

    private MockHttpSession registrarYLoguear(String nombre, String email, String password) throws Exception {
        String registroJson = String.format(
                "{\"nombre\":\"%s\",\"email\":\"%s\",\"passwordHash\":\"%s\"}",
                nombre, email, password
        );

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registroJson))
                .andExpect(status().isCreated());

        String loginJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email, password
        );

        MvcResult loginRes = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
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
                        .content("{\"nombre\":\"Osmar\",\"email\":\"osmar@uv.mx\",\"passwordHash\":\"clave123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("osmar@uv.mx"));

        // Caso de error: correo duplicado -> 409 Conflict
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Osmar Clon\",\"email\":\"osmar@uv.mx\",\"passwordHash\":\"otraClave\"}"))
                .andExpect(status().isConflict());

        // Caso de error: contraseña incorrecta -> 401 Unauthorized
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"osmar@uv.mx\",\"password\":\"incorrecta\"}"))
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
                        .content("{\"materia\":\"\",\"titulo\":\"Sin materia\",\"fecha_limite\":\"2026-11-25T23:59\"}"))
                .andExpect(status().isBadRequest());

        // Caso de éxito: crear tarea válida -> 201 Created
        MvcResult createRes = mockMvc.perform(post("/api/tareas")
                        .session(sesion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"materia\":\"Desarrollo Web\",\"titulo\":\"Entrega Final\",\"fecha_limite\":\"2026-11-23T23:59\",\"estado\":\"pendiente\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String idTarea = extraerCampoJson(createRes.getResponse().getContentAsString(), "id_tarea");

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
                        .content("{\"materia\":\"Seguridad\",\"titulo\":\"Tarea Privada de A\",\"fecha_limite\":\"2026-11-24T12:00\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String idTareaDeA = extraerCampoJson(res.getResponse().getContentAsString(), "id_tarea");

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
                        .content("{\"destinatario_email\":\"noexiste@uv.mx\",\"materia\":\"Redes\",\"titulo\":\"Práctica\",\"fecha_limite\":\"2026-11-25T18:00\"}"))
                .andExpect(status().isNotFound());

        // Caso de éxito: enviar tarea a compañero -> 201 Created
        MvcResult envioRes = mockMvc.perform(post("/api/tareas/enviar")
                        .session(sesionRemitente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"destinatario_email\":\"compa@uv.mx\",\"materia\":\"Desarrollo Web\",\"titulo\":\"Grabar video de demostración\",\"fecha_limite\":\"2026-11-23T18:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado_invitacion").value("pendiente_aceptacion"))
                .andReturn();

        String idInvitacion = extraerCampoJson(envioRes.getResponse().getContentAsString(), "id_tarea");

        // Destinatario acepta la tarea -> 200 OK
        mockMvc.perform(put("/api/tareas/" + idInvitacion + "/responder")
                        .session(sesionDestino)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accion\":\"aceptar\"}"))
                .andExpect(status().isOk());
    }
}
```

### 2. Revisa también `registerForm` en `app.js`

Para evitar que pase lo mismo al registrar cuentas nuevas desde el navegador, verifica que en `app.js` (dentro de `registerForm.addEventListener("submit", ...)`) envíes `passwordHash` en lugar de `password_hash`:

  

JavaScript

```
body: JSON.stringify({ nombre, email, passwordHash: password })
```

Vuelve a ejecutar `./mvnw test` dentro de `backend/` y ya deberían pasar las 6 pruebas con `Failures: 0, Errors: 0`.