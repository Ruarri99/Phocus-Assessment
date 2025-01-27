package com.assessment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class DatasetGenerator {

    private static final List<String> namePool = Arrays.asList("Tom", "Cruise", "Matt", "Damon", "Jason", "Statham", "Rose", "Tyler", "Conner", "Price", "Michael", "Jackson", "Matt", "Smith", "Angelina", "Jolie", "Emily", "Blunt", "John", "Krasinski", "Jenna", "Fischer");
    private static final List<String> countryPool = Arrays.asList("New Zealand", "Australia", "USA", "Germany", "Italy", "China", "Japan", "Mexico", "South Africa", "Canada", "Sweden", "England", "Russia");

    public static List<Person> generateDataset(int rowCount, String outputFile) throws IOException {
        // Create the empty "dataset" array of "Person" objects
        Random random = new Random();
        List<Person> dataset = new ArrayList<>();

        // Adds "rowCount" number of "Person" objects to the "dataset" array
        for (int i = 1; i <= rowCount; i++) {
            String id = String.valueOf(i);
            String firstName = namePool.get(random.nextInt(namePool.size()));
            String lastName = namePool.get(random.nextInt(namePool.size()));
            int age = random.nextInt(100) + 1;  // Generates an age from 1 - 100 inclusive
            String country = countryPool.get(random.nextInt(countryPool.size()));

            dataset.add(new Person(id, firstName, lastName, age, country));
        }

        return dataset;     
    }

    public static void writeDataToNDJSONFile(List<Person> dataset, String outputFile) throws IOException {
        // Open the output file with a buffered writer
        try (BufferedWriter jsonWriter = Files.newBufferedWriter(Paths.get(outputFile))) {
            Gson gson = new Gson();

            // Write each person to the output file as an NDJSON row
            for (Person person : dataset) {
                jsonWriter.write(gson.toJson(person));
                jsonWriter.newLine();
            }
        }
    }

    public static List<Person> readDataFromNDJSONFile(String inputFile) throws IOException {
        List<Person> dataset = new ArrayList<>();

        // Open NDJSON file and read
        try (BufferedReader jsonReader = Files.newBufferedReader(Paths.get(inputFile))) {
            Gson gson = new Gson();
            String readLine;

            while ((readLine = jsonReader.readLine()) != null) {
                Person person = gson.fromJson(readLine, Person.class);
                dataset.add(person);
            }
        } catch (Exception e) {
            System.out.println("Invalid file given: " + inputFile);
        }

        return dataset;
    }
    
}
