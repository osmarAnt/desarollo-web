Ya compiló y corrieron las 6 pruebas sin errores de código: pasaron 3 (incluyendo el registro con `201 Created`, el rechazo de correo duplicado `409` y el bloqueo `401` sin sesión), y las otras 3 fallaron en la misma línea (`SistemaTareasTests.java:90`) porque `POST /api/auth/login` devolvió `401 Unauthorized` inmediatamente después de registrar al usuario.


Eso ocurre por una diferencia entre los campos o el cifrado que usan tus clases reales (`AuthController.java` / `Usuario.java`) y lo que envió el test:


- **Nombre del campo de contraseña:** En el test se mandó `"password_hash"` al registrar y `"password"` al hacer login; si tu `Usuario.java` usa `password` (o tu `AuthController` espera `password_hash` en ambos), el usuario se crea con contraseña vacía o nula y el login rechaza con `401`.
    
- **Cifrado en `AuthController`:** Si tu endpoint `/api/auth/register` no encripta con `BCryptPasswordEncoder` antes de guardar en `db.json` (o si `SecurityConfig` usa otro `PasswordEncoder`), `AuthenticationManager` falla al comparar la clave.
    

Pega aquí el contenido de **`AuthController.java`** y **`Usuario.java`** para alinear exactamente los nombres de campos en `SistemaTareasTests.java` y dejar las 6 pruebas en verde.