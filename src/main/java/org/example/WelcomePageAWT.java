package org.example;

import java.awt.*;
import java.awt.event.*;

public class WelcomePageAWT extends Frame {

    Label title, welcomeMsgLbl, userEmailLbl, infoLbl;
    Button logoutBtn;

    WelcomePageAWT(String email) {

        // Frame settings
        setTitle("Welcome");
        setSize(500, 350);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        /* ---------- TITLE PANEL ---------- */
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(60, 179, 113));

        title = new Label("Welcome to Our Application!", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        /* ---------- CONTENT PANEL ---------- */
        Panel contentPanel = new Panel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Welcome icon/graphic using a colored panel
        Panel iconPanel = new Panel();
        iconPanel.setBackground(new Color(70, 130, 180));
        iconPanel.setPreferredSize(new Dimension(80, 80));
        Label iconLabel = new Label("✓", Label.CENTER);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 50));
        iconLabel.setForeground(Color.WHITE);
        iconPanel.add(iconLabel);

        gbc.gridy = 0;
        contentPanel.add(iconPanel, gbc);

        // Welcome message
        welcomeMsgLbl = new Label("Login Successful!", Label.CENTER);
        welcomeMsgLbl.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMsgLbl.setForeground(new Color(60, 179, 113));

        gbc.gridy = 1;
        contentPanel.add(welcomeMsgLbl, gbc);

        // User email
        userEmailLbl = new Label("Logged in as: " + email, Label.CENTER);
        userEmailLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        userEmailLbl.setForeground(new Color(70, 70, 70));

        gbc.gridy = 2;
        contentPanel.add(userEmailLbl, gbc);

        // Additional info
        infoLbl = new Label("You have successfully logged into the system.", Label.CENTER);
        infoLbl.setFont(new Font("Arial", Font.ITALIC, 13));
        infoLbl.setForeground(new Color(100, 100, 100));

        gbc.gridy = 3;
        contentPanel.add(infoLbl, gbc);

        add(contentPanel, BorderLayout.CENTER);

        /* ---------- BUTTON PANEL ---------- */
        Panel buttonPanel = new Panel();
        buttonPanel.setBackground(new Color(245, 245, 245));

        logoutBtn = new Button("Logout");
        logoutBtn.setBackground(new Color(220, 20, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setPreferredSize(new Dimension(120, 35));

        buttonPanel.add(logoutBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        /* ---------- EVENT HANDLERS ---------- */

        // Logout button action
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });

        /* ---------- WINDOW CLOSE ---------- */
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Center the frame on screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleLogout() {
        // Confirmation dialog
        Dialog confirmDialog = new Dialog(this, "Confirm Logout", true);
        confirmDialog.setLayout(new BorderLayout());
        confirmDialog.setSize(300, 150);
        confirmDialog.setBackground(new Color(245, 245, 245));

        Label confirmMsg = new Label("Are you sure you want to logout?", Label.CENTER);
        confirmMsg.setFont(new Font("Arial", Font.PLAIN, 13));
        confirmDialog.add(confirmMsg, BorderLayout.CENTER);

        Panel dialogButtonPanel = new Panel();
        dialogButtonPanel.setBackground(new Color(245, 245, 245));

        Button yesBtn = new Button("Yes");
        yesBtn.setBackground(new Color(60, 179, 113));
        yesBtn.setForeground(Color.WHITE);

        Button noBtn = new Button("No");
        noBtn.setBackground(new Color(220, 20, 60));
        noBtn.setForeground(Color.WHITE);

        dialogButtonPanel.add(yesBtn);
        dialogButtonPanel.add(noBtn);
        confirmDialog.add(dialogButtonPanel, BorderLayout.SOUTH);

        yesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmDialog.dispose();
                new LoginFormAWT();
                dispose();
            }
        });

        noBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmDialog.dispose();
            }
        });

        confirmDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                confirmDialog.dispose();
            }
        });

        confirmDialog.setLocationRelativeTo(this);
        confirmDialog.setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomePageAWT("test@example.com");
    }
}