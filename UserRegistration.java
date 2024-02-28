package com.nis.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.nis.database.DatabaseConnector;

public class UserRegistration extends JFrame {

    private JTextField nameField;
    private JTextField dobField;
    private JTextField phoneNumberField;
    private JTextField aadhaarNumberField;
    private JTextField emailField;
    private JTextField cityField;
    private JTextField stateField;
    private JPasswordField passwordField;

    public UserRegistration() {
        setTitle("User Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        addComponents();

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void initComponents() {
        nameField = new JTextField(15);
        dobField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        aadhaarNumberField = new JTextField(15);
        emailField = new JTextField(15);
        cityField = new JTextField(15);
        stateField = new JTextField(15);
        passwordField = new JPasswordField(15);
    }

    private void addComponents() {
        setLayout(new GridLayout(10, 2, 10, 10));

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        add(dobField);

        add(new JLabel("Phone Number:"));
        add(phoneNumberField);

        add(new JLabel("Aadhaar Number:"));
        add(aadhaarNumberField);

        add(new JLabel("Email:"));
        add(emailField);

        add(new JLabel("City:"));
        add(cityField);

        add(new JLabel("State:"));
        add(stateField);

        add(new JLabel("Password:"));
        add(passwordField);

        JButton registerButton = new JButton("Register");
        add(registerButton);

        JButton backButton = new JButton("Back"); // New Back button
        add(backButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToMainMenu();
            }
        });
    }

    private void registerUser() {
        String name = nameField.getText();
        String dob = dobField.getText();
        String phoneNumber = phoneNumberField.getText();
        String aadhaarNumber = aadhaarNumberField.getText();
        String email = emailField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String password = new String(passwordField.getPassword());

        if (validateInputs()) {
            try (Connection connection = DatabaseConnector.getConnection()) {
                String sql = "INSERT INTO users (name, dob, phone_number, aadhaar_number, email, city, state, password, approved, unique_id) VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, false, null)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, dob);
                    statement.setString(3, phoneNumber);
                    statement.setString(4, aadhaarNumber);
                    statement.setString(5, email);
                    statement.setString(6, city);
                    statement.setString(7, state);
                    statement.setString(8, password);

                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "User registration pending approval.");
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error occurred while registering user.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while connecting to the database.");
            }
        }
    }

    private void clearFields() {
    	nameField.setText("");
        dobField.setText("");
        phoneNumberField.setText("");
        aadhaarNumberField.setText("");
        emailField.setText("");
        cityField.setText("");
        stateField.setText("");
        passwordField.setText("");
		
	}

	private boolean validateInputs() {
        String name = nameField.getText();
        String dob = dobField.getText();
        String phoneNumber = phoneNumberField.getText();
        String aadhaarNumber = aadhaarNumberField.getText();
        String email = emailField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || dob.isEmpty() || phoneNumber.isEmpty() || aadhaarNumber.isEmpty() ||
                email.isEmpty() || city.isEmpty() || state.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required. Please fill in all the information.");
            return false;
        }

        if (isPhoneNumberExists(phoneNumber) || isAadhaarNumberExists(aadhaarNumber)) {
            JOptionPane.showMessageDialog(null, "Error: Phone number, Aadhaar number, or email already in use. Use different credentials.");
            return false;
        }

        // Check if the phone number is exactly 10 digits
        if (phoneNumber.length() != 10) {
            JOptionPane.showMessageDialog(null, "Error: Phone number should be exactly 10 digits.");
            return false;
        }

        // Check if the Aadhaar number is exactly 12 digits
        if (aadhaarNumber.length() != 12) {
            JOptionPane.showMessageDialog(null, "Error: Aadhaar number should be exactly 12 digits.");
            return false;
        }

        // Check if the phone number already exists in the database
        if (isPhoneNumberExists(phoneNumber)) {
            JOptionPane.showMessageDialog(null, "Error: Phone number already in use. Use a different phone number.");
            return false;
        }

        // Check if the Aadhaar number already exists in the database
        if (isAadhaarNumberExists(aadhaarNumber)) {
            JOptionPane.showMessageDialog(null, "Error: Aadhaar number already in use. Use a different Aadhaar number.");
            return false;
        }

        // You can add more specific validation logic here if needed

        return true;
    }

    private boolean isPhoneNumberExists(String phoneNumber) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE phone_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, phoneNumber);
                return statement.executeQuery().next();
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while checking phone number existence: " + e.getMessage());
            return false;
        }
    }

    private boolean isAadhaarNumberExists(String aadhaarNumber) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE aadhaar_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, aadhaarNumber);
                return statement.executeQuery().next();
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while checking Aadhaar number existence: " + e.getMessage());
            return false;
        }
    }

    private void goBackToMainMenu() {
        dispose(); // Close the UserRegistration frame
        new Main(); // Open the Main frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserRegistration());
    }
}
