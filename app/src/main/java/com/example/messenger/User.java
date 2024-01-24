package com.example.messenger;

public class User {

    private String id;
    private String name;
    private String surname;
    private int age;
    private boolean isOnline;

    public User(String id, String name, String surname, int age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.isOnline = isOnline;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
