package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import javax.swing.Timer;

public class RegistrationFormAWT extends Frame {

    Label title, nameLbl, emailLbl, passLbl, confirmPassLbl, genderLbl, courseLbl, msgLbl, loginLinkLbl;
    TextField nameTxt, emailTxt, passTxt, confirmPassTxt;
    Checkbox male, female;
    CheckboxGroup genderGroup;
    Choice courseChoice;
    Button registerBtn, clearBtn;

    RegistrationFormAWT() {

        // Frame settings
        setTitle("Registration Form");
        setSize(450, 450);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        /* ---------- TITLE PANEL ---------- */
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(70, 130, 180));

        title = new Label("User Registration Form", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        /* ---------- FORM PANEL ---------- */
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        nameLbl = new Label("Name:");
        nameLbl.setFont(labelFont);
        nameTxt = new TextField();
        nameTxt.setFont(new Font("Arial", Font.PLAIN, 13));

        emailLbl = new Label("Email:");
        emailLbl.setFont(labelFont);
        emailTxt = new TextField();
        emailTxt.setFont(new Font("Arial", Font.PLAIN, 13));

        passLbl = new Label("Password:");
        passLbl.setFont(labelFont);
        passTxt = new TextField();
        passTxt.setEchoChar('*');
        passTxt.setFont(new Font("Arial", Font.PLAIN, 13));

        confirmPassLbl = new Label("Confirm Password:");
        confirmPassLbl.setFont(labelFont);
        confirmPassTxt = new TextField();
        confirmPassTxt.setEchoChar('*');
        confirmPassTxt.setFont(new Font("Arial", Font.PLAIN, 13));

        genderLbl = new Label("Gender:");
        genderLbl.setFont(labelFont);

        genderGroup = new CheckboxGroup();
        male = new Checkbox("Male", genderGroup, false);
        female = new Checkbox("Female", genderGroup, false);

        Panel genderPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(new Color(245, 245, 245));
        genderPanel.add(male);
        genderPanel.add(female);

        courseLbl = new Label("Course:");
        courseLbl.setFont(labelFont);

        courseChoice = new Choice();
        courseChoice.add("Select");
        courseChoice.add("BCA");
        courseChoice.add("MCA");
        courseChoice.add("B.Tech");
        courseChoice.add("M.Tech");
        courseChoice.setFont(new Font("Arial", Font.PLAIN, 13));

        formPanel.add(nameLbl);
        formPanel.add(nameTxt);

        formPanel.add(emailLbl);
        formPanel.add(emailTxt);

        formPanel.add(passLbl);
        formPanel.add(passTxt);

        formPanel.add(confirmPassLbl);
        formPanel.add(confirmPassTxt);

        formPanel.add(genderLbl);
        formPanel.add(genderPanel);

        formPanel.add(courseLbl);
        formPanel.add(courseChoice);

        add(formPanel, BorderLayout.CENTER);

        /* ---------- BOTTOM PANEL ---------- */
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));

        /* ---------- BUTTON PANEL ---------- */
        Panel buttonPanel = new Panel();
        buttonPanel.setBackground(new Color(245, 245, 245));

        registerBtn = new Button("Register");
        clearBtn = new Button("Clear");

        registerBtn.setBackground(new Color(60, 179, 113));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 13));

        clearBtn.setBackground(new Color(220, 20, 60));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 13));

        buttonPanel.add(registerBtn);
        buttonPanel.add(clearBtn);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        /* ---------- MESSAGE LABEL ---------- */
        msgLbl = new Label("", Label.CENTER);
        msgLbl.setForeground(Color.RED);
        msgLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(msgLbl, BorderLayout.CENTER);

        /* ---------- LOGIN LINK ---------- */
        Panel loginPanel = new Panel();
        loginPanel.setBackground(new Color(245, 245, 245));

        loginLinkLbl = new Label("Already have an account? Click here to Login", Label.CENTER);
        loginLinkLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        loginLinkLbl.setForeground(new Color(70, 130, 180));
        loginLinkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginPanel.add(loginLinkLbl);
        bottomPanel.add(loginPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        /* ---------- EVENT HANDLERS ---------- */

        // Register button action
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        // Clear button action
        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Login link action
        loginLinkLbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openLoginForm();
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

    private void handleRegistration() {
        String name = nameTxt.getText().trim();
        String email = emailTxt.getText().trim();
        String password = passTxt.getText();
        String confirmPassword = confirmPassTxt.getText();
        Checkbox selectedGender = genderGroup.getSelectedCheckbox();
        String course = courseChoice.getSelectedItem();

        // Validation
        String validationError = validateInputs(name, email, password, confirmPassword, selectedGender, course);

        if (validationError != null) {
            msgLbl.setText(validationError);
            msgLbl.setForeground(Color.RED);
            return;
        }

        // If validation passes
        msgLbl.setText("Registration successful! Redirecting to login...");
        msgLbl.setForeground(new Color(60, 179, 113));

        // Delay to show success message
        Timer timer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLoginForm();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private String validateInputs(String name, String email, String password, String confirmPassword,
                                  Checkbox selectedGender, String course) {

        // Name validation
        if (name.isEmpty()) {
            return "Name is required!";
        }

        if (name.length() < 2) {
            return "Name must be at least 2 characters!";
        }

        if (!name.matches("^[a-zA-Z\\s]+$")) {
            return "Name should contain only letters!";
        }

        // Email validation
        if (email.isEmpty()) {
            return "Email is required!";
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if (!emailPattern.matcher(email).matches()) {
            return "Invalid email format!";
        }

        // Password validation
        if (password.isEmpty()) {
            return "Password is required!";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters!";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter!";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter!";
        }

        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one digit!";
        }

        if (!password.matches(".*[@#$%^&+=!].*")) {
            return "Password must contain at least one special character (@#$%^&+=!)!";
        }

        // Confirm password validation
        if (confirmPassword.isEmpty()) {
            return "Please confirm your password!";
        }

        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }

        // Gender validation
        if (selectedGender == null) {
            return "Please select a gender!";
        }

        // Course validation
        if (course.equals("Select")) {
            return "Please select a course!";
        }

        return null; // No errors
    }

    private void clearForm() {
        nameTxt.setText("");
        emailTxt.setText("");
        passTxt.setText("");
        confirmPassTxt.setText("");
        genderGroup.setSelectedCheckbox(null);
        courseChoice.select(0);
        msgLbl.setText("");
    }

    private void openLoginForm() {
        new LoginFormAWT();
        dispose();
    }

    public static void main(String[] args) {
        new RegistrationFormAWT();
    }
}