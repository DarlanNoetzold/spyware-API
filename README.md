# spyware-API
## Desenvolvimento:
* Foi usado Java 1.8 como linguagem base;
* Foi desenvolvido com Spring Boot;
* O Banco utilizado foi o PostgreSQL;
* O login e o token JWT foram implementados com Spring Security;
* A documentação foi construida com OpenAPI e Swagger;
* Uso de Redis (banco noSQL) para manter dados de cache;
* Mensageiria construída com RabbitMQ;
* Uso do docker para configuração e implantação do projeto;
* Atualmente esta sendo desenvolvido uma bateria de testes com JUnit e Mockito.

## Projeto:
* Projeto de Prova de conceito para o desenvolvimento de malware's para que assim possamos aprender como evitá-los e reconhece-los;
* Esta API faz parte de um projeto maior chamado Remote-Analyser, o qual é um sistema desenvolvido por mim, para coleta de dados suspeitos em computadores empresarias e/ou institucionais. Servindo assim, como um monitoramento mais eficiente do patrimônio destas entidades;
* Este API Gateway está hospedado na Heroku e foi desenvolvido com Spring Boot usando um banco de dados (PostgreSQL) para armazenar os dados coletados. Além disso, ele tem um endpoint para login sendo encriptado para melhor confiabilidade e segurança dos dados. Para a melhor usabilidade da API, um dos endpoints do mesmo tem uma documentação para uma exemplificação de uso de cada endpoint;
* A aplicação contém cache gerenciado pelo SpringBoot e salvo em um banco noSQL chamado Redis;
* Segurança baseada em Tokens JWT também gerenciada pelo Spring Security;
* Os dados são salvos em um banco PostgreSql são consumidos por filas gerenciadas pelo serviço de mensageiria RabbitMQ de maneira escalável.


## Como utilizar:
* A aplicação completa contendo todos os microserviços configurados pode ser obtida no [DockerHub](https://hub.docker.com/repository/docker/darlannoetzold/tcc-spyware/general).
* Para executá-lo de maneira mais fácil basta excutar os seguintes comandos:
```
docker container run --platform=linux/amd64 -it -p 8091:8091 -p 8090:8090 -p 5000:5000 -p 9091:9090 -p 3000:3000 --name=app -d darlannoetzold/tcc-spyware:4.0

docker exec -itd app /init-spyware-api.sh
docker exec -itd app /init-remoteanalyser.sh
docker exec -itd app /init-handler-hatespeech.sh
```
---
## Script do Spyware:
* Repositório no GitHub:
<br>Link: https://github.com/DarlanNoetzold/spyware

---
⭐️ From [DarlanNoetzold](https://github.com/DarlanNoetzold)
