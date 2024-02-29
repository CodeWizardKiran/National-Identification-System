package com.nis.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.UUID;

public class AdminMenu extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nis";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "kiran2003";

    public AdminMenu() {
        setTitle("Admin Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton processUsersButton = new JButton("Process Users Pending Approval");
        JButton searchUserButton = new JButton("Search User by Unique ID");
        JButton exitButton = new JButton("Exit");

        panel.add(processUsersButton);
        panel.add(searchUserButton);
        panel.add(exitButton);

        add(panel);

        processUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processUsersPendingApproval();
            }
        });

        searchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUserByUniqueId();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
    }

    private void processUsersPendingApproval() {
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM users WHERE approved = false";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String phoneNumber = resultSet.getString("phone_number");
                String aadhaarNumber = resultSet.getString("aadhaar_number");
                String email = resultSet.getString("email");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");

                displayUserDetails(userId, name, dob, phoneNumber, aadhaarNumber, email, city, state);

                int approvalChoice = JOptionPane.showOptionDialog(
                        null,
                        "Approve (1) or Reject (0) the user registration?",
                        "Approval",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Approve", "Reject", "Skip"},
                        "Approve");

                if (approvalChoice == JOptionPane.YES_OPTION || approvalChoice == JOptionPane.NO_OPTION) {
                    approveRejectUserRegistration(userId, approvalChoice);
                } else if (approvalChoice == 2) {
                    continue;  // Skip to the next user
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                }
            }

            JOptionPane.showMessageDialog(null, "No more users pending approval.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while fetching users pending approval: " + e.getMessage());
        }
    }

    private void displayUserDetails(int userId, String name, String dob, String phoneNumber, String aadhaarNumber, String email, String city, String state) {
        JOptionPane.showMessageDialog(null,
                "User ID: " + userId + "\n" +
                        "Name: " + name + "\n" +
                        "Date of Birth: " + dob + "\n" +
                        "Phone Number: " + phoneNumber + "\n" +
                        "Aadhaar Number: " + aadhaarNumber + "\n" +
                        "Email: " + email + "\n" +
                        "City: " + city + "\n" +
                        "State: " + state + "\n" +
                        "-----------------------------------");
    }

    private void approveRejectUserRegistration(int userId, int approvalChoice) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            if (approvalChoice == JOptionPane.YES_OPTION) {
                // Generate a 12-digit unique ID (example using UUID)
                String uniqueId = generateUniqueId();

                // Update the database with the unique ID
                String sql = "UPDATE users SET approved = true, unique_id = '" + uniqueId + "' WHERE id = " + userId;
                int updatedRows = statement.executeUpdate(sql);

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(null, "User registration approved. Unique ID: " + uniqueId);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found with ID: " + userId);
                }
            } else {
                // If rejected, simply update the approved status
                String sql = "UPDATE users SET approved = false WHERE id = " + userId;
                int updatedRows = statement.executeUpdate(sql);

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(null, "User registration rejected.");
                } else {
                    JOptionPane.showMessageDialog(null, "User not found with ID: " + userId);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while approving/rejecting user registration: " + e.getMessage());
        }
    }

    private String generateUniqueId() {
        // Example using UUID to generate a random unique ID
        UUID uuid = UUID.randomUUID();
        long longValue = uuid.getMostSignificantBits();
        return String.valueOf(Math.abs(longValue)).substring(0, 12);
    }

    private void searchUserByUniqueId() {
              String uniqueId = JOptionPane.showInputDialog("Enter the Unique ID:");
        if (uniqueId != null && !uniqueId.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM users WHERE unique_id = '" + uniqueId + "'";
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String dob = resultSet.getString("dob");
                    String phoneNumber = resultSet.getString("phone_number");
                    String aadhaarNumber = resultSet.getString("aadhaar_number");
                    String email = resultSet.getString("email");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");

                    displayUserDetails(userId, name, dob, phoneNumber, aadhaarNumber, email, city, state);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found with Unique ID: " + uniqueId);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error occurred while searching user by Unique ID: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Unique ID. Please try again.");
        }
    }

    private void returnToMainMenu() {
        dispose(); // Close the AdminMenu window
        SwingUtilities.invokeLater(() -> new Main().setVisible(true)); // Open the Main window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminMenu().setVisible(true);
            }
        });
    }
}
