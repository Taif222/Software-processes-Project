/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoaware1;

/**
 *
 * @author taifa
 */
 
import java.io.*;
import java.util.*;



class User {
    private String email;
    private String password;
    private List<Activity> activities;
    public User() {
        this.activities = new ArrayList<>();
    }
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

    public void addActivitytoList(Activity activity) {
        activities.add(activity); }
    
    public void AddActivity(Scanner scanner){
         System.out.println("Please enter the activity type(Walking , biking , driving)");
        String type = scanner.nextLine();
        System.out.println("Please enter the distance in km:");
        double distance = scanner.nextDouble();
        scanner.nextLine();
        Activity activity = new Activity(type, distance);
        this.addActivitytoList(activity);
        System.out.println("Your Activity successfully added.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Activity.txt", true))) {
            writer.write(this.getEmail() + "," + type + "," + distance);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to users file: " + e);
        }
    }
    
    public List<Activity> getActivities() {
        return activities; }
    
    public void setActivities(List<Activity> activties){        
        this.activities=activities;
    }
    
    public void viewActivities() {
        try (BufferedReader br = new BufferedReader(new FileReader("Activity.txt"))) {
        String line;
        List<Activity> activties=new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            String email = parts[0];
            String activityType = parts[1];
            double distance = Double.parseDouble(parts[2]);
            Activity activity=new Activity(activityType,distance);
            if(email.equals(this.getEmail())){
                activties.add(activity);
            }    
        }
        this.setActivities(activties);
         if (activties.isEmpty()) {
            System.out.println("There is no activities found.");
            return;
        }
        System.out.println("Your Activities:");
        for (Activity activity : activties) {
            System.out.println(activity.getType() + ": " + activity.getDistance() + " km");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }     
    }
    
    public void register(Scanner scanner,UserDatabase database) {
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        User existingUser = database.getUser(email);
        if (existingUser != null) {
            System.out.println("User with email " + email + " already exists. Please try again.");
            return;
        }
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        this.setEmail(email);
        this.setPassword(password);
        //User user = new User(email, password);
        database.addUser(this);
        System.out.println("User with email " + email + " successfully registered.");
    }
    
    public boolean login (Scanner scanner , UserDatabase database){
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        User user = database.getUser(email);
        if (user == null) {
            System.out.println("User with email " + email + " does not exist. Please try again.");
            return false;
        }
        this.setEmail(email);
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again.");
            return false;
        }
        this.setPassword(password);
        return true;
    }
    
    
    public double getTotalCarbonEmissions() {
        double total = 0.0;
        for (Activity activity : activities) {
            total += activity.getCarbonEmissions(); }
        return total;
    }
}