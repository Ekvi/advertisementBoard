package com.shribak.board;


import com.shribak.board.controller.Controller;
import com.shribak.board.view.ConsoleView;

public class Board  {

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.readAdvertisementsFromFile();

        ConsoleView view = new ConsoleView(controller);
        view.showDialog();

        controller.writeAdvertisementsToFile();
    }
}
