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
| `GET` | `/start/{ticket_type}/{nr_plate}`  |  Starts parking meter with given ticket_type and nr_plate| ticket_type: String "regular"/"disabled", nr_plate: 5 char String	|Ticket           |
| `GET`     | `/stop/{id}`                           |  Stops parking  meter/ stops ticket with  given id    		|	id: Long 			|String			|
| `GET`      | `/check_charge/{id}`              |  Checks charge for exact ticket with given id       			|	id: Long				|String			|
| `GET` 	|  ` /sum`								   |  Returns sum of all tickets charges									|	- 						|SumJSON		|
| `GET`     | `/hasStarted/{nr_plate}` 		   |  Checks if car with given nr_plate started parking meter 	|	nr_plate: 5 char String |HasStartedJSON |


## Return format
### Start ticket
#### Ticket

Values here are:

* ticketType can be REGULAR or DISABLED only
* charge is calculated according to charge table
* stampStart is start time
* id  - duh

```
{
    "ticketType": "REGULAR",
    "charge": 0,
    "stampStart": "2019-01-20T18:02:39.568",
    "id": 9
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
    "charge": 0,
    "stampStart": null,
    "id": null
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
|Ticket doesnot exist  	| TICKET DOES NOT EXIST | 400					  |
|Fine, ticket does exist       | Double value as String    | 200					|

### Check sum
#### SumJSON

```
{
    "sum": 34.18
}
```
In case there is no value field "sum" has null value. Always HTTP 200 status.


### Check if has started
#### HasStartedJSON

Values here can be:
* YES if the car with given nr_plate has started the ticket.
* NO  if the car with given nr_plate has not started the ticket OR there is no such car.
* INVALID_NR_PLATE if nr_plate is invalid.

```
{
    "hasStarted": "NO"
}
```

| Case                           | Value returned                   | HTTP status |
|----------------------------- |----------------------------------|-----------------|
|Invalid number	| HasStartedJSON.INVALID_NR_PLATE| 400	     |
|Fine, ticket has started      | HasStartedJSON.YES   | 200					 |
|Fine, ticket has not started      | HasStartedJSON.NO   | 200					 |



### Charge table

| Ticket type | 1st hour | 2ndhour | 3rd and each next hour|
|---------------|------------|-----------|----------------------------|
|regular			| 1 PLN    | 2 PLN    | 1.5 x more than hour before|
|disabled       | free		  | 2 PLN    | 1.2 x more tahn hour before|
