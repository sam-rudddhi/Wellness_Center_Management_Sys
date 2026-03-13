<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is registered (session exists)
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("userId") == null) {
        response.sendRedirect("register.jsp");
        return;
    }

    // Retrieve user data from session
    String userId = (String) userSession.getAttribute("userId");
    String username = (String) userSession.getAttribute("username");
    String email = (String) userSession.getAttribute("email");
    String phone = (String) userSession.getAttribute("phone");
    String role = (String) userSession.getAttribute("role");
    String registrationTime = (String) userSession.getAttribute("registrationTime");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Successful – Serenova Wellness</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,300;0,400;0,600&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
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
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem;
        }

        .success-container {
            max-width: 650px;
            background: #fff;
            border: 1px solid #EDE6DD;
            border-radius: 20px;
            padding: 3rem;
            box-shadow: 0 10px 40px rgba(0,0,0,0.06);
            animation: fadeIn 0.6s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        .success-icon {
            width: 90px;
            height: 90px;
            background: var(--sage-light);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 3rem;
            margin: 0 auto 1.5rem;
            animation: bounceIn 0.8s ease 0.2s both;
        }

        @keyframes bounceIn {
            0% { transform: scale(0); }
            50% { transform: scale(1.1); }
            100% { transform: scale(1); }
        }

        h1 {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2.5rem;
            font-weight: 400;
            color: var(--charcoal);
            text-align: center;
            margin-bottom: 0.5rem;
        }

        .subtitle {
            text-align: center;
            font-size: 0.95rem;
            color: var(--stone);
            margin-bottom: 2rem;
            line-height: 1.6;
        }

        .user-info-card {
            background: var(--sage-light);
            border-radius: 14px;
            padding: 1.5rem;
            margin-bottom: 2rem;
        }

        .info-title {
            font-weight: 600;
            font-size: 0.75rem;
            text-transform: uppercase;
            letter-spacing: 0.08em;
            color: var(--sage-dark);
            margin-bottom: 1rem;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            padding: 0.7rem 0;
            border-bottom: 1px solid rgba(92,122,96,0.15);
        }

        .info-row:last-child {
            border-bottom: none;
        }

        .info-label {
            font-size: 0.82rem;
            color: var(--stone);
            font-weight: 500;
        }

        .info-value {
            font-size: 0.85rem;
            color: var(--charcoal);
            font-weight: 500;
            text-align: right;
        }

        .session-details {
            background: #FEF3E2;
            border-left: 4px solid var(--gold);
            border-radius: 10px;
            padding: 1.2rem;
            margin-bottom: 2rem;
        }

        .session-title {
            font-weight: 600;
            font-size: 0.85rem;
            color: var(--gold);
            margin-bottom: 0.6rem;
        }

        .session-item {
            font-size: 0.8rem;
            color: var(--stone);
            line-height: 1.8;
        }

        .session-item strong {
            color: var(--charcoal);
        }

        .next-steps {
            margin-bottom: 2rem;
        }

        .next-steps h3 {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.4rem;
            font-weight: 500;
            color: var(--charcoal);
            margin-bottom: 1rem;
        }

        .step-list {
            list-style: none;
            padding: 0;
        }

        .step-item {
            display: flex;
            align-items: flex-start;
            gap: 1rem;
            margin-bottom: 1rem;
            padding: 1rem;
            background: #FDFCFB;
            border-radius: 10px;
            transition: background 0.2s;
        }

        .step-item:hover {
            background: var(--sage-light);
        }

        .step-number {
            width: 32px;
            height: 32px;
            background: var(--sage);
            color: #fff;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 600;
            font-size: 0.85rem;
            flex-shrink: 0;
        }

        .step-text {
            flex: 1;
            font-size: 0.85rem;
            color: var(--charcoal);
            padding-top: 0.3rem;
        }

        .btn-group {
            display: flex;
            gap: 1rem;
        }

        .btn {
            flex: 1;
            padding: 1rem 1.5rem;
            border: none;
            border-radius: 12px;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.9rem;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .btn:hover {
            transform: translateY(-2px);
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            color: #fff;
            box-shadow: 0 4px 15px rgba(92,122,96,0.3);
        }

        .btn-primary:hover {
            box-shadow: 0 6px 20px rgba(92,122,96,0.4);
        }

        .btn-secondary {
            background: #fff;
            color: var(--sage-dark);
            border: 1.5px solid var(--sage);
        }

        .brand-footer {
            margin-top: 2rem;
            padding-top: 1.5rem;
            border-top: 1px solid #F5F0EA;
            text-align: center;
        }

        .brand-logo {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            color: var(--stone);
            font-size: 0.85rem;
        }

        .logo-icon {
            width: 32px;
            height: 32px;
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1rem;
        }

        @media (max-width: 600px) {
            .success-container { padding: 2rem 1.5rem; }
            .btn-group { flex-direction: column; }
            h1 { font-size: 2rem; }
        }
    </style>
</head>
<body>

<div class="success-container">

    <div class="success-icon">✓</div>

    <h1>Welcome to Serenova!</h1>
    <p class="subtitle">
        Your account has been successfully created. You can now access all wellness services and features.
    </p>

    <!-- User Information Card -->
    <div class="user-info-card">
        <div class="info-title">Your Account Details</div>
        <div class="info-row">
            <span class="info-label">User ID</span>
            <span class="info-value"><%= userId %></span>
        </div>
        <div class="info-row">
            <span class="info-label">Full Name</span>
            <span class="info-value"><%= username %></span>
        </div>
        <div class="info-row">
            <span class="info-label">Email</span>
            <span class="info-value"><%= email %></span>
        </div>
        <div class="info-row">
            <span class="info-label">Phone</span>
            <span class="info-value"><%= phone != null ? phone : "Not provided" %></span>
        </div>
        <div class="info-row">
            <span class="info-label">Account Type</span>
            <span class="info-value"><%= role %></span>
        </div>
    </div>

    <!-- Session Details (For Demonstration) -->
    <div class="session-details">
        <div class="session-title">🔒 Session Information (Demonstration)</div>
        <div class="session-item"><strong>Session ID:</strong> <%= userSession.getId() %></div>
        <div class="session-item"><strong>Registration Time:</strong> <%= registrationTime %></div>
        <div class="session-item"><strong>Session Timeout:</strong> <%= userSession.getMaxInactiveInterval() / 60 %> minutes</div>
        <div class="session-item"><strong>Session Status:</strong> Active</div>
    </div>

    <!-- Next Steps -->
    <div class="next-steps">
        <h3>What's Next?</h3>
        <ul class="step-list">
            <li class="step-item">
                <div class="step-number">1</div>
                <div class="step-text">Complete your health profile to get personalized recommendations</div>
            </li>
            <li class="step-item">
                <div class="step-number">2</div>
                <div class="step-text">Book your first consultation with a certified wellness specialist</div>
            </li>
            <li class="step-item">
                <div class="step-number">3</div>
                <div class="step-text">Explore our wellness programs and join a community</div>
            </li>
        </ul>
    </div>

    <!-- Action Buttons -->
    <div class="btn-group">
        <a href="dashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
        <a href="session-demo.jsp" class="btn btn-secondary">View Session Details</a>
    </div>

    <!-- Brand Footer -->
    <div class="brand-footer">
        <div class="brand-logo">
            <div class="logo-icon">🌿</div>
            <span>Serenova Wellness Center</span>
        </div>
    </div>

</div>

</body>
</html>
