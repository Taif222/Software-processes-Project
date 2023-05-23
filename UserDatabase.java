
package EcoAware1;
import java.io.*;
import java.util.*;



class UserDatabase {
    
    private static final String FILENAME = "usersInfo.txt";
    private static final String DELIMITER = ",";
    private Map<String, User> users;
    public UserDatabase() {
        users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 2) {
                    String email = parts[0];
                    String password = parts[1];
                    List<Activity> activities = new ArrayList<>();
                    if (parts.length > 2) {
                        String[] activityParts = Arrays.copyOfRange(parts, 2, parts.length);
                        for (String activityPart : activityParts) {
                            String[] activityInfo = activityPart.split(":");
                            if (activityInfo.length == 2) {
                                String type = activityInfo[0];
                                double distance = Double.parseDouble(activityInfo[1]);
                                Activity activity = new Activity(type, distance);
                                activities.add(activity);
                            }
                        }
                    }
                    User user = new User(email, password);
                    user.getActivities().addAll(activities);
                    users.put(email, user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from users file: " + e);
        }
    }
    public User getUser(String email) {
        return users.get(email);
    }
    public void addUser(User user) {
        users.put(user.getEmail(), user);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(user.getEmail() + DELIMITER + user.getPassword());
            for (Activity activity : user.getActivities()) {
                writer.write(DELIMITER + activity.getType() + ":" + activity.getDistance());
            }
            writer.write("\n");
        } catch (IOException e) {
            System.err.println("Error writing to users file: " + e);
        }
    }
}

