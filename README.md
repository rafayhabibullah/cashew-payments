# cashew-payments
RESTful API implementation for money transfers in Spring Boot

## Execution
```
mvn clean package
java -jar target/cashew-payments-0.0.1-SNAPSHOT.jar
```

## Testing
```
mvn test
```

## Data
accounts-mock.json used to import data in H2 Database at startup.

## Features
Application provides APIs for following 2 features

- Retrieve Accounts service /accounts
- Transfer Service /transfer


## APIs
By default it will be executed over localhost:8080
```

- @GET http://{{host}}:{{port}}/accounts

Response:
[
  ...
  {
      "id": "3d253e29-8785-464f-8fa0-9e4b57699db9",
      "name": "Trupe",
      "balance": 1033.26
  },
  {
      "id": "17f904c1-806f-4252-9103-74e7a5d3e340",
      "name": "Fivespan",
      "balance": 0.00
  }
  ...
]

- @POST http://{{host}}:{{port}}/transfer

Request:
{
  "transferer": "{{transferrerId}}",
  "transferee": "{{transfereeId}}",
  "amount": {{amount}}
}
Response: 
Transferrer updated account balance after transfer
{
    "id": "{{transferrerId}}",
    "name": "{{transferrerName}}",
    "balance": {{transferrerBalance}}
}
```

