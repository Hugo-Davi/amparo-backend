package com.tatu.amparo.config;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Indexes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexDbConfig implements CommandLineRunner {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Override
    public void run(String... args) throws Exception {
        var mongoClient = MongoClients.create(this.uri);
        var database = mongoClient.getDatabase("mulher");
        var instCollection = database.getCollection("institutes");
        var denounceCollection = database.getCollection("denounces");

        try {
            String resultCreateIndex = instCollection.createIndex(Indexes.geo2dsphere("location"));
            System.out.println(String.format("Index created: " + resultCreateIndex +" at institutes collection"));
        } catch (MongoCommandException e) {
            if (e.getErrorCodeName().equals("IndexOptionsConflict"))
                System.out.println("there's an existing text index with different options");
        }

        try {
            String resultCreateIndex = denounceCollection.createIndex(Indexes.geo2dsphere("location"));
            System.out.println(String.format("Index created: "+ resultCreateIndex +" at denounces collection"));
        } catch (MongoCommandException e) {
            if (e.getErrorCodeName().equals("IndexOptionsConflict"))
                System.out.println("there's an existing text index with different options");
        }
    }
}
