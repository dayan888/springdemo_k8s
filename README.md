# Spring Boot demo application for k8s

* This is a sample application for testing basic configuration of web application that is aimed to run on Kubernetes environment.
* The application consists of the following servers:
  * Web server (using nginx)
  * Application server (using tomcat [embedded in spring boot])
  * DB server (using postgres)
  * Session server (using redis for keeping application server's session)
  * Network Storage (Using PVC Dynamic provisioning or nfs)
* Web server and Application server are stateless, so they can be scaled to multiple pods.
* DB and Session server are statefull. Especially data on DB server must be kept permanently. While session server does not need to be permanent but if the data are lost, it causes poor user experience that means that users must login again and if user enter something to save, it can be lost. But compared to DB, it is not so critical.
* This demo uses single pod for both DB and Session server. If you want to use on production environment, I strongly suggest to use PaaS for both, at least for DB server.
* The application sample is for managing ToDos. It consists only three pages. Just offering function of security and crud of database table. 
  * Login Page
  * Todo List page
  * Todo Edit page
  * Todo contains uploaded images just for testing storage.
* The purpose is to know configuration of k8s, I made the source code as concise as possible, just enough to cover least functions to test. If you apply to product, you might want to consider such as validation, security, maintainability, using framework, and so on.

## Preparation for Local environment

### Prerequisite

The following application must be installed if you want to run and develop the application locally.

* JDK 8
* Docker
* Postgres 9
* Redis
* IntelliJ (or other IDEs)

### DB

* Install postgres and run postgres
* Run the following command and sql to create database and two tables.

```
psql -U postgres demodb
create database demodb;
¥q
```


```
psql -U postgres demodb

create table users (id integer primary key, login character varying(16) not null, password character varying(16) not null);
create unique index users_ux1 on users(login);
create table todos (id integer primary key, title character varying(16) not null, status integer default 0 not null, dt timestamp default now() not null);
create sequence todo_id_seq;

insert into users values(1, 'admin', '111111');
```

* If you install postgres on other than local, then you have to configure host, user and password on the following key in application.yml.
  * spring.datasource

### File

* create directory for saving uploaded images. You can change directory by setting value of app.picDir on application.yml.

```
sudo mkdir /opt/picDir
sudo chmod 777 /opt/picDir
```

### Redis

* If you use redis run on docker, follow the next instruction.

```
docker pull redis
docker run -d -p 6379:6379 --name redis redis:latest
```
* If you use redis on another server, then you have to change the following setting in application.yml.
  * app.redis


### RUN

* Open or Point the following java class on IDE
  src/main/java/net/in/dayan/springdemo/SpringdemoApplication
* Run the Application class
* Or, execute the command:
  * ./gradlew build -x test
  * java -jar build/libs/springdemo-0.5.0.jar
* Open browser and enter http://localhost:8080
* If login page is shown, enter ID and PW (admin/111111)
* Create todos. Enter title and upload file.

## RUN on Docker

```
# create jar
./gradlew build -x test

# image build
docker image build . -t springdemo:latest

# To know redis ip address run on docker
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' redis

# prepare picDir on host mounted from container
mkdir /tmp/picDir
chmod 777 /tmp/picDir

# Run application server on docker (If you run postgres and redis on other than local you have to change env variables)
docker run --name springdemo -d --rm -e "SPRING_PROFILES_ACTIVE=prd"  -e "DB_URL=jdbc:postgresql://host.docker.internal:5432/demodb?user=postgres&password=postgres" -e "PIC_DIR=/opt/picDir" -e "REDIS_HOST=host.docker.internal" -e "REDIS_PORT=6379" -v /tmp/picDir:/opt/picDir -p 8080:8080 -t springdemo:latest

# show log
docker logs -f springdemo

# stop
docker stop springdemo


```


## Configuration for K8S

TBD


