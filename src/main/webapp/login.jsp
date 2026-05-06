<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login – Serenova Wellness</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,300;0,400;0,600;1,300;1,400&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --sage:        #7A9E7E;
            --sage-dark:   #5C7A60;
            --sage-light:  #C5D9C7;
            --cream:       #FAF7F2;
            --warm-white:  #F5EFE6;
            --stone:       #8B7D6B;
            --charcoal:    #2D2D2D;
            --error:       #C0392B;
            --gold:        #C9A96E;
        }

        body {
            min-height: 100vh;
            display: flex;
            font-family: 'DM Sans', sans-serif;
            background-color: var(--cream);
            overflow: hidden;
        }

        /* ── Left Panel ── */
        .panel-left {
            width: 45%;
            background: linear-gradient(160deg, #3D5A40 0%, #5C7A60 50%, #7A9E7E 100%);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 3rem;
            position: relative;
            overflow: hidden;
        }

        .panel-left::before {
            content: '';
            position: absolute;
            width: 500px; height: 500px;
            border-radius: 50%;
            background: rgba(255,255,255,0.04);
            top: -150px; left: -150px;
        }
        .panel-left::after {
            content: '';
            position: absolute;
            width: 300px; height: 300px;
            border-radius: 50%;
            background: rgba(255,255,255,0.06);
            bottom: 80px; right: -80px;
        }

        .brand {
            position: relative; z-index: 2;
        }
        .brand-icon {
            width: 48px; height: 48px;
            background: rgba(255,255,255,0.15);
            border-radius: 14px;
            display: flex; align-items: center; justify-content: center;
            margin-bottom: 1rem;
            font-size: 1.4rem;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255,255,255,0.2);
        }
        .brand-name {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.8rem;
            font-weight: 300;
            color: #fff;
            letter-spacing: 0.05em;
        }
        .brand-tagline {
            font-size: 0.78rem;
            color: rgba(255,255,255,0.6);
            letter-spacing: 0.15em;
            text-transform: uppercase;
            margin-top: 0.2rem;
        }

        .panel-content {
            position: relative; z-index: 2;
        }
        .panel-heading {
            font-family: 'Cormorant Garamond', serif;
            font-size: 3rem;
            font-weight: 300;
            color: #fff;
            line-height: 1.2;
            margin-bottom: 1.2rem;
        }
        .panel-heading em { font-style: italic; color: var(--sage-light); }
        .panel-sub {
            font-size: 0.9rem;
            color: rgba(255,255,255,0.65);
            line-height: 1.7;
            max-width: 300px;
        }

        .panel-features {
            position: relative; z-index: 2;
            display: flex; flex-direction: column; gap: 0.75rem;
        }
        .feature-item {
            display: flex; align-items: center; gap: 0.75rem;
            font-size: 0.82rem; color: rgba(255,255,255,0.7);
        }
        .feature-dot {
            width: 6px; height: 6px;
            border-radius: 50%;
            background: var(--sage-light);
            flex-shrink: 0;
        }

        /* ── Right Panel ── */
        .panel-right {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 3rem;
            background: var(--cream);
        }

        .form-card {
            width: 100%;
            max-width: 420px;
            animation: fadeUp 0.6s ease both;
        }

        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(24px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        .form-header { margin-bottom: 2.5rem; }
        .form-title {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2.4rem;
            font-weight: 400;
            color: var(--charcoal);
            margin-bottom: 0.4rem;
        }
        .form-subtitle {
            font-size: 0.85rem;
            color: var(--stone);
        }
        .form-subtitle a {
            color: var(--sage-dark);
            text-decoration: none;
            font-weight: 500;
        }
        .form-subtitle a:hover { text-decoration: underline; }

        /* ── Input Groups ── */
        .input-group {
            margin-bottom: 1.4rem;
            position: relative;
        }
        .input-label {
            display: block;
            font-size: 0.75rem;
            font-weight: 500;
            color: var(--stone);
            letter-spacing: 0.08em;
            text-transform: uppercase;
            margin-bottom: 0.5rem;
        }
        .input-wrapper {
            position: relative;
        }
        .input-wrapper .input-icon {
            position: absolute;
            left: 1rem; top: 50%;
            transform: translateY(-50%);
            color: var(--stone);
            font-size: 1rem;
            pointer-events: none;
            transition: color 0.2s;
        }
        .form-input {
            width: 100%;
            padding: 0.85rem 1rem 0.85rem 2.8rem;
            border: 1.5px solid #DDD5C8;
            border-radius: 10px;
            background: #fff;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.9rem;
            color: var(--charcoal);
            transition: border-color 0.2s, box-shadow 0.2s;
            outline: none;
        }
        .form-input:focus {
            border-color: var(--sage);
            box-shadow: 0 0 0 3px rgba(122,158,126,0.15);
        }
        .form-input.input-error {
            border-color: var(--error);
            box-shadow: 0 0 0 3px rgba(192,57,43,0.1);
        }
        .form-input:focus + .input-focus-line { width: 100%; }

        /* password toggle */
        .toggle-password {
            position: absolute;
            right: 1rem; top: 50%;
            transform: translateY(-50%);
            background: none; border: none;
            cursor: pointer; color: var(--stone);
            font-size: 0.85rem;
            padding: 0; transition: color 0.2s;
        }
        .toggle-password:hover { color: var(--sage-dark); }

        .error-msg {
            font-size: 0.75rem;
            color: var(--error);
            margin-top: 0.35rem;
            display: none;
            animation: shake 0.3s ease;
        }
        .error-msg.visible { display: block; }

        @keyframes shake {
            0%,100% { transform: translateX(0); }
            25%      { transform: translateX(-4px); }
            75%      { transform: translateX(4px); }
        }

        /* ── Remember + Forgot ── */
        .form-options {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }
        .remember-label {
            display: flex; align-items: center; gap: 0.5rem;
            font-size: 0.82rem; color: var(--stone);
            cursor: pointer;
        }
        .remember-label input[type="checkbox"] {
            accent-color: var(--sage);
            width: 15px; height: 15px;
        }
        .forgot-link {
            font-size: 0.82rem;
            color: var(--sage-dark);
            text-decoration: none;
            font-weight: 500;
        }
        .forgot-link:hover { text-decoration: underline; }

        /* ── Submit Button ── */
        .btn-submit {
            width: 100%;
            padding: 0.95rem;
            background: linear-gradient(135deg, var(--sage-dark) 0%, var(--sage) 100%);
            color: #fff;
            border: none;
            border-radius: 10px;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.9rem;
            font-weight: 500;
            letter-spacing: 0.05em;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s, opacity 0.2s;
            position: relative;
            overflow: hidden;
        }
        .btn-submit::after {
            content: '';
            position: absolute; inset: 0;
            background: rgba(255,255,255,0);
            transition: background 0.2s;
        }
        .btn-submit:hover {
            transform: translateY(-1px);
            box-shadow: 0 8px 25px rgba(92,122,96,0.4);
        }
        .btn-submit:hover::after { background: rgba(255,255,255,0.08); }
        .btn-submit:active { transform: translateY(0); box-shadow: none; }
        .btn-submit.loading { opacity: 0.7; pointer-events: none; }

        /* ── Divider ── */
        .divider {
            display: flex; align-items: center; gap: 1rem;
            margin: 1.5rem 0;
            color: #C8BFB3; font-size: 0.75rem;
        }
        .divider::before, .divider::after {
            content: ''; flex: 1;
            height: 1px; background: #E5DDD5;
        }

        /* ── Success Toast ── */
        .toast {
            position: fixed;
            top: 1.5rem; right: 1.5rem;
            background: var(--sage-dark);
            color: #fff;
            padding: 0.85rem 1.5rem;
            border-radius: 10px;
            font-size: 0.85rem;
            transform: translateX(150%);
            transition: transform 0.4s cubic-bezier(0.34,1.56,0.64,1);
            z-index: 999;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        .toast.show { transform: translateX(0); }

        /* ── Responsive ── */
        @media (max-width: 768px) {
            body { overflow: auto; }
            .panel-left { display: none; }
            .panel-right { padding: 2rem 1.5rem; }
        }
    </style>
</head>
<body>

<!-- Left Decorative Panel -->
<div class="panel-left">
    <div class="brand">
        <div class="brand-icon">🌿</div>
        <div class="brand-name">Serenova</div>
        <div class="brand-tagline">Wellness Center</div>
    </div>

    <div class="panel-content">
        <h2 class="panel-heading">Welcome<br><em>back to</em><br>balance.</h2>
        <p class="panel-sub">Your wellness journey continues here. Sign in to manage your appointments, track progress, and connect with your care team.</p>
    </div>

    <div class="panel-features">
        <div class="feature-item"><span class="feature-dot"></span>Manage appointments with ease</div>
        <div class="feature-item"><span class="feature-dot"></span>Track your wellness progress</div>
        <div class="feature-item"><span class="feature-dot"></span>Connect with certified specialists</div>
        <div class="feature-item"><span class="feature-dot"></span>Secure &amp; private health records</div>
    </div>
</div>

<!-- Right Form Panel -->
<div class="panel-right">
    <div class="form-card">

        <div class="form-header">
            <h1 class="form-title">Sign In</h1>
            <p class="form-subtitle">Don't have an account? <a href="register.jsp">Create one</a></p>
        </div>

        <form id="loginForm" action="LoginServlet" method="post" novalidate>
            <!-- Email -->
            <div class="input-group">
                <label class="input-label" for="email">Email Address</label>
                <div class="input-wrapper">
                    <span class="input-icon">✉</span>
                    <input type="email" id="email" name="email" class="form-input"
                           placeholder="you@example.com" autocomplete="email">
                </div>
                <span class="error-msg" id="emailError">Please enter a valid email address.</span>
            </div>

            <!-- Password -->
            <div class="input-group">
                <label class="input-label" for="password">Password</label>
                <div class="input-wrapper">
                    <span class="input-icon">🔒</span>
                    <input type="password" id="password" name="password" class="form-input"
                           placeholder="Enter your password" autocomplete="current-password">
                    <button type="button" class="toggle-password" onclick="togglePassword('password', this)">Show</button>
                </div>
                <span class="error-msg" id="passwordError">Password must be at least 6 characters.</span>
            </div>

            <!-- Options -->
            <div class="form-options">
                <label class="remember-label">
                    <input type="checkbox" name="remember"> Remember me
                </label>
                <a href="#" class="forgot-link">Forgot password?</a>
            </div>

            <!-- Submit -->
            <button type="submit" class="btn-submit" id="loginBtn">Sign In to Your Account</button>

        </form>

        <div class="divider">or</div>
        <p style="text-align:center; font-size:0.82rem; color:var(--stone);">
            New staff member? Contact your <a href="#" style="color:var(--sage-dark); text-decoration:none; font-weight:500;">system administrator</a>.
        </p>

    </div>
</div>

<!-- Success Toast -->
<div class="toast" id="toast">✓ Logging you in…</div>

<!-- ============================================================
     CLIENT-SIDE VALIDATION
     ============================================================ -->
<script>
    // ── Helpers ──────────────────────────────────────────────
    function showError(inputId, errorId) {
        document.getElementById(inputId).classList.add('input-error');
        document.getElementById(errorId).classList.add('visible');
    }
    function clearError(inputId, errorId) {
        document.getElementById(inputId).classList.remove('input-error');
        document.getElementById(errorId).classList.remove('visible');
    }
    function isValidEmail(email) {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }

    // ── Toggle Password Visibility ────────────────────────────
    function togglePassword(fieldId, btn) {
        var field = document.getElementById(fieldId);
        if (field.type === 'password') {
            field.type = 'text';
            btn.textContent = 'Hide';
        } else {
            field.type = 'password';
            btn.textContent = 'Show';
        }
    }

    // ── Real-time validation on blur ──────────────────────────
    document.getElementById('email').addEventListener('blur', function () {
        if (!isValidEmail(this.value.trim())) showError('email', 'emailError');
        else clearError('email', 'emailError');
    });
    document.getElementById('email').addEventListener('input', function () {
        if (isValidEmail(this.value.trim())) clearError('email', 'emailError');
    });

    document.getElementById('password').addEventListener('blur', function () {
        if (this.value.trim().length < 6) showError('password', 'passwordError');
        else clearError('password', 'passwordError');
    });
    document.getElementById('password').addEventListener('input', function () {
        if (this.value.trim().length >= 6) clearError('password', 'passwordError');
    });

    // ── Form Submission Validation ────────────────────────────
    document.getElementById('loginForm').addEventListener('submit', function (e) {
        e.preventDefault();   // stop default form submission for validation

        var email    = document.getElementById('email').value.trim();
        var password = document.getElementById('password').value.trim();
        var valid    = true;

        // Validate email
        if (!email || !isValidEmail(email)) {
            showError('email', 'emailError');
            valid = false;
        } else {
            clearError('email', 'emailError');
        }

        // Validate password
        if (password.length < 6) {
            showError('password', 'passwordError');
            valid = false;
        } else {
            clearError('password', 'passwordError');
        }

        // If all valid, show loading state and submit
        if (valid) {
            var btn = document.getElementById('loginBtn');
            btn.classList.add('loading');
            btn.textContent = 'Signing in…';

            // Show toast
            var toast = document.getElementById('toast');
            toast.classList.add('show');
            setTimeout(function () { toast.classList.remove('show'); }, 3000);

            // Submit the form after a brief delay (remove setTimeout in production)
            // In production, just call: this.submit();
            setTimeout(function () {
                document.getElementById('loginForm').submit();
            }, 800);
        }
    });
</script>

</body>
</html>
