# OrderManagement API using Custom Oauth2.0 Provider
Spring Boot based REST API of OrderManagement Application with OAUTh2.0 using custom OAuth2.0 provider.

# # Project Title/Description
OrderManagement Rest Application with OAuth2.0

It is a java based backend application using REST. 
It contains the following endpoints;
-   GET  api/v1/orders (Get a list of orders details) 
-   GET  api/v1/order/1 (Get an order detail)
-   POST api/v1/orders (Create an Order Details) 
-   PUT  api/v1/orders/1/status/DELIVERED (Update the status of an order)
-   DELETE  api/v1/orders/1 (Delete an order)



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

## Running the server locally
- First build the package
```
mvn package
```
- To run the app from a command line in a Terminal window you can you the java -jar command. This is provided your Spring Boot app was packaged as an executable jar file.

```
java -jar target/OrderManagementResource_CustomProvider-0.0.1-SNAPSHOT.jar
```
- You can also use Maven plugin to run the app.
 ```
 mvn spring-boot:run
 ```
## Running the server in Docker Container
* Build an Image ordermanagement with version 0.0.1-SNAPSHOT
```
docker build -t ordermanagement:0.0.1-SNAPSHOT .
```
* Run a container from the ordermanagement version 0.0.1-SNAPSHOT image, name the running container “ordermanagement” and expose port 9090 externally, mapped to port 9090 inside the container.
```
docker container run --name ordermanagement -p 9090:9090 savingtargets:0.0.1-SNAPSHOT
```

##Swagger Rest Specification :
- [Order Management API](https://github.com/jainchirag21064/OrderManagementWithOAuth/blob/master/OrderManagementAPI.yml)

##Note : 
This application by default uses port 9090 if that port is not free you might see a “Port already in use” error.
Either make the port 9090 free or you can change the port in application.properties server.port= <avialable ports>.


## Release Notes
- 0.0.1 Basic CRUD operations to get,add,update and cancel orderDetails.

