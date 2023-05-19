




package EcoAware2;

import java.io.*;
import java.util.*;

/**
 * @author taifa
 */
public class EcoAware2 {
    private static UserDatabase database;

    public static void main(String[] args) {
        database = new UserDatabase();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(
           "Welcome to EcoAware Please choose one of the services "+
           "1. Register"+
           "2. Login"+
           "3. Exit");

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

    private static void registerUser(Scanner scanner) {
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        User existingUser = database.getUser(email);
        if (existingUser != null) {
            System.out.println("User with email " + email + " already exists. Please try again.");
            return;
        }
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        User user = new User(email, password);
        database.addUser(user);
        System.out.println("User with email " + email + " successfully registered.");
    }

    private static void loginUser(Scanner scanner) {
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        User user = database.getUser(email);
        if (user == null) {
            System.out.println("User with email " + email + " does not exist. Please try again.");
            return;
        }
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again.");
            return;
        }
        while (true) {
            System.out.println(
            "Welcome " + email + "To EcoAwre !"+
            "1. Add activity"+
            "2. View activities"+
            "3. View Your total carbon emissions"+
            "4. Join Computation with friends"+
            "5. Edit Profile"+
            "6. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addActivity(scanner, user);
                    break;
                case 2:
                    viewActivities(user);
                    break;
                case 3:
                    viewTotalCarbonEmissions(user);
                    break;
                case 4:
                    comptiationFriends(user);
                    break;
                case 5:
                    editProfile(user);
                    break;
                case 6:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void viewActivities(User user) {
        List<Activity> activities = user.getActivities();
        if (activities.isEmpty()) {
            System.out.println("There is no activities found.");
            return;
        }
        System.out.println("Your Activities:");
        for (Activity activity : activities) {
            System.out.println(activity.getType() + ": " + activity.getDistance() + " km");
        }
    }

    private static void addActivity(Scanner scanner, User user) {
        System.out.println("Please enter the activity type(Walking , biking , driving)");
        String type = scanner.nextLine();
        System.out.println("Please enter the distance in km:");
        double distance = scanner.nextDouble();
        scanner.nextLine();
        Activity activity = new Activity(type, distance);
        user.addActivity(activity);
        System.out.println("Your Activity successfully added.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Activity.txt", true))) {
            writer.write(user.getEmail() + "," + type + "," + distance);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to users file: " + e);
        }


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


    private static void editProfile(User user){

        ArrayList<String[]> usersFileList = new ArrayList<>();

        String userEmail = user.getEmail();

        String fileName = "usersInfo.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (userEmail.equals(fields[0])) {
                }else {
                    usersFileList.add(fields);
                }
            }
            br.close();
        } catch (IOException e) {
        }


        Scanner input = new Scanner(System.in);
        System.out.println("1- Change Password");
        System.out.println("2- Change Email");
        int choice = input.nextInt();
        if (choice == 1) {
            System.out.println("Enter new password: ");
            String newPassword = input.next();
            user.setPassword(newPassword);
            System.out.println("Your password has been changed successfully.");

        } else if (choice == 2) {
            System.out.println("Enter new email: ");
            String newEmail = input.next();
            user.setEmail(newEmail);
            System.out.println("Your email address has been changed successfully.");

        } else {
            editProfile(user);
            return;
        }

        String[] newdata = {user.getEmail(), user.getPassword()};

        usersFileList.add(newdata);


        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("usersInfo" + ".txt"));
            for (String[] row : usersFileList) {
                try {

                    writer.write(row[0]);
                    writer.write("," + row[1]+"\n");


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("Profile updated successfully!");

    }

    private static void viewTotalCarbonEmissions(User user) {
        double totalCarbonEmissions = user.getTotalCarbonEmissions();
        System.out.printf("Your Total carbon emissions: %.2f kg\n", totalCarbonEmissions);
    }
}


