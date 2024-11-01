# Contributing guidelines

## Architecture considerations

Incident Manager is not considered as super high volume scenario, so we can use Spring Data REST for basic CRUD operations, 
and use customized controllers for more complex business logic.

And there is no need for an event sourcing like architecture for this service.


## Code layering

* **Controller**: Deal with presentation logic, such as data transformation, http code management, delegate to the service layer and handle error thrown in a way that respect http protocol and REST practices.
* **Service**: Deal with business logic, transaction management and cacheing logic. 
