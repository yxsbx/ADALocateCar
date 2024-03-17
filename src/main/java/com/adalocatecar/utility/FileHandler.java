package com.adalocatecar.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());

    public static void writeToFile(List<String> lines, String filename, boolean op) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, op))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while writing to file: " + filename, e);
        }
    }
}

