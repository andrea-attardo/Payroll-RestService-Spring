# A Payroll REST services example with Spring

From this tutorial: https://spring.io/guides/tutorials/rest/

A simple payroll service that manages the employees of a company. 
Employee objects are stored in a (H2 in-memory) database, and access them (via JPA). 
Then wrap that with something that will allow access over the internet (called the Spring MVC layer).

Using Spring HATEOAS eases building RESTful, adding hypermedia links to your service without hard coding. 

After run it you can test it via Postman.com or similar.
