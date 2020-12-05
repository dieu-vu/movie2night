package fi.mobiles13.movietonight;

import java.util.ArrayList;

public class User {

    String username, password, email;
    int age;

    ArrayList<String> searchHistory;
    //constructor
    public User(String username, String password, int age, String email) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.searchHistory = new ArrayList<String>();
    }

    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.age = -1;
        this.email = null;
        this.searchHistory = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void addHistory(String entry){
        this.searchHistory.add(entry);
    }

    public ArrayList<String> getSearchHistory() {
        return this.searchHistory;
    }
}
