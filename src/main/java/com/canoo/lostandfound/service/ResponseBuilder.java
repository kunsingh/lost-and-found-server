package com.canoo.lostandfound.service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 * Set required response headers for cross origin request
 *
 * @author Kunal  Singh
 */
public class ResponseBuilder {

    /**
     * Response header settings for OPTIONS call to filter CORS
     *
     * @return {@link javax.ws.rs.core.Response}
     */
    public static Response buildCORSResponse() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "X-Requested-With,X-Prototype-Version,Content-type,Cache-Control,Pragma,Origin")
                .build();
    }

    /**
     * HttpServletResponse header settings for CORS
     *
     * @param response {@link javax.servlet.http.HttpServletResponse}
     */
    public static void addCORSResponseHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, UPDATE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,X-Prototype-Version,Content-type,Cache-Control,Pragma,Origin");
    }

}
