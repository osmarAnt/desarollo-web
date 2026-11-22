Cuando mueves el archivo a `src/test/java`, los errores que suelen quedar se deben a que **VS Code todavía tiene en caché el classpath anterior** o a que falta sincronizar `pom.xml` con Maven.

  

Haz estas tres verificaciones en orden:

  

1. **Confirma que `spring-boot-starter-test` esté guardado en `backend/pom.xml`:** Abre `backend/pom.xml` y asegúrate de tener esta dependencia dentro de `<dependencies>` (con el archivo guardado, sin el punto blanco de cambios pendientes):
    
    XML
    
    ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    ```
    
    _Verificación:_ Al guardar `pom.xml`, VS Code debería mostrar abajo a la derecha una notificación preguntando si deseas sincronizar el proyecto Java (`Always` / `Now`).
    
2. **Verifica que el `package` coincida con tu carpeta real:** Revisa la primera línea de tu clase principal (`*Application.java` dentro de `src/main/java/...`) y asegúrate de que `SistemaTareasTests.java` tenga **exactamente el mismo `package`** y esté dentro de la misma subcarpeta en `src/test/java/...` (por ejemplo, si tu paquete es `mx.uv.desarolloweb` con una sola `r`, cámbialo en la línea 1)._Verificación:_ Desaparece el subrayado rojo debajo de la declaración `package ...;`.
    
3. **Prueba compilar desde WSL y fuerza el refresco en VS Code:** Abre la terminal de WSL dentro de `backend/` y ejecuta:
    
    Bash
    
    ```
    ./mvnw test
    ```
    
    Si en la terminal pasan los 5 tests (`Tests run: 5, Failures: 0, Errors: 0`), el código está bien y solo es caché visual del editor: presiona `Ctrl + Shift + P` en VS Code y ejecuta **`Java: Clean Java Language Server Workspace`** → **Reload and delete**._Verificación:_ Tras recargar la ventana, todos los subrayados rojos en los `import` de JUnit y `MockMvc` desaparecen.
    

Si al correr `./mvnw test` en la terminal te arroja algún error de compilación, pega aquí el texto del error de la consola para corregirlo directo.