# Pruebatecnica

## Preguntas Técnicas

### 1. ¿Qué es REST?

Es un estilo de arquitectura a la hora de realizar peticiones entre cliente y servidor.  
Puede definir cómo debe funcionar una API en la web. Cuando se hace una solicitud a través de una API RESTful, se emite una representación del estado del recurso solicitado entregada por medio de HTTP.

**Principios:**

- Basada en recursos que se representan mediante URI (Identificadores Uniformes de Recursos).
- Sin estado (Stateless): No se guarda el estado de una petición anterior en el servidor, ya que cada petición debe contener la información necesaria para comprender y procesar la solicitud.
- Separación cliente-servidor: Deben ser independientes, para que el sistema sea modular.
- Interfaz uniforme: Esto facilita la interacción clara y concisa entre cliente y servidor, facilitando la comprensión.
- Arquitectura compuesta por múltiples capas: Esto genera que cada capa tenga una responsabilidad separada y sea más escalable en el tiempo.
- Uso de códigos de estado: Se usan códigos HTTP para el resultado de las peticiones.

---

### 3. Pregunta sobre Optimización de Rendimiento

- Agregar caching o memoria temporal a mi proyecto con Redis.
- El objetivo es reducir la carga en la base de datos a través de la configuración de Redis.
- Guardar resultados de consultas comunes.
- Configurar la caché con expiración TTL para que los datos se mantengan actualizados.
- Además de esto, usaría paginación para no traer todos los resultados de la base de datos de una sola vez.
- Por último, configuraría el pool de conexiones con HikariCP, evitando las sobrecargas al abrir y cerrar conexiones constantemente.

---

### 4. Analiza el log y describe cuál es el problema principal

Creo que el principal error está en que la aplicación no logra mantener una conexión estable con la base de datos, lo cual genera interrupciones en el servicio y errores en las operaciones que requieren acceso a datos.  
La aplicación está alcanzando el máximo de conexiones permitidas por el pool.

#### ¿Cómo resolverías el problema que encontraste en el log?

- Aumentar el tiempo máximo de conexiones si la app tiene alta demanda.
- Ajustar el tiempo de espera (`connectionTimeout`) para permitir que las conexiones se establezcan en condiciones de carga.
- Usar bloques `try-with-resources` para asegurar que todas las conexiones, sentencias y resultados se cierren adecuadamente.
- Revisar el código para evitar fugas de conexión (por ejemplo, conexiones abiertas sin cerrar en casos de error).

---

### 5. Ejercicio de integración AWS simulado

- Elegiría **Elastic Beanstalk** para desplegar mi aplicación porque simplifica el proceso.
- Empaquetar mi aplicación como `.jar` o `.war` y la subiría al entorno Java 17 en Beanstalk.
- Elastic Beanstalk configura automáticamente los recursos necesarios (EC2, balanceador de carga, escalado automático).
- Si se requiere mayor control, usaría EC2 manualmente, instalando Java y configurando Tomcat o Spring Boot con scripts de inicio.
- Beanstalk permite escalar automáticamente según el tráfico configurando políticas de Auto Scaling.

#### Almacenamiento de archivos estáticos con S3

- Crear un bucket en Amazon S3 para almacenar archivos (ejemplo: imágenes, PDFs).
- Configurar los permisos del bucket: acceso público (si son archivos públicos) o restringido (si son privados).
- Integrar el AWS SDK para Java que permite subir y descargar archivos desde S3.
- Usar URLs firmadas para que los usuarios descarguen archivos de forma segura.

#### Monitorización y alertas

Configuraría **CloudWatch Logs** en Elastic Beanstalk o EC2 para capturar logs de la aplicación:

- Definir alarmas de métricas como:
  - Uso de CPU alto
  - Número de errores 5xx
  - Fallos en las instancias

Estas alarmas pueden enviar notificaciones por **SNS (Simple Notification Service)** en caso de anomalías.  
Esto me permite monitorear la salud de la aplicación en tiempo real y reaccionar rápidamente.

#### Seguridad con IAM

- Usaría roles de IAM asignados a la instancia EC2 o entorno Beanstalk.
- Por ejemplo, para acceder a S3 desde Java, asignaría un rol con permisos específicos (`s3:PutObject`, `s3:GetObject`) sobre el bucket.
- Evitaría almacenar credenciales directamente en el código; en su lugar, usaría el rol asignado al entorno para que el SDK de AWS lo gestione automáticamente.

Esto mantiene las credenciales seguras y centraliza la gestión de permisos.

---

### 6. Pregunta sobre Microservicios

Para tener una comunicación óptima entre microservicios usaría una **comunicación asíncrona**, donde los microservicios no tengan que esperar la respuesta del receptor.  
Este acoplamiento temporal puede hacer que el sistema no tenga una alta escalabilidad y puede generarse una sobrecarga, afectando la disponibilidad de toda la aplicación.

- Las ventajas al usar REST: es simple y fácil de implementar, tiene buen soporte.  
  **Desventajas:** mayor latencia, no es óptimo para microservicios en alto rendimiento.

- gRPC:  
  **Ventajas:** menos consumo de ancho de banda.  
  **Desventajas:** curva de aprendizaje alta.

- Con **RabbitMQ** se puede lograr comunicación asíncrona entre microservicios.  
  **Ventajas:** escalabilidad alta, desacoplamiento.  
  **Desventajas:** puede ser más difícil de implementar, la latencia es variable.

---

### 7. Pregunta sobre Herramientas y Frameworks

Para pruebas unitarias he usado **JUnit** y **Mockito**.

- Con **JUnit** en cada servicio, lógica de negocio o controlador, creo pruebas para asegurarme de que cada método funciona correctamente, con diferentes solicitudes.
- Con **Mockito** en cada prueba he simulado dependencias externas sin necesidad de una conexión real.

También he usado **GitHub Actions**, configurando workflows que funcionan y ejecutan pruebas cada vez que hay un push o cuando hay un pull request.  
Esto me ayuda a integrar mejor el código y depurarlo.

---
## Instrucciones de uso de esta App

### si accedes a través del repo
- clonar el repositorio
- ``` git clone git@github.com:SoyNar/pruebatecnica.git ```
- reconstruir la aplicación para descargar las dependencias
- ``` mvn clean install -DskipTests```
### Luego de correr la aplicacion 
puedes correr la app:
### usando el comando de mvn

``` mvn spring-boot:run ```
o simplemente desde tu IDE (como IntelliJ IDEA, Eclipse o VS Code),
 haciendo clic en el botón Run

### Accede a los endpoinst a través de 
#### swagger
En este link, luego de correr la app
Alli estara documentada tambien
http://localhost:8080/swagger-ui/index.html


