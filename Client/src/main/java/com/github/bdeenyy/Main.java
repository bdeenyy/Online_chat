package com.github.bdeenyy;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Settings.applySettings();
        Client client = new Client();
    }
}