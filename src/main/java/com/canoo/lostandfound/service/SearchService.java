package com.canoo.lostandfound.service;

import com.canoo.lostandfound.model.Country;
import com.canoo.lostandfound.mongodb.dao.categories.CategoryDAO;
import com.canoo.lostandfound.mongodb.dao.listing.ListingDAO;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Path("/searchservice")
public class SearchService {

    @Context
    private HttpServletResponse response;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private ListingDAO listingDAO;


    @OPTIONS
    @Path("/getallCountries")
    public Response getOptionsForGetCountries() {
        return ResponseBuilder.buildCORSResponse();
    }

    @OPTIONS
    @Path("/categories")
    public Response getOptionsForGetCategories() {
        return ResponseBuilder.buildCORSResponse();
    }

    @OPTIONS
    @Path("/categories/{categoryId}/subcategories")
    public Response getOptionsForGetSubCategories() {
        return ResponseBuilder.buildCORSResponse();
    }

    @OPTIONS
    @Path("/listings")
    public Response getOptionsForListing() {
        return ResponseBuilder.buildCORSResponse();
    }

    @GET
    @Path("/getallCountries")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> getOptionsForSearch() {
        String[] locales = Locale.getISOCountries();
        List<Country> countries = new ArrayList<>();
        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);

            Country country = new Country();
            country.setCode(obj.getCountry());
            country.setName(obj.getDisplayCountry());
            countries.add(country);
        }
        ResponseBuilder.addCORSResponseHeaders(response);
        return countries;
    }

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> getCategories() {
        ResponseBuilder.addCORSResponseHeaders(response);
        return categoryDAO.getAllCategories();
    }

    @GET
    @Path("/categories/{categoryId}/subcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> getSubCategories(@PathParam("categoryId") long categoryId) {
        ResponseBuilder.addCORSResponseHeaders(response);
        return categoryDAO.getSubCategories(categoryId);
    }

    @GET
    @Path("/listings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> getListings() {
        ResponseBuilder.addCORSResponseHeaders(response);
        return listingDAO.getAllListings();
    }
}
