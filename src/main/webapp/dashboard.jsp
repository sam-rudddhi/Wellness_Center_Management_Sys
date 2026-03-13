<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard – Serenova Wellness</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,300;0,400;0,600;1,300&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet">
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
        }

        /* ── Top Nav ── */
        nav {
            background: #fff;
            border-bottom: 1px solid #EDE6DD;
            padding: 0 2.5rem;
            display: flex; align-items: center;
            justify-content: space-between;
            height: 64px;
            position: sticky; top: 0; z-index: 100;
        }
        .nav-brand {
            display: flex; align-items: center; gap: 0.75rem;
        }
        .nav-logo {
            width: 36px; height: 36px;
            background: linear-gradient(135deg, var(--sage-dark), var(--sage));
            border-radius: 10px;
            display: flex; align-items: center; justify-content: center;
            font-size: 1.1rem;
        }
        .nav-name {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.4rem; font-weight: 400;
            color: var(--charcoal);
        }
        .nav-actions { display: flex; align-items: center; gap: 1rem; }
        .nav-user {
            display: flex; align-items: center; gap: 0.6rem;
            font-size: 0.85rem; color: var(--stone);
        }
        .nav-avatar {
            width: 34px; height: 34px;
            border-radius: 50%;
            background: var(--sage-light);
            border: 2px solid var(--sage);
            display: flex; align-items: center; justify-content: center;
            font-size: 0.85rem; color: var(--sage-dark);
            font-weight: 500;
        }
        .btn-logout {
            padding: 0.45rem 1rem;
            border: 1.5px solid #DDD5C8;
            border-radius: 8px;
            background: none;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.8rem; color: var(--stone);
            cursor: pointer;
            transition: background 0.2s, border-color 0.2s;
        }
        .btn-logout:hover { background: var(--sage-light); border-color: var(--sage); color: var(--sage-dark); }

        /* ── Main Layout ── */
        .main {
            padding: 2.5rem;
            max-width: 1100px;
            margin: 0 auto;
            animation: fadeUp 0.5s ease;
        }
        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(16px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        /* ── Welcome Banner ── */
        .welcome-banner {
            background: linear-gradient(135deg, #3D5A40, #5C7A60, #7A9E7E);
            border-radius: 16px;
            padding: 2rem 2.5rem;
            display: flex; justify-content: space-between; align-items: center;
            margin-bottom: 2rem;
            overflow: hidden;
            position: relative;
        }
        .welcome-banner::after {
            content: '🌿';
            position: absolute;
            right: 2rem; top: 50%;
            transform: translateY(-50%);
            font-size: 5rem;
            opacity: 0.15;
        }
        .welcome-text h2 {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2rem; font-weight: 300;
            color: #fff; margin-bottom: 0.3rem;
        }
        .welcome-text h2 em { font-style: italic; color: #C5D9C7; }
        .welcome-text p {
            font-size: 0.85rem; color: rgba(255,255,255,0.65);
        }
        .welcome-date {
            font-size: 0.8rem; color: rgba(255,255,255,0.55);
            text-align: right;
        }

        /* ── Stat Cards ── */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1.2rem;
            margin-bottom: 2rem;
        }
        .stat-card {
            background: #fff;
            border: 1px solid #EDE6DD;
            border-radius: 14px;
            padding: 1.4rem;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 24px rgba(0,0,0,0.06);
        }
        .stat-icon {
            width: 40px; height: 40px;
            border-radius: 10px;
            display: flex; align-items: center; justify-content: center;
            font-size: 1.1rem; margin-bottom: 0.8rem;
        }
        .stat-value {
            font-family: 'Cormorant Garamond', serif;
            font-size: 2rem; font-weight: 400;
            color: var(--charcoal); margin-bottom: 0.15rem;
        }
        .stat-label {
            font-size: 0.75rem; color: var(--stone);
            text-transform: uppercase; letter-spacing: 0.05em;
        }

        /* ── Bottom Grid ── */
        .bottom-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1.5rem;
        }
        .card {
            background: #fff;
            border: 1px solid #EDE6DD;
            border-radius: 14px;
            padding: 1.5rem;
        }
        .card-title {
            font-family: 'Cormorant Garamond', serif;
            font-size: 1.2rem; font-weight: 400;
            color: var(--charcoal); margin-bottom: 1rem;
            padding-bottom: 0.75rem;
            border-bottom: 1px solid #F0EAE1;
        }
        .appt-item {
            display: flex; align-items: center; gap: 1rem;
            padding: 0.75rem 0;
            border-bottom: 1px solid #F5F0EA;
        }
        .appt-item:last-child { border-bottom: none; }
        .appt-dot {
            width: 8px; height: 8px;
            border-radius: 50%; flex-shrink: 0;
        }
        .appt-info h4 { font-size: 0.88rem; color: var(--charcoal); font-weight: 500; }
        .appt-info p  { font-size: 0.75rem; color: var(--stone); margin-top: 0.1rem; }
        .appt-badge {
            margin-left: auto;
            padding: 0.2rem 0.7rem;
            border-radius: 99px;
            font-size: 0.7rem; font-weight: 500;
        }

        .quick-link {
            display: flex; align-items: center; gap: 0.75rem;
            padding: 0.85rem 1rem;
            border-radius: 10px;
            text-decoration: none;
            color: var(--charcoal);
            transition: background 0.2s;
            font-size: 0.85rem;
            margin-bottom: 0.4rem;
        }
        .quick-link:hover { background: var(--sage-light); color: var(--sage-dark); }
        .quick-link-icon {
            width: 34px; height: 34px;
            border-radius: 8px;
            background: var(--sage-light);
            display: flex; align-items: center; justify-content: center;
            font-size: 1rem;
        }

        @media (max-width: 900px) {
            .stats-grid { grid-template-columns: repeat(2, 1fr); }
            .bottom-grid { grid-template-columns: 1fr; }
        }
        @media (max-width: 600px) {
            .main { padding: 1.5rem; }
            .welcome-banner { flex-direction: column; align-items: flex-start; gap: 0.5rem; }
            .stats-grid { grid-template-columns: 1fr 1fr; }
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
    <div class="nav-actions">
        <div class="nav-user">
            <div class="nav-avatar">JD</div>
            <span>Jane Doe</span>
        </div>
        <form action="LogoutServlet" method="post" style="display:inline;">
            <button type="submit" class="btn-logout">Sign Out</button>
        </form>
    </div>
</nav>

<!-- Main Content -->
<div class="main">

    <!-- Welcome Banner -->
    <div class="welcome-banner">
        <div class="welcome-text">
            <h2>Good morning, <em>Jane</em> 👋</h2>
            <p>Here's an overview of your wellness activity today.</p>
        </div>
        <div class="welcome-date" id="currentDate"></div>
    </div>

    <!-- Stats -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon" style="background:#EBF2EC;">📅</div>
            <div class="stat-value">3</div>
            <div class="stat-label">Upcoming Appointments</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon" style="background:#FEF3E2;">✅</div>
            <div class="stat-value">12</div>
            <div class="stat-label">Sessions Completed</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon" style="background:#EBF2EC;">🧘</div>
            <div class="stat-value">5</div>
            <div class="stat-label">Programs Enrolled</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon" style="background:#FEF0F0;">💆</div>
            <div class="stat-value">84%</div>
            <div class="stat-label">Wellness Score</div>
        </div>
    </div>

    <!-- Bottom Grid -->
    <div class="bottom-grid">

        <!-- Upcoming Appointments -->
        <div class="card">
            <div class="card-title">Upcoming Appointments</div>
            <div class="appt-item">
                <div class="appt-dot" style="background:#7A9E7E;"></div>
                <div class="appt-info">
                    <h4>Yoga &amp; Mindfulness Session</h4>
                    <p>Tomorrow · 9:00 AM · Dr. Priya Sharma</p>
                </div>
                <div class="appt-badge" style="background:#EBF2EC; color:#5C7A60;">Confirmed</div>
            </div>
            <div class="appt-item">
                <div class="appt-dot" style="background:#C9A96E;"></div>
                <div class="appt-info">
                    <h4>Nutritional Consultation</h4>
                    <p>Feb 15 · 2:30 PM · Dr. Anil Mehta</p>
                </div>
                <div class="appt-badge" style="background:#FEF3E2; color:#C9A96E;">Pending</div>
            </div>
            <div class="appt-item">
                <div class="appt-dot" style="background:#7A9E7E;"></div>
                <div class="appt-info">
                    <h4>Physiotherapy</h4>
                    <p>Feb 18 · 11:00 AM · Ms. Kavitha Rao</p>
                </div>
                <div class="appt-badge" style="background:#EBF2EC; color:#5C7A60;">Confirmed</div>
            </div>
        </div>

        <!-- Quick Links -->
        <div class="card">
            <div class="card-title">Quick Actions</div>
            <a href="#" class="quick-link">
                <div class="quick-link-icon">📅</div>
                Book a New Appointment
            </a>
            <a href="#" class="quick-link">
                <div class="quick-link-icon">📋</div>
                View My Health Records
            </a>
            <a href="#" class="quick-link">
                <div class="quick-link-icon">💬</div>
                Message My Care Team
            </a>
            <a href="#" class="quick-link">
                <div class="quick-link-icon">🧘</div>
                Browse Wellness Programs
            </a>
            <a href="#" class="quick-link">
                <div class="quick-link-icon">💳</div>
                View Billing &amp; Invoices
            </a>
        </div>

    </div>
</div>

<script>
    // Set current date
    var d = new Date();
    var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    document.getElementById('currentDate').textContent = d.toLocaleDateString('en-IN', options);
</script>

</body>
</html>
