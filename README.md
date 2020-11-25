# OrderManagement with OAuth 
Spring Boot application with security OAuth2.0.

# # Project Title/Description
Project consist of three sub projects

#### OrderManagementOauth2CustomProvider
This is to create a custom OAUTH server which will be responsible to issue access token.
- This will create two in memory user "admin" and password "admin" with role of type ADMIN  
- This will create two in memory user "user" and password "user" with role of type USER
- It will also expose a Rest API to get access token on port 8100
 
#### OrderManagementOauth2RestApplicationCustomProvider
This is the rest application exposing CRUD operations for Order Management secured with OAuth2.0 from custom provider. 
- This Rest API's are secured with OAUTH2.0 and can be access with valid access token and role  
- You can acquire the access token using below instruction
```
curl --location --request POST 'http://localhost:8100/oauth/token?grant_type=client_credentials'
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```
Above Authorization is Base encoding of admin:admin or user:user

#### OrderManagementOauth2RestApplicationExternalProvider
This is the rest application exposing CRUD operations for Order Management secured with OAuth2.0 from external Otka provider. 
- This Rest API's are secured with OAUTH2.0 and can be access with valid access token and role  
- You can acquire the access token using from below link and provide the details
```
https://oidcdebugger.com/
```

#### OrderManagementOauth2PostmanTestScripts
This is the postman colles=ction of test scripts for the CRUD opertion of Order Management 
- Import the collection in Postman Tool
- You can also run the collection using below newman command 
```
newman run OrderManagement.postman_collection.json -e SecuredOrderManagementAPI.postman_environment.json
```

