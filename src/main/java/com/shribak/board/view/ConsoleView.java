package com.shribak.board.view;


import com.shribak.board.controller.Controller;
import com.shribak.board.model.Advertisement;
import com.shribak.board.utils.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * The {@code ConsoleView} class shows dialogs for interaction user with
 * a program.
 *
 * @author Dmitrii Shribak
 */
public class ConsoleView {
    private Validator validator = new Validator();
    private Controller controller;
    private String name = "";

    public ConsoleView(Controller controller) {
        this.controller =  controller;
    }

    public void showDialog() {
        String end;

        try {
            do {
                dialog();
                printMessage(ViewConstants.FINISH_OR_CONTINUE);
                end = readFromConsole();
            } while (!end.equalsIgnoreCase("exit"));
        } catch(NullPointerException e) {}
    }

    private void dialog() {
        name = showEnterNameDialog(ViewConstants.ENTER_YOUR_NAME);
        String action = showSelectActionDialog();
        selectedAction(Integer.parseInt(action));
    }

    /**
     * Shows dialog for asking user to enter his name.
     *
     * @param message user name
     * @return name typed by user
     */
    private String showEnterNameDialog(String message) {
        String enterName;
        do {
            printMessage(message);
            enterName = readFromConsole();
        } while(!validator.validateName(enterName));

        return enterName;
    }

    /**
     * Shows dialog for asking user to make a choice.
     *
     * @return action choose user
     */
    private String showSelectActionDialog() {
        String action;
        do {
            printMessage(ViewConstants.OFFER_SELECT_ACTION);
            showActions();
            action = readFromConsole();
        } while(!validator.validateAction(action));

        return action;
    }

    /**
     * Reads information typed by user from the console.
     *
     * @return  information from console
     */
    private String readFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text = "";
        try {
            text = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Shows the list of actions which user should to select.
     */
    private void showActions() {
        printMessage("1- view your advertisements");
        printMessage("2- view advertisements in category");
        printMessage("3- view advertisements of author");
        printMessage("4- create new advertisement");
        printMessage("5- edit advertisement");
        printMessage("6- remove advertisement");
    }

    private void selectedAction(int action) {
        switch (action) {
            case 1 :
                showOwnAdvertisements();
                break;
            case 2 :
                showAdvertisementsOfCategory();
                break;
            case 3 :
                showAdvertisementsOfAuthor();
                break;
            case 4 :
                createAdvertisementDialog();
                break;
            case 5 :
                editAdvertisementDialog();
                break;
            case 6 :
                removeAdvertisementDialog();
                break;
        }
    }

    /**
     * Shows all advertisements of user.
     */
    private void showOwnAdvertisements() {
        List<Advertisement> advertisements = controller.getAdvertisementsByName(name);
        if(advertisements == null || advertisements.isEmpty()) {
            printMessage(ViewConstants.MESSAGE_YOUR_ADVERTISEMENT);
        } else {
            showAdvertisements(advertisements);
        }
    }

    /**
     * Shows all advertisements in certain category.
     */
    private void showAdvertisementsOfCategory() {
        String category = showSelectCategoryDialog();

        List<Advertisement> advertisements = controller.getAdvertisementsByCategory(category);

        if(advertisements == null || advertisements.isEmpty()) {
            printMessage(ViewConstants.MESSAGE_EMPTY_CATEGORY);
        } else {
            showAdvertisements(advertisements);
        }
    }

    /**
     * Shows the dialog of asking user to select category in
     * which he wants to see advertisements.
     */
    private String showSelectCategoryDialog() {
        String category;
        do {
            printMessage(ViewConstants.OFFER_SELECT_CATEGORY);
            category = readFromConsole();
        } while(!validator.validateCategory(category));

        return category;
    }

    /**
     * Shows all advertisements of author.
     */
    private void showAdvertisementsOfAuthor() {
        String author = showEnterNameDialog(ViewConstants.ENTER_AUTHOR_NAME);

        List<Advertisement> advertisements = controller.getAdvertisementsByName(author);

        if(advertisements == null || advertisements.isEmpty()) {
            printMessage(ViewConstants.MESSAGE_AUTHOR_ADVERTISEMENT);

        } else {
            showAdvertisements(advertisements);
        }
    }

    /**
     * Prints information about advertisements.
     */
    private void showAdvertisements(List<Advertisement> advertisements) {
        for(Advertisement advertisement : advertisements) {
            printMessage("-------------------------------------------------------------");
            printMessage("Author: " + advertisement.getAuthor());
            printMessage("Date: " + controller.convertDate(advertisement.getDate()));
            printMessage("Category: " + advertisement.getCategory());
            printMessage("Title: " + advertisement.getTitle());
            printMessage("\n" + advertisement.getContent());
            printMessage("-------------------------------------------------------------");
        }
    }

    /**
     * Shows dialog for creating new advertisement.
     */
    private void createAdvertisementDialog() {
        String category = showSelectCategoryDialog();
        String title = showCreateTitleDialog(ViewConstants.ENTER_TITLE, ViewConstants.WRONG_TITLE);
        String content = showCreateAdvertisementContent(
                    ViewConstants.ENTER_ADVERTISEMENT, ViewConstants.WRONG_ADVERTISEMENT);

        controller.addAdvertisement(name, category, title, content);
        printMessage(ViewConstants.SUCCESS_ADDED_ADVERTISEMENT);
    }

    /**
     * Shows dialog to enter a new title.
     *
     * @param message ask user to type a title
     * @param warning show user warning if the title isn't correct
     * @return title typed by user
     */
    private String showCreateTitleDialog(String message, String warning) {
        printMessage(message);

        String title = readFromConsole();

        while(!validator.validateTitle(title)) {
            printMessage(warning);
            title = readFromConsole();
        }

        return title;
    }

    /**
     * Shows dialog to create a content for new advertisement.
     *
     * @param message ask user to type a content
     * @param warning show user warning if the content isn't correct
     * @return content typed by user
     */
    private String showCreateAdvertisementContent(String message, String warning) {
        printMessage(message);

        String content = readFromConsole();

        while(!validator.validateContent(content)) {
            printMessage(warning);
            content = readFromConsole();
        }

        return content;
    }

    /**
     * Shows dialog to edit an advertisement.
     */
    private void editAdvertisementDialog() {
        String title = showCreateTitleDialog(ViewConstants.ENTER_EDIT_TITLE, ViewConstants.WRONG_TITLE);

        Advertisement advertisement = controller.findByTitle(name, title);

        if(advertisement != null) {
            String content = showCreateAdvertisementContent(
                        ViewConstants.ENTER_EDIT_ADVERTISEMENT, ViewConstants.WRONG_ADVERTISEMENT);

            advertisement.setContent(content);
            controller.updateAdvertisement(advertisement);

            printMessage(ViewConstants.SUCCESS_CHANGED_ADVERTISEMENT);
        } else {
            printMessage(ViewConstants.NO_ADVERTISEMENT_WITH_TITLE);
        }
    }

    /**
     * Shows dialog for removing advertisement.
     */
    private void removeAdvertisementDialog() {
        String title = showCreateTitleDialog(ViewConstants.ENTER_TITLE_FOR_REMOVE, ViewConstants.WRONG_TITLE);

        Advertisement advertisement = controller.findByTitle(name, title);

        if(advertisement != null) {
            controller.removeAdvertisement(advertisement);
            printMessage(ViewConstants.SUCCESS_REMOVED_ADVERTISEMENT);
        } else {
            printMessage(ViewConstants.NO_ADVERTISEMENT_WITH_TITLE);
        }
    }

    /**
     * Prints all messages to console.
     */
    private void printMessage(String message) {
        System.out.println(message);
    }
}


