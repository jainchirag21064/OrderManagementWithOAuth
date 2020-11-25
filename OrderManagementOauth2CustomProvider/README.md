# OrderManagement OAuth Provider
Spring Boot Security creating custom OAuth2.0 provider.

# # Project Title/Description
OrderManagement OAuth Provider creates a custom OAuth2.0 using Spring Security

It is a java based backend application exposing the basic spring security OAuth endpoint 
It contains the following endpoints;
-   POST oauth/token (Request for Access Token) 
-   POST oauth/check_token (Check the token.)



Dummy List of Order Details are created in memory on application startup. 
The orderDetails object contains following fields:
-   id (Number),
-   referenceNumber (String), 
-   currentPrice (Amount)
-   totalPrice
-   status
-   lastUpdate (Timestamp).


## Build with
- [Java]()
- [Sping Boot]()
- [Maven]()

## Running the application
- As a JAR
```
java -jar target/OrderManagementOauth2CustomProvider-0.0.1-SNAPSHOT.jar
```

- From IDE
```
As this is a spring boot application this can be executed as a simple java application with main program.
One need to import the soruce code in respective IDE and maven should be able to pull all dependencies required for the application.
Therafter you can look for OrderManagementAuthorizationServerApplication.java and run the main method from there
```

- From MVN
```
mvn spring-boot:run
```

## Docker Build and run
* Build an Image ordermanagementoauth with version 0.0.1-SNAPSHOT
```
docker build -t ordermanagementoauth:0.0.1-SNAPSHOT .
```
* Run a container from the ordermanagementoauth version 0.0.1-SNAPSHOT image, name the running container “ordermanagementoauth” and expose port 8100 externally, mapped to port 8100 inside the container.
```
docker container run --name ordermanagementoauth -p 8100:8100 ordermanagementoauth:0.0.1-SNAPSHOT
```
* Stop a running container through STOP
```
docker container stop ordermanagementoauth
```
* Stop a running container through KILL
```
docker container kill ordermanagementoauth
```

##Note : 
This application by default uses port 8100 if that port is not free you might see a “Port already in use” error.
Either make the port 8100 free or you can change the port in application.properties server.port= <avialable ports>.


## Release Notes
- 0.0.1 Oauth2.0 Basic OAuth Authorizer Server.

