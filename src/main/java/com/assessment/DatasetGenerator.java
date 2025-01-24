package com.assessment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class DatasetGenerator {

    private static final List<String> namePool = Arrays.asList("Tom", "Cruise", "Matt", "Damon", "Jason", "Statham", "Rose", "Tyler", "Conner", "Price", "Michael", "Jackson", "Matt", "Smith", "Angelina", "Jolie", "Emily", "Blunt", "John", "Krasinski", "Jenna", "Fischer");
    private static final List<String> countryPool = Arrays.asList("New Zealand", "Australia", "USA", "Germany", "Italy", "China", "Japan", "Mexico", "South Africa", "Canada", "Sweden", "England", "Russia");

    public static void generateData(int rowCount, String outputFile) throws IOException {
        Random random = new Random();
        List<Person> dataset = new ArrayList<>(); // Create the empty "dataset" array of "Person" objects

        for (int i = 1; i <= rowCount; i++) { // Adds "rowCount" number of "Person" objects to the "dataset" array
            String id = String.valueOf(i);
            String firstName = namePool.get(random.nextInt(namePool.size()));
            String lastName = namePool.get(random.nextInt(namePool.size()));
            int age = random.nextInt(100) + 1; // Generates an age from 1 - 100 inclusive
            String country = countryPool.get(random.nextInt(countryPool.size()));

            dataset.add(new Person(id, firstName, lastName, age, country));
        }

        try (BufferedWriter jsonWriter = Files.newBufferedWriter(Paths.get(outputFile))) { // Open the output file with a buffered writer
            Gson gson = new Gson();
            for (Person person : dataset) { // Write each person to the output file as an NDJSON row
                jsonWriter.write(gson.toJson(person));
                jsonWriter.newLine();
            }
        }
        

    }
    
}
