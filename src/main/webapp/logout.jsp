<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Invalidate the current session
    HttpSession userSession = request.getSession(false);
    if (userSession != null) {
        userSession.invalidate();
    }

    // Clear all cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logged Out – Serenova Wellness</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,300;0,400;0,600&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --sage:       #7A9E7E;
            --sage-dark:  #5C7A60;
            --sage-light: #EBF2EC;
            --cream:      #FAF7F2;
            --stone:      #8B7D6B;
            --charcoal:   #2D2D2D;
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

        .logout-card {
            background: #fff;
            border: 1px solid #EDE6DD;
            border-radius: 20px;
            padding: 3rem;
            max-width: 480px;
            text-align: center;
            box-shadow: 0 10px 40px rgba(0,0,0,0.06);
            animation: fadeIn 0.5s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: scale(0.95); }
            to   { opacity: 1; transform: scale(1); }
        }

        .logout-icon {
            width: 80px;
            height: 80px;
            background: var(--sage-light);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
            margin: 0 auto 1.5rem;
        }

        .logout-title {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2.2rem;
            font-weight: 400;
            color: var(--charcoal);
            margin-bottom: 0.5rem;
        }

        .logout-message {
            font-size: 0.9rem;
            color: var(--stone);
            line-height: 1.7;
            margin-bottom: 2rem;
        }

        .session-info {
            background: var(--sage-light);
            border-radius: 12px;
            padding: 1rem;
            margin-bottom: 2rem;
            font-size: 0.82rem;
            color: var(--sage-dark);
        }

        .btn-group {
            display: flex;
            gap: 1rem;
        }

        .btn {
            flex: 1;
            padding: 0.9rem 1.5rem;
            border: none;
            border-radius: 10px;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.85rem;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
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

        .brand-footer {
            margin-top: 2rem;
            padding-top: 1.5rem;
            border-top: 1px solid #F5F0EA;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            color: var(--stone);
            font-size: 0.82rem;
        }

        .brand-logo {
            width: 28px;
            height: 28px;
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

<div class="logout-card">
    <div class="logout-icon">👋</div>
    <h1 class="logout-title">You've Been Logged Out</h1>
    <p class="logout-message">
        Your session has been successfully ended. All session data and cookies have been cleared for your security.
    </p>

    <div class="session-info">
        ✓ Session invalidated<br>
        ✓ Cookies cleared<br>
        ✓ Secure logout complete
    </div>

    <div class="btn-group">
        <a href="login.jsp" class="btn btn-primary">Sign In Again</a>
        <a href="register.jsp" class="btn btn-secondary">Create Account</a>
    </div>

    <div class="brand-footer">
        <div class="brand-logo">🌿</div>
        <span>Serenova Wellness Center</span>
    </div>
</div>

<script>
    // Auto-redirect after 5 seconds (optional)
    // setTimeout(function() {
    //     window.location.href = 'login.jsp';
    // }, 5000);
</script>

</body>
</html>
