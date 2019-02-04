# Parking meter REST API 
Aim of this project is to build a service for managing the city parking spaces.


### Technologies

* JDK 8
* Embeded H2 databse
* Mapstruct 1.2.0.CR2
* Spring boot starter data jpa
* Spring boot starter web
* Spring boot devtools
* Spring boot starter test



### Charge table

| Ticket type | 1st hour | 2ndhour | 3rd and each next hour|
|---------------|------------|-----------|----------------------------|
|regular			| 1 PLN    | 2 PLN    | 1.5 x more than hour before|
|disabled       | free		  | 2 PLN    | 1.2 x more tahn hour before|
