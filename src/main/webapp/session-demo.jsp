<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%
    // ═══════════════════════════════════════════════════════════
    // SESSION MANAGEMENT DEMO
    // ═══════════════════════════════════════════════════════════

    // Create or retrieve session attributes
    HttpSession userSession = request.getSession();

    // Simulating user login - set session attributes
    if (userSession.getAttribute("username") == null) {
        userSession.setAttribute("username", "Jane Doe");
        userSession.setAttribute("email", "jane.doe@example.com");
        userSession.setAttribute("userId", "WC2024001");
        userSession.setAttribute("loginTime", new Date());
        userSession.setAttribute("role", "Patient");
    }

    // Track page visits
    Integer visitCount = (Integer) userSession.getAttribute("visitCount");
    if (visitCount == null) {
        visitCount = 1;
    } else {
        visitCount++;
    }
    userSession.setAttribute("visitCount", visitCount);

    // ═══════════════════════════════════════════════════════════
    // COOKIE MANAGEMENT DEMO
    // ═══════════════════════════════════════════════════════════

    // Create cookies for user preferences
    Cookie themeCookie = new Cookie("userTheme", "wellness-sage");
    themeCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
    response.addCookie(themeCookie);

    Cookie langCookie = new Cookie("userLanguage", "en-US");
    langCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
    response.addCookie(langCookie);

    Cookie lastVisitCookie = new Cookie("lastVisit", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    lastVisitCookie.setMaxAge(365 * 24 * 60 * 60); // 1 year
    response.addCookie(lastVisitCookie);

    // Read existing cookies
    Cookie[] cookies = request.getCookies();
    Map<String, String> cookieMap = new HashMap<>();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
    }

    // Format dates
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm:ss a");
    Date loginTime = (Date) userSession.getAttribute("loginTime");
    String formattedLoginTime = loginTime != null ? sdf.format(loginTime) : "N/A";

    Date sessionCreationTime = new Date(userSession.getCreationTime());
    String formattedCreationTime = sdf.format(sessionCreationTime);

    Date lastAccessedTime = new Date(userSession.getLastAccessedTime());
    String formattedLastAccessed = sdf.format(lastAccessedTime);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Session Manager – Serenova Wellness</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,300;0,400;0,600;1,300&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --sage:       #7A9E7E;
            --sage-dark:  #5C7A60;
            --sage-light: #EBF2EC;
            --cream:      #FAF7F2;
            --stone:      #8B7D6B;
            --charcoal:   #2D2D2D;
            --gold:       #C9A96E;
        }

        body {
            font-family: 'DM Sans', sans-serif;
            background: var(--cream);
            min-height: 100vh;
            padding: 2rem;
        }

        /* ── Navigation ── */
        nav {
            max-width: 1200px;
            margin: 0 auto 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 1.5rem;
            border-bottom: 1px solid #E5DDD5;
        }
        .nav-brand {
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }
        .nav-logo {
            width: 42px;
            height: 42px;
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.2rem;
        }
        .nav-name {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.5rem;
            font-weight: 400;
            color: var(--charcoal);
        }
        .nav-links {
            display: flex;
            gap: 1.5rem;
        }
        .nav-link {
            text-decoration: none;
            color: var(--stone);
            font-size: 0.85rem;
            transition: color 0.2s;
        }
        .nav-link:hover {
            color: var(--sage-dark);
        }
        .nav-link.active {
            color: var(--sage-dark);
            font-weight: 500;
        }

        /* ── Container ── */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            animation: fadeUp 0.5s ease;
        }
        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(20px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        /* ── Header ── */
        .page-header {
            text-align: center;
            margin-bottom: 2.5rem;
        }
        .page-title {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2.8rem;
            font-weight: 400;
            color: var(--charcoal);
            margin-bottom: 0.5rem;
        }
        .page-subtitle {
            font-size: 0.9rem;
            color: var(--stone);
            line-height: 1.6;
        }

        /* ── Grid Layout ── */
        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        /* ── Cards ── */
        .card {
            background: #fff;
            border: 1px solid #EDE6DD;
            border-radius: 16px;
            padding: 1.8rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.02);
        }
        .card-header {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            margin-bottom: 1.5rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid #F5F0EA;
        }
        .card-icon {
            width: 44px;
            height: 44px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.2rem;
        }
        .card-title {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.35rem;
            font-weight: 500;
            color: var(--charcoal);
        }

        /* ── Data Rows ── */
        .data-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.85rem 0;
            border-bottom: 1px solid #F8F5F0;
        }
        .data-row:last-child {
            border-bottom: none;
        }
        .data-label {
            font-size: 0.8rem;
            font-weight: 500;
            color: var(--stone);
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }
        .data-value {
            font-size: 0.9rem;
            color: var(--charcoal);
            font-weight: 500;
        }
        .data-value.highlight {
            color: var(--sage-dark);
            background: var(--sage-light);
            padding: 0.3rem 0.8rem;
            border-radius: 6px;
        }

        /* ── Badge ── */
        .badge {
            display: inline-block;
            padding: 0.3rem 0.75rem;
            border-radius: 99px;
            font-size: 0.72rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }
        .badge-success {
            background: #EBF2EC;
            color: #5C7A60;
        }
        .badge-info {
            background: #FEF3E2;
            color: #C9A96E;
        }

        /* ── Full Width Card ── */
        .card-full {
            grid-column: 1 / -1;
        }

        /* ── Cookie Table ── */
        .cookie-table {
            width: 100%;
            border-collapse: collapse;
        }
        .cookie-table th {
            text-align: left;
            font-size: 0.75rem;
            font-weight: 600;
            color: var(--stone);
            text-transform: uppercase;
            letter-spacing: 0.05em;
            padding: 0.75rem 1rem;
            background: var(--sage-light);
            border-bottom: 2px solid var(--sage);
        }
        .cookie-table td {
            padding: 0.9rem 1rem;
            font-size: 0.85rem;
            color: var(--charcoal);
            border-bottom: 1px solid #F5F0EA;
        }
        .cookie-table tr:last-child td {
            border-bottom: none;
        }
        .cookie-table tr:hover {
            background: #FDFCFB;
        }

        /* ── Action Buttons ── */
        .btn-group {
            display: flex;
            gap: 1rem;
            margin-top: 1.5rem;
        }
        .btn {
            flex: 1;
            padding: 0.8rem 1.2rem;
            border: none;
            border-radius: 10px;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.85rem;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .btn:hover {
            transform: translateY(-2px);
        }
        .btn-primary {
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            color: #fff;
            box-shadow: 0 4px 12px rgba(92,122,96,0.3);
        }
        .btn-primary:hover {
            box-shadow: 0 6px 20px rgba(92,122,96,0.4);
        }
        .btn-secondary {
            background: #fff;
            color: var(--sage-dark);
            border: 1.5px solid var(--sage);
        }
        .btn-danger {
            background: #C0392B;
            color: #fff;
            box-shadow: 0 4px 12px rgba(192,57,43,0.3);
        }
        .btn-danger:hover {
            box-shadow: 0 6px 20px rgba(192,57,43,0.4);
        }

        /* ── Info Box ── */
        .info-box {
            background: linear-gradient(135deg, #EBF2EC, #F5F0EA);
            border-left: 4px solid var(--sage);
            border-radius: 10px;
            padding: 1.2rem;
            margin-top: 1.5rem;
        }
        .info-box-title {
            font-weight: 600;
            color: var(--sage-dark);
            margin-bottom: 0.5rem;
            font-size: 0.85rem;
        }
        .info-box-text {
            font-size: 0.82rem;
            color: var(--stone);
            line-height: 1.6;
        }

        /* ── Responsive ── */
        @media (max-width: 900px) {
            .grid {
                grid-template-columns: 1fr;
            }
            body {
                padding: 1.5rem;
            }
        }
    </style>
</head>
<body>

<!-- Navigation -->
<nav>
    <div class="nav-brand">
        <div class="nav-logo">🌿</div>
        <div class="nav-name">Serenova</div>
    </div>
    <div class="nav-links">
        <a href="dashboard.jsp" class="nav-link">Dashboard</a>
        <a href="session-demo.jsp" class="nav-link active">Session Manager</a>
        <a href="login.jsp" class="nav-link">Logout</a>
    </div>
</nav>

<!-- Main Container -->
<div class="container">

    <!-- Page Header -->
    <div class="page-header">
        <h1 class="page-title">Session & Cookie Manager</h1>
        <p class="page-subtitle">
            This page demonstrates session management and cookie handling in JSP.<br>
            Session data persists across page requests, while cookies store user preferences on the client side.
        </p>
    </div>

    <!-- Grid Layout -->
    <div class="grid">

        <!-- Session Information Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon" style="background: var(--sage-light);">🔐</div>
                <h2 class="card-title">Session Details</h2>
            </div>

            <div class="data-row">
                <span class="data-label">Session ID</span>
                <span class="data-value"><%= userSession.getId() %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Session Status</span>
                <span class="data-value"><span class="badge badge-success">Active</span></span>
            </div>
            <div class="data-row">
                <span class="data-label">Created At</span>
                <span class="data-value"><%= formattedCreationTime %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Last Accessed</span>
                <span class="data-value"><%= formattedLastAccessed %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Max Inactive (sec)</span>
                <span class="data-value"><%= userSession.getMaxInactiveInterval() %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Is New Session?</span>
                <span class="data-value"><%= userSession.isNew() ? "Yes" : "No" %></span>
            </div>
        </div>

        <!-- User Session Attributes Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon" style="background: #FEF3E2;">👤</div>
                <h2 class="card-title">User Information</h2>
            </div>

            <div class="data-row">
                <span class="data-label">User ID</span>
                <span class="data-value highlight"><%= userSession.getAttribute("userId") %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Username</span>
                <span class="data-value"><%= userSession.getAttribute("username") %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Email</span>
                <span class="data-value"><%= userSession.getAttribute("email") %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Role</span>
                <span class="data-value"><span class="badge badge-info"><%= userSession.getAttribute("role") %></span></span>
            </div>
            <div class="data-row">
                <span class="data-label">Login Time</span>
                <span class="data-value"><%= formattedLoginTime %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Page Visits</span>
                <span class="data-value highlight"><%= visitCount %></span>
            </div>
        </div>

        <!-- Cookies Card (Full Width) -->
        <div class="card card-full">
            <div class="card-header">
                <div class="card-icon" style="background: #FEF0F0;">🍪</div>
                <h2 class="card-title">Browser Cookies</h2>
            </div>

            <table class="cookie-table">
                <thead>
                <tr>
                    <th>Cookie Name</th>
                    <th>Value</th>
                    <th>Purpose</th>
                </tr>
                </thead>
                <tbody>
                <% if (cookieMap.isEmpty()) { %>
                <tr>
                    <td colspan="3" style="text-align: center; color: var(--stone); padding: 2rem;">
                        No cookies found. Refresh the page to create demo cookies.
                    </td>
                </tr>
                <% } else {
                    for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                        String purpose = "Session Management";
                        if (entry.getKey().equals("userTheme")) purpose = "User Theme Preference";
                        else if (entry.getKey().equals("userLanguage")) purpose = "Language Preference";
                        else if (entry.getKey().equals("lastVisit")) purpose = "Last Visit Tracking";
                        else if (entry.getKey().contains("JSESSIONID")) purpose = "Session Identifier";
                %>
                <tr>
                    <td><strong><%= entry.getKey() %></strong></td>
                    <td><%= entry.getValue() %></td>
                    <td style="color: var(--stone);"><%= purpose %></td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>

        <!-- Session Actions Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon" style="background: var(--sage-light);">⚙️</div>
                <h2 class="card-title">Session Actions</h2>
            </div>

            <div class="btn-group">
                <form action="session-demo.jsp" method="post" style="flex: 1;">
                    <button type="submit" class="btn btn-primary" style="width: 100%;">
                        🔄 Refresh Session
                    </button>
                </form>
                <form action="logout.jsp" method="post" style="flex: 1;">
                    <button type="submit" class="btn btn-danger" style="width: 100%;">
                        🚪 End Session
                    </button>
                </form>
            </div>

            <div class="info-box">
                <div class="info-box-title">ℹ️ About Sessions</div>
                <div class="info-box-text">
                    Sessions store user data on the server and last until the browser closes or the session expires.
                    Cookies store data on the client side and can persist across browser sessions.
                </div>
            </div>
        </div>

        <!-- Technical Details Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon" style="background: #FEF3E2;">📊</div>
                <h2 class="card-title">Technical Details</h2>
            </div>

            <div class="data-row">
                <span class="data-label">Server</span>
                <span class="data-value">Apache Tomcat</span>
            </div>
            <div class="data-row">
                <span class="data-label">Session Tracking</span>
                <span class="data-value">Cookie-based</span>
            </div>
            <div class="data-row">
                <span class="data-label">Protocol</span>
                <span class="data-value"><%= request.getProtocol() %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Method</span>
                <span class="data-value"><%= request.getMethod() %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Client IP</span>
                <span class="data-value"><%= request.getRemoteAddr() %></span>
            </div>
            <div class="data-row">
                <span class="data-label">Server Port</span>
                <span class="data-value"><%= request.getServerPort() %></span>
            </div>
        </div>

    </div>

</div>

<script>
    // Auto-refresh page visit count display (optional enhancement)
    console.log('Session ID: <%= userSession.getId() %>');
    console.log('Visit Count: <%= visitCount %>');
    console.log('User: <%= userSession.getAttribute("username") %>');
</script>

</body>
</html>
