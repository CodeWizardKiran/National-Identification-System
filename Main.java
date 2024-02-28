package com.nis.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private UserRegistration userRegistrationFrame;
    private UserLogin userLoginFrame;
    private AdminLogin adminLoginFrame;

    public Main() {
        setTitle("National Identification System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        addComponents();

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void initComponents() {
        // Initialize components...
    }

    private void addComponents() {
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton userRegistrationButton = new JButton("User Registration");
        JButton userLoginButton = new JButton("User Login");
        JButton adminLoginButton = new JButton("Admin Login");
        JButton exitButton = new JButton("Exit");

        add(userRegistrationButton);
        add(userLoginButton);
        add(adminLoginButton);
        add(exitButton);

        userRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                userRegistrationFrame = new UserRegistration();
                userRegistrationFrame.setVisible(true);
            }
        });

        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                userLoginFrame = new UserLogin();
                userLoginFrame.setVisible(true);
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                adminLoginFrame = new AdminLogin();
                adminLoginFrame.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
