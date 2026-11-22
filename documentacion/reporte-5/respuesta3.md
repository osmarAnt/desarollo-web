En la captura se ve que VS Code sí reconoce `ObjectMapper` y `@Autowired`, pero marca en rojo todo lo de `org.junit.jupiter` y `org.springframework.test`. Eso pasa por una de estas tres razones:



### 1. Revisa que el archivo esté en `src/test/java` (y no en `src/main/java`)

Si creaste `SistemaTareasTests.java` dentro de `src/main/java/mx/uv/desarrolloweb/`, Java bloquea automáticamente todas las librerías con `<scope>test</scope>` (JUnit y MockMvc).

  

Asegúrate de que la ruta exacta sea:

  

Plaintext

```
backend/src/test/java/mx/uv/desarrolloweb/SistemaTareasTests.java
```

### 2. Confirma la dependencia en `backend/pom.xml` y descarga desde WSL

Verifica que dentro de `<dependencies>` en tu `backend/pom.xml` esté este bloque:

  

XML

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

Luego abre tu terminal de WSL dentro de `backend/` y ejecuta:

  

Bash

```
./mvnw test-compile
```

_(Esto obliga a Maven a descargar los `.jar` de JUnit 5 y MockMvc si todavía no estaban en tu máquina)._

  

### 3. Limpia la caché de Java en VS Code

Aunque Maven ya haya descargado las librerías en terminal, la extensión de Java en VS Code suele quedarse con el classpath viejo:

  

1. Presiona `Ctrl + Shift + P`.
    
2. Escribe y selecciona **`Java: Clean Java Language Server Workspace`**.
    
3. Dale clic a **Reload and delete** cuando te lo pida.
    

Si al correr `./mvnw test-compile` en la terminal te marca algún error distinto a los imports, pega aquí la salida de la consola o tu `pom.xml` para revisarlo.