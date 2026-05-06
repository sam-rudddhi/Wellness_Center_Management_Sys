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
import java.util.Date;
import java.util.logging.Logger;

/**
 * LoginServlet - Handles user authentication with JDBC database
 * 
 * Demonstrates:
 * - Database authentication
 * - PreparedStatement for security
 * - ResultSet processing
 * - Session management after login
 * - Login history tracking
 */
@WebServlet(
    name = "LoginServlet",
    urlPatterns = {"/login", "/LoginServlet"}
)
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    
    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("✓ LoginServlet initialized");
    }
    
    /**
     * doGet() - Handle logout or redirect to login page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("logout".equals(action)) {
            handleLogout(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    
    /**
     * doPost() - Handle login form submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ═══════════════════════════════════════════════════════════
        // 1. RETRIEVE LOGIN CREDENTIALS
        // ═══════════════════════════════════════════════════════════
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember");
        
        // ═══════════════════════════════════════════════════════════
        // 2. VALIDATE INPUT
        // ═══════════════════════════════════════════════════════════
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "Email and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // ═══════════════════════════════════════════════════════════
        // 3. AUTHENTICATE USER AGAINST DATABASE
        // ═══════════════════════════════════════════════════════════
        
        Connection conn = null;
        PreparedStatement loginStmt = null;
        PreparedStatement historyStmt = null;
        ResultSet rs = null;
        
        try {
            // Get database connection
            conn = DatabaseConnection.getConnection();
            
            // ─────────────────────────────────────────────────
            // 3.1 Query database for user credentials
            // ─────────────────────────────────────────────────
            String loginQuery = "SELECT user_id, first_name, last_name, email, role, is_active " +
                               "FROM users " +
                               "WHERE email = ? AND password = ?";
            
            loginStmt = conn.prepareStatement(loginQuery);
            loginStmt.setString(1, email.trim().toLowerCase());
            loginStmt.setString(2, password); // In production: compare hashed passwords!
            
            rs = loginStmt.executeQuery();
            
            // ─────────────────────────────────────────────────
            // 3.2 Check if user exists and credentials match
            // ─────────────────────────────────────────────────
            if (rs.next()) {
                // User found - check if account is active
                boolean isActive = rs.getBoolean("is_active");
                
                if (!isActive) {
                    logger.warning("Login attempt for inactive account: " + email);
                    request.setAttribute("errorMessage", "Your account has been deactivated. Please contact support.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                
                // ─────────────────────────────────────────────────
                // 4. SUCCESSFUL LOGIN - Extract user data
                // ─────────────────────────────────────────────────
                String userId = rs.getString("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userEmail = rs.getString("email");
                String role = rs.getString("role");
                
                logger.info("✓ User logged in successfully: " + email + " (Role: " + role + ")");
                
                // ─────────────────────────────────────────────────
                // 5. CREATE SESSION
                // ─────────────────────────────────────────────────
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userId);
                session.setAttribute("username", firstName + " " + lastName);
                session.setAttribute("email", userEmail);
                session.setAttribute("role", role);
                session.setAttribute("loginTime", new Date());
                session.setAttribute("isAuthenticated", true);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                
                // ─────────────────────────────────────────────────
                // 6. HANDLE "REMEMBER ME" COOKIE
                // ─────────────────────────────────────────────────
                if ("on".equals(rememberMe)) {
                    Cookie rememberCookie = new Cookie("serenova_remember", userId);
                    rememberCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    rememberCookie.setPath("/");
                    rememberCookie.setHttpOnly(true);
                    response.addCookie(rememberCookie);
                }
                
                // ─────────────────────────────────────────────────
                // 7. LOG LOGIN HISTORY (Optional)
                // ─────────────────────────────────────────────────
                try {
                    String historyQuery = "INSERT INTO login_history " +
                                        "(user_id, ip_address, user_agent, login_status) " +
                                        "VALUES (?, ?, ?, ?)";
                    
                    historyStmt = conn.prepareStatement(historyQuery);
                    historyStmt.setString(1, userId);
                    historyStmt.setString(2, request.getRemoteAddr());
                    historyStmt.setString(3, request.getHeader("User-Agent"));
                    historyStmt.setString(4, "SUCCESS");
                    historyStmt.executeUpdate();
                    
                } catch (SQLException e) {
                    // Login history is optional, don't fail login if this fails
                    logger.warning("Failed to log login history: " + e.getMessage());
                }
                
                // ─────────────────────────────────────────────────
                // 8. REDIRECT BASED ON ROLE
                // ─────────────────────────────────────────────────
                if ("Admin".equals(role)) {
                    response.sendRedirect("dashboard.jsp"); // Can create admin-dashboard.jsp later
                } else if ("Doctor".equals(role)) {
                    response.sendRedirect("dashboard.jsp"); // Can create doctor-dashboard.jsp later
                } else {
                    response.sendRedirect("dashboard.jsp");
                }
                
            } else {
                // ─────────────────────────────────────────────────
                // AUTHENTICATION FAILED
                // ─────────────────────────────────────────────────
                logger.warning("✗ Failed login attempt for: " + email);
                
                // Log failed attempt
                try {
                    String historyQuery = "INSERT INTO login_history " +
                                        "(user_id, ip_address, user_agent, login_status) " +
                                        "VALUES (?, ?, ?, ?)";
                    
                    historyStmt = conn.prepareStatement(historyQuery);
                    historyStmt.setString(1, null);
                    historyStmt.setString(2, request.getRemoteAddr());
                    historyStmt.setString(3, request.getHeader("User-Agent"));
                    historyStmt.setString(4, "FAILED - " + email);
                    historyStmt.executeUpdate();
                    
                } catch (SQLException e) {
                    logger.warning("Failed to log failed login: " + e.getMessage());
                }
                
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            // Database error
            logger.severe("✗ Database error during login: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("errorMessage", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } finally {
            // ═══════════════════════════════════════════════════════════
            // 9. CLEANUP - ALWAYS CLOSE RESOURCES
            // ═══════════════════════════════════════════════════════════
            try {
                if (rs != null) rs.close();
                if (loginStmt != null) loginStmt.close();
                if (historyStmt != null) historyStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                logger.warning("Error closing database resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Handle user logout
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String username = (String) session.getAttribute("username");
            logger.info("User logged out: " + username);
            session.invalidate();
        }
        
        // Clear "Remember Me" cookie
        Cookie rememberCookie = new Cookie("serenova_remember", "");
        rememberCookie.setMaxAge(0);
        rememberCookie.setPath("/");
        response.addCookie(rememberCookie);
        
        response.sendRedirect("logout.jsp");
    }
    
    @Override
    public String getServletInfo() {
        return "LoginServlet with JDBC Database Authentication - Serenova Wellness Center";
    }
}
