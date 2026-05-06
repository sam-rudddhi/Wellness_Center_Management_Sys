package main.java.com.serenova.servlets;

import com.serenova.database.DatabaseConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * SqlInjectionDemoServlet - Demonstrates SQL Injection Prevention
 * 
 * This servlet showcases:
 * - Vulnerable SQL queries (vulnerable to SQL injection)
 * - Secure prepared statements (parameterized queries)
 * - Prevention techniques using parameterized queries
 * - Best practices for database security
 */
@WebServlet(
    name = "SqlInjectionDemoServlet",
    urlPatterns = {"/sql-injection-demo", "/SqlInjectionDemoServlet"}
)
public class SqlInjectionDemoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SqlInjectionDemoServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("search".equals(action)) {
            handleSecureSearch(request, response);
        } else {
            // Default: show demo page
            request.getRequestDispatcher("sql-injection-demo.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Demonstrate SECURE search using PreparedStatement
     * This method is safe from SQL injection
     */
    private void handleSecureSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        List<Map<String, String>> results = new ArrayList<>();
        String message = "";
        
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Please enter an email to search.");
            request.getRequestDispatcher("sql-injection-demo.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // ═══════════════════════════════════════════════════════════
            // SECURE METHOD - Using PreparedStatement
            // ═══════════════════════════════════════════════════════════
            // The ? placeholder ensures the input is treated as data, not SQL code
            // This prevents SQL injection attacks completely
            
            String secureQuery = "SELECT user_id, first_name, last_name, email, role, is_active " +
                                "FROM users " +
                                "WHERE email = ?";
            
            stmt = conn.prepareStatement(secureQuery);
            stmt.setString(1, email.trim().toLowerCase()); // Safe parameter binding
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("user_id", rs.getString("user_id"));
                user.put("first_name", rs.getString("first_name"));
                user.put("last_name", rs.getString("last_name"));
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));
                user.put("is_active", rs.getBoolean("is_active") ? "Yes" : "No");
                results.add(user);
            }
            
            if (results.isEmpty()) {
                message = "No users found with email: " + email;
            } else {
                message = "Found " + results.size() + " user(s) matching your search.";
            }
            
            logger.info("✓ Secure search executed successfully for email: " + email);
            
        } catch (SQLException e) {
            logger.severe("Database error: " + e.getMessage());
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("sql-injection-demo.jsp").forward(request, response);
            return;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                logger.warning("Error closing resources: " + e.getMessage());
            }
        }
        
        // Set attributes for display
        request.setAttribute("results", results);
        request.setAttribute("message", message);
        request.setAttribute("searchEmail", email);
        request.getRequestDispatcher("sql-injection-demo.jsp").forward(request, response);
    }
}
