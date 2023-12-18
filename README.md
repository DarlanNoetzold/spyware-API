# spyware-API
## Development:
* Java 1.8 was used as the base language;
* Developed with Spring Boot;
* PostgreSQL was used as the database;
* Login and JWT token were implemented with Spring Security;
* Documentation was built with OpenAPI and Swagger;
* Use of Redis (noSQL database) to maintain cache data;
* Messaging built with RabbitMQ;
* Use of Docker for project configuration and deployment;
* Currently, a suite of tests is being developed with JUnit and Mockito.

## Project:
* Proof of concept project for the development of malware so that we can learn how to avoid and recognize them;
* This API is part of a larger project called Remote-Analyser, which is a system developed by me, for collecting suspicious data on corporate and/or institutional computers. Thus, serving as a more efficient monitoring of these entities' assets;
* This API Gateway is hosted on Heroku and was developed with Spring Boot using a database (PostgreSQL) to store the collected data. In addition, it has an encrypted login endpoint for better reliability and security of the data. For better usability of the API, one of its endpoints has documentation for an example of the use of each endpoint;
* The application contains cache managed by SpringBoot and saved in a noSQL database called Redis;
* Security based on JWT Tokens also managed by Spring Security;
* The data saved in a PostgreSQL database are consumed by queues managed by the RabbitMQ messaging service in a scalable way.

## How to use:
* The complete application containing all configured microservices can be obtained at [DockerHub](https://hub.docker.com/repository/docker/darlannoetzold/tcc-spyware/general).
* To run it more easily, just execute the following commands:
```
docker container run --platform=linux/amd64 -it -p 8091:8091 -p 8090:8090 -p 5000:5000 -p 9091:9090 -p 3000:3000 --name=app -d darlannoetzold/tcc-spyware:4.0

docker exec -it app service rabbitmq-server start
docker exec -itd app /init-spyware-api.sh
docker exec -itd app /init-remoteanalyser.sh
docker exec -itd app /init-handler-hatespeech.sh
```
---
## Spyware Script:
* GitHub Repository:
<br>Link: [https://github.com/DarlanNoetzold/spyware](https://github.com/DarlanNoetzold/spyware)

---
⭐️ From [DarlanNoetzold](https://github.com/DarlanNoetzold)

