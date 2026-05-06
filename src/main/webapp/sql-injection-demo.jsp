<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.util.List, java.util.Map" %>
<%
    List<Map<String, String>> results = (List<Map<String, String>>) request.getAttribute("results");
    String message = (String) request.getAttribute("message");
    String searchEmail = (String) request.getAttribute("searchEmail");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SQL Injection Prevention Demo – Serenova Wellness</title>
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
            --error:      #C0392B;
            --success:    #27AE60;
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
        .card-full { grid-column: 1 / -1; }

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

        /* ── Code Blocks ── */
        .code-block {
            background: #2D2D2D;
            color: #E8E8E8;
            padding: 1.2rem;
            border-radius: 8px;
            font-family: 'Courier New', monospace;
            font-size: 0.8rem;
            overflow-x: auto;
            margin-bottom: 1rem;
            line-height: 1.6;
            border-left: 4px solid var(--error);
        }
        .code-block.secure {
            border-left-color: var(--success);
            background: #1a3a2a;
        }
        .code-label {
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            margin-bottom: 0.75rem;
            color: var(--stone);
        }

        /* ── Input Form ── */
        .input-group {
            margin-bottom: 1.2rem;
        }
        .input-label {
            display: block;
            font-size: 0.75rem;
            font-weight: 500;
            color: var(--stone);
            text-transform: uppercase;
            letter-spacing: 0.08em;
            margin-bottom: 0.5rem;
        }
        .input-wrapper {
            position: relative;
        }
        .form-input {
            width: 100%;
            padding: 0.85rem 1rem;
            border: 1.5px solid #DDD5C8;
            border-radius: 10px;
            background: #fff;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.9rem;
            color: var(--charcoal);
            transition: border-color 0.2s;
            outline: none;
        }
        .form-input:focus {
            border-color: var(--sage);
            box-shadow: 0 0 0 3px rgba(122,158,126,0.15);
        }

        /* ── Buttons ── */
        .btn {
            padding: 0.85rem 1.5rem;
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
            width: 100%;
        }
        .btn-primary:hover {
            box-shadow: 0 6px 20px rgba(92,122,96,0.4);
        }

        /* ── Results Table ── */
        .results-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        .results-table th {
            text-align: left;
            font-size: 0.75rem;
            font-weight: 600;
            color: var(--stone);
            text-transform: uppercase;
            letter-spacing: 0.05em;
            padding: 0.75rem;
            background: var(--sage-light);
            border-bottom: 2px solid var(--sage);
        }
        .results-table td {
            padding: 0.9rem 0.75rem;
            font-size: 0.85rem;
            color: var(--charcoal);
            border-bottom: 1px solid #F5F0EA;
        }
        .results-table tr:hover {
            background: #FDFCFB;
        }

        /* ── Alert Messages ── */
        .alert {
            padding: 1rem 1.2rem;
            border-radius: 10px;
            margin-bottom: 1rem;
            font-size: 0.85rem;
            border-left: 4px solid;
        }
        .alert-info {
            background: #EBF5FB;
            color: #1a3a5c;
            border-left-color: #3498db;
        }
        .alert-success {
            background: #EAFAF1;
            color: #0b5345;
            border-left-color: var(--success);
        }
        .alert-error {
            background: #FADBD8;
            color: #78281f;
            border-left-color: var(--error);
        }

        /* ── Info Box ── */
        .info-box {
            background: linear-gradient(135deg, #EBF2EC, #F5F0EA);
            border-left: 4px solid var(--sage);
            border-radius: 10px;
            padding: 1.2rem;
            margin-top: 1rem;
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
        .info-box-text code {
            background: #FFF;
            padding: 0.2rem 0.4rem;
            border-radius: 4px;
            font-family: monospace;
            color: var(--sage-dark);
        }

        /* ── Data Row ── */
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
        <a href="sql-injection-demo.jsp" class="nav-link active">SQL Security Demo</a>
        <a href="session-demo.jsp" class="nav-link">Session Demo</a>
        <a href="login.jsp" class="nav-link">Logout</a>
    </div>
</nav>

<!-- Main Container -->
<div class="container">

    <!-- Page Header -->
    <div class="page-header">
        <h1 class="page-title">SQL Injection Prevention</h1>
        <p class="page-subtitle">
            Learn how to use prepared statements to prevent SQL injection attacks.<br>
            This demo shows the difference between vulnerable and secure database queries.
        </p>
    </div>

    <!-- Grid Layout -->
    <div class="grid">

        <!-- Vulnerable Query Example -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon" style="background: #FADBD8;">❌</div>
                <h2 class="card-title">Vulnerable Query</h2>
            </div>
            
            <p class="code-label">❌ INSECURE - DO NOT USE</p>
            <div class="code-block">
String email = request.getParameter("email");
String query = "SELECT * FROM users " +
               "WHERE email = '" + email + "'";
ResultSet rs = stmt.executeQuery(query);
            </div>

            <div class="info-box">
                <div class="info-box-title">⚠️ The Problem</div>
                <div class="info-box-text">
                    User input is directly concatenated into the SQL query. If a user enters:
                    <code>' OR '1'='1</code>
                    The query becomes:
                    <code>WHERE email = '' OR '1'='1'</code>
                    This returns ALL users from the database!
                </div>
            </div>
        </div>

        <!-- Secure Query Example -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon" style="background: #EAFAF1;">✓</div>
                <h2 class="card-title">Secure Query (Prepared Statement)</h2>
            </div>
            
            <p class="code-label">✓ SECURE - RECOMMENDED</p>
            <div class="code-block secure">
String email = request.getParameter("email");
String query = "SELECT * FROM users " +
               "WHERE email = ?";
PreparedStatement stmt = 
    conn.prepareStatement(query);
stmt.setString(1, email);
ResultSet rs = stmt.executeQuery();
            </div>

            <div class="info-box">
                <div class="info-box-title">✓ The Solution</div>
                <div class="info-box-text">
                    The <code>?</code> placeholder separates SQL code from user input. 
                    User input is treated as DATA, not SQL code. 
                    Even if a user enters special characters, they are escaped safely.
                </div>
            </div>
        </div>

        <!-- Live Demo Form -->
        <div class="card card-full">
            <div class="card-header">
                <div class="card-icon" style="background: #FEF3E2;">🧪</div>
                <h2 class="card-title">Live Demo: Secure User Search</h2>
            </div>

            <% if (error != null) { %>
                <div class="alert alert-error"><%= error %></div>
            <% } %>

            <% if (message != null && results != null) { %>
                <div class="alert alert-success">✓ <%= message %></div>
            <% } %>

            <form action="SqlInjectionDemoServlet" method="get">
                <input type="hidden" name="action" value="search">
                
                <div class="input-group">
                    <label class="input-label" for="email">Search by Email Address</label>
                    <div class="input-wrapper">
                        <input type="email" id="email" name="email" class="form-input"
                               placeholder="e.g., john@example.com" 
                               value="<%= searchEmail != null ? searchEmail : "" %>">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">🔍 Search (Using PreparedStatement)</button>

                <div class="info-box">
                    <div class="info-box-title">ℹ️ Try This Demo</div>
                    <div class="info-box-text">
                        1. Enter a valid email address to search for users in the database.<br>
                        2. Try entering special characters like <code>' OR '1'='1</code> — they will be treated as literal text, not SQL code.<br>
                        3. The query safely executes using prepared statements, preventing injection attacks.
                    </div>
                </div>
            </form>

            <!-- Results Table -->
            <% if (results != null && !results.isEmpty()) { %>
                <table class="results-table">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Active</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Map<String, String> user : results) { %>
                            <tr>
                                <td><%= user.get("user_id") %></td>
                                <td><%= user.get("first_name") %></td>
                                <td><%= user.get("last_name") %></td>
                                <td><%= user.get("email") %></td>
                                <td><span style="background: var(--sage-light); padding: 0.2rem 0.6rem; border-radius: 4px; font-size: 0.75rem;"><%= user.get("role") %></span></td>
                                <td><%= user.get("is_active") %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>

     

    </div>

</div>

<script>
    // Log security demo access
    console.log('SQL Injection Prevention Demo loaded');
    console.log('✓ All queries on this page use PreparedStatement');
    console.log('✓ User input is safely parameterized');
</script>

</body>
</html>
