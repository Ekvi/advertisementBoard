package com.shribak.board.model;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Categories {
    private Logger logger = Logger.getLogger(Categories.class);
    private final Properties properties;
    private String[] categories;
    private String path = "/sections.properties";
    private static Categories instance;

    private Categories() {
        properties = new Properties();

        try {
            InputStream is = getClass().getResourceAsStream(path);
            properties.load(is);
        } catch (IOException e) {
            logger.error("Can't load categories");
        }

        categories = new String[properties.stringPropertyNames().size()];

        for(String key : properties.stringPropertyNames()) {
            int index = Integer.parseInt(properties.getProperty(key));
            categories[index-1] = key;
        }
    }

    public static Categories getInstance() {
        if (instance == null) {
            instance = new Categories();
        }
        return instance;
    }

    public String getCategory(String category) {
        int index = Integer.parseInt(category);
        return categories[index-1];
    }
}
