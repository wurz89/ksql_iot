
1) Avviare UN terminale posizionandosi nella cartella dov'è presente il file docker-compose.yml
2) Eseguire l'istruzione docker-compose up
3) Una volta startati tutti i servizi, avviare KSQL aprendo un nuovo terminare ed eseguire il comando --> docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
4) Una volta startato KSQL Lanciare i seguenti comandi: 

CREATE STREAM temp (sensorID VARCHAR, latitude DOUBLE, longitude DOUBLE, altitude DOUBLE) WITH (KAFKA_TOPIC='drone_locations', VALUE_FORMAT='Avro', PARTITIONS=4);

INSERT INTO temp (sensorID, latitude, longitude, altitude) VALUES ('drone321', 37.83, 122.29, 98.2);
INSERT INTO temp (sensorID, latitude, longitude, altitude) VALUES ('drone814', 47.61, 122.33, 18.1);

DROP STREAM temp;

5) Avviare l'applicativo java