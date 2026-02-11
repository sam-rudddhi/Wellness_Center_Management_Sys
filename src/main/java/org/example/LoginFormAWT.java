package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import javax.swing.Timer;

public class LoginFormAWT extends Frame {

    Label title, emailLbl, passLbl, msgLbl, registerLinkLbl;
    TextField emailTxt;
    TextField passTxt;
    Button loginBtn, clearBtn;

    // Dummy account for testing
    private static final String DUMMY_EMAIL = "test@example.com";
    private static final String DUMMY_PASSWORD = "Test@123";

    LoginFormAWT() {

        // Frame settings
        setTitle("Login Form");
        setSize(450, 300);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        /* ---------- TITLE PANEL ---------- */
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(70, 130, 180));

        title = new Label("User Login", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        /* ---------- FORM PANEL ---------- */
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(3, 2, 15, 15));
        formPanel.setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        emailLbl = new Label("Email:");
        emailLbl.setFont(labelFont);
        emailTxt = new TextField();
        emailTxt.setFont(new Font("Arial", Font.PLAIN, 13));

        passLbl = new Label("Password:");
        passLbl.setFont(labelFont);
        passTxt = new TextField();
        passTxt.setEchoChar('*');
        passTxt.setFont(new Font("Arial", Font.PLAIN, 13));

        // Empty cells for spacing
        Label empty1 = new Label("");
        Label empty2 = new Label("");

        formPanel.add(emailLbl);
        formPanel.add(emailTxt);

        formPanel.add(passLbl);
        formPanel.add(passTxt);

        formPanel.add(empty1);
        formPanel.add(empty2);

        add(formPanel, BorderLayout.CENTER);

        /* ---------- BUTTON PANEL ---------- */
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));

        Panel buttonPanel = new Panel();
        buttonPanel.setBackground(new Color(245, 245, 245));

        loginBtn = new Button("Login");
        clearBtn = new Button("Clear");

        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 13));

        clearBtn.setBackground(new Color(220, 20, 60));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 13));

        buttonPanel.add(loginBtn);
        buttonPanel.add(clearBtn);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        /* ---------- MESSAGE LABEL ---------- */
        msgLbl = new Label("", Label.CENTER);
        msgLbl.setForeground(Color.RED);
        msgLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(msgLbl, BorderLayout.CENTER);

        /* ---------- REGISTER LINK ---------- */
        Panel registerPanel = new Panel();
        registerPanel.setBackground(new Color(245, 245, 245));

        registerLinkLbl = new Label("Don't have an account? Click here to Register", Label.CENTER);
        registerLinkLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        registerLinkLbl.setForeground(new Color(70, 130, 180));
        registerLinkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerPanel.add(registerLinkLbl);
        bottomPanel.add(registerPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        /* ---------- EVENT HANDLERS ---------- */

        // Login button action
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Clear button action
        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Register link action
        registerLinkLbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openRegistrationForm();
            }
        });

        // Enter key support for password field
        passTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
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

    private void handleLogin() {
        String email = emailTxt.getText().trim();
        String password = passTxt.getText();

        // Validation
        String validationError = validateInputs(email, password);

        if (validationError != null) {
            msgLbl.setText(validationError);
            msgLbl.setForeground(Color.RED);
            return;
        }

        // Verify credentials
        if (email.equals(DUMMY_EMAIL) && password.equals(DUMMY_PASSWORD)) {
            msgLbl.setText("Login successful! Redirecting...");
            msgLbl.setForeground(new Color(60, 179, 113));

            // Delay to show success message
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    openWelcomePage(email);
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            msgLbl.setText("Invalid email or password!");
            msgLbl.setForeground(Color.RED);
        }
    }

    private String validateInputs(String email, String password) {
        // Check if fields are empty
        if (email.isEmpty()) {
            return "Email is required!";
        }

        if (password.isEmpty()) {
            return "Password is required!";
        }

        // Email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if (!emailPattern.matcher(email).matches()) {
            return "Invalid email format!";
        }

        // Password validation (minimum 6 characters)
        if (password.length() < 6) {
            return "Password must be at least 6 characters!";
        }

        return null; // No errors
    }

    private void clearForm() {
        emailTxt.setText("");
        passTxt.setText("");
        msgLbl.setText("");
    }

    private void openRegistrationForm() {
        new RegistrationFormAWT();
        dispose();
    }

    private void openWelcomePage(String email) {
        new WelcomePageAWT(email);
        dispose();
    }

    public static void main(String[] args) {
        new LoginFormAWT();
    }
}