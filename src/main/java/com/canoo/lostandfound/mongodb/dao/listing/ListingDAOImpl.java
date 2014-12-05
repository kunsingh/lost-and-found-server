package com.canoo.lostandfound.mongodb.dao.listing;

import com.canoo.lostandfound.model.SearchCriteria;
import com.canoo.lostandfound.mongodb.helper.DAOHelper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Component
public class ListingDAOImpl implements ListingDAO {

    private static final String LISTING_COLLECTION_NAME = "listing";
    @Autowired
    private static DAOHelper daoHelper = new DAOHelper();
    private static DB mongoDB = daoHelper.getMongoDB();

    private static void insertJson(String json) {
        Object jsona = JSON.parse(json);
        BasicDBList basicDBList = (BasicDBList) jsona;
        Iterator<Object> iterator = basicDBList.iterator();
        while (iterator.hasNext()) {
            mongoDB.getCollection(LISTING_COLLECTION_NAME).insert((BasicDBObject) iterator.next());
        }
    }

    private static DBCollection getCollection(String collectionName) {
        return mongoDB.getCollection(collectionName);
    }

    @Override
    public List<DBObject> getAllListings() {
        if (getCollection(LISTING_COLLECTION_NAME).getCount() == 0) {
            insertJson(daoHelper.readFile(LISTING_COLLECTION_NAME));
        }
        StringBuilder json = new StringBuilder();
        DBCursor cursor = getCollection(LISTING_COLLECTION_NAME).find(new BasicDBObject(), new BasicDBObject("_id", 0));
        return cursor.toArray();
    }

    @Override
    public List<DBObject> getListing(SearchCriteria searchCriteria) {
        if (getCollection(LISTING_COLLECTION_NAME).getCount() == 0) {
            insertJson(daoHelper.readFile(LISTING_COLLECTION_NAME));
        }
        DBCollection listingCollection = getCollection(LISTING_COLLECTION_NAME);
        BasicDBList query = new BasicDBList();

        if (searchCriteria.getCategoryId() > 0) {
            query.add(new BasicDBObject("categoryId", searchCriteria.getCategoryId()));
        }
        if (searchCriteria.getSubCategoryId() > 0) {
            query.add(new BasicDBObject("subCategoryId", searchCriteria.getSubCategoryId()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getCountryCode())) {
            query.add(new BasicDBObject("countryCode", searchCriteria.getCountryCode()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getDate())) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            query.add(new BasicDBObject("date", formatter.format(DateUtils.parseDate(searchCriteria.getDate(), new String[]{"dd.MM.yyyy"}))));
        }
        if (StringUtils.isNotBlank(searchCriteria.getPlace())) {
            query.add(new BasicDBObject("place", new BasicDBObject("$regex", ".*" + searchCriteria.getPlace() + ".*")));
        }
        if (searchCriteria.isLost()) {
            query.add(new BasicDBObject("lost", true));
        } else {
            query.add(new BasicDBObject("lost", false));
        }
        DBCursor cursor = listingCollection.find(new BasicDBObject("$and", query), new BasicDBObject("_id", 0));
        return cursor.toArray();
    }
}
