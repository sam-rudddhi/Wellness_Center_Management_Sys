package com.serenova.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * LoginServlet - Handles user authentication
 *
 * Demonstrates:
 * - User authentication logic
 * - Session creation and management
 * - Cookie handling (Remember Me)
 * - Request/Response manipulation
 * - Error handling and validation
 */
@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/login", "/LoginServlet"}
)
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    // Simulated user database (In real app, this would be a database)
    private static Map<String, User> userDatabase = new HashMap<>();

    // Simple User class
    static class User {
        String userId;
        String email;
        String password;
        String fullName;
        String role;

        User(String userId, String email, String password, String fullName, String role) {
            this.userId = userId;
            this.email = email;
            this.password = password;
            this.fullName = fullName;
            this.role = role;
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();

        // Initialize with demo users
        userDatabase.put("jane.doe@example.com",
                new User("WC2024001", "jane.doe@example.com", "password123", "Jane Doe", "Patient"));
        userDatabase.put("admin@serenova.com",
                new User("WC2024ADMIN", "admin@serenova.com", "admin123", "Admin User", "Admin"));
        userDatabase.put("doctor@serenova.com",
                new User("WC2024DOC", "doctor@serenova.com", "doctor123", "Dr. Sharma", "Doctor"));

        logger.info("✓ LoginServlet initialized with " + userDatabase.size() + " demo users");
    }

    /**
     * doGet() - Display login form or handle logout
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            handleLogout(request, response);
        } else {
            // Redirect to login page
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
        // 3. AUTHENTICATE USER
        // ═══════════════════════════════════════════════════════════
        User user = authenticateUser(email, password);

        if (user == null) {
            // Authentication failed
            logger.warning("Failed login attempt for: " + email);

            request.setAttribute("errorMessage", "Invalid email or password.");
            request.setAttribute("email", email); // Preserve email
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ═══════════════════════════════════════════════════════════
        // 4. CREATE SESSION (User authenticated successfully)
        // ═══════════════════════════════════════════════════════════
        HttpSession session = request.getSession(true);

        // Store user information in session
        session.setAttribute("userId", user.userId);
        session.setAttribute("username", user.fullName);
        session.setAttribute("email", user.email);
        session.setAttribute("role", user.role);
        session.setAttribute("loginTime", LocalDateTime.now().toString());
        session.setAttribute("isAuthenticated", true);

        // Set session timeout (30 minutes)
        session.setMaxInactiveInterval(30 * 60);

        logger.info("✓ User logged in: " + email + " (Role: " + user.role + ")");

        // ═══════════════════════════════════════════════════════════
        // 5. HANDLE "REMEMBER ME" COOKIE
        // ═══════════════════════════════════════════════════════════
        if ("on".equals(rememberMe)) {
            // Create persistent cookie (7 days)
            Cookie rememberCookie = new Cookie("serenova_remember", user.userId);
            rememberCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            rememberCookie.setPath("/");
            rememberCookie.setHttpOnly(true);
            response.addCookie(rememberCookie);

            logger.info("Remember Me cookie created for: " + email);
        }

        // ═══════════════════════════════════════════════════════════
        // 6. SET ADDITIONAL COOKIES
        // ═══════════════════════════════════════════════════════════
        Cookie lastLoginCookie = new Cookie("last_login",
                LocalDateTime.now().toString().replace(":", "-"));
        lastLoginCookie.setMaxAge(365 * 24 * 60 * 60); // 1 year
        lastLoginCookie.setPath("/");
        response.addCookie(lastLoginCookie);

        // ═══════════════════════════════════════════════════════════
        // 7. TRACK LOGIN IN APPLICATION CONTEXT
        // ═══════════════════════════════════════════════════════════
        getServletContext().setAttribute("lastLoginUser", user.email);
        getServletContext().setAttribute("lastLoginTime", LocalDateTime.now().toString());

        // Increment login counter
        Integer loginCount = (Integer) getServletContext().getAttribute("totalLogins");
        loginCount = (loginCount == null) ? 1 : loginCount + 1;
        getServletContext().setAttribute("totalLogins", loginCount);

        // ═══════════════════════════════════════════════════════════
        // 8. REDIRECT TO DASHBOARD (based on role)
        // ═══════════════════════════════════════════════════════════
        if ("Admin".equals(user.role)) {
            response.sendRedirect("admin-dashboard.jsp");
        } else if ("Doctor".equals(user.role)) {
            response.sendRedirect("doctor-dashboard.jsp");
        } else {
            response.sendRedirect("dashboard.jsp");
        }
    }

    /**
     * Authenticate user credentials
     * In real application, this would query a database
     */
    private User authenticateUser(String email, String password) {
        User user = userDatabase.get(email);

        if (user != null && user.password.equals(password)) {
            return user;
        }

        return null; // Authentication failed
    }

    /**
     * Handle user logout
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get session (don't create if doesn't exist)
        HttpSession session = request.getSession(false);

        if (session != null) {
            String username = (String) session.getAttribute("username");
            logger.info("User logged out: " + username);

            // Invalidate session
            session.invalidate();
        }

        // Clear "Remember Me" cookie
        Cookie rememberCookie = new Cookie("serenova_remember", "");
        rememberCookie.setMaxAge(0); // Delete cookie
        rememberCookie.setPath("/");
        response.addCookie(rememberCookie);

        // Redirect to logout page
        response.sendRedirect("logout.jsp");
    }

    @Override
    public String getServletInfo() {
        return "LoginServlet for Serenova Wellness Center - Handles user authentication";
    }
}