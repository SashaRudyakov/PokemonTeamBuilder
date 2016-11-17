package com.pokemon.model;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

class Person {

    int id;

    @Size(min=2, max=30, message="First name must be between 2 and 30 characters long.")
    String firstName;

    @Size(min=2, max=30, message="Last name must be between 2 and 30 characters long")
    String lastName;

//    @Min(0)
    int age;

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return this.firstName
    }

    public void setFirstName(String name) {
        this.firstName = name
    }

    public String getLastName() {
        return this.lastName
    }

    public void setLastName(String lastName) {
        this.lastName = lastName
    }

    public int getAge() {
        return this.age
    }

    public void setAge(int age) {
        this.age = age
    }
}