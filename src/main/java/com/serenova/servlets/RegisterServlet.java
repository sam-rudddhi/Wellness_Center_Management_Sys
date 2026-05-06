package com.serenova.servlets;

import com.serenova.database.DatabaseConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * RegisterServlet - Handles user registration with JDBC database integration
 * 
 * Demonstrates:
 * - JDBC connectivity
 * - PreparedStatement (prevents SQL injection)
 * - Database insertion
 * - Duplicate email checking
 * - Transaction management
 * - Proper resource cleanup
 */
@WebServlet(
    name = "RegisterServlet",
    urlPatterns = {"/register", "/RegisterServlet"},
    loadOnStartup = 1
)
public class RegisterServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());
    
    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("✓ RegisterServlet initialized");
        
        // Test database connection on startup
        if (DatabaseConnection.testConnection()) {
            logger.info("✓ Database connection verified");
        } else {
            logger.severe("✗ Database connection failed! Check configuration.");
        }
    }
    
    /**
     * doGet() - Redirect to registration page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
    
    /**
     * doPost() - Handle registration form submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ═══════════════════════════════════════════════════════════
        // 1. RETRIEVE FORM DATA
        // ═══════════════════════════════════════════════════════════
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // ═══════════════════════════════════════════════════════════
        // 2. SERVER-SIDE VALIDATION
        // ═══════════════════════════════════════════════════════════
        StringBuilder errors = new StringBuilder();
        
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.append("First name is required. ");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.append("Last name is required. ");
        }
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            errors.append("Valid email is required. ");
        }
        if (password == null || password.length() < 8) {
            errors.append("Password must be at least 8 characters. ");
        }
        if (!password.equals(confirmPassword)) {
            errors.append("Passwords do not match. ");
        }
        
        // If validation fails, return to form with errors
        if (errors.length() > 0) {
            request.setAttribute("errorMessage", errors.toString());
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        // ═══════════════════════════════════════════════════════════
        // 3. DATABASE OPERATIONS
        // ═══════════════════════════════════════════════════════════
        
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;
        
        try {
            // Get database connection
            conn = DatabaseConnection.getConnection();
            
            // ─────────────────────────────────────────────────
            // 3.1 Check if email already exists
            // ─────────────────────────────────────────────────
            String checkQuery = "SELECT email FROM users WHERE email = ?";
            checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Email already exists
                logger.warning("Registration failed: Email already exists - " + email);
                request.setAttribute("errorMessage", "Email already registered. Please login or use a different email.");
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            // ─────────────────────────────────────────────────
            // 3.2 Insert new user into database
            // ─────────────────────────────────────────────────
            
            // Generate unique user ID
            String userId = "WC" + System.currentTimeMillis();
            
            // SQL INSERT query using PreparedStatement (prevents SQL injection)
            String insertQuery = "INSERT INTO users " +
                                "(user_id, first_name, last_name, email, password, phone, dob, gender, role) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, userId);
            insertStmt.setString(2, firstName.trim());
            insertStmt.setString(3, lastName.trim());
            insertStmt.setString(4, email.trim().toLowerCase());
            insertStmt.setString(5, password); // In production: hash with BCrypt!
            insertStmt.setString(6, phone);
            insertStmt.setString(7, dob);
            insertStmt.setString(8, gender);
            insertStmt.setString(9, "Patient"); // Default role
            
            // Execute INSERT
            int rowsAffected = insertStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("✓ User registered successfully: " + email + " (ID: " + userId + ")");
                
                // ─────────────────────────────────────────────────
                // 4. CREATE SESSION FOR NEW USER
                // ─────────────────────────────────────────────────
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userId);
                session.setAttribute("username", firstName + " " + lastName);
                session.setAttribute("email", email);
                session.setAttribute("phone", phone);
                session.setAttribute("dob", dob);
                session.setAttribute("gender", gender);
                session.setAttribute("role", "Patient");
                session.setAttribute("registrationTime", LocalDateTime.now().toString());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                
                // ─────────────────────────────────────────────────
                // 5. SET COOKIES
                // ─────────────────────────────────────────────────
                Cookie userIdCookie = new Cookie("serenova_user_id", userId);
                userIdCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                userIdCookie.setPath("/");
                userIdCookie.setHttpOnly(true);
                response.addCookie(userIdCookie);
                
                // ─────────────────────────────────────────────────
                // 6. REDIRECT TO SUCCESS PAGE
                // ─────────────────────────────────────────────────
                response.sendRedirect("registration-success.jsp");
                
            } else {
                logger.severe("✗ Registration failed: No rows affected");
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            // Database error
            logger.severe("✗ Database error during registration: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("errorMessage", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            
        } finally {
            // ═══════════════════════════════════════════════════════════
            // 7. CLEANUP - ALWAYS CLOSE RESOURCES
            // ═══════════════════════════════════════════════════════════
            try {
                if (rs != null) rs.close();
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                logger.warning("Error closing database resources: " + e.getMessage());
            }
        }
    }
    
    @Override
    public String getServletInfo() {
        return "RegisterServlet with JDBC Database Integration - Serenova Wellness Center";
    }
}
