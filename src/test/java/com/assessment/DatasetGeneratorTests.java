package com.assessment;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class DatasetGeneratorTests {
    
    // Testing  edge cases

    @Test
    public void testZeroRowsGiven() throws IOException {
        // Call with Zero rows
        String testFile = "TestFile.json";
        List<Person> writeDataset = DatasetGenerator.generateDataset(0, testFile);
        DatasetGenerator.writeDataToNDJSONFile(writeDataset, testFile);

        // Open and read the file
        List<Person> readDataset = DatasetGenerator.readDataFromNDJSONFile(testFile);

        // Verify the file contains zero rows
        assertTrue(readDataset.isEmpty());

        // Cleanup
        File datasetFile = new File(testFile);
        if (datasetFile.delete() == false) {
            System.out.println("Failed to delete test file: " + testFile);
        }
    }

    @Test
    public void testOneRowGiven() throws IOException {
        // Call with One rows
        String testFile = "TestFile.json";
        List<Person> writeDataset = DatasetGenerator.generateDataset(1, testFile);
        DatasetGenerator.writeDataToNDJSONFile(writeDataset, testFile);

        // Open and read the file
        List<Person> readDataset = DatasetGenerator.readDataFromNDJSONFile(testFile);

        // Verify the file contains zero rows
        assertTrue(readDataset.size() == 1);

        // Cleanup
        File datasetFile = new File(testFile);
        if (datasetFile.delete() == false) {
            System.out.println("Failed to delete test file: " + testFile);
        }
    }

    @Test
    public void testTenThousandRowsGiven() throws IOException {
        // Call with 10_000 rows
        String testFile = "TestFile.json";
        List<Person> writeDataset = DatasetGenerator.generateDataset(10_000, testFile);
        DatasetGenerator.writeDataToNDJSONFile(writeDataset, testFile);

        // Open and read the file
        List<Person> readDataset = DatasetGenerator.readDataFromNDJSONFile(testFile);

        // Verify the file contains zero rows
        assertTrue(readDataset.size() == 10_000);

        // Cleanup
        File datasetFile = new File(testFile);
        if (datasetFile.delete() == false) {
            System.out.println("Failed to delete test file: " + testFile);
        }
    }

    @Test
    public void testReadInvalidFileGiven() throws IOException {
        // Redirect system output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Create a file with invalid data
        String testFile = "TestFile.json";
        File newFile = new File(testFile);

        if (newFile.createNewFile()) {
            try (BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(testFile))) {
                // Write one row
                fileWriter.write("Hello World");
                fileWriter.newLine();
            }
        }
     
        // Open and read the file
        DatasetGenerator.readDataFromNDJSONFile(testFile);

        // Verify the file contains zero rows
        assertTrue(outContent.toString().contains("Invalid file given: " + testFile));

        // Cleanup
        System.setOut(System.out);
        File datasetFile = new File(testFile);
        if (datasetFile.delete() == false) {
            System.out.println("Failed to delete test file: " + testFile);
        }   
    }

}
