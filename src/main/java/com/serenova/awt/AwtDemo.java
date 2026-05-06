package com.serenova.awt;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * AwtDemo - AWT Components Demonstration
 * 
 * This class demonstrates:
 * - Creating a Frame with an attractive layout
 * - Adding various AWT components (TextFields, Buttons, Choice, etc.)
 * - Implementing event listeners
 * - Performing validation on user input
 * - Proper resource management
 * 
 * To run: java com.serenova.awt.AwtDemo
 */
public class AwtDemo extends Frame {
    
    private static final long serialVersionUID = 1L;
    
    // ═══════════════════════════════════════════════════════════
    // UI COMPONENTS
    // ═══════════════════════════════════════════════════════════
    
    // Input Fields
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField phoneField;
    private TextArea feedbackArea;
    
    // Constants for TextArea scroll bars
    private static final int SCROLLBARS_VERTICAL = TextArea.SCROLLBARS_VERTICAL_ONLY;
    
    // Dropdown
    private Choice roleChoice;
    
    // Buttons
    private Button submitButton;
    private Button clearButton;
    private Button exitButton;
    
    // Labels
    private Label statusLabel;
    private Label validationLabel;
    
    // Validation results
    private Label resultLabel;
    
    /**
     * Constructor - Initialize Frame and Components
     */
    public AwtDemo() {
        // Frame Configuration
        super("Serenova Wellness - AWT Components Demo");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(true);
        setBackground(new Color(250, 247, 242)); // Cream color
        
        // Create UI
        initializeComponents();
        addEventListeners();
        
        // Window Close Handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("✓ Application closed successfully.");
                System.exit(0);
            }
        });
    }
    
    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        // Main Panel
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(250, 247, 242));
        
        // ─────────────────────────────────────────────────
        // HEADER PANEL
        // ─────────────────────────────────────────────────
        Panel headerPanel = new Panel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(92, 122, 96)); // Sage dark
        
        Label titleLabel = new Label("Wellness Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        Label subtitleLabel = new Label("Demonstrate AWT Components with Validation");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 217, 199));
        
        Panel titlePanelContainer = new Panel();
        titlePanelContainer.setLayout(new BorderLayout());
        titlePanelContainer.setBackground(new Color(92, 122, 96));
        titlePanelContainer.add(titleLabel, BorderLayout.CENTER);
        titlePanelContainer.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanelContainer);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ─────────────────────────────────────────────────
        // FORM PANEL (CENTER)
        // ─────────────────────────────────────────────────
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10));
        formPanel.setBackground(new Color(250, 247, 242));
        
        // First Name
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 11));
        firstNameField = new TextField(25);
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        
        // Last Name
        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 11));
        lastNameField = new TextField(25);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        
        // Email
        Label emailLabel = new Label("Email Address:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 11));
        emailField = new TextField(25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        
        // Phone
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 11));
        phoneField = new TextField(25);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        
        // Role (Dropdown)
        Label roleLabel = new Label("Select Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        roleChoice = new Choice();
        roleChoice.add("-- Select Role --");
        roleChoice.add("Patient");
        roleChoice.add("Doctor");
        roleChoice.add("Administrator");
        roleChoice.add("Nurse");
        roleChoice.add("Receptionist");
        formPanel.add(roleLabel);
        formPanel.add(roleChoice);
        
        // Feedback Area Label
        Label feedbackLabel = new Label("Additional Comments:");
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 11));
        feedbackArea = new TextArea(4, 25);
        feedbackArea.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(feedbackLabel);
        formPanel.add(feedbackArea);
        
        // Status Label
        statusLabel = new Label("Status: Ready");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        statusLabel.setForeground(new Color(92, 122, 96));
        formPanel.add(new Label("")); // Spacer
        formPanel.add(statusLabel);
        
        // Scroll Panel for Form
        Panel scrollWrapper = new Panel();
        scrollWrapper.setLayout(new BorderLayout());
        scrollWrapper.setBackground(new Color(250, 247, 242));
        scrollWrapper.add(formPanel, BorderLayout.CENTER);
        
        mainPanel.add(scrollWrapper, BorderLayout.CENTER);
        
        // ─────────────────────────────────────────────────
        // VALIDATION RESULT LABEL
        // ─────────────────────────────────────────────────
        validationLabel = new Label("Enter data and click Submit to validate");
        validationLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        validationLabel.setForeground(new Color(139, 125, 107));
        mainPanel.add(validationLabel, BorderLayout.SOUTH);
        
        // ─────────────────────────────────────────────────
        // BUTTON PANEL (SOUTH)
        // ─────────────────────────────────────────────────
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(235, 242, 236)); // Sage light
        
        submitButton = new Button("✓ Submit Form");
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setBackground(new Color(92, 122, 96));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusable(true);
        
        clearButton = new Button("✕ Clear Fields");
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setBackground(new Color(201, 169, 110));
        clearButton.setForeground(Color.WHITE);
        
        exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.setBackground(new Color(192, 57, 43));
        exitButton.setForeground(Color.WHITE);
        
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Add event listeners to components
     */
    private void addEventListeners() {
        // ─────────────────────────────────────────────────
        // REAL-TIME VALIDATION ON INPUT
        // ─────────────────────────────────────────────────
        firstNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateStatus("First name: " + firstNameField.getText());
            }
        });
        
        lastNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateStatus("Last name: " + lastNameField.getText());
            }
        });
        
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateStatus("Email being entered...");
            }
        });
        
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateStatus("Phone: " + phoneField.getText());
            }
        });
        
        // ─────────────────────────────────────────────────
        // ROLE SELECTION
        // ─────────────────────────────────────────────────
        roleChoice.addItemListener(e -> {
            String selected = roleChoice.getSelectedItem();
            updateStatus("Role selected: " + selected);
        });
        
        // ─────────────────────────────────────────────────
        // SUBMIT BUTTON - VALIDATION LOGIC
        // ─────────────────────────────────────────────────
        submitButton.addActionListener(e -> {
            validateAndSubmit();
        });
        
        // ─────────────────────────────────────────────────
        // CLEAR BUTTON
        // ─────────────────────────────────────────────────
        clearButton.addActionListener(e -> {
            clearAllFields();
            updateStatus("All fields cleared");
        });
        
        // ─────────────────────────────────────────────────
        // EXIT BUTTON
        // ─────────────────────────────────────────────────
        exitButton.addActionListener(e -> {
            System.out.println("✓ Application closed by user.");
            System.exit(0);
        });
        
        // Keyboard shortcut: Enter to submit
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (e.getComponent() instanceof TextField) {
                    validateAndSubmit();
                    return true;
                }
            }
            return false;
        });
    }
    
    /**
     * ═══════════════════════════════════════════════════════════
     * VALIDATION LOGIC
     * ═══════════════════════════════════════════════════════════
     */
    
    private void validateAndSubmit() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String role = roleChoice.getSelectedItem();
        String feedback = feedbackArea.getText().trim();
        
        // ─────────────────────────────────────────────────
        // VALIDATION CHECKS
        // ─────────────────────────────────────────────────
        
        if (firstName.isEmpty()) {
            showError("✗ First name is required");
            return;
        }
        
        if (firstName.length() < 2) {
            showError("✗ First name must be at least 2 characters");
            return;
        }
        
        if (!firstName.matches("^[a-zA-Z\\s]+$")) {
            showError("✗ First name must contain only letters");
            return;
        }
        
        if (lastName.isEmpty()) {
            showError("✗ Last name is required");
            return;
        }
        
        if (!lastName.matches("^[a-zA-Z\\s]+$")) {
            showError("✗ Last name must contain only letters");
            return;
        }
        
        if (email.isEmpty()) {
            showError("✗ Email address is required");
            return;
        }
        
        if (!isValidEmail(email)) {
            showError("✗ Invalid email format. Use: user@example.com");
            return;
        }
        
        if (phone.isEmpty()) {
            showError("✗ Phone number is required");
            return;
        }
        
        if (!isValidPhone(phone)) {
            showError("✗ Invalid phone format. Use 10 digits or +1-234-567-8900");
            return;
        }
        
        if ("-- Select Role --".equals(role)) {
            showError("✗ Please select a role");
            return;
        }
        
        // ─────────────────────────────────────────────────
        // ALL VALIDATIONS PASSED
        // ─────────────────────────────────────────────────
        
        String successMessage = String.format(
            "✓ FORM SUBMITTED SUCCESSFULLY!\n\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Phone: %s\n" +
            "Role: %s\n" +
            "Feedback: %s\n" +
            "Timestamp: %s",
            firstName, lastName, email, phone, role,
            feedback.isEmpty() ? "(No comments)" : feedback,
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
        );
        
        showSuccess(successMessage);
        System.out.println(successMessage);
        
        // Optional: Log to console
        System.out.println("✓ All validation checks passed!");
        System.out.println("✓ Form data is valid and ready for submission.");
    }
    
    /**
     * Email validation using regex
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(emailRegex, email);
    }
    
    /**
     * Phone validation - accepts various formats
     */
    private boolean isValidPhone(String phone) {
        // Remove common phone characters
        String cleaned = phone.replaceAll("[^0-9+]", "");
        // Check if it's at least 10 digits or +1 followed by 10 digits
        return cleaned.matches("^(\\+1)?\\d{10}$") || 
               cleaned.matches("^\\d{10,}$");
    }
    
    /**
     * Clear all form fields
     */
    private void clearAllFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        feedbackArea.setText("");
        roleChoice.select(0);
        validationLabel.setForeground(new Color(139, 125, 107));
        validationLabel.setText("All fields cleared. Ready for new entry.");
    }
    
    /**
     * Show error message with red highlight
     */
    private void showError(String message) {
        validationLabel.setForeground(new Color(192, 57, 43)); // Error red
        validationLabel.setText(message);
        System.out.println(message);
    }
    
    /**
     * Show success message with green highlight
     */
    private void showSuccess(String message) {
        Dialog successDialog = new Dialog(this, "Submission Successful", true);
        successDialog.setSize(400, 300);
        successDialog.setLocationRelativeTo(this);
        
        TextArea resultArea = new TextArea(message, 12, 40, SCROLLBARS_VERTICAL);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 11));
        
        Button closeBtn = new Button("Close");
        closeBtn.addActionListener(e -> successDialog.dispose());
        
        Panel panel = new Panel(new BorderLayout());
        panel.add(resultArea, BorderLayout.CENTER);
        panel.add(closeBtn, BorderLayout.SOUTH);
        
        successDialog.add(panel);
        successDialog.setVisible(true);
        
        validationLabel.setForeground(new Color(39, 174, 96)); // Success green
        validationLabel.setText("✓ Form submitted successfully!");
    }
    
    /**
     * Update status label with current action
     */
    private void updateStatus(String status) {
        statusLabel.setText("Status: " + status);
    }
    
    /**
     * Main method - Entry point for the application
     */
    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  SERENOVA WELLNESS - AWT COMPONENTS DEMONSTRATION");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println();
        System.out.println("✓ Demonstrating:");
        System.out.println("  1. Frame with attractive layout");
        System.out.println("  2. Text input fields with real-time feedback");
        System.out.println("  3. Dropdown selection (Choice component)");
        System.out.println("  4. Text area for multi-line input");
        System.out.println("  5. Event listeners for user interactions");
        System.out.println("  6. Form validation with error handling");
        System.out.println("  7. Data validation with regex patterns");
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println();
        
        // Create and show the frame
        AwtDemo demo = new AwtDemo();
        demo.setVisible(true);
        
        System.out.println("✓ AWT Demo window opened successfully.");
        System.out.println("✓ Try entering data and clicking 'Submit Form' to see validation in action.");
    }
}
