# Spring boot - Hibernate - RESTful API example

# Spring boot - Hibernate - RESTful API example
## API
### Project Struct
#### Struct
```
- sbh-restful
  - src.main.java
    + sbh.rest
      - main
    + sbh.rest.config
      - All configs to spring security
    + sbh.rest.controller
      - Controller mapping request path
    + sbh.rest.entities
      - Object entity mapping to database table
    + sbh.rest.models
      - Object mapping data request to object
    + sbh.rest.services
      - Service jwt, hibernate
  - src.main.resource
    + application.properties
      - config connect database, key seceret jwt, port api,...
  
```
#### Database
```sql
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sex` varchar(1) NOT NULL
)

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) 

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `password` varchar(32) NOT NULL,
  `username` varchar(20) NOT NULL
)

```
### Apis
* API use Authentication and Authorization
* Login get token and use token get data
* API:
  * POST: api/login -> allow all
  * GET: api/profile -> login
  * GET: api/customers -> login
  * POST: api/customers -> ROLE_EDIT
  * PUT: api/customers -> ROLE_EDIT
  * DELETE: api/customers -> ROLE_DELETE
* Add variable "Authorization" with value = "token" (response login) to headers

### Auth
#### 1. Login
* Request: POST http://localhost:9123/api/login
* Request body:
```json
{
    "userName": "admin",
    "password": "admin"
}
```
* Response: status code 200
```
token_login
```
 -> token
### 2. get Profile
* Request: GET http://localhost:9123/api/profile
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Response: status code 200
```json
{
    "userName": "admin",
    "password": "admin",
    "roles": [
        "ROLE_DELETE",
        "ROLE_EDIT"
    ]
}
```
### Customers

#### 1. Get all customer
* Request: GET http://localhost:9123/api/customers 
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Response: status code 200
```json
[
  {
    "id": 6,
    "name": "Tung Nui",
    "age": 23,
    "sex": "1",
    "email": "tungNui1@mail.mu",
    "address": "Quy Nhon"
  },
  ...
]
```

#### 2. Get filter customer
* Request: GET http://localhost:9123/api/customers?name=tung&sex=1
  * Request params: "name" and "sex" 
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Response: status code 200
```json
[
  {
    "id": 6,
    "name": "Tung Nui",
    "age": 23,
    "sex": "1",
    "email": "tungNui1@mail.mu",
    "address": "Quy Nhon"
  },
  ...
]
```
#### 3. Get one cutomer by Id: 
* Requset: GET http://localhost:9123/api/customers/4
  * Request path variable: customerId 
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Response: status code 200
```json
{
    "id": 4,
    "name": "Ga Nui",
    "age": 23,
    "sex": "1",
    "email": "tungNui@mail.mu",
    "address": "Quy Nhon"
}
```
#### 4. Create customer:
* Requset: POST http://localhost:9123/api/customers 
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Requset body:
```json
{
    "name": "Ga Nui",
    "age": 23,
    "sex": "1",
    "email": "tungNui@mail.mu",
    "address": "Quy Nhon"
}
```
* Response: status code 201
```
Created!
```
#### 5. Update customers
* Requset: PUT http://localhost:9123/api/customers/4
  * Request path variable: customerId 
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Requset body:
```json
{
    "name": "Ga Nui",
    "age": 23,
    "sex": "1",
    "email": "tungNui@mail.mu",
    "address": "Quy Nhon"
}
```
* Response: status code 200
```
updated!
```
#### 6. Delete customers
* Requset: DELETE http://localhost:9123/api/customers/4
  * Request path variable: customerId 
* Request headers:
```
...
Authorizatiion: "token_login"
...
```
* Response: status code 200
```
Deleted!
```