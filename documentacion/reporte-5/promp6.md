[INFO] Scanning for projects...
[INFO] Loaded 24571 auto-discovered prefixes for remote repository central (prefixes-central.txt)
[INFO] 
[INFO] --------------------< mx.uv.desarrolloweb:backend >---------------------
[INFO] Building backend 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.5.0:resources (default-resources) @ backend ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] Copying 0 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.15.0:compile (default-compile) @ backend ---
[INFO] Loaded 64 auto-discovered prefixes for remote repository apache.snapshots (prefixes-apache.snapshots.txt)
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- resources:3.5.0:testResources (default-testResources) @ backend ---
[INFO] skip non existing resourceDirectory /home/osmarlinx/programas-scul/repositorios/desarollo-web/backend/src/test/resources
[INFO] 
[INFO] --- compiler:3.15.0:testCompile (default-testCompile) @ backend ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- surefire:3.5.6:test (default-test) @ backend ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mx.uv.desarrolloweb.BackendApplicationTests
00:21:24.396 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [mx.uv.desarrolloweb.BackendApplicationTests]: BackendApplicationTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
00:21:24.654 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.BackendApplicationTests
00:21:24.825 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [mx.uv.desarrolloweb.BackendApplicationTests]: BackendApplicationTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
00:21:24.831 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.BackendApplicationTests

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.2)

2026-11-22T00:21:26.003-06:00  INFO 22645 --- [backend] [           main] m.u.d.BackendApplicationTests            : Starting BackendApplicationTests using Java 27.0.1.0.3 with PID 22645 (started by osmarlinx in /home/osmarlinx/programas-scul/repositorios/desarollo-web/backend)
2026-11-22T00:21:26.025-06:00  INFO 22645 --- [backend] [           main] m.u.d.BackendApplicationTests            : No active profile set, falling back to 1 default profile: "default"
2026-11-22T00:21:28.146-06:00  INFO 22645 --- [backend] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with UserDetailsService bean with name customUserDetailsService
2026-11-22T00:21:29.123-06:00  WARN 22645 --- [backend] [           main] o.s.w.s.resource.ResourceHandlerUtils    : Appended trailing slash to static resource location: file:../frontend/
2026-11-22T00:21:29.577-06:00  INFO 22645 --- [backend] [           main] m.u.d.BackendApplicationTests            : Started BackendApplicationTests in 4.585 seconds (process running for 7.327)
Mockito is currently self-attaching to enable the inline-mock-maker. This will no longer work in future releases of the JDK. Please add Mockito as an agent to your build as described in Mockito's documentation: https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.3
WARNING: A Java agent has been loaded dynamically (/home/osmarlinx/.m2/repository/net/bytebuddy/byte-buddy-agent/1.18.15/byte-buddy-agent-1.18.15.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.711 s -- in mx.uv.desarrolloweb.BackendApplicationTests
[INFO] Running mx.uv.desarrolloweb.SistemaTareasTests
2026-11-22T00:21:31.782-06:00  INFO 22645 --- [backend] [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [mx.uv.desarrolloweb.SistemaTareasTests]: SistemaTareasTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-11-22T00:21:31.804-06:00  INFO 22645 --- [backend] [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.SistemaTareasTests
2026-11-22T00:21:31.813-06:00  INFO 22645 --- [backend] [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [mx.uv.desarrolloweb.SistemaTareasTests]: SistemaTareasTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-11-22T00:21:31.821-06:00  INFO 22645 --- [backend] [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.SistemaTareasTests
2026-11-22T00:21:31.924-06:00  INFO 22645 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T00:21:31.927-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T00:21:31.937-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 9 ms
2026-11-22T00:21:32.171-06:00  INFO 22645 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T00:21:32.171-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T00:21:32.173-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 2 ms
2026-11-22T00:21:33.662-06:00  INFO 22645 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T00:21:33.663-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T00:21:33.665-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 2 ms
2026-11-22T00:21:33.838-06:00  INFO 22645 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T00:21:33.839-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T00:21:33.841-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 2 ms
2026-11-22T00:21:33.882-06:00  INFO 22645 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T00:21:33.883-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T00:21:33.884-06:00  INFO 22645 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 1 ms
[ERROR] Tests run: 5, Failures: 3, Errors: 0, Skipped: 0, Time elapsed: 2.142 s <<< FAILURE! -- in mx.uv.desarrolloweb.SistemaTareasTests
[ERROR] mx.uv.desarrolloweb.SistemaTareasTests.pruebaEnvioYAceptacionDeTarea -- Time elapsed: 1.481 s <<< FAILURE!
java.lang.AssertionError: Status expected:<200> but was:<401>
	at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:62)
	at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:129)
	at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$0(StatusResultMatchers.java:600)
	at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:212)
	at mx.uv.desarrolloweb.SistemaTareasTests.registrarYLoguear(SistemaTareasTests.java:90)
	at mx.uv.desarrolloweb.SistemaTareasTests.pruebaEnvioYAceptacionDeTarea(SistemaTareasTests.java:206)

[ERROR] mx.uv.desarrolloweb.SistemaTareasTests.pruebaCrudTareasExitoYValidacion -- Time elapsed: 0.169 s <<< FAILURE!
java.lang.AssertionError: Status expected:<200> but was:<401>
	at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:62)
	at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:129)
	at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$0(StatusResultMatchers.java:600)
	at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:212)
	at mx.uv.desarrolloweb.SistemaTareasTests.registrarYLoguear(SistemaTareasTests.java:90)
	at mx.uv.desarrolloweb.SistemaTareasTests.pruebaCrudTareasExitoYValidacion(SistemaTareasTests.java:130)

[ERROR] mx.uv.desarrolloweb.SistemaTareasTests.pruebaAislamientoEntreUsuarios -- Time elapsed: 0.033 s <<< FAILURE!
java.lang.AssertionError: Status expected:<200> but was:<401>
	at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:62)
	at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:129)
	at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$0(StatusResultMatchers.java:600)
	at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:212)
	at mx.uv.desarrolloweb.SistemaTareasTests.registrarYLoguear(SistemaTareasTests.java:90)
	at mx.uv.desarrolloweb.SistemaTareasTests.pruebaAislamientoEntreUsuarios(SistemaTareasTests.java:177)

[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   SistemaTareasTests.pruebaAislamientoEntreUsuarios:177->registrarYLoguear:90 Status expected:<200> but was:<401>
[ERROR]   SistemaTareasTests.pruebaCrudTareasExitoYValidacion:130->registrarYLoguear:90 Status expected:<200> but was:<401>
[ERROR]   SistemaTareasTests.pruebaEnvioYAceptacionDeTarea:206->registrarYLoguear:90 Status expected:<200> but was:<401>
[INFO] 
[ERROR] Tests run: 6, Failures: 3, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[ERROR] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  17.492 s
[INFO] Finished at: 2026-11-22T00:21:34-06:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.5.6:test (default-test) on project backend: There are test failures.
[ERROR] 
[ERROR] See /home/osmarlinx/programas-scul/repositorios/desarollo-web/backend/target/surefire-reports for the individual test results.
[ERROR] See dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException