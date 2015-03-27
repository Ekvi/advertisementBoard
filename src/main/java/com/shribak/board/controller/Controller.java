package com.shribak.board.controller;


import com.shribak.board.model.Advertisement;
import com.shribak.board.model.Categories;
import com.shribak.board.utils.DateConverter;
import com.shribak.board.utils.FileProvider;
import com.shribak.board.utils.ProviderFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code Controller} class is a provider between view and model.
 * It finds advertisements by name, by category, add new advertisement,
 * update existing advertisement, and remove.
 *
 * @author Dmitrii Shribak
 */
public class Controller {
    //private String fileName = "advertisements.json";
    private String fileName = "advertisements.xml";
    private Path path;
    private ProviderFactory factory = new ProviderFactory();
    private FileProvider provider;
    private List<Advertisement> advertisements;


    public Controller() {
        advertisements = new ArrayList<Advertisement>();
        path = Paths.get(System.getProperty("user.home") + "/.board");
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        provider = factory.getProvider(extractExtension(path.toString() + "/" + fileName));
    }

    /**
     * Writes advertisements to file.
     */
    public void writeAdvertisementsToFile() {
        provider.write(advertisements);
    }

    /**
     * Reads advertisement from file.
     */
    public void readAdvertisementsFromFile() {
        //advertisements = provider.read(path);
        advertisements = provider.read(path.toString() + "/" + fileName);
    }

    /**
     * Extracts extension from the file name.
     *
     * @param path path to file
     * @return extension
     */
    private String extractExtension(String path) {
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }

    /**
     * Finds advertisements by name.
     *
     * @param name user name
     * @return list of advertisements with such name
     */
    public List<Advertisement> getAdvertisementsByName(String name) {
        List<Advertisement> userAdvertisements = new ArrayList<Advertisement>();

        for(Advertisement advertisement : advertisements) {
            if(advertisement.getAuthor().equalsIgnoreCase(name)) {
                userAdvertisements.add(advertisement);
            }
        }
        return userAdvertisements;
    }

    /**
     * Finds advertisements in given category.
     *
     * @param category category name
     * @return list of advertisements in given category
     */
    public List<Advertisement> getAdvertisementsByCategory(String category) {
        List<Advertisement> categoryAdvertisements = new ArrayList<Advertisement>();

        category = Categories.getInstance().getCategory(category);

        for(Advertisement advertisement : advertisements) {
            if(advertisement.getCategory().equals(category)) {
                categoryAdvertisements.add(advertisement);
            }
        }
        return categoryAdvertisements;
    }

    /**
     * Adds new advertisement to collection.
     *
     * @param name  user name
     * @param title  title of new advertisement
     * @param content  content of new advertisement
     */
    public void addAdvertisement(String name, String category, String title, String content) {
        long date = System.currentTimeMillis();
        String categoryName = Categories.getInstance().getCategory(category);

        advertisements.add(new Advertisement(name, date, categoryName, title, content));
    }

    /**
     * Finds advertisements by title.
     *
     * @param name user name
     * @param title title name
     * @return null if there is no advertisement with such title
     */
    public Advertisement findByTitle(String name, String title) {
        for(Advertisement adv : advertisements) {
            if(adv.getAuthor().equalsIgnoreCase(name) && adv.getTitle().equalsIgnoreCase(title)) {
                return adv;
            }
        }
        return null;
    }

    /**
     * Update content and date of existing advertisement.
     *
     * @param advertisement advertisement needs to update
     */
    public void updateAdvertisement(Advertisement advertisement) {
        Advertisement old = findByTitle(advertisement.getAuthor(), advertisement.getTitle());
        removeAdvertisement(old);

        advertisement.setDate(System.currentTimeMillis());
        advertisements.add(advertisement);
    }

    /**
     * Remove existing advertisement.
     *
     * @param advertisement advertisement needs to delete
     */
    public void removeAdvertisement(Advertisement advertisement) {
        advertisements.remove(advertisement);
    }

    /**
     * Gets date in the 'dd/MM/yyyy HH:mm' format.
     *
     * @param  date date in milliseconds
     * @return string in 'dd/MM/yyyy HH:mm' format
     */
    public String convertDate(long date) {
        return DateConverter.getInstance().convertDate(date);
    }

    /**
     * @return collection of advertisements
     */
    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }
}
