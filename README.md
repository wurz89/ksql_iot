# KSQl

Di seguito le istruzioni per poter avviare 2 esempi differenti che mostrano le potenzialità e caratteristiche di KSQL, una nuova forma per concepire la persistenza.

KSQL è un motore di elaborazione stream SQL per Apache Kafka.
Permette di scrivere query SQL per analizzare e trasformare i dati in tempo reale che fluiscono su Kafka.

<span style="text-align: center; color: green;"> --------------------- Istruzioni per eseguire le classi nel package example ---------------------</span>

1) Avviare un terminale posizionandosi nella cartella dov'è presente il file docker-compose.yml
2) Avviare i servizi docker con il comando:
```
docker-compose up
```
3) Una volta startati tutti i servizi, avviare KSQL aprendo un nuovo terminare ed eseguire il comando:
```
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
```
4) Una volta startato KSQL Lanciare i seguenti comandi: 
```
CREATE STREAM temp (sensorID VARCHAR, latitude DOUBLE, longitude DOUBLE, altitude DOUBLE) WITH (KAFKA_TOPIC='drone_locations', VALUE_FORMAT='Avro', PARTITIONS=4);
```
```
INSERT INTO temp (sensorID, latitude, longitude, altitude) VALUES ('drone321', 37.83, 122.29, 98.2);
INSERT INTO temp (sensorID, latitude, longitude, altitude) VALUES ('drone814', 47.61, 122.33, 18.1);
```

```
DROP STREAM temp;
```
5) Avviare l'applicativo java


<span style="text-align: center; color: green;">--------------------- Istruzioni per eseguire la classe nel package example2 ---------------------
</span>


1) Avviare un terminale posizionandosi nella cartella dov'è presente il file docker-compose.yml
2) Avviare i servizi docker con il comando:
```
docker-compose up
```
3) Una volta startati tutti i servizi, avviare un terminale sul servizio broker per farlo, sempre aprire un nuovo terminare ed eseguire il comando:
```
docker exec -it broker bash
```
4) Dal teminale del servizio broker, occorre creare un nuovo topic, per farlo, lanciare il comando:
```
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic USERS
```
5) Inserire gli elementi eseguendo il comando :
```
kafka-console-producer --bootstrap-server localhost:9092 --topic USERS
```
6) Successivamente inserire i valori:
```
   Deepa,Ind
   Prateek,USA
   Bob,UK
```
7) Avviare KSQL aprendo un nuovo terminare ed eseguire il comando:
```
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
```
8) Creare un nuovo stream con il comando:
```
create stream users_stream (name VARCHAR, countrycode VARCHAR) WITH (KAFKA_TOPIC='USERS', VALUE_FORMAT='DELIMITED');
```
9) Avviare il comando:
```
select * from users_stream; 
```
questo comando mostrerà i dati presenti in tabella, quelli inseriti nel topic del broker

10) Avviare il comando:
```
select * from users_stream emit changes; 
```
(questo comando lancerà una query che sarà in continua esecuzione e da questo momento in poi, se verranno eseguiti nuovi inserimenti sul topic, verrano subito mostrati nei risultati di questa query)
11) Avviare da java la classe ExampleWithKafkaTopic
12) Tornare nel terminale del broker e aggiungere nuovi elementi, gli stessi saranno mostrati nel terminale java e nel terminale di ksql