package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import presentation.Controller;

public class Main {

// Maakt het hoofdscherm zichtbaar bij opstarten en maakt Manager aan
    public static void main(String[] args) {
        Controller c = new Controller();
        c.makeVisible("Mainmenu");
    }
}
