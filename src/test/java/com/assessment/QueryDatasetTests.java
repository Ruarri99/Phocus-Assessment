package com.assessment;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class QueryDatasetTests {
    
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
        QueryDataset.readDataFromNDJSONFile(testFile);

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
