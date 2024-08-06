# Users service

Servicio para administrar usuarios.

### Funcionalidades:

- Registrar usuario
- Login de usuario

### Requisitos

- Java 11
- Maven 3.9.7

### Instalación
Ejecutar el siguiente comando:

```
./mvnw clean package
```

### Ejecución
Ejecutar el siguiente comando:

```
java -jar target/users-service-1.0.0.jar
```

### Endpoints

####  Registrar usuario

```
curl 'http://localhost:8080/api/users/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "John Doe",
    "email": "johndoe@domain.com",
    "password": "qAsnm1w2",
    "phones": [
        {
            "number": 119998877,
            "citycode": 1901,
            "countrycode": "0054"
        }
    ]
}'
```
```
curl 'http://localhost:8080/api/users/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "John Doe"
}'
```
```
curl 'http://localhost:8080/api/users/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "John Doe",
    "email": "johndoe_domain.com",
    "password": "A12Bnmwewbgh"
}'
```
```
curl 'http://localhost:8080/api/users/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "johndoe@domain.com",
    "password": "A12snmwewbgh"
}'
```

####  Login de usuario
```
curl 'http://localhost:8080/api/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "johndoe@domain.com",
    "password": "qAsnm1w2"
}'
```
```
curl 'http://localhost:8080/api/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "johndoe@domain.com",
    "password": "A12snmwewbgh"
}'
```