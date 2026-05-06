package main.java.com.serenova.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * LogoutServlet - Handles user logout
 * 
 * Demonstrates:
 * - Session invalidation
 * - Cookie cleanup
 * - Proper logout flow
 * - Security best practices
 */
@WebServlet(
    name = "LogoutServlet",
    urlPatterns = {"/logout", "/LogoutServlet"}
)
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleLogout(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleLogout(request, response);
    }
    
    /**
     * Handle logout - invalidate session and clear cookies
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // ═══════════════════════════════════════════════════════════
        // 1. GET AND INVALIDATE SESSION
        // ═══════════════════════════════════════════════════════════
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String username = (String) session.getAttribute("username");
            String email = (String) session.getAttribute("email");
            
            logger.info("User logged out: " + (username != null ? username : email));
            
            // Invalidate the session
            session.invalidate();
        }
        
        // ═══════════════════════════════════════════════════════════
        // 2. CLEAR ALL COOKIES
        // ═══════════════════════════════════════════════════════════
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Clear all serenova-related cookies
                if (cookie.getName().startsWith("serenova_")) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    logger.info("Cleared cookie: " + cookie.getName());
                }
            }
        }
        
        // ═══════════════════════════════════════════════════════════
        // 3. REDIRECT TO LOGOUT PAGE
        // ═══════════════════════════════════════════════════════════
        response.sendRedirect("logout.jsp");
    }
    
    @Override
    public String getServletInfo() {
        return "LogoutServlet - Handles user logout and cleanup";
    }
}