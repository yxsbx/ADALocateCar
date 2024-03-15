package com.adalocatecar.utility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reading from file: " + filename, e);
        }
        return lines;
    }
}

