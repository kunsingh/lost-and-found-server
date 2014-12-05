package com.canoo.lostandfound.mongodb.dao.categories;

import java.util.ArrayList;
import java.util.List;

import com.canoo.lostandfound.model.Category;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Convert Google returned json result to java object.  
 *
 */
public class JsonConverter {

	public static List<Category> convertJsonToObjects(String json) {
		System.out.println(json);
		JsonParser parser = new JsonParser();
		
		JsonArray jArray = (JsonArray) parser.parse(json);
		List<Category> categories = new ArrayList<Category>();
		if (jArray != null && jArray.size() > 0) {
			for (JsonElement obj : jArray) {
				Category category = new Gson().fromJson(obj, Category.class);
				categories.add(category);
			}
		}
		return categories;
	}
}
