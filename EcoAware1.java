/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoaware1;

import java.io.*;
import java.util.*;

/**
 *
 * @author taifa
 */

public class EcoAware1 {
    private static UserDatabase database;
    private static DataAnalysis dataAnalysis;
    public static void main(String[] args) {
        database = new UserDatabase();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to EcoAware Please choose one of the services ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner){
        User user = new User();
        user.register(scanner, database);
    }    

    private static void loginUser(Scanner scanner) {
        User user = new User();
        boolean loginTest=user.login(scanner, database);
        if(loginTest==false){
            return;
        }
        String email= user.getEmail();
        
        while (true) {
            System.out.println("Welcome " + email + " To EcoAwre !");
            System.out.println("1. Add activity");
            System.out.println("2. View activities");
            System.out.println("3. View Your total carbon emissions");
            System.out.println("4. Join Computation with friends");
            System.out.println("5. Edit Profile");
            System.out.println("6. data analysis ");
            System.out.println("7. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    user.AddActivity(scanner);
                    break;
                case 2:
                    user.viewActivities();
                    break;
                case 3:
                    viewTotalCarbonEmissions(user);
                    break;
                case 4:
                    ComptiationFriends comptiationFriends = new ComptiationFriends(user);
                    comptiationFriends.compareTheUsersActivity();
                    break;
                case 5:
                    EditProfile editProfile = new EditProfile(user);
                   editProfile.chooseEdit();
                    break;
                case 6:
                    dataAnalysisFunction();
                    break;
                case 7:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void dataAnalysisFunction(){
        
      dataAnalysis.readActivitiesFromFile();
      System.out.println();
    
}
    
    private static void comptiationFriends(User user) {
        ArrayList<String[]> activitiesListUsers = new ArrayList<>();

        String fileName = "Activity.txt";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] activityData = line.split(",");
                activitiesListUsers.add(activityData);
            }

//            for (String[] activity : activitiesListUsers) {
//                System.out.println("[" + activity[0] + " " + activity[1] + " " + activity[2] + "]");
//            }
            compareTheUsersActivity(user, activitiesListUsers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compareTheUsersActivity(User user, ArrayList<String[]> data){
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();

        boolean isInPlace3 = false;
        int place = 0;

        for (String[] record : data) {
            String userEmail = record[0];
            double distance = Double.parseDouble(record[2]);

            int index = users.indexOf(userEmail);
            if (index == -1) {
                users.add(userEmail);
                distances.add(distance);
            } else {
                distances.set(index, distances.get(index) + distance);
            }
        }

        // Sort the users list based on the total distance for each user
        for (int i = 0; i < distances.size() - 1; i++) {
            for (int j = i + 1; j < distances.size(); j++) {
                if (distances.get(j) > distances.get(i)) {
                    double tempDistance = distances.get(i);
                    distances.set(i, distances.get(j));
                    distances.set(j, tempDistance);

                    String tempUser = users.get(i);
                    users.set(i, users.get(j));
                    users.set(j, tempUser);
                }
            }
        }

        System.out.println("Places sorted by total distances:");
        for (int i = 0; i < users.size(); i++) {
            if (i == 3){
                break;
            }
            String userEmail = users.get(i);
            double distance = distances.get(i);
            System.out.printf((i+1) +"- %s has a total distance of %.1f\n", userEmail, distance);

            if (user.getEmail().equals(userEmail)){

                isInPlace3 = true;
                place = i+1;

            }

        }
        if(isInPlace3){
            System.out.println("Congratulation, Place "+ (place));
            System.out.println("Congratulations on your win! To claim your prize, please press 1 and hit enter to roll the virtual roller.");
            Scanner input = new Scanner(System.in);

            input.next();


            String[] prizes = {"Vox Cinema Ticket", "Dinner for Two at a 5-star Restaurant", "Amazon Gift Card", "Smartwatch", "Wireless Headphones"};

            Random random = new Random();
            int randomIndex = random.nextInt(prizes.length); // generate a random index
            String prize = prizes[randomIndex]; // get the prize at the random index

            System.out.println("You won the following prize: " + prize+"\n\n");

        }else {
            System.out.println("Great effort, but you didn't quite make it to the top! Don't give up though, keep pushing yourself and you'll get there!\n\n");
        }

    }
    private static void viewTotalCarbonEmissions(User user) {
        double totalCarbonEmissions = user.getTotalCarbonEmissions();
        System.out.printf("Your Total carbon emissions: %.2f kg\n", totalCarbonEmissions);
    }
}

