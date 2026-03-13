package com.serenova.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * COMPREHENSIVE SERVLET DEMONSTRATION
 * RegisterServlet - Handles user registration for Serenova Wellness Center
 *
 * This servlet demonstrates ALL servlet fundamentals:
 * 1. Servlet Lifecycle (init, service, destroy)
 * 2. Request Handling (GET, POST)
 * 3. Request Parameters
 * 4. Session Management
 * 5. Cookie Handling
 * 6. ServletContext & ServletConfig
 * 7. Request/Response Headers
 * 8. Request Dispatcher (forwarding/including)
 * 9. Error Handling
 * 10. Filters & Logging
 */
@WebServlet(
        name = "RegisterServlet",
        urlPatterns = {"/register", "/RegisterServlet"},
        loadOnStartup = 1  // Load servlet on server startup
)
public class RegisterServlet extends HttpServlet {

    // Servlet instance variables
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    // ServletConfig - specific to this servlet
    private ServletConfig servletConfig;

    // ServletContext - shared across entire web application
    private ServletContext servletContext;

    // Registration counter (shared across all requests)
    private int registrationCount = 0;

    // ═══════════════════════════════════════════════════════════
    // 1. SERVLET LIFECYCLE METHODS
    // ═══════════════════════════════════════════════════════════

    /**
     * init() - Called ONCE when servlet is first loaded
     * Used for: Loading config, establishing DB connections, etc.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.servletConfig = config;
        this.servletContext = config.getServletContext();

        // Log servlet initialization
        logger.info("✓ RegisterServlet initialized at: " + LocalDateTime.now());

        // Set application-level attribute (shared across all servlets)
        servletContext.setAttribute("appStartTime", LocalDateTime.now().toString());
        servletContext.setAttribute("appName", "Serenova Wellness Center");

        // Initialize registration counter from context (if exists)
        Integer existingCount = (Integer) servletContext.getAttribute("totalRegistrations");
        registrationCount = (existingCount != null) ? existingCount : 0;

        System.out.println("════════════════════════════════════════");
        System.out.println("RegisterServlet INITIALIZED");
        System.out.println("Servlet Name: " + servletConfig.getServletName());
        System.out.println("Total Registrations: " + registrationCount);
        System.out.println("════════════════════════════════════════");
    }

    /**
     * service() - Called for EVERY request (routes to doGet/doPost/etc.)
     * Usually you don't override this, but here for demonstration
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Log every request
        logger.info("→ Request received: " + request.getMethod() + " " + request.getRequestURI());

        // Call parent service() which routes to doGet(), doPost(), etc.
        super.service(request, response);
    }

    /**
     * destroy() - Called ONCE when servlet is being shut down
     * Used for: Cleanup, closing connections, saving state, etc.
     */
    @Override
    public void destroy() {
        // Save registration count before shutdown
        servletContext.setAttribute("totalRegistrations", registrationCount);

        logger.info("✗ RegisterServlet destroyed at: " + LocalDateTime.now());
        System.out.println("════════════════════════════════════════");
        System.out.println("RegisterServlet DESTROYED");
        System.out.println("Total Registrations Saved: " + registrationCount);
        System.out.println("════════════════════════════════════════");

        super.destroy();
    }

    // ═══════════════════════════════════════════════════════════
    // 2. HTTP METHOD HANDLERS
    // ═══════════════════════════════════════════════════════════

    /**
     * doGet() - Handles HTTP GET requests
     * Used when user navigates to the servlet URL or clicks a link
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get query parameters from URL (e.g., ?action=info)
        String action = request.getParameter("action");

        if ("info".equals(action)) {
            // Show servlet information page
            showServletInfo(request, response);
        } else if ("stats".equals(action)) {
            // Show registration statistics
            showStatistics(request, response);
        } else {
            // Default: Show registration form
            response.sendRedirect("register.jsp");
        }
    }

    /**
     * doPost() - Handles HTTP POST requests
     * Used when form is submitted with method="post"
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ─────────────────────────────────────────────────
            // 3. RETRIEVE REQUEST PARAMETERS
            // ─────────────────────────────────────────────────
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String dob = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String password = request.getParameter("password");

            // ─────────────────────────────────────────────────
            // 4. SERVER-SIDE VALIDATION
            // ─────────────────────────────────────────────────
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

            // If validation fails, redirect back with errors
            if (errors.length() > 0) {
                request.setAttribute("errorMessage", errors.toString());
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);

                // Forward back to registration page with errors
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // ─────────────────────────────────────────────────
            // 5. SESSION MANAGEMENT
            // ─────────────────────────────────────────────────
            HttpSession session = request.getSession(true); // Create session if doesn't exist

            // Generate unique user ID
            String userId = "WC" + System.currentTimeMillis();

            // Store user data in session
            session.setAttribute("userId", userId);
            session.setAttribute("username", firstName + " " + lastName);
            session.setAttribute("email", email);
            session.setAttribute("phone", phone);
            session.setAttribute("dob", dob);
            session.setAttribute("gender", gender);
            session.setAttribute("role", "Patient");
            session.setAttribute("registrationTime", LocalDateTime.now().toString());

            // Set session timeout (30 minutes)
            session.setMaxInactiveInterval(30 * 60);

            logger.info("✓ New user registered: " + email + " (ID: " + userId + ")");

            // ─────────────────────────────────────────────────
            // 6. COOKIE MANAGEMENT
            // ─────────────────────────────────────────────────

            // Create "Remember Me" cookie
            Cookie userIdCookie = new Cookie("serenova_user_id", userId);
            userIdCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            userIdCookie.setPath("/");
            userIdCookie.setHttpOnly(true); // Prevent JavaScript access (security)
            response.addCookie(userIdCookie);

            // Create theme preference cookie
            Cookie themeCookie = new Cookie("serenova_theme", "wellness-sage");
            themeCookie.setMaxAge(365 * 24 * 60 * 60); // 1 year
            themeCookie.setPath("/");
            response.addCookie(themeCookie);

            // Store last registration time
            Cookie lastRegCookie = new Cookie("last_registration",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-"));
            lastRegCookie.setMaxAge(365 * 24 * 60 * 60);
            lastRegCookie.setPath("/");
            response.addCookie(lastRegCookie);

            // ─────────────────────────────────────────────────
            // 7. APPLICATION-LEVEL TRACKING
            // ─────────────────────────────────────────────────

            // Increment registration counter
            registrationCount++;
            servletContext.setAttribute("totalRegistrations", registrationCount);

            // Track in application context
            servletContext.setAttribute("lastRegisteredUser", email);
            servletContext.setAttribute("lastRegistrationTime", LocalDateTime.now().toString());

            // ─────────────────────────────────────────────────
            // 8. REQUEST ATTRIBUTES (for forwarding data)
            // ─────────────────────────────────────────────────
            request.setAttribute("successMessage", "Registration successful!");
            request.setAttribute("userId", userId);
            request.setAttribute("userName", firstName + " " + lastName);

            // ─────────────────────────────────────────────────
            // 9. RESPONSE - Redirect to success page
            // ─────────────────────────────────────────────────

            // Option 1: Forward (same request, URL doesn't change)
            // request.getRequestDispatcher("registration-success.jsp").forward(request, response);

            // Option 2: Redirect (new request, URL changes) - PREFERRED
            response.sendRedirect("registration-success.jsp");

        } catch (Exception e) {
            // ─────────────────────────────────────────────────
            // 10. ERROR HANDLING
            // ─────────────────────────────────────────────────
            logger.severe("Error during registration: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("errorMessage", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // ═══════════════════════════════════════════════════════════
    // ADDITIONAL DEMONSTRATION METHODS
    // ═══════════════════════════════════════════════════════════

    /**
     * Shows comprehensive servlet information
     */
    private void showServletInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<title>Servlet Information</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;margin:40px;background:#FAF7F2;}");
        out.println("h1{color:#5C7A60;}");
        out.println(".section{background:#fff;padding:20px;margin:20px 0;border-radius:8px;border:1px solid #EDE6DD;}");
        out.println(".label{font-weight:bold;color:#8B7D6B;}");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>🌿 RegisterServlet Information</h1>");

        // ServletConfig Information
        out.println("<div class='section'>");
        out.println("<h2>ServletConfig Details</h2>");
        out.println("<p><span class='label'>Servlet Name:</span> " + servletConfig.getServletName() + "</p>");
        out.println("<p><span class='label'>Init Parameters:</span></p><ul>");
        Enumeration<String> initParams = servletConfig.getInitParameterNames();
        while (initParams.hasMoreElements()) {
            String param = initParams.nextElement();
            out.println("<li>" + param + " = " + servletConfig.getInitParameter(param) + "</li>");
        }
        out.println("</ul></div>");

        // ServletContext Information
        out.println("<div class='section'>");
        out.println("<h2>ServletContext (Application) Details</h2>");
        out.println("<p><span class='label'>Application Name:</span> " + servletContext.getAttribute("appName") + "</p>");
        out.println("<p><span class='label'>App Start Time:</span> " + servletContext.getAttribute("appStartTime") + "</p>");
        out.println("<p><span class='label'>Total Registrations:</span> " + registrationCount + "</p>");
        out.println("<p><span class='label'>Last Registered User:</span> " + servletContext.getAttribute("lastRegisteredUser") + "</p>");
        out.println("<p><span class='label'>Server Info:</span> " + servletContext.getServerInfo() + "</p>");
        out.println("<p><span class='label'>Context Path:</span> " + servletContext.getContextPath() + "</p>");
        out.println("</div>");

        // Request Information
        out.println("<div class='section'>");
        out.println("<h2>Request Details</h2>");
        out.println("<p><span class='label'>Method:</span> " + request.getMethod() + "</p>");
        out.println("<p><span class='label'>Request URI:</span> " + request.getRequestURI() + "</p>");
        out.println("<p><span class='label'>Protocol:</span> " + request.getProtocol() + "</p>");
        out.println("<p><span class='label'>Remote Address:</span> " + request.getRemoteAddr() + "</p>");
        out.println("<p><span class='label'>Server Name:</span> " + request.getServerName() + "</p>");
        out.println("<p><span class='label'>Server Port:</span> " + request.getServerPort() + "</p>");

        // Request Headers
        out.println("<p><span class='label'>Request Headers:</span></p><ul>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            out.println("<li>" + header + ": " + request.getHeader(header) + "</li>");
        }
        out.println("</ul></div>");

        // Session Information
        out.println("<div class='section'>");
        out.println("<h2>Session Information</h2>");
        HttpSession session = request.getSession(false);
        if (session != null) {
            out.println("<p><span class='label'>Session ID:</span> " + session.getId() + "</p>");
            out.println("<p><span class='label'>Creation Time:</span> " + new java.util.Date(session.getCreationTime()) + "</p>");
            out.println("<p><span class='label'>Last Accessed:</span> " + new java.util.Date(session.getLastAccessedTime()) + "</p>");
            out.println("<p><span class='label'>Max Inactive Interval:</span> " + session.getMaxInactiveInterval() + " seconds</p>");

            out.println("<p><span class='label'>Session Attributes:</span></p><ul>");
            Enumeration<String> attrNames = session.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String attr = attrNames.nextElement();
                out.println("<li>" + attr + " = " + session.getAttribute(attr) + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No active session</p>");
        }
        out.println("</div>");

        // Cookies
        out.println("<div class='section'>");
        out.println("<h2>Cookies</h2>");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            out.println("<ul>");
            for (Cookie cookie : cookies) {
                out.println("<li><strong>" + cookie.getName() + "</strong> = " + cookie.getValue() +
                        " (Max Age: " + cookie.getMaxAge() + " seconds)</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No cookies found</p>");
        }
        out.println("</div>");

        out.println("<p><a href='register.jsp' style='color:#5C7A60;'>← Back to Registration</a></p>");
        out.println("</body></html>");
    }

    /**
     * Shows registration statistics
     */
    private void showStatistics(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<title>Registration Statistics</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;margin:40px;background:#FAF7F2;}");
        out.println("h1{color:#5C7A60;}");
        out.println(".stat{background:#fff;padding:20px;margin:20px 0;border-radius:8px;border:1px solid #EDE6DD;}");
        out.println(".number{font-size:3em;color:#7A9E7E;font-weight:bold;}");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>📊 Registration Statistics</h1>");

        out.println("<div class='stat'>");
        out.println("<h2>Total Registrations</h2>");
        out.println("<div class='number'>" + registrationCount + "</div>");
        out.println("</div>");

        out.println("<div class='stat'>");
        out.println("<h2>Last Registered User</h2>");
        out.println("<p>" + servletContext.getAttribute("lastRegisteredUser") + "</p>");
        out.println("<p><small>" + servletContext.getAttribute("lastRegistrationTime") + "</small></p>");
        out.println("</div>");

        out.println("<div class='stat'>");
        out.println("<h2>Application Start Time</h2>");
        out.println("<p>" + servletContext.getAttribute("appStartTime") + "</p>");
        out.println("</div>");

        out.println("<p><a href='register.jsp' style='color:#5C7A60;'>← Back to Registration</a></p>");
        out.println("</body></html>");
    }

    /**
     * getServletInfo() - Returns servlet description
     */
    @Override
    public String getServletInfo() {
        return "RegisterServlet for Serenova Wellness Center - Comprehensive Servlet Demonstration";
    }
}