package com.app.cryptography.service.common;

import com.google.gson.Gson;

public class JsonRequestService {

    static Gson gson = new Gson();

   public static String toJsonMethod(Object model) {
        String jsonString = gson.toJson(model);
        return jsonString;
    }

    public static Object fromJsonMethod(String jsonString, Class className) {
        Object object = gson.fromJson(jsonString, className);
        return object;
    }
}
