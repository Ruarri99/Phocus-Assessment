package com.assessment;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().run(args);
    }

    public void run(String args[]) throws IOException {
        if (args.length < 2) {
            displayHelp();
            return;
        }

        switch (args[0]) {
            case "generate":
                int rowCount;
                String outputFile = "AutoGenDataset.json";

                // Check argument is a valid integer
                try {
                    rowCount = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    displayHelp();
                    break;
                }

                List<Person> generatedDataset = DatasetGenerator.generateDataset(rowCount, outputFile); // Generate a dataset
                DatasetGenerator.writeDataToNDJSONFile(generatedDataset, outputFile); // Write dataset to file as NDJSON
                break;

            case "query":
                // Read dataset file
                String inputFile = args[1];
                List<Person> dataset = DatasetGenerator.readDataFromNDJSONFile(inputFile);

                // Query dataset
                QueryDataset.queryDataset(dataset);
                break;

            default:
                displayHelp();
                break;
        }
    }

    private static void displayHelp() {
        System.out.println("\nUsage: java -cp target/classes com.assessment.Main <command> [arguments]\n");
        System.out.println("generate [number of rows] - Generates a dataset with the specified number of rows.");
        System.out.println("");
    }
    
}
