# Despliegue de una App en Wildfly con una base datos Postgres
_Se indica a como construir una aplicación y desplegar usando Dockerfile y docker-compose_

## Requisitos previos 🚀
Tener una apicación JavaEE, (adjunto un ejemplo).
También debemos tener Docker instalado.

## Instrucciones

- Compilar nuestra aplicación a .war o en su defecto usaremos un contenedor con Maven para compilar la aplicación

```sh
$ cd My-App-Docker
```

```sh
$ docker run -it --rm -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install
```
- Listo, el contenedor nos generar el directorio Target que contiene el .war

- Debemos copiar el archivo standalone.xml de nuestro servidor local y realizar las siguientes configuraciones

```xml
<datasource jta="true" jndi-name="java:jboss/datasources/app" pool-name="app" enabled="true"                use-java-context="true" use-ccm="true">
                    <connection-url>jdbc:postgresql://app-postgres:5432/app</connection-url>
                    <driver>postgresql-driver</driver>
                    <security>
                        <user-name>postgres</user-name>
                        <password>admin</password>
                    </security>
</datasource>
```

Tener en cuenta el nombre la base de datos 'app' y el host de la base de datos luego veremos por que tiene 'app-postgres'.

- Ahora tenemos que copiar nuestro datasource de postgres en nuestro directorio como se muestra arriba en el repo.

- Creamos nuestro Dockerfile con las capas necesarias para generar nuestra imagen.

```Docker
FROM jboss/wildfly:20.0.1.Final
COPY org /opt/jboss/wildfly/modules/org
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration

ADD My-App-Docker/target/My-App-Docker.war /opt/jboss/wildfly/standalone/deployments/

RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent


EXPOSE 9990
EXPOSE 8080

USER jboss
CMD [ "/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0" ]
```
```Docker
FROM jboss/wildfly:20.0.1.Final
```

Tomaremos la imagen de wildfly:20.0.1.Final como base de la nueva imagen que generaremos.

```Docker
COPY org /opt/jboss/wildfly/modules/org
```
Copiamos nuestra carpeta de los datasources a la carpeta modules del wildfly:20.0.1.Final.

```Docker
COPY standalone.xml /opt/jboss/wildfly/standalone/
```
Reemplazamos el archivo .xml que esta por defecto, por el que contiene nuestra configuración.

```Docker
ADD My-App-Docker/target/My-App-Docker.war /opt/jboss/wildfly/standalone/deployments/
```
Copiamos nuestra aplicación ya compilada a deployments.

```Docker
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent
```
Crearemos el usuario admin.

```Docker
EXPOSE 8080
```
Exponemos el puerto 8080 y 9990 que es de Wildfly

```Docker
USER jboss
```
Con el usuario jboss ejecutamos el servidor

```Docker
CMD [ "/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0" ]
```

- Lo siguiente seria crear un archivo donde generemos nuestra base de datos 'app' al momento de que nuestro conteendor de Postgres inicie por primera vez el archivo se llama 'create-user.sql'.

```sql
CREATE DATABASE app;
```
- Ahora tenemos que crear nuestro docker-compose para generar los contenedores necesarios para que nuestra aplicación entre en funcionamiento.

```Docker
version: '3.8'
services: => se especifican los servico(contenedores)

  app-postgres: => servicio base datos(es el mismo que va en nuestro standalone.xml)
    image: postgres => bajara una imagen de postgres ultima versión
    environment: 
      POSTGRES_PASSWORD: admin => variable de entorno contiene la contraseña de la base.
    volumes:
      - ./create-user.sql:/docker-entrypoint-initdb.d/create-user.sql => archivo con el script para crear la base de datos, cuando inicie el contendor por primera vez.
    ports:
      - "5432:5432" => puerto mapeado del host al contendor.

  app-wildfly: => servicio de nuestro servidor
    build: . => usara la imagen que construira(Dockerfile)
    ports:
      - "80:8080" => puerto mapeado del host al contendor.
  
    depends_on:
        - app-postgres => para crear este servicio depende del servicio anterior.
```

- Finalmente para levantar usamos docker compose.
```sh
$ docker-compose up
```

- Si todo esta bien deberemos ver nuestra aplicacion funcionando y conectada a la base de datos.

# Funcionamiento
![Github Logo](/img/web.png)
Imagen del la aplicación web.

![Github Logo](/img/rest.png)
Imagen prueba del servicio rest desde Postman.







