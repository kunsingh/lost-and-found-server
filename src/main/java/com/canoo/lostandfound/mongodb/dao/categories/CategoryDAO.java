package com.canoo.lostandfound.mongodb.dao.categories;

import com.mongodb.DBObject;

import java.util.List;

public interface CategoryDAO {
    List<DBObject> getAllCategories();

    List<DBObject> getCategory(long categoryId);

    List<DBObject> getSubCategories(long categoryId);
}
