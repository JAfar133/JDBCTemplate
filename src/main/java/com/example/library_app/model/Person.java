package com.example.library_app.model;

import jakarta.validation.constraints.*;

public class Person {

    private int person_id;
    @NotEmpty(message = "fio shouldn't be empty")
    @Pattern(regexp = "[A-Z]\\w+ *[A-Z]\\w+ *[A-Z]\\w+",message = "not valid fio")
    private String fio;

    @Min(value = 1900, message = "please enter valid year of birth")
    @Max(value = 2023, message = "please enter valid year of birth")
    private int year;

    public Person(int person_id, String fio, int year) {
        this.person_id = person_id;
        this.fio = fio;
        this.year = year;
    }

    public Person() {
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", fio='" + fio + '\'' +
                ", year=" + year +
                '}';
    }
}
