BROKERAGE CHALLENGE CASE
---------------------------
**Brokerage Project Tech Definition**

In this project the Technologies were used which is listed below;

• Java 21

• Springboot

• H2 db

• Map Struct for mapping operations(Entity to DTO, DTO to Entity etc...)

• Lombok

• Maven

• Spring security

• Jakarta Controller Validations

• Mockito for unit and integration testing

• Restful endpoints

RUNNING THE PROJECT
-----------------------------
Clone the project from this github link: https://github.com/ilkertpz/brokerage.git

• After clone the project, **run mvn clean install** command

• Run the BrokerageApplication.java class

• The project will run on 8080 port

• For H2 db UI you can visit : http://localhost:8080/h2-console username: **sa** password: **sa**

• By default a **customer** data has been added to the resource path. You can use this data for the endpoint tests

• Endpoints have been authorized with Spring Security. On the RestController endpoints, you will see @PreAuthorize annotations.

• @PreAuthorize annotation shows the authorized roles.

• For the admin role, you can use these credentials: username: **admin** password: **admin**

• For the customer role, you can use these credentials: username: **customer** password: **customer**

NOTE: Basic authentication process has been implemented. JWT would be much more suitable implementation for authentication and authorization but in limited time basic authentication prefered for now





