Para permitir que tus solicitudes realizadas mediante `curl` (o herramientas como Postman) no sean interceptadas por la pantalla de inicio de sesión de **Spring Security**, necesitas configurar un filtro web personalizado.


Por defecto, Spring Boot protege todas las rutas y bloquea las peticiones `POST`, `PUT` o `DELETE` exigiendo un token CSRF y credenciales de autenticación.  

### Solución: Crear la clase de configuración de seguridad

Para relajar estas restricciones durante el desarrollo de tu API, puedes crear una clase de configuración que deshabilite la protección CSRF y permita el acceso libre a cualquier endpoint.

Crea un archivo llamado **`SecurityConfig.java`** dentro de la raíz de tu paquete principal (por ejemplo, en `src/main/java/mx/uv/desarrolloweb/SecurityConfig.java`):

Java

```java
package mx.uv.desarrolloweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactiva la protección CSRF para evitar bloqueos en peticiones POST, PUT o DELETE
            .csrf(csrf -> csrf.disable())
            // Configura las reglas de autorización para las peticiones HTTP
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite el acceso público a todos los endpoints sin pedir login
            );
            
        return http.build();
    }
}
```

### ¿Qué hace este código?

1. **`.csrf(csrf -> csrf.disable())`**: Evita que Spring rechace tus peticiones `POST` o `PUT` por no incluir un token de validación CSRF, lo cual es ideal para pruebas locales con `curl`.
2. **`.anyRequest().permitAll()`**: Abre el acceso a todas las rutas de tu aplicación, eliminando la generación automática de la página de login y las credenciales temporales.

Una vez que guardes este archivo y reinicies tu servidor Spring Boot, tus comandos `curl` podrán interactuar directamente con tu API sin restricciones de seguridad.