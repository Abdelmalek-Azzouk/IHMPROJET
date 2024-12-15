package com.project.ui;

import com.project.database.DatabaseConnection;
import com.project.model.Client;
import com.project.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddClient extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color HOVER_COLOR = new Color(25, 118, 210);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Login Components
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;

    // Signup Components
    private JTextField nameField, surnameField, signupEmailField, passwordField, addressField;
    private JComboBox<String> roleComboBox;

    public AddClient() {
        setTitle("Login / Signup");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create Login and Signup Panels
        JPanel loginPanel = createLoginPanel();
        JPanel signupPanel = createSignupPanel();

        // Add panels to card layout
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(signupPanel, "Signup");

        // Add card panel to frame
        add(cardPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        // Title
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(LABEL_FONT);
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        loginEmailField = createStyledTextField();
        mainPanel.add(loginEmailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(LABEL_FONT);
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPasswordField = new JPasswordField();
        loginPasswordField.setFont(INPUT_FONT);
        loginPasswordField.setPreferredSize(new Dimension(250, 40));
        loginPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(loginPasswordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = createStyledButton("Login");
        mainPanel.add(loginButton, gbc);

        // Sign up text
        gbc.gridy = 4;
        JLabel signupLabel = new JLabel("Don't have an account?", SwingConstants.CENTER);
        signupLabel.setFont(LABEL_FONT);
        signupLabel.setForeground(Color.GRAY);
        mainPanel.add(signupLabel, gbc);

        // Make "Sign up here" part clickable	
        JLabel signupHereLabel = new JLabel("Sign up here", SwingConstants.CENTER);
        signupHereLabel.setFont(LABEL_FONT);
        signupHereLabel.setForeground(PRIMARY_COLOR);
        signupHereLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover and click effects
        signupHereLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                signupHereLabel.setForeground(HOVER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                signupHereLabel.setForeground(PRIMARY_COLOR);
            }
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "Signup");
            }
        });

        gbc.gridy = 5;
        mainPanel.add(signupHereLabel, gbc);

        // Login Button Action Listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginEmailField.getText();
                String password = new String(loginPasswordField.getPassword());
                
                Client authenticatedClient = authenticateClient(email, password);
                if (authenticatedClient != null) {
                    JOptionPane.showMessageDialog(AddClient.this, "Login Successful!");
                    dispose();
                    new MainMenu(authenticatedClient);
                } else {
                    JOptionPane.showMessageDialog(AddClient.this, "Invalid email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return mainPanel;
    }

    private JPanel createSignupPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        // Title
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("First Name:");
        nameLabel.setFont(LABEL_FONT);
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = createStyledTextField();
        mainPanel.add(nameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel surnameLabel = new JLabel("Last Name:");
        surnameLabel.setFont(LABEL_FONT);
        mainPanel.add(surnameLabel, gbc);

        gbc.gridx = 1;
        surnameField = createStyledTextField();
        mainPanel.add(surnameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(LABEL_FONT);
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        signupEmailField = createStyledTextField();
        mainPanel.add(signupEmailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(LABEL_FONT);
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setFont(INPUT_FONT);
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(passwordField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(LABEL_FONT);
        mainPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        addressField = createStyledTextField();
        mainPanel.add(addressField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(LABEL_FONT);
        mainPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"client"});
        roleComboBox.setFont(INPUT_FONT);
        roleComboBox.setPreferredSize(new Dimension(250, 40));
        mainPanel.add(roleComboBox, gbc);

        // Submit Button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JButton submitButton = createStyledButton("Submit");
        mainPanel.add(submitButton, gbc);

        // Login switch text
        JLabel loginLabel = new JLabel("Already have an account?", SwingConstants.CENTER);
        loginLabel.setFont(LABEL_FONT);
        loginLabel.setForeground(Color.GRAY);
        gbc.gridy = 8;
        mainPanel.add(loginLabel, gbc);

        JLabel loginHereLabel = new JLabel("Login here", SwingConstants.CENTER);
        loginHereLabel.setFont(LABEL_FONT);
        loginHereLabel.setForeground(PRIMARY_COLOR);
        loginHereLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover and click effects
        loginHereLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginHereLabel.setForeground(HOVER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                loginHereLabel.setForeground(PRIMARY_COLOR);
            }
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "Login");
            }
        });

        gbc.gridy = 9;
        mainPanel.add(loginHereLabel, gbc);

        // Action Listener for submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = signupEmailField.getText();
                String password = passwordField.getText();
                String address = addressField.getText();
                String role = (String) roleComboBox.getSelectedItem();
                
                // Insert into Client table
                if (insertClient(name, surname, email, password, address)) {
                    JOptionPane.showMessageDialog(AddClient.this, "Signup Successful!");
                    cardLayout.show(cardPanel, "Login");
                }
            }
        });

        return mainPanel;
    }

    private boolean insertClient(String name, String surname, String email, String password, String address) {
        String insertClientQuery = "INSERT INTO Client (nom, prenom, email, password, adresse) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement clientStmt = conn.prepareStatement(insertClientQuery);
            clientStmt.setString(1, name);
            clientStmt.setString(2, surname);
            clientStmt.setString(3, email);
            clientStmt.setString(4, password);
            clientStmt.setString(5, address);
            clientStmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding client.");
            return false;
        }
    }

    private Client authenticateClient(String email, String password) {
        String query = "SELECT * FROM Client WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Create a Client instance with the data from the database
                String name = rs.getString("nom");
                String surname = rs.getString("prenom");
                String dbEmail = rs.getString("email");
                String dbPassword = rs.getString("password");
                String address = rs.getString("adresse");
                
                // Create and return the Client object
                return new Client(getDefaultCloseOperation(), name, surname, dbEmail, dbPassword, address, address);
            }
            return null;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(INPUT_FONT);
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddClient());
    }
}