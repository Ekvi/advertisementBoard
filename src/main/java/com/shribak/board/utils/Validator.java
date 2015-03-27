package com.shribak.board.utils;


/**
 * The {@code Validator} class validate input parameters.
 *
 * @author Dmitrii Shribak
 */
public class Validator {

    /**
     * Validate if the name length between 4 and 20 characters, and first character
     * is a letter.
     *
     * @param name user name
     * @return if the name is correct
     */
    public boolean validateName(String name) {
        if(!validateLength(name, 4, 20)) {
            return false;
        } else if(isInteger(name.substring(0, 1))){
            return false;
        }
        return true;
    }

    /**
     * Checks if the parameter is integer.
     *
     * @param letter input parameter
     * @return if can parse return true otherwise false
     */
    boolean isInteger(String letter) {
        try{
            Integer.parseInt(letter);
            return true;
        } catch(NumberFormatException exc) {
            return false;
        }
    }

    /**
     * Checks if the title length between 10 and 30 characters.
     *
     * @param title title received from user
     * @return if the title length is correct
     */
    public boolean validateTitle(String title) {
        return validateLength(title, 10, 30);
    }

    /**
     * Checks if the content length between 20 and 400 characters.
     *
     * @param content advertisement content
     * @return if the content length is correct
     */
    public boolean validateContent(String content) {
        return validateLength(content, 20, 400);
    }

    /**
     * Checks if the number of action between 1 and 6.
     *
     * @param action number of action received from user
     * @return if the a user make a correct choice
     */
    public boolean validateAction(String action) {
        return validateChoice(action, 1, 6);
    }

    /**
     * Checks if the number of category between 1 and 5.
     *
     * @param category number of category received from user
     * @return if the a user make a correct choice
     */
    public boolean validateCategory(String category) {
        return validateChoice(category, 1, 5);
    }

    /**
     * Checks if the user make a correct choice.
     *
     * @param value action or category
     * @param start start from
     * @param end to end
     * @return if the a user make a correct choice
     */
    private boolean validateChoice(String value, int start, int end) {
        try{
            int i = Integer.parseInt(value);

            return i >= start && i <= end;
        } catch(NumberFormatException exc) {
            return false;
        }
    }
    /**
     * Checks if the length of input information is in the allowable range.
     *
     * @param content input value
     * @param less can't be less then
     * @param  more can't be more then
     * @return if the input value correct
     */
    private boolean validateLength(String content, int less, int more) {
        if(content.length() < less || content.length() > more) {
            return false;
        }
        return true;
    }
}
