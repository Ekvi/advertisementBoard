package com.shribak.board.utils;


import com.shribak.board.model.Advertisement;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code JsonProvider} class provides reading and writing advertisements
 * to json file.
 *
 * @author Dmitrii Shribak
 */
public class JsonProvider implements FileProvider {
    private Logger logger = Logger.getLogger(JsonProvider.class);
    private String jsonPath;

    /**
     * Reads advertisements from json file.
     *
     * @param jsonPath  path to json file
     * @return list of advertisements
     */
    @Override
    public List<Advertisement> read(String jsonPath) {
        this.jsonPath = jsonPath;

        ObjectMapper mapper = new ObjectMapper();
        List<Advertisement> advertisements = new ArrayList<Advertisement>();

        try {
            advertisements = mapper.readValue(new File(jsonPath),
                    mapper.getTypeFactory().constructCollectionType(List.class, Advertisement.class));

        } catch (JsonGenerationException e) {
            logger.error("Can't read json file");
        } catch (JsonMappingException e) {
            logger.error("Can't read json file");
        } catch (IOException e) {
            logger.info("All information will be store in the " + jsonPath + " file.");
        }

        return advertisements;
    }

    /**
     * Writes list of advertisements to json file.
     *
     * @param advertisements  list of advertisements
     */
    @Override
    public void write(List<Advertisement> advertisements) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(jsonPath), advertisements);
        } catch (IOException e) {
            logger.error("Can't write advertisements to file");
        }
    }
}
