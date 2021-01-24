# Farazpardazan card management
This project contains of two module `card-management-system` and `notification-system`.


### Building
For creating each module JAR file run:
``
  ./mvnw clean package
``

### Running
To run project first we need to have postgres database,Kafka message broker and Zookeeper so make sure you had
installed docker compose on your machine.
then run in project directory :
````
cd docker
docker-compose run
````

After database and message broker service started you can start project with IDE or running it as single jar file.
``
java -jar card-management-system-0.0.1-SNAPSHOT.jar
``
``
java -jar notification-system-0.0.1-SNAPSHOT.jar
``

### Endpoints
The `card-management-system` module will run on port 8081 and here are  end-points exposed in this application and secured with
basic authentication.
username:`javier` and password:`456`

#### POST `/api/cards`
* It creates new card

##### Sample Request
````
curl --location --request POST 'http://localhost:8081/api/cards' \
--header 'Authorization: Basic amF2aWVyOjQ1Ng==' \
--header 'Content-Type: application/json' \
--data-raw '{
"name":"mellat",
"cardNumber":"6219861036585411"

}'
````

#### DELETE `/api/cards/{cardNumber}`
* It delete card.

##### Sample Request
````
curl --location --request DELETE 'http://localhost:8081/api/cards/6219861036585411' \
--header 'Authorization: Basic amF2aWVyOjQ1Ng==' \
--data-raw ''
````

#### GET `/api/cards`
* It get all current user cards.

##### Sample Request
````
curl --location --request GET 'http://localhost:8081/api/cards' \
--header 'Authorization: Basic amF2aWVyOjQ1Ng==' \
--data-raw ''
````


#### POST `/api/transfer/card-to-card`
* It transfer money from card to another one.

##### Sample Request
````
curl --location --request POST 'http://localhost:8081/api/transfer/card-to-card' \
--header 'Authorization: Basic amF2aWVyOjQ1Ng==' \
--header 'Content-Type: application/json' \
--data-raw '{
"sourceCardNumber":"6219861036583456",
"destinationCardNumber":"6219861036585411",
"pin":"1234",
"expirationDate":"2345",
"amount":"10000"
}'
````

#### POST `/api/transfer/report`
* It retrieve transfer reports.
* Accept `from` and `to` parameter.

##### Sample Request
````
curl --location --request GET 'http://localhost:8081/api/transfer/report?from=2012-07-10%2014:58:00&to=2012-07-20%2014:58:00' \
--header 'Authorization: Basic amF2aWVyOjQ1Ng==' \
--data-raw ''
````








