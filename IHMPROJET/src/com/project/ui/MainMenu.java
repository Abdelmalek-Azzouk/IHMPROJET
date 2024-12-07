package com.project.ui;

import com.project.database.DatabaseConnection;
import com.project.model.Client;
import com.project.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p1, p2, p3, p4, p5;
    private JButton b1, b2, b3, b4, bSignOut, bAddCar;
    private JTextField tf1;

    private static final Color BACKGROUND_COLOR = new Color(240, 246, 252);
    private static final Color PANEL_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(51, 102, 204);
    private static final Color SECONDARY_COLOR = new Color(102, 153, 255);
    private static final Color TEXT_COLOR = new Color(33, 43, 54);

    private Client currentClient;

    public MainMenu(Client client) {
        this.currentClient = client;
        setTitle("Vehicle Management");
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 800);

        p1 = new JPanel();
        p1.setBackground(PANEL_COLOR);
        p1.setLayout(new BorderLayout(10, 10));
        p1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        p4 = new JPanel();
        p4.setBackground(PANEL_COLOR);
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(300, 40));
        tf1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf1.setForeground(Color.GRAY);
        
        b1 = new JButton("Filter");
        b1.setBackground(PRIMARY_COLOR);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b1.setPreferredSize(new Dimension(100, 40));
        
        p4.add(tf1);
        p4.add(b1);

        p5 = new JPanel();
        p5.setBackground(PANEL_COLOR);
        p5.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        b2 = createStyledButton("Balance");
        b3 = createStyledButton("Settings");
        b4 = createStyledButton("Profile");
        bSignOut = createStyledButton("Sign Out");

        // Profile button action
        b4.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               openProfilePage();
           }
        });

        // Sign Out button action
        bSignOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentClient = null;
                dispose();
                new Signup();
            }
        });

        p5.add(b2);
        p5.add(b3);
        p5.add(b4);
        p5.add(bSignOut);

        p1.add(p4, BorderLayout.WEST);
        p1.add(p5, BorderLayout.EAST);
        

        // Vehicle display panel
        p2 = new JPanel();
        p2.setBackground(BACKGROUND_COLOR);
        p2.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 20));
        JScrollPane scrollPane = new JScrollPane(p2);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(null);

        // Load and display vehicles
        loadVehicles();

        add(p1, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    private void loadVehicles() {
        // Clear any existing vehicle panels
        p2.removeAll();

        // Get available vehicles from database
        List<Vehicle> vehicles = DatabaseConnection.getAvailableVehicles();

        // Add vehicle panels dynamically
        for (Vehicle vehicle : vehicles) {
            p2.add(createVehiclePanel(vehicle));
        }

        // Add "Add Car" panel as the last panel
        bAddCar = createAddCarPanel();
        p2.add(bAddCar);

        // Refresh the panel
        p2.revalidate();
        p2.repaint();
    }
    

    private JPanel createVehiclePanel(Vehicle vehicle) {
        JPanel carPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PANEL_COLOR);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
                g2.dispose();
            }
        };
        carPanel.setLayout(new BorderLayout());
        carPanel.setPreferredSize(new Dimension(500, 400));
        carPanel.setBackground(PANEL_COLOR);

        carPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) 
        ));

        // Detailed vehicle information
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(PANEL_COLOR);

        JLabel brandModelLabel = new JLabel(vehicle.getMarque() + " " + vehicle.getModele());
        JLabel yearLabel = new JLabel("Year: " + vehicle.getAnnee());
        JLabel fuelTypeLabel = new JLabel("Fuel: " + vehicle.getCarburant());
        JLabel priceLabel = new JLabel("Price/Day: $" + vehicle.getPrixLocationJour());

        brandModelLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fuelTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        detailPanel.add(brandModelLabel);
        detailPanel.add(yearLabel);
        detailPanel.add(fuelTypeLabel);
        detailPanel.add(priceLabel);
        

        carPanel.add(detailPanel, BorderLayout.CENTER);
        

        return carPanel;
    }

    private JButton createAddCarPanel() {
        JButton addCarButton = new JButton("+") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PANEL_COLOR);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
                g2.setColor(PRIMARY_COLOR);
                g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
            }
        };
        
        
        addCarButton.setPreferredSize(new Dimension(500, 400));
        addCarButton.setBackground(PANEL_COLOR);
        addCarButton.setForeground(PRIMARY_COLOR);
        addCarButton.setFont(new Font("Segoe UI", Font.BOLD, 100));
        addCarButton.setBorderPainted(false);
        
        addCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCarDialog(MainMenu.this);
                // Reload vehicles after adding
                loadVehicles();
            }
        });
        
        return addCarButton;
        
    }
    
   
   
   private void openProfilePage() {
	    // Create a new JFrame for the profile page
	    JFrame profileFrame = new JFrame("Client Profile");
	    profileFrame.setSize(450, 350);
	    profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    profileFrame.setLayout(new BorderLayout());

	    // Define color scheme
	    Color backgroundColor = new Color(240, 245, 255); // Soft blue background
	    Color panelColor = new Color(220, 230, 255);      // Slightly darker blue panel
	    Color textColor = new Color(30, 30, 60);          // Darker text for readability
	    Color borderColor = new Color(180, 200, 230);     // Subtle border color

	    // Create a panel to hold client information
	    JPanel profilePanel = new JPanel();
	    profilePanel.setBackground(panelColor);
	    profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
	    profilePanel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(borderColor, 2),
	        BorderFactory.createEmptyBorder(20, 20, 20, 20)
	    ));

	    // 
	    JLabel profileName = new JLabel("Name: " + currentClient.getName() + " " + currentClient.getSurname());
	    JLabel profileEmail = new JLabel("Email: " + currentClient.getEmail());
	    JLabel profileAddress = new JLabel("Address: " + currentClient.getAddress());

	    Font profileFont = new Font("Segoe UI", Font.PLAIN, 16);
	    profileName.setFont(profileFont);
	    profileEmail.setFont(profileFont);
	    profileAddress.setFont(profileFont);

	    profileName.setForeground(textColor);
	    profileEmail.setForeground(textColor);
	    profileAddress.setForeground(textColor);

	    profilePanel.add(profileName);
	    profilePanel.add(Box.createRigidArea(new Dimension(0, 15))); 
	    profilePanel.add(profileEmail);
	    profilePanel.add(Box.createRigidArea(new Dimension(0, 15))); 
	    profilePanel.add(profileAddress);

	    
	    JLabel titleLabel = new JLabel("Client Profile");
	    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	    titleLabel.setForeground(textColor);
	    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	    profileFrame.add(titleLabel, BorderLayout.NORTH);

	    
	    profileFrame.add(profilePanel, BorderLayout.CENTER);

	    // close buton
	    JButton closeButton = new JButton("Close");
	    closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    closeButton.setBackground(new Color(200, 220, 255)); 
	    closeButton.setForeground(textColor);
	    closeButton.setFocusPainted(false);
	    closeButton.setBorder(BorderFactory.createLineBorder(borderColor, 1));
	   
	    closeButton.addActionListener(e -> profileFrame.dispose());
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setBackground(backgroundColor);
	    buttonPanel.add(closeButton);
	    profileFrame.add(buttonPanel, BorderLayout.SOUTH);

	    
	    profileFrame.getContentPane().setBackground(backgroundColor);
	    profileFrame.setLocationRelativeTo(null);
	    profileFrame.setVisible(true);
	}


}