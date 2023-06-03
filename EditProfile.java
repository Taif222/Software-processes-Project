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
import java.util.ArrayList;
import java.util.Scanner;

public class EditProfile {

    ArrayList<String[]> usersFileList = new ArrayList<>();
    private User user;
    public EditProfile(User user){
        this.user = user;

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
    }
public  void chooseEdit(){
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
            chooseEdit();
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



}