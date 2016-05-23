package main;

import presentation.Controller;

public class Main {

// Maakt het hoofdscherm zichtbaar bij opstarten en maakt Manager aan
    public static void main(String[] args) {
        Controller c = new Controller();
        c.makeVisible("Login");
    }
}
