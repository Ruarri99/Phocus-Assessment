package com.assessment;

public class Person {
    
    String id;
    String firstName;
    String lastName;
    Integer age;
    String country;

    public Person(String id, String firstName, String lastName, int age, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.country = country;
    }

    public boolean isValid() {
        return (this.id != null && this.firstName != null && this.lastName != null && this.age != null && this.country != null);
    }
}
