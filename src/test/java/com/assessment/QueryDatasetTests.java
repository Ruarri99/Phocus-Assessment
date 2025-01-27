package com.assessment;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class QueryDatasetTests {

    @Test
    public void testReadInvalidFileGiven() throws IOException {        
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
        List<Person> dataset = QueryDataset.readDataFromNDJSONFile(testFile);

        // Verify the file is invalid
        assertTrue(dataset == null);

        // Cleanup
        System.setOut(System.out);
        File datasetFile = new File(testFile);
        if (datasetFile.delete() == false) {
            System.out.println("Failed to delete test file: " + testFile);
        }   
    }
}
