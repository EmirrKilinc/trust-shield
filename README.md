<p align="center">
<img src="https://www.google.com/search?q=https://img.shields.io/badge/Turkcell-CodeNight-blue%3Fstyle%3Dfor-the-badge%26logo%3Dturkcell" alt="Turkcell CodeNight" />
<img src="https://www.google.com/search?q=https://img.shields.io/badge/Status-Active-success%3Fstyle%3Dfor-the-badge" alt="Status" />
<img src="https://www.google.com/search?q=https://img.shields.io/badge/License-Turkcell-orange%3Fstyle%3Dfor-the-badge" alt="License" />
</p>

<h1 align="center">🛡️ TrustShield</h1>

<p align="center">
<strong>Modern Fraud Detection and Risk Management Platform for Turkcell Ecosystem</strong>
<br />
<i>Paycell • BiP • Superonline • TV+</i>
</p>

📸 Screenshots

<p align="center">
<img src="docs/screenshots/dashboard.png" width="45%" alt="Dashboard" />
<img src="docs/screenshots/fraud_cases.png" width="45%" alt="Fraud Cases" />
</p>
<p align="center">
<img src="docs/screenshots/decisions.png" width="91%" alt="Decision Logs" />
</p>

🎯 Project Overview

TrustShield is a robust security layer designed to analyze suspicious activities in digital services in real-time. It provides operators with full control through rule-based risk assessment, advanced vaka management, and transparent decision logs.

✨ Core Features

📊 Dashboard & Monitoring

Summary Cards: Real-time tracking of total events, open cases, and high-risk users.

Live Data: Manual refresh capabilities and instant risk scoring.

Visualization: Dynamic progress bars and color-coded alerts for intuitive monitoring.

🛡️ Fraud Case Management

Smart Workflow: Seamless status management: OPEN ➡️ IN_PROGRESS ➡️ CLOSED.

Prioritization: Dynamic sorting from CRITICAL to LOW priority levels.

User Scorecard: Tracking user-based risk signals and historical activity data.

⚙️ Risk Engine & Rule Management

Dynamic Rules: Full CRUD operations to define new security rules on the fly.

Event Filtering: Categorized data streams for PAYMENT, LOGIN, and SUBSCRIPTION.

Automated Actions: Integrated triggers for FORCE_2FA, TEMPORARY_BLOCK, and ANOMALY_ALERT.

🏗️ Tech Stack

Layer

Technology

Description

Backend



Robust RESTful API architecture

Frontend



High-performance SPA with Context API

Styling



Utility-first, responsive design system

DevOps



Multi-container orchestration (Compose)

Database



Seamless Data Access Layer

🚀 Quick Start

🐳 Docker (Recommended)

Launch the entire system in seconds with a single command:

docker-compose up --build


Frontend: http://localhost:5173

Backend: http://localhost:8080

🛠️ Manual Installation

<details>
<summary><b>Click for Backend Steps</b></summary>

cd backend
./mvnw spring-boot:run


</details>

<details>
<summary><b>Click for Frontend Steps</b></summary>

cd frontend
npm install
npm run dev


</details>

📂 Project Structure

.
├── 🐳 docker-compose.yml  # Docker orchestration file
├── 📂 backend/            # Spring Boot Project (Java 17+)
├── 📂 frontend/           # React 18 & Vite Project
└── 📂 docs/               # Technical documentation
    └── 🖼️ screenshots/    # Application screenshots


🎨 Design Standards

Color Palette:

🔵 Primary: #2563EB (Trustworthy Blue)

🔴 High Risk: #EF4444 (Critical Alert)

🟢 Low Risk: #10B981 (Safe Flow)

Compatibility: 100% Responsive design across all device types.

👥 Developer & Event

Hackathon: Turkcell CodeNight 2026

Duration: ~3 Hours

Scope: 2500+ Lines of Code & Fully Dockerized

<p align="center">
<i>Protect your future, not just your data, with TrustShield.</i> 🛡️
<br />
<strong>© 2026 Turkcell CodeNight Project</strong>
</p>