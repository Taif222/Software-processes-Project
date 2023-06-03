
package ecoaware1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


 class DataAnalysis {
    private static final String FILENAME = "Activity.txt";
    private static final String DELIMITER = ",";

  //function for each user with total carbon Emissions and distance traveled
  public static void readActivitiesFromFile() {
    Map<String, double[]> distancesforUser = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
        String line;
       // double totalDistance=0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            String email = parts[0];
            String activityType = parts[1];
            double distance = Double.parseDouble(parts[2]);
            Activity activity=new Activity(activityType,distance);
            double carbonEmission=activity.getCarbonEmissions();
           
            if (distancesforUser.containsKey(email)) {
                double[] d = distancesforUser.get(email);
                d[0] += distance;
                d[1]+=carbonEmission;
                distancesforUser.put(email, d);
            } else {
                double[] d = new double[2];
                d[0] = distance;
                d[1] = carbonEmission;
                distancesforUser.put(email, d);
            }
            
        }
        //carbonArray for sorting.
        Map<Double, String> carbonArray = new HashMap<>();
        for (Map.Entry<String, double[]> entry : distancesforUser.entrySet()) {
            String email = entry.getKey();
            double carbon = entry.getValue()[1];
            carbonArray.put(carbon, email);
        }
        Comparator<Double> comparator = Double::compare;
        TreeMap<Double, String> s = new TreeMap<>(comparator);
        s.putAll(carbonArray);
        // Print the sorted map
        System.out.println("data analysis for Activities file");
        System.out.println("All emails sorted by carbon emissions");
        for (Map.Entry<Double, String> entry : s.entrySet()) {
            double carbon = entry.getKey();
            String email = entry.getValue();
            double distance = distancesforUser.get(email)[0];
            System.out.println(email + ":  distance=" + distance + " km   "+"CarbonEmissions= "+carbon);
        }
        
    } catch (IOException e) {
        e.printStackTrace();
    }

 }
}
