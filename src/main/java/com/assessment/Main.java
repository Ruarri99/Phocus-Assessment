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
                int rowCount = Integer.parseInt(args[1]);
                String outputFile = "AutoGenDataset.json";

                List<Person> generatedDataset = DatasetGenerator.generateDataset(rowCount, outputFile); // Generate a dataset
                DatasetGenerator.writeDataToJSONFile(generatedDataset, outputFile); // Write dataset to file as NDJSON
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
