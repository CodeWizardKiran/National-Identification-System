package com.nis.gui;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogin extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Database_Name";
    private static final String DB_USERNAME = "Username";
    private static final String DB_PASSWORD = "password";

    private JTextField phoneNumberField;
    private JPasswordField passwordField;

    public UserLogin() {
        setTitle("User Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        panel.add(phoneNumberField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText();
                String password = new String(passwordField.getPassword());
                loginUser(phoneNumber, password);
            }
        });
        panel.add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
        panel.add(backButton);

        add(panel);
    }

    private void loginUser(String phoneNumber, String password) {
    	 try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                String sql = "SELECT * FROM users WHERE phone_number = '" + phoneNumber + "' AND password = '" + password + "' AND approved = true";
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!\n" +
                            "Your Unique ID: " + resultSet.getString("unique_id") + "\n" +
                            "Your ID: " + resultSet.getInt("id") + "\n" +
                            "Your Name: " + resultSet.getString("name") + "\n" +
                            "Your Aadhaar Number: " + resultSet.getString("aadhaar_number") + "\n" +
                            "Your Email: " + resultSet.getString("email") + "\n" +
                            "Your City: " + resultSet.getString("city") + "\n" +
                            "Your State: " + resultSet.getString("state"));
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid phone number, password, or pending approval. Please try again.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while logging in: " + e.getMessage());
            }
    }

    private void returnToMainMenu() {
        dispose(); // Close the UserLogin window
        SwingUtilities.invokeLater(() -> new Main().setVisible(true)); // Open the Main window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserLogin userLogin = new UserLogin();
            userLogin.setVisible(true);
        });
    }
}
