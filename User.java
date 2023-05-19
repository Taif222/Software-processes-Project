



package EcoAware2;

import java.io.*;
import java.util.*;



class User {
    private String email;
    private String password;
    private List<Activity> activities;
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.activities = new ArrayList<>();
    }
    public String getEmail() {
        return email; }
    
    public String getPassword() {
        return password; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addActivity(Activity activity) {
        activities.add(activity); }
     
    public List<Activity> getActivities() {
        return activities; }
   
    public double getTotalCarbonEmissions() {
        double total = 0.0;
        for (Activity activity : activities) {
            total += activity.getCarbonEmissions(); }
        return total;
    }
}