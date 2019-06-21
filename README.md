# Cashier Tech Test

## Requirements:
1. Must use Java
2. Should use a build & dependency management tool e.g. Maven or Gradle.
3. We should be able to compile and run your tests e.g. mvn clean verify

## Problem:
Develop a Registration Service that implements a /register endpoint taking a JSON body.

### Required data & validation:
1. Username - alphanumeric, no spaces
2. Password – min length 4, at least one upper case letter & number
3. DoB (Date of Birth) - ISO 8601 format
4. Payment Card Number – between 15 and 19 digits

### Expected responses:
1. If the request body fails to conform to any of the basic validation checks return HTTP Status code: 400
2· Reject registrations if the user is under the age of 18 and return HTTP Status code: 403
3· If the username has already been used reject the request and return HTTP Status code: 409
4· A successful registration should return HTTP Status code: 201
5. On start-up allow a list of blocked payment issuer identification numbers to be provided. The issuer identification number (IIN) is the first 6 digits of the payment card’s number. If the IIN is blocked registration should fail returning HTTP Status code: 406


# Build
Application has been built using **Maven**, **java8** and **SpringBoot**. and requires **JDK8** and **Maven**

### Build command that needs to be executed from project root:
`maven clean install` 

This action will compile, run all the unit and integration tests and generate the **cashier.jar** artifact inside **/target** folder.


# Runtime
**cashier.jar.jar** has been built to be runnable as:
 
1. stand alone java application using the command:  `java -jar target/cashier.jar`
2. run it in a **docker** (look at [*Docker Commands*](#docker-commands) section)
3. from any Java development IDE like Eclipse. Runnable class: **com.cashier.application.CashierApplication** 
4. The list of IIN blocked can be provided through **config-cashier.properties** at the same directory level as **cashier.jar**, 
setting the property **card.issuer.blocked.list** with the IIN values separated by comma e.g: `card.issuer.blocked.list=123456,789123`

### Logging
Application logs will be generated inside **/logs** folder at the same directory level of the **cashier.jar**  
Log file pattern name is: **cashier-apl-{yyyy-MM-dd}-{sequencial}.log** 


# API Swagger
#### JSON: {{host}}:8080/v2/api-docs
#### HTML: {{host}}:8080/swagger-ui.html
 

# Application Testing

Once running, the application can be tested using either any REST client as Postman or from HTML Swagger page **{{host}}:8080/swagger-ui.html**

### Example request:

```

curl -X POST \
http://127.0.0.1:8080/register \
-H 'Content-Type: application/json' \
-d '{
"username": "BobFrench",
"password": "Password1",
"dob": "1980-02-21",
"paymentCardNumber": "349293081054422"
}'

```

# Persistent layer

In order to be easily runnable, this version is using in memory data repository.    
For scalable production environment, it is strongly recommended to use some third part database. 
This can be a no-sql database due the data format, for example: MongoDB, Google Datastore, Google BigTable, etc...


# Docker Commands

Those are useful docker commands and pre-requires docker installation.
They should be run from project root.
 
### Build and register docker image from jar
`docker build -f Dockerfile -t "cashier:v1" .`

### run docker image in container
`docker run  -p 8080:8080 --name cashier  -t "cashier:v1"` 

### remove running application from docker
`docker rm -f cashier`

### remove docker image from docker repository
```
docker images
docker rmi {{IMAGE ID}}
```

