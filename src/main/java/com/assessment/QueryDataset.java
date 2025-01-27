package com.assessment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDataset {
    
    public static void queryDataset(List<Person> dataset) {
        // Find the oldest person
        Person oldestPerson = findOldestPerson(dataset);

        // Group by country and return count
        String countryCount = getCountryCount(dataset);

        // Group by age range (10s) for a given country (USA)
        String ageGroupings = getAgeGroupsForCountry(dataset, "USA");

        // Find the average age of all the people
        Double averageAge = getAverageAge(dataset);

        System.out.println("Oldest Person\n" + oldestPerson.firstName + " " + oldestPerson.lastName + " - " + oldestPerson.age + "\n");
        System.out.println("Country Count\n" + countryCount);
        System.out.println("Age Range Groups\n" + ageGroupings);
        System.out.println("Average Age\n" + averageAge );
    }


    private static Person findOldestPerson(List<Person> dataset) {
        Person oldestPerson = dataset.get(0);
        int oldestAge = 0;

        // Iterate over each row and compare the age to the current oldest
        for (Person person : dataset) {
            int age = person.age;

            // If this person is older, replace the current oldest
            if (oldestAge < age) {
                oldestAge = age;
                oldestPerson = person;
            }

        }

        return oldestPerson;
    }

    private static String getCountryCount(List<Person> dataset) {
        Map<String, Integer> countryCount = new HashMap<>();

        // Iterate over each row and add the country to the total count
        for (Person person : dataset) {
            String country = person.country;

            // Add country to the hashmap if it doesn't exist already
            countryCount.computeIfAbsent(country, k -> 0);

            // Update the country count
            countryCount.put(country, countryCount.get(country) + 1);
        }

        // Create string and return
        String totalCount = "";
        for (Map.Entry<String, Integer> entry : countryCount.entrySet()) {
            String country = entry.getKey();
            int count = entry.getValue();
            totalCount += country + ": " + count + "\n";
        }

        return totalCount;
    }

    private static String getAgeGroupsForCountry(List<Person> dataset, String country) {
        Map<Integer, Integer> rangeCount = new HashMap<>();

        // Add each range to the map
        // Ranges are represented by their floor value (1 = 1-10, 11 = 11-20, etc)
        for (int i = 1; i < 100; i += 10) {
            rangeCount.put(i, 0);
        }

        // Iterate over each person and add them to the respective group count if they are in the right country
        for (Person person : dataset) {
            if (person.country.equals(country)) {
                int rangeKey = person.age % 10 == 0 ? person.age - 9 : ((person.age/10)*10+1);

                rangeCount.put(rangeKey, rangeCount.get(rangeKey) + 1);
            }
        }

        // Create string and return
        String totalCount = country + ":\n";
        for (int i = 1; i < 100; i += 10) {
            int count = rangeCount.get(i);
            totalCount += i + "-" + (i+9) + ": " + count + "\n";
        }

        return totalCount;
    }

    private static Double getAverageAge(List<Person> dataset) {
        int ageTotal = 0;
        
        // Iterate over each person, adding up the ages
        for (Person person : dataset) {
            ageTotal += person.age;
        }

        return (double) (ageTotal / dataset.size());
    }
}
