package com.canoo.lostandfound.mongodb.helper;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

@Component
public class DAOHelper {

    private static MongoClient mongoClient;
    private static DB mongoDB;
    private static String databaseName = "test";


    public static MongoClient getMongoClient() throws UnknownHostException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }
        return mongoClient;
    }

    public static DB getMongoDB() {
        try {
            if (mongoDB == null) {
                mongoDB = getMongoClient().getDB(databaseName);
            }
        } catch (UnknownHostException uhe) {

        }
        return mongoDB;
    }

    public String readFile(String fileName) {
        StringBuilder json = new StringBuilder();
        BufferedInputStream bufferedReader = new BufferedInputStream(getClass().getResourceAsStream("/" + fileName + ".json"));
        int c;
        try {
            while ((c = bufferedReader.read()) != -1) {
                json.append((char) c);
            }
        } catch (IOException ioe) {
            System.out.println("IOException" + ioe);
        }
        return json.toString();
    }
}
