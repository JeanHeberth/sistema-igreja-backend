package com.igreja.adapters.web.support.dataBase;

import com.mongodb.client.MongoClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TestDatabaseCleaner {

    @Inject
    MongoClient mongoClient;

    public void limparBanco() {
        var database = mongoClient.getDatabase("sistema-igreja");

        database.listCollectionNames().forEach(collection -> {
            database.getCollection(collection).deleteMany(new org.bson.Document());
        });
    }
}