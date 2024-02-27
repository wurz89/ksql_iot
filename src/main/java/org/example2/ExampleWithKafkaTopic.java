package org.example2;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ClientOptions;
import io.confluent.ksql.api.client.Row;
import io.confluent.ksql.api.client.StreamedQueryResult;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ExampleWithKafkaTopic {

    public static String KSQLDB_SERVER_HOST = "0.0.0.0";
    public static int KSQLDB_SERVER_HOST_PORT = 8088;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Map<String, Object> properties = Collections.singletonMap(
                "auto.offset.reset", "earliest"
        );

        ClientOptions options = ClientOptions.create()
                .setHost(KSQLDB_SERVER_HOST)
                .setPort(KSQLDB_SERVER_HOST_PORT)
                .setUseTls(false)
                .setUseAlpn(true);
        Client client = Client.create(options);

        String pullQuery = "select name, countrycode from USERS_STREAM emit changes;";

        StreamedQueryResult streamedQueryResult = client.streamQuery(pullQuery, properties).get();

        while
        (true){
            Row row = streamedQueryResult.poll();
            if (row != null) {
                System.out.println("Received a row!");
                System.out.println("Row: " + row.values());
            } else {
                System.out.println("Query has ended.");
            }
        }
    }
}