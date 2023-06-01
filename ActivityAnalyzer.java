/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoaware;

/**
 *
 * @author taifa
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityAnalyzer {
    private ArrayList<Activity> activities;
    
    public ActivityAnalyzer() {
        activities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("activity.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                double distance = Double.parseDouble(parts[1]);
                Activity activity = new Activity(type, distance);
                activities.add(activity);
            }
        } catch (IOException e) {
            System.err.println("Error reading activity file: " + e); 
        }
    }
    
    public double calculateAverageDistance() {
        double totalDistance = 0;
        for (Activity activity : activities) {
            totalDistance += activity.getDistance();
        }
        return totalDistance / activities.size();
    }
    
    public double calculateAverageCarbonEmissions() {
        double totalEmissions = 0;
        for (Activity activity : activities) {
            totalEmissions += activity.getCarbonEmissions();
        }
        return totalEmissions / activities.size();
    }
    
    public void printAnalysis() {
        double averageDistance = calculateAverageDistance();
        double averageEmissions = calculateAverageCarbonEmissions();
        
        System.out.println("Average distance: " + averageDistance + " km");
        System.out.println("Average carbon emissions: " + averageEmissions + " kg");
    }
}
