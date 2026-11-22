cambie la forma de la base de datos de un archivo plano a un servicio con mongodb y las pruebas fallan: [INFO] Scanning for projects...
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
01:04:45.860 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [mx.uv.desarrolloweb.BackendApplicationTests]: BackendApplicationTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
01:04:46.730 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.BackendApplicationTests
01:04:46.930 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [mx.uv.desarrolloweb.BackendApplicationTests]: BackendApplicationTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
01:04:46.933 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.BackendApplicationTests

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.2)

2026-11-22T01:04:47.711-06:00  INFO 37619 --- [backend] [           main] m.u.d.BackendApplicationTests            : Starting BackendApplicationTests using Java 27.0.1.0.3 with PID 37619 (started by osmarlinx in /home/osmarlinx/programas-scul/repositorios/desarollo-web/backend)
2026-11-22T01:04:47.714-06:00  INFO 37619 --- [backend] [           main] m.u.d.BackendApplicationTests            : No active profile set, falling back to 1 default profile: "default"
2026-11-22T01:04:50.656-06:00  INFO 37619 --- [backend] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data MongoDB repositories in DEFAULT mode.
2026-11-22T01:04:50.735-06:00  INFO 37619 --- [backend] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 66 ms. Found 2 MongoDB repository interfaces.
2026-11-22T01:04:52.491-06:00  INFO 37619 --- [backend] [           main] org.mongodb.driver.client                : MongoClient with metadata {"driver": {"name": "mongo-java-driver|spring-boot|sync", "version": "5.8.1"}, "os": {"type": "Linux", "name": "Linux", "architecture": "amd64", "version": "7.3.4-arch1-2"}, "platform": "Java/Arch Linux/27.0.1.0.3"} created with settings MongoClientSettings{readPreference=primary, writeConcern=WriteConcern{w=null, wTimeout=null ms, journal=null}, retryWrites=true, retryReads=true, readConcern=ReadConcern{level=null}, credential=null, transportSettings=null, commandListeners=[], codecRegistry=ProvidersCodecRegistry{codecProviders=[ValueCodecProvider{}, BsonValueCodecProvider{}, DBRefCodecProvider{}, DBObjectCodecProvider{}, DocumentCodecProvider{}, CollectionCodecProvider{}, IterableCodecProvider{}, MapCodecProvider{}, GeoJsonCodecProvider{}, GridFSFileCodecProvider{}, Jsr310CodecProvider{}, JsonObjectCodecProvider{}, BsonCodecProvider{}, com.mongodb.client.model.mql.ExpressionCodecProvider@5b02a984, com.mongodb.Jep395RecordCodecProvider@57186526, com.mongodb.KotlinCodecProvider@391d1e33, EnumCodecProvider{}]}, loggerSettings=LoggerSettings{maxDocumentLength=1000}, clusterSettings={hosts=[localhost:27017], srvServiceName=mongodb, mode=SINGLE, requiredClusterType=UNKNOWN, requiredReplicaSetName='null', serverSelector='null', clusterListeners='[]', serverSelectionTimeout='30000 ms', localThreshold='15 ms'}, socketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=0, receiveBufferSize=0, proxySettings=ProxySettings{host=null, port=null, username=null, password=null}}, heartbeatSocketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=10000, receiveBufferSize=0, proxySettings=ProxySettings{host=null, port=null, username=null, password=null}}, connectionPoolSettings=ConnectionPoolSettings{maxSize=100, minSize=0, maxWaitTimeMS=120000, maxConnectionLifeTimeMS=0, maxConnectionIdleTimeMS=0, maintenanceInitialDelayMS=0, maintenanceFrequencyMS=60000, connectionPoolListeners=[], maxConnecting=2}, serverSettings=ServerSettings{heartbeatFrequencyMS=10000, minHeartbeatFrequencyMS=500, serverMonitoringMode=AUTO, serverListeners='[]', serverMonitorListeners='[]'}, sslSettings=SslSettings{enabled=false, invalidHostNameAllowed=false, context=null}, applicationName='null', compressorList=[], uuidRepresentation=UNSPECIFIED, serverApi=null, autoEncryptionSettings=null, dnsClient=null, inetAddressResolver=null, contextProvider=null, timeoutMS=null}
2026-11-22T01:04:52.581-06:00  INFO 37619 --- [backend] [localhost:27017] org.mongodb.driver.cluster               : Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=STANDALONE, cryptd=false, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=9, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=61231993, minRoundTripTimeNanos=0}
2026-11-22T01:04:53.718-06:00  INFO 37619 --- [backend] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with UserDetailsService bean with name customUserDetailsService
2026-11-22T01:04:54.612-06:00  WARN 37619 --- [backend] [           main] o.s.w.s.resource.ResourceHandlerUtils    : Appended trailing slash to static resource location: file:../frontend/
2026-11-22T01:04:55.059-06:00  INFO 37619 --- [backend] [           main] m.u.d.BackendApplicationTests            : Started BackendApplicationTests in 7.945 seconds (process running for 12.461)
Mockito is currently self-attaching to enable the inline-mock-maker. This will no longer work in future releases of the JDK. Please add Mockito as an agent to your build as described in Mockito's documentation: https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.3
WARNING: A Java agent has been loaded dynamically (/home/osmarlinx/.m2/repository/net/bytebuddy/byte-buddy-agent/1.18.15/byte-buddy-agent-1.18.15.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 12.22 s -- in mx.uv.desarrolloweb.BackendApplicationTests
[INFO] Running mx.uv.desarrolloweb.SistemaTareasTests
2026-11-22T01:04:57.268-06:00  INFO 37619 --- [backend] [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [mx.uv.desarrolloweb.SistemaTareasTests]: SistemaTareasTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-11-22T01:04:57.283-06:00  INFO 37619 --- [backend] [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.SistemaTareasTests
2026-11-22T01:04:57.291-06:00  INFO 37619 --- [backend] [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [mx.uv.desarrolloweb.SistemaTareasTests]: SistemaTareasTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-11-22T01:04:57.299-06:00  INFO 37619 --- [backend] [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration mx.uv.desarrolloweb.BackendApplication for test class mx.uv.desarrolloweb.SistemaTareasTests
2026-11-22T01:04:57.382-06:00  INFO 37619 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T01:04:57.383-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T01:04:57.390-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 7 ms
2026-11-22T01:04:58.145-06:00  INFO 37619 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T01:04:58.147-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T01:04:58.151-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 4 ms
2026-11-22T01:04:58.893-06:00  INFO 37619 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T01:04:58.893-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T01:04:58.896-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 3 ms
2026-11-22T01:04:58.938-06:00  INFO 37619 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T01:04:58.939-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T01:04:58.941-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 2 ms
2026-11-22T01:04:59.873-06:00  INFO 37619 --- [backend] [           main] o.s.b.t.m.w.SpringBootMockServletContext : Initializing Spring TestDispatcherServlet ''
2026-11-22T01:04:59.873-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Initializing Servlet ''
2026-11-22T01:04:59.875-06:00  INFO 37619 --- [backend] [           main] o.s.t.web.servlet.TestDispatcherServlet  : Completed initialization in 2 ms
[ERROR] Tests run: 5, Failures: 3, Errors: 0, Skipped: 0, Time elapsed: 2.641 s <<< FAILURE! -- in mx.uv.desarrolloweb.SistemaTareasTests
[ERROR] mx.uv.desarrolloweb.SistemaTareasTests.pruebaEnvioYAceptacionDeTarea -- Time elapsed: 0.738 s <<< FAILURE!
java.lang.AssertionError: Status expected:<201> but was:<409>
	at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:62)
	at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:129)
	at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$0(StatusResultMatchers.java:600)
	at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:212)
	at mx.uv.desarrolloweb.SistemaTareasTests.registrarYLoguear(SistemaTareasTests.java:80)
	at mx.uv.desarrolloweb.SistemaTareasTests.pruebaEnvioYAceptacionDeTarea(SistemaTareasTests.java:206)

[ERROR] mx.uv.desarrolloweb.SistemaTareasTests.pruebaCrudTareasExitoYValidacion -- Time elapsed: 0.037 s <<< FAILURE!
java.lang.AssertionError: Status expected:<201> but was:<409>
	at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:62)
	at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:129)
	at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$0(StatusResultMatchers.java:600)
	at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:212)
	at mx.uv.desarrolloweb.SistemaTareasTests.registrarYLoguear(SistemaTareasTests.java:80)
	at mx.uv.desarrolloweb.SistemaTareasTests.pruebaCrudTareasExitoYValidacion(SistemaTareasTests.java:130)

[ERROR] mx.uv.desarrolloweb.SistemaTareasTests.pruebaAutenticacionExitoYError -- Time elapsed: 0.025 s <<< FAILURE!
java.lang.AssertionError: Status expected:<201> but was:<409>
	at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:62)
	at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:129)
	at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$0(StatusResultMatchers.java:600)
	at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:212)
	at mx.uv.desarrolloweb.SistemaTareasTests.pruebaAutenticacionExitoYError(SistemaTareasTests.java:103)

[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   SistemaTareasTests.pruebaAutenticacionExitoYError:103 Status expected:<201> but was:<409>
[ERROR]   SistemaTareasTests.pruebaCrudTareasExitoYValidacion:130->registrarYLoguear:80 Status expected:<201> but was:<409>
[ERROR]   SistemaTareasTests.pruebaEnvioYAceptacionDeTarea:206->registrarYLoguear:80 Status expected:<201> but was:<409>
[INFO] 
[ERROR] Tests run: 6, Failures: 3, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[ERROR] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  22.928 s
[INFO] Finished at: 2026-11-22T01:05:00-06:00
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