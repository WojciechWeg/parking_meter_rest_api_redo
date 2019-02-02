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

### Setup

```
chmod +x ./start.sh
./start.sh
```

### Action table

| Method     | URI                               | Action                                                							    |  Input format | Return format | 
|------------|-----------------------------------|-------------------------------------------------------------------------|------------------|-------------------|
| `POST` | `/tickets`  |  Starts parking meter with given ticket_type and nr_plate|  JSON: carNumberPlate : 5 char String, ticketType: String "regular"/"disabled"	|TicketDTO           |
| `PUT`     | `/tickets/{id}`                           |  Stops parking  meter/ stops ticket with  given id    		|	id: Long 			|String			|
| `GET`      | `/tickets/{id}/charge`              |  Checks charge for exact ticket with given id       			|	id: Long				|BigDecimal			|
| `GET` 	|  ` /tickets/sum`								   |  Returns sum of all tickets charges									|	- 						|BigDecimal		|
| `GET`     | `/cars/{nrPlate}/hasStarted` 		   |  Checks if car with given nr_plate started parking meter 	|	nrPlate: 5 char String |boolean |


### Start ticket
#### Ticket

Example input values:

```
{
"carNumberPlate" : "WN999",
"ticketType" : "REGULAR"
}
```


Output values here are:

* ticketType can be REGULAR or DISABLED only
* stampStart is start time
* carNumberPlate
* id  - duh

```
{
    "ticketType": "REGULAR",
    "stampStart": "2019-02-02T13:03:23.738",
    "id": 9,
    "carNumberPlate": "WN999"
}
```


| Case                           | Value returned                   | HTTP status |
|----------------------------- |----------------------------------|-----------------|
|Fine, new ticket				| Ticket								 | 200			  |
|Invalid nr_plate				| Null ticket						 | 400			  |
|Invalid ticket type			| Null ticket						 | 400			  |
|Ticket alredy started		| Null ticket						 | 226			  |

Null ticket:
```
{
    "ticketType": null,
    "stampStart": null,
    "id": null,
    "carNumberPlate": null
}
```


### Stop ticket
#### String

| Case                           | Value returned                   | HTTP status |
|----------------------------- |----------------------------------|-----------------|
|Fine, ticket already stopped	| TICKET ALREADY STOPPED  | 200			  |
|Ticket does not exist      | TICKET DOES NOT EXIST     | 400			  |
| Fine, ticket stopped		| TICKET STOPPED				 | 200			  |


### Check charge
#### String

| Case                           | Value returned                   | HTTP status |
|----------------------------- |----------------------------------|-----------------|
|Ticket does not exist  	| -1 | 400					  |
|Fine, ticket does exist       | BigDecimal    | 200					|


### Charge table

| Ticket type | 1st hour | 2ndhour | 3rd and each next hour|
|---------------|------------|-----------|----------------------------|
|regular			| 1 PLN    | 2 PLN    | 1.5 x more than hour before|
|disabled       | free		  | 2 PLN    | 1.2 x more tahn hour before|
