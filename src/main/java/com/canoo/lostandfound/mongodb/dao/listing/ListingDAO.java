package com.canoo.lostandfound.mongodb.dao.listing;

import com.canoo.lostandfound.model.SearchCriteria;
import com.mongodb.DBObject;

import java.util.List;

public interface ListingDAO {
    List<DBObject> getAllListings();

    List<DBObject> getListing(SearchCriteria searchCriteria);
}
