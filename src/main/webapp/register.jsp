<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register – Serenova Wellness</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,300;0,400;0,600;1,300;1,400&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --sage:       #7A9E7E;
            --sage-dark:  #5C7A60;
            --sage-light: #C5D9C7;
            --cream:      #FAF7F2;
            --stone:      #8B7D6B;
            --charcoal:   #2D2D2D;
            --error:      #C0392B;
            --success:    #27AE60;
        }

        body {
            min-height: 100vh;
            font-family: 'DM Sans', sans-serif;
            background: var(--cream);
            display: flex;
            align-items: stretch;
        }

        /* ── Left Accent Strip ── */
        .panel-left {
            width: 42%;
            background: linear-gradient(170deg, #2D4A31 0%, #3D5A40 40%, #5C7A60 80%, #7A9E7E 100%);
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 4rem 3rem;
            position: relative;
            overflow: hidden;
        }
        .panel-left::before {
            content: '';
            position: absolute;
            width: 600px; height: 600px; border-radius: 50%;
            background: rgba(255,255,255,0.03);
            top: -200px; right: -200px;
        }
        .circle-deco {
            position: absolute;
            border-radius: 50%;
            border: 1px solid rgba(255,255,255,0.08);
        }
        .c1 { width:200px; height:200px; bottom: 60px; left: -80px; }
        .c2 { width:120px; height:120px; bottom: 30px; left: -20px; }

        .brand { margin-bottom: 4rem; position: relative; z-index: 2; }
        .brand-icon {
            width: 50px; height: 50px;
            background: rgba(255,255,255,0.12);
            border-radius: 14px;
            display: flex; align-items: center; justify-content: center;
            font-size: 1.5rem; margin-bottom: 1rem;
            border: 1px solid rgba(255,255,255,0.18);
        }
        .brand-name {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.8rem; font-weight: 300;
            color: #fff; letter-spacing: 0.05em;
        }
        .brand-tagline {
            font-size: 0.72rem; color: rgba(255,255,255,0.55);
            letter-spacing: 0.18em; text-transform: uppercase; margin-top: 0.2rem;
        }

        .left-heading {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2.8rem; font-weight: 300; line-height: 1.2;
            color: #fff; margin-bottom: 1rem;
            position: relative; z-index: 2;
        }
        .left-heading em { font-style: italic; color: var(--sage-light); }

        .left-text {
            font-size: 0.88rem;
            color: rgba(255,255,255,0.6);
            line-height: 1.8; max-width: 280px;
            position: relative; z-index: 2;
            margin-bottom: 3rem;
        }

        .steps { position: relative; z-index: 2; }
        .step-item {
            display: flex; align-items: flex-start; gap: 1rem;
            margin-bottom: 1.2rem;
        }
        .step-num {
            width: 28px; height: 28px; flex-shrink: 0;
            border-radius: 50%;
            background: rgba(255,255,255,0.12);
            border: 1px solid rgba(255,255,255,0.2);
            display: flex; align-items: center; justify-content: center;
            font-size: 0.72rem; color: rgba(255,255,255,0.8); font-weight: 500;
        }
        .step-text {
            font-size: 0.82rem; color: rgba(255,255,255,0.65);
            padding-top: 0.3rem; line-height: 1.5;
        }
        .step-text strong { color: rgba(255,255,255,0.9); display: block; font-weight: 500; }

        /* ── Right Form Panel ── */
        .panel-right {
            flex: 1;
            display: flex; align-items: flex-start;
            justify-content: center;
            padding: 3rem 3rem 3rem 3.5rem;
            overflow-y: auto;
        }

        .form-card {
            width: 100%; max-width: 460px;
            padding-top: 1rem;
            animation: fadeUp 0.6s ease both;
        }

        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(20px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        .form-header { margin-bottom: 2rem; }
        .form-title {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2.2rem; font-weight: 400;
            color: var(--charcoal); margin-bottom: 0.3rem;
        }
        .form-subtitle {
            font-size: 0.83rem; color: var(--stone);
        }
        .form-subtitle a {
            color: var(--sage-dark); text-decoration: none; font-weight: 500;
        }
        .form-subtitle a:hover { text-decoration: underline; }

        /* ── Form Row (2 columns) ── */
        .form-row {
            display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;
            margin-bottom: 0;
        }

        /* ── Input Groups ── */
        .input-group { margin-bottom: 1.2rem; }
        .input-label {
            display: block;
            font-size: 0.72rem; font-weight: 500;
            color: var(--stone);
            letter-spacing: 0.08em; text-transform: uppercase;
            margin-bottom: 0.45rem;
        }
        .input-wrapper { position: relative; }
        .input-wrapper .input-icon {
            position: absolute; left: 0.9rem; top: 50%;
            transform: translateY(-50%);
            font-size: 0.9rem; color: #B8AFA6;
            pointer-events: none; transition: color 0.2s;
        }
        .form-input {
            width: 100%;
            padding: 0.8rem 1rem 0.8rem 2.6rem;
            border: 1.5px solid #DDD5C8;
            border-radius: 10px;
            background: #fff;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.88rem; color: var(--charcoal);
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
        .form-input.input-valid {
            border-color: var(--success);
        }
        select.form-input { appearance: none; cursor: pointer; }

        .toggle-password {
            position: absolute; right: 0.9rem; top: 50%;
            transform: translateY(-50%);
            background: none; border: none;
            cursor: pointer; color: var(--stone);
            font-size: 0.8rem; padding: 0;
            transition: color 0.2s;
        }
        .toggle-password:hover { color: var(--sage-dark); }

        .error-msg {
            font-size: 0.72rem; color: var(--error);
            margin-top: 0.3rem; display: none;
        }
        .error-msg.visible { display: block; }

        /* ── Password Strength ── */
        .strength-bar {
            display: flex; gap: 4px; margin-top: 0.5rem;
        }
        .strength-segment {
            flex: 1; height: 3px; border-radius: 99px;
            background: #E5DDD5;
            transition: background 0.3s;
        }
        .strength-label {
            font-size: 0.7rem; color: var(--stone); margin-top: 0.3rem;
        }

        /* ── Terms ── */
        .terms-group {
            display: flex; align-items: flex-start; gap: 0.75rem;
            margin-bottom: 1.4rem;
        }
        .terms-group input[type="checkbox"] {
            accent-color: var(--sage);
            width: 16px; height: 16px;
            margin-top: 2px; flex-shrink: 0;
        }
        .terms-text {
            font-size: 0.8rem; color: var(--stone); line-height: 1.5;
        }
        .terms-text a { color: var(--sage-dark); text-decoration: none; font-weight: 500; }

        /* ── Submit Button ── */
        .btn-submit {
            width: 100%; padding: 0.95rem;
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            color: #fff; border: none; border-radius: 10px;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.9rem; font-weight: 500;
            letter-spacing: 0.04em;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s, opacity 0.2s;
            position: relative; overflow: hidden;
        }
        .btn-submit:hover {
            transform: translateY(-1px);
            box-shadow: 0 8px 24px rgba(92,122,96,0.4);
        }
        .btn-submit:active { transform: translateY(0); box-shadow: none; }
        .btn-submit.loading { opacity: 0.7; pointer-events: none; }

        /* ── Section Label ── */
        .section-label {
            font-size: 0.7rem; font-weight: 500;
            letter-spacing: 0.12em; text-transform: uppercase;
            color: var(--sage-dark);
            padding: 0.5rem 0 0.8rem;
            border-bottom: 1px solid #E5DDD5;
            margin-bottom: 1rem;
        }

        /* ── Toast ── */
        .toast {
            position: fixed; top: 1.5rem; right: 1.5rem;
            background: var(--sage-dark); color: #fff;
            padding: 0.85rem 1.5rem; border-radius: 10px;
            font-size: 0.85rem;
            transform: translateX(150%);
            transition: transform 0.4s cubic-bezier(0.34,1.56,0.64,1);
            z-index: 999;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        .toast.show { transform: translateX(0); }

        @media (max-width: 768px) {
            body { display: block; }
            .panel-left { display: none; }
            .panel-right { padding: 2rem 1.5rem; }
            .form-row { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>

<!-- Left Panel -->
<div class="panel-left">
    <div class="circle-deco c1"></div>
    <div class="circle-deco c2"></div>

    <div class="brand">
        <div class="brand-icon">🌿</div>
        <div class="brand-name">Serenova</div>
        <div class="brand-tagline">Wellness Center</div>
    </div>

    <h2 class="left-heading">Begin your<br><em>wellness</em><br>journey.</h2>
    <p class="left-text">Join thousands who trust Serenova for holistic health management. Create your account and take the first step toward lasting wellbeing.</p>

    <div class="steps">
        <div class="step-item">
            <div class="step-num">1</div>
            <div class="step-text"><strong>Create your profile</strong>Fill in your personal details securely</div>
        </div>
        <div class="step-item">
            <div class="step-num">2</div>
            <div class="step-text"><strong>Book a consultation</strong>Choose from our certified specialists</div>
        </div>
        <div class="step-item">
            <div class="step-num">3</div>
            <div class="step-text"><strong>Track your progress</strong>Monitor your wellness milestones</div>
        </div>
    </div>
</div>

<!-- Right Form Panel -->
<div class="panel-right">
    <div class="form-card">

        <div class="form-header">
            <h1 class="form-title">Create Account</h1>
            <p class="form-subtitle">Already registered? <a href="login.jsp">Sign in here</a></p>
        </div>

        <form id="registerForm" action="RegisterServlet" method="post">
            <!-- Personal Info -->
            <div class="section-label">Personal Information</div>

            <div class="form-row">
                <div class="input-group">
                    <label class="input-label" for="firstName">First Name</label>
                    <div class="input-wrapper">
                        <span class="input-icon">👤</span>
                        <input type="text" id="firstName" name="firstName" class="form-input"
                               placeholder="Jane" autocomplete="given-name">
                    </div>
                    <span class="error-msg" id="firstNameError">First name is required.</span>
                </div>
                <div class="input-group">
                    <label class="input-label" for="lastName">Last Name</label>
                    <div class="input-wrapper">
                        <span class="input-icon">👤</span>
                        <input type="text" id="lastName" name="lastName" class="form-input"
                               placeholder="Doe" autocomplete="family-name">
                    </div>
                    <span class="error-msg" id="lastNameError">Last name is required.</span>
                </div>
            </div>

            <div class="form-row">
                <div class="input-group">
                    <label class="input-label" for="phone">Phone Number</label>
                    <div class="input-wrapper">
                        <span class="input-icon">📞</span>
                        <input type="tel" id="phone" name="phone" class="form-input"
                               placeholder="+91 98765 43210" autocomplete="tel">
                    </div>
                    <span class="error-msg" id="phoneError">Enter a valid phone number.</span>
                </div>
                <div class="input-group">
                    <label class="input-label" for="dob">Date of Birth</label>
                    <div class="input-wrapper">
                        <span class="input-icon">📅</span>
                        <input type="date" id="dob" name="dob" class="form-input" autocomplete="bdate">
                    </div>
                    <span class="error-msg" id="dobError">Please enter a valid date of birth.</span>
                </div>
            </div>

            <div class="input-group">
                <label class="input-label" for="gender">Gender</label>
                <div class="input-wrapper">
                    <span class="input-icon">⚥</span>
                    <select id="gender" name="gender" class="form-input">
                        <option value="">Select gender</option>
                        <option value="female">Female</option>
                        <option value="male">Male</option>
                        <option value="non-binary">Non-binary</option>
                        <option value="prefer-not">Prefer not to say</option>
                    </select>
                </div>
                <span class="error-msg" id="genderError">Please select your gender.</span>
            </div>

            <!-- Account Info -->
            <div class="section-label" style="margin-top:0.5rem;">Account Credentials</div>

            <div class="input-group">
                <label class="input-label" for="email">Email Address</label>
                <div class="input-wrapper">
                    <span class="input-icon">✉</span>
                    <input type="email" id="email" name="email" class="form-input"
                           placeholder="you@example.com" autocomplete="email">
                </div>
                <span class="error-msg" id="emailError">Please enter a valid email address.</span>
            </div>

            <div class="input-group">
                <label class="input-label" for="password">Password</label>
                <div class="input-wrapper">
                    <span class="input-icon">🔒</span>
                    <input type="password" id="password" name="password" class="form-input"
                           placeholder="Min. 8 characters" autocomplete="new-password">
                    <button type="button" class="toggle-password" onclick="togglePassword('password', this)">Show</button>
                </div>
                <div class="strength-bar">
                    <div class="strength-segment" id="seg1"></div>
                    <div class="strength-segment" id="seg2"></div>
                    <div class="strength-segment" id="seg3"></div>
                    <div class="strength-segment" id="seg4"></div>
                </div>
                <div class="strength-label" id="strengthLabel"></div>
                <span class="error-msg" id="passwordError">Password must be at least 8 characters.</span>
            </div>

            <div class="input-group">
                <label class="input-label" for="confirmPassword">Confirm Password</label>
                <div class="input-wrapper">
                    <span class="input-icon">🔒</span>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-input"
                           placeholder="Re-enter your password" autocomplete="new-password">
                    <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword', this)">Show</button>
                </div>
                <span class="error-msg" id="confirmPasswordError">Passwords do not match.</span>
            </div>

            <!-- Terms -->
            <div class="terms-group">
                <input type="checkbox" id="terms" name="terms">
                <span class="terms-text">
                    I agree to the <a href="#">Terms of Service</a> and
                    <a href="#">Privacy Policy</a> of Serenova Wellness Center.
                </span>
            </div>
            <span class="error-msg" id="termsError" style="margin-top:-0.8rem; margin-bottom:1rem;">You must agree to the terms.</span>

            <!-- Submit -->
            <button type="submit" class="btn-submit" id="registerBtn">Create My Account</button>

        </form>

    </div>
</div>

<!-- Toast -->
<div class="toast" id="toast">✓ Account created! Redirecting…</div>

<!-- ============================================================
     CLIENT-SIDE VALIDATION
     ============================================================ -->
<script>
    // ── Helpers ──────────────────────────────────────────────
    function showError(inputId, errorId) {
        var input = document.getElementById(inputId);
        if (input) input.classList.add('input-error');
        var err = document.getElementById(errorId);
        if (err) err.classList.add('visible');
    }
    function clearError(inputId, errorId) {
        var input = document.getElementById(inputId);
        if (input) { input.classList.remove('input-error'); input.classList.add('input-valid'); }
        var err = document.getElementById(errorId);
        if (err) err.classList.remove('visible');
    }
    function isValidEmail(v) { return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v); }
    function isValidPhone(v) { return /^[+]?[\d\s\-()]{7,15}$/.test(v); }

    // ── Toggle Password Visibility ────────────────────────────
    function togglePassword(fieldId, btn) {
        var f = document.getElementById(fieldId);
        if (f.type === 'password') { f.type = 'text'; btn.textContent = 'Hide'; }
        else { f.type = 'password'; btn.textContent = 'Show'; }
    }

    // ── Password Strength Meter ───────────────────────────────
    var colors = ['#C0392B', '#E67E22', '#F1C40F', '#27AE60'];
    var labels = ['Weak', 'Fair', 'Good', 'Strong'];

    function calcStrength(pw) {
        var score = 0;
        if (pw.length >= 8)  score++;
        if (/[A-Z]/.test(pw)) score++;
        if (/[0-9]/.test(pw)) score++;
        if (/[^A-Za-z0-9]/.test(pw)) score++;
        return score;   // 0-4
    }

    document.getElementById('password').addEventListener('input', function () {
        var pw = this.value;
        var score = calcStrength(pw);
        for (var i = 1; i <= 4; i++) {
            var seg = document.getElementById('seg' + i);
            seg.style.background = (i <= score) ? colors[score - 1] : '#E5DDD5';
        }
        var lbl = document.getElementById('strengthLabel');
        lbl.textContent = pw.length ? labels[score - 1] || '' : '';
        lbl.style.color  = pw.length ? colors[score - 1] : '';
    });

    // ── Real-time Blur Validation ─────────────────────────────
    function addBlurValidation(id, errorId, validFn) {
        var el = document.getElementById(id);
        if (!el) return;
        el.addEventListener('blur', function () {
            if (!validFn(this.value.trim())) showError(id, errorId);
            else clearError(id, errorId);
        });
        el.addEventListener('input', function () {
            if (validFn(this.value.trim())) clearError(id, errorId);
        });
    }

    addBlurValidation('firstName', 'firstNameError', function(v){ return v.length >= 2; });
    addBlurValidation('lastName',  'lastNameError',  function(v){ return v.length >= 2; });
    addBlurValidation('phone',     'phoneError',     function(v){ return isValidPhone(v); });
    addBlurValidation('email',     'emailError',     function(v){ return isValidEmail(v); });

    document.getElementById('dob').addEventListener('blur', function () {
        var dob = new Date(this.value);
        var now = new Date();
        var age = now.getFullYear() - dob.getFullYear();
        if (!this.value || age < 16 || age > 120) showError('dob', 'dobError');
        else clearError('dob', 'dobError');
    });

    document.getElementById('confirmPassword').addEventListener('input', function () {
        var pw  = document.getElementById('password').value;
        if (this.value === pw && pw.length > 0) clearError('confirmPassword', 'confirmPasswordError');
        else if (this.value.length > 0) showError('confirmPassword', 'confirmPasswordError');
    });

    // ── Full Form Validation on Submit ────────────────────────
    document.getElementById('registerForm').addEventListener('submit', function (e) {
        e.preventDefault();
        var valid = true;

        var firstName       = document.getElementById('firstName').value.trim();
        var lastName        = document.getElementById('lastName').value.trim();
        var phone           = document.getElementById('phone').value.trim();
        var dob             = document.getElementById('dob').value;
        var gender          = document.getElementById('gender').value;
        var email           = document.getElementById('email').value.trim();
        var password        = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        var terms           = document.getElementById('terms').checked;

        // First Name
        if (firstName.length < 2) { showError('firstName', 'firstNameError'); valid = false; }
        else clearError('firstName', 'firstNameError');

        // Last Name
        if (lastName.length < 2) { showError('lastName', 'lastNameError'); valid = false; }
        else clearError('lastName', 'lastNameError');

        // Phone
        if (!isValidPhone(phone)) { showError('phone', 'phoneError'); valid = false; }
        else clearError('phone', 'phoneError');

        // DOB (must be at least 16 years old)
        var dobDate = new Date(dob);
        var now = new Date();
        var age = now.getFullYear() - dobDate.getFullYear();
        if (!dob || age < 16 || age > 120) { showError('dob', 'dobError'); valid = false; }
        else clearError('dob', 'dobError');

        // Gender
        if (!gender) { showError('gender', 'genderError'); valid = false; }
        else clearError('gender', 'genderError');

        // Email
        if (!isValidEmail(email)) { showError('email', 'emailError'); valid = false; }
        else clearError('email', 'emailError');

        // Password (min 8 chars)
        if (password.length < 8) { showError('password', 'passwordError'); valid = false; }
        else clearError('password', 'passwordError');

        // Confirm Password
        if (password !== confirmPassword) { showError('confirmPassword', 'confirmPasswordError'); valid = false; }
        else clearError('confirmPassword', 'confirmPasswordError');

        // Terms
        var termsErr = document.getElementById('termsError');
        if (!terms) {
            termsErr.classList.add('visible'); valid = false;
        } else {
            termsErr.classList.remove('visible');
        }

        if (valid) {
            var btn = document.getElementById('registerBtn');
            btn.classList.add('loading');
            btn.textContent = 'Creating account…';

            var toast = document.getElementById('toast');
            toast.classList.add('show');
            setTimeout(function () { toast.classList.remove('show'); }, 3000);

            // Remove setTimeout in production, just call: document.getElementById('registerForm').submit();
            setTimeout(function () {
                document.getElementById('registerForm').submit();
            }, 900);
        } else {
            // Scroll to first error
            var firstError = document.querySelector('.input-error');
            if (firstError) firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    });
</script>

</body>
</html>
