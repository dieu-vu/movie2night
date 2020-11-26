package fi.mobiles13.movietonight;

import java.util.ArrayList;

public class User {

    private String username;
    private int age;
    private ArrayList<String> searchHistory = new ArrayList<>(); //where to get the data ???

    //constructor
    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getSearchHistory() {
        return searchHistory;
    }
}
