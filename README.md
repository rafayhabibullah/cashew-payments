# cashew-payments
RESTful API implementation for money transfers in Spring Boot

## Packaging
```
mvn clean package
```

## Testing
```
mvn test
```

## Execution
```
java -jar demo-0.0.1-SNAPSHOT.jar
```

## Data
accounts-mock.json used to import data in H2 Database at startup.

## Features
Application provides APIs for following 2 features

- Retrieve Accounts service /accounts
- Transfer Service /transfer


## APIs
- http://{{host}}:{{port}}/accounts
- http://{{host}}:{{port}}/transfer
