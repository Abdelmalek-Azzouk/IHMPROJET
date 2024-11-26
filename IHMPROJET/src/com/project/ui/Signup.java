package com.project.ui;

import com.project.database.DatabaseConnection;
import com.project.model.Client;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Signup {
    private JFrame frame;
    private JTextField nameField, surnameField, emailField, passwordField;
    private JComboBox<String> roleComboBox;
    private JTextComponent addressField;

    public Signup() {
        // Setup frame
        frame = new JFrame("Sign Up");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Add input fields
        frame.add(new JLabel("First Name:"));
        nameField = new JTextField(20);
        frame.add(nameField);

        frame.add(new JLabel("Last Name:"));
        surnameField = new JTextField(20);
        frame.add(surnameField);

        frame.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        frame.add(emailField);

        frame.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        frame.add(passwordField);
        
        // Add address input field
        frame.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(20);
        frame.add(addressField);

        frame.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[] { "client" });
        frame.add(roleComboBox);

        // Submit button
        JButton submitButton = new JButton("Submit");
        frame.add(submitButton);

        // Action Listener for submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String address = addressField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                // Insert only into Client table
                insertClient(name, surname, email, address);
            }
        });

        frame.setVisible(true);
    }

    private void insertClient(String name, String surname, String email, String address) {
        String insertClientQuery = "INSERT INTO Client (nom, prenom, email, adresse) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insert into Client table
            PreparedStatement clientStmt = conn.prepareStatement(insertClientQuery);
            clientStmt.setString(1, name);
            clientStmt.setString(2, surname);
            clientStmt.setString(3, email);
            clientStmt.setString(4, address);  // Set the address
            clientStmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Client added successfully!");

            // Clear fields after submission
            nameField.setText("");
            surnameField.setText("");
            emailField.setText("");
            passwordField.setText("");  // Clear the password field
            addressField.setText("");  // Clear the address field
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while adding client.");
        }
    }

    public static void main(String[] args) {
        new Signup(); // Show the sign-up form
    }
}
