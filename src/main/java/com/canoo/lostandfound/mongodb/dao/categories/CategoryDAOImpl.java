package com.canoo.lostandfound.mongodb.dao.categories;

import com.canoo.lostandfound.mongodb.helper.DAOHelper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class CategoryDAOImpl implements CategoryDAO {

    private static final String CATEGORY_COLLECTION_NAME = "categories";
    private static DB mongoDB = DAOHelper.getMongoDB();
    private static DBCollection categoriesCollection = mongoDB.getCollection(CATEGORY_COLLECTION_NAME);
    private DAOHelper daoHelper = new DAOHelper();

    {
        populateCategories();
    }

    @Override
    public List<DBObject> getAllCategories() {
        StringBuilder json = new StringBuilder();
        if (isCollectionEmpty()) {
            populateCategories();
        }
        DBCursor cursor = categoriesCollection.find(new BasicDBObject(), new BasicDBObject("_id", 0));
        return cursor.toArray();
    }

    @Override
    public List<DBObject> getCategory(long categoryId) {
        StringBuilder json = new StringBuilder();
        if (isCollectionEmpty()) {
            populateCategories();
        }
        DBCursor cursor = categoriesCollection.find(new BasicDBObject(), new BasicDBObject("_id", 0));
        return cursor.toArray();
    }

    @Override
    public List<DBObject> getSubCategories(long categoryId) {
        StringBuilder json = new StringBuilder();
        if (isCollectionEmpty()) {
            populateCategories();
        }
        DBCursor cursor = categoriesCollection.find(new BasicDBObject("id", categoryId), new BasicDBObject("_id", 0));
        return cursor.toArray();
    }

    private boolean isCollectionEmpty() {
        return categoriesCollection.getCount() == 0;
    }

    private void populateCategories() {
        if (mongoDB.getCollection(CATEGORY_COLLECTION_NAME).getCount() == 0) {
            insertJson(daoHelper.readFile(CATEGORY_COLLECTION_NAME));
        }
    }

    private void insertJson(String json) {
        Object jsona = JSON.parse(json);
        BasicDBList basicDBList = (BasicDBList) jsona;
        Iterator<Object> iterator = basicDBList.iterator();
        while (iterator.hasNext()) {
            mongoDB.getCollection(CATEGORY_COLLECTION_NAME).insert((BasicDBObject) iterator.next());
        }
    }

    private DBCollection getCollection(String collectionName) {
        return mongoDB.getCollection(collectionName);
    }
}
