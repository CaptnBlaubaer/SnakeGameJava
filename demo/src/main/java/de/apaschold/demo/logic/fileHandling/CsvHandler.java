package de.apaschold.demo.logic.fileHandling;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>CsvHandler class</h2>
 * <li>Singleton class for handling CSV file operations</li>
 * <li>Provides methods to read and write highscore data to a CSV file</li>
 */
public class CsvHandler {
    //0. constants
    private final static String CSV_SEPARATOR = ";";
    private final static String FILE_PATH = "src/main/resources/de/apaschold/demo/data/highscores.csv";

    private static CsvHandler instance;

    //1. attributes

    //2. constructors
    private CsvHandler(){
    }

    public static synchronized CsvHandler getInstance() {
        if (instance == null) {
            instance = new CsvHandler();
        }
        return instance;
    }

    //3. read'n'write methods
    /**
     * <h2>writeHighscoreToCsv method</h2>
     * <li>Writes a list of highscores to a CSV file</li>
     * <li>Each entry in the list is an array with two elements: name and score</li>
     * @param highscores List of String arrays containing name and score
     */
    public void writeHighscoreToCsv(List<String[]> highscores){

        try (FileWriter writer = new FileWriter(FILE_PATH, StandardCharsets.UTF_8)) {
            for (String[] entry : highscores) {
                String csvLine = entry[0] + CSV_SEPARATOR + entry[1] + "\n";
                writer.write(csvLine);
            }
        } catch (IOException e) {
            System.err.println("Error saving to File: " + FILE_PATH);
            e.printStackTrace();
        }
    }

    /**
     * <h2>readHighscoresFromCsv method</h2>
     * <li>Reads highscores from a CSV file and returns them as a list</li>
     * <li>Each entry in the list is an array with two elements: name and score</li>
     * @return List of String arrays containing name and score
     */
    public List<String[]> readHighscoresFromCsv() {
        List<String[]> highscores = new ArrayList<>();

        File file = new File(FILE_PATH);

        try(FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader)) {

            String fileLine;
            boolean eof = false;

            while (!eof) {
                fileLine = in.readLine();

                if (fileLine == null) {
                    eof = true;
                } else {
                    String[] parts = fileLine.split(CSV_SEPARATOR);
                    if (parts.length == 2) {
                        highscores.add(parts);
                    } else {
                        System.err.println("Invalid line format: " + fileLine);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getAbsolutePath());
            e.printStackTrace();
        }

        return highscores;
    }
}
