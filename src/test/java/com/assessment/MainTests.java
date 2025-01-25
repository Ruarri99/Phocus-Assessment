package com.assessment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

public class MainTests {
 
    // Testing program arguments edge cases

    @Test
    public void testDisplaysHelpWhenNoArgs() throws IOException {      
        // Redirect system output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Pass no args
        String[] args = {};
        Main main = new Main();
        main.run(args);

        // Verify the help message is shown
        assertTrue(outContent.toString().contains("Usage:"));

        System.setOut(System.out);
    }

    @Test
    public void testDisplaysHelpWhenOneWrongArg() throws IOException {      
        // Redirect system output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Pass one incorrect arg
        String[] args = {"test"};
        Main main = new Main();
        main.run(args);

        // Verify the help message is shown
        assertTrue(outContent.toString().contains("Usage:"));

        System.setOut(System.out);
    }

    @Test
    public void testHandlesInvalidRowCount() throws IOException {
        // Redirect system output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Pass incorrect row count
        String[] args = {"generate", "x"};
        Main main = new Main();
        main.run(args);

        // Verify the help message is shown
        assertTrue(outContent.toString().contains("Usage:"));

        System.setOut(System.out);
    }

    @Test
    public void testCorrectArgsGenerateDataset() throws Exception {
        // Mock the DatasetGenerator method
        try (MockedStatic<DatasetGenerator> mockDataGen = mockStatic(DatasetGenerator.class)) {
            // Make sure the method does nothing when called
            mockDataGen.when(() -> DatasetGenerator.generateData(10, "AutoGenDataset.json")).thenAnswer((Answer<Void>) invocation -> null);
        
            // Pass correct args to generate 10 rows
            String[] args = {"generate", "10"};
            Main main = new Main();
            main.run(args);

            // Verify the dataset generator method was called as expected
            mockDataGen.verify(() -> DatasetGenerator.generateData(10, "AutoGenDataset.json"), times(1));
        }
    }

}
